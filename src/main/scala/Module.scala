abstract class Module:
  def parameters: Vector[Value]

  def zeroGrad = this.parameters.foreach(_.grad = 0)
