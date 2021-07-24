class Layer(nin: Int, nout: Int, nonlin: Boolean=true) extends Module {
  val neurons: Vector[Neuron] = (1 to nout).toVector.map(e => Neuron(nin, nonlin))

  def parameters: Vector[Value] = this.neurons.flatMap(_.parameters)

  def apply(x: List[Value]): List[Value] = {
    val out = this.neurons.map(_(x)).toList
    return out
  }

  def info() = {
    val sb = new StringBuilder()
    val msg: StringBuilder = this.neurons.map(_.info()).addString(sb, ", ")
    println(msg)
  }
}
