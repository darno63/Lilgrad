class Network(var nouts: Vector[Int]) {
  val layers = (0 until (nouts.length - 1)).map(x => {
    Layer(nouts(x), nouts(x + 1))
  })

  def apply(inputs: List[Float]) = {
    this.layers.foldLeft(inputs.map(el => new Value(el)))((a,b) => b(a))
  }

}
