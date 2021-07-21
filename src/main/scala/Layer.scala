class Layer(nin: Int, nout: Int) {
  val neurons: List[Neuron] = (1 to nout).toList.map(e => Neuron(nin))

  def apply(x: List[Value]): List[Value] = {
    val out = this.neurons.map(_(x))
    return out
  }

  def info() = {
    val sb = new StringBuilder()
    val msg: StringBuilder = this.neurons.map(_.info()).addString(sb, ", ")
    println(msg)
  }
}
