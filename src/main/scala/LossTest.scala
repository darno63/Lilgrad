import Learn.loss

object LossTest {
  def test = {
    /** import data */
    //val (inputs, targets) = data()
    val inputs: List[List[Float]] = List(
      List(1F, 3F), List(2F, 2F),
      List(3F, 2F), List(4F, 3F),
      List(2F, 4F), List(2F, 3F),
      List(3F, 3F), List(3F, 4F),
    )
    val targets = List(-1F, -1F, -1F, -1F, 1F, 1F, 1F, 1F)

    /** create model */
    val model = new Network(Vector(2, 4, 4, 1))

    /** set weights */
    val w1 = List(IndexedSeq(-0.6697383 , -0.41612196), IndexedSeq( 0.80296254,  0.28500414),
                  IndexedSeq( 0.26194835,  0.951571  ), IndexedSeq(-0.13090777, -0.12980103))

    val w2 = IndexedSeq( 0.02021408, -0.1129365 , -0.1829338 ,  0.98498464)

    for (n, i) <- w1.zipWithIndex do {
      model.layers(0).neurons(i).w = n.map(w => Value(w.toFloat))// (j) = Value(w.toFloat)
      model.layers(1).neurons(i).w = n.map(w => Value(w.toFloat))// (j) = Value(w.toFloat)
    }

    model.layers(2).neurons(0).w = w2.map(w => Value(w.toFloat))

    model.parameters.foreach(v => println(v.data))
    optimize(model, inputs, targets, 1000)
    //model.parameters.foreach(v => println(v.data))
    model.layers(0).neurons(0).w.foreach(v => println(v.data))
  }

  def optimize(model: Network, X: List[List[Float]], y: List[Float], epochs: Int) = {
    for k <- (0 until epochs) do {

      /** forward */
      val (totalLoss, acc) = loss(model, X, y)

      /** backward */
      model.zeroGrad
      totalLoss.backward()

      /** update (sgd) */
      val learningRate = 0.05F //1F - 0.9F * k / 100
      val params = model.parameters
      //println("------")
      //println(s"v1 : ${params(0).data.toString}, grad : ${params(0).grad.toString}")
      model.parameters.foreach(p => p.data -= learningRate * p.grad)
      //println(s"after v1 : ${params(0).data.toString}")

      if k % 1 == 0 then println(s"step $k loss ${totalLoss.data}, accuracy ${acc * 100}")
    }
  }


}
