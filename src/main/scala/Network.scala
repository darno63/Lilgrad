class Network(var nouts: Vector[Int]) extends Module {
  val layers = (0 until (nouts.length - 1)).map(x => {
    Layer(nouts(x), nouts(x + 1), if (x == nouts.length - 2) then false else true)
  })

  def parameters: Vector[Value] = this.layers.toVector.flatMap(_.parameters)

  def apply(inputs: List[Float]) = {
    this.layers.foldLeft(inputs.map(el => new Value(el)))((a,b) => b(a))
  }

}
