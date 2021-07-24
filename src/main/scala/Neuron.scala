import scala.util.Random

class Neuron(nin: Int, nonlin: Boolean=true) extends Module {
  var w = (1 to nin).map(e => Value(Random.nextFloat))
  var b = Value(0)

  def parameters: Vector[Value] = this.w.toVector :+ this.b

  def apply(x: List[Value]): Value = {
    var act: Value = new Value(0)
    for ((wi, xi) <- this.w.zip(x)) act += wi*xi
    return if nonlin then act.relu() else act
  }
  
  def info(): String = {
    return s"${if this.nonlin then "ReLU" else "Linear"} Neuron(${this.w.length})"
  }
}
