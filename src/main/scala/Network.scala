import Learn.data

class Network(var nouts: Vector[Int]) extends Module {
  val layers = (0 until (nouts.length - 1)).map(x => {
    Layer(nouts(x), nouts(x + 1), if (x == nouts.length - 2) then false else true)
  })

  def parameters: Vector[Value] = this.layers.toVector.flatMap(_.parameters)

  def apply(inputs: List[Float]) = {
    this.layers.foldLeft(inputs.map(el => new Value(el)))((a,b) => b(a))
  }

  private def loss(inputs: List[List[Float]], y: List[Float]): (Value, Float) = {

    /** forward the model to get scores */
    val scores = inputs.map(this(_).last)

    /** svm "max-margin" loss */
    val losses = (scores zip y).map({ case (score, yi) => (score * -yi + 1F).relu()})
    val dataLoss: Value = losses.reduce((a,b) => a + b) * (1F / losses.length.toFloat)
    /** L2 regularization */
    val alpha = 1e-4
    val regLoss = this.parameters.map(_.pow(2F)).reduce(_ + _)
    val totalLoss = dataLoss //+ regLoss

    /** also get accuracy */
    val acc = (scores zip y).filter({ case (score, y) => (score.data > 0) == (y > 0)}).length.toFloat / y.length.toFloat

    return  (totalLoss, acc)
  }

  def fit(x: List[List[Float]], y: List[Float], epochs: Int): Float = {
    var accuracy: Float = 0
    for k <- (0 until epochs) do {

      /** forward */
      val (totalLoss, acc) = this.loss(x, y)

      /** backward */
      this.zeroGrad
      totalLoss.backward()

      /** update (sgd) */
      val learningRate = 0.3F //1F - 0.9F * k / 100
      val params = this.parameters
      this.parameters.foreach(p => p.data -= learningRate * p.grad)

      accuracy = acc * 100
      if k % 1 == 0 then println(s"step $k loss ${totalLoss.data}, accuracy ${acc * 100}")
    }
    return accuracy
  }

}
