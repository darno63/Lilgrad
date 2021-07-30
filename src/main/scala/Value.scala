import scala.collection.mutable.ArrayBuffer

/*
* @tparam T should be a Double or Double
 */
class Value(var data: Double, var children: ArrayBuffer[Value]=ArrayBuffer(), var op: String=""):
  var _prev: Set[Value] = children.toSet
  var grad: Double = 0
  var _backward = () => 1

  def +(other1: Value | Double): Value = {
    val other = other1 match { case v: Value => v case f: Double => Value(f) }
    val out = Value(this.data + other.data, ArrayBuffer(this, other), "+")

    def _backward(): Int = {
      this.grad += out.grad
      other.grad += out.grad
      return 1
    }
    out._backward = _backward
    return out
  }

  def *(other1: Value | Double): Value = {
    val other = other1 match { case v: Value => v case f: Double => Value(f) }
    val out = Value(this.data * other.data, ArrayBuffer(this, other), "*")

    def _backward(): Int = {
      this.grad += other.data * out.grad
      other.grad += this.data * out.grad
      return 1
    }
    out._backward = _backward
    return out
  }

  def pow(other1: Value | Double): Value = {
    val other = other1 match { case v: Value => v case f: Double => Value(f) }
    val out = Value(math.pow(this.data, other.data), ArrayBuffer(this, other), "**")

    def _backward(): Int = {
      this.grad += other.data * math.pow(this.data, other.data-1) * out.grad
      return 1
    }
    out._backward = _backward
    return out
  }

  def relu(): Value = {
    val out = Value(if this.data < 0 then 0 else this.data, ArrayBuffer(this), "ReLU")

    def _backward(): Int = {
      val res = if (out.data > 0) then out.grad else 0
      this.grad += res
      return 1
    }
    out._backward = _backward
    return out
  }

  def tanh(): Value = {
    val tanh = (math.exp(this.data * 2) - 1) / (math.exp(this.data * 2) + 1)
    val out = Value(tanh, ArrayBuffer(this), "tanh")

    def _backward(): Int = {
      this.grad += (1 - math.pow(tanh, 2))
      return 1
    }
    out._backward = _backward
    return out
  }

  def backward() = {
    val topo = ArrayBuffer[Value]()
    var visited = Set[Value]()

    def build_topo(v: Value): Unit = {
      if !visited.contains(v) then
        visited += v
        for (child <- v._prev) build_topo(child)
        topo.addOne(v)
    }
    build_topo(this)

    this.grad = 1
    for (v <- topo.reverseIterator) v._backward()
  }

  /** Returns the negation of this value */
  def unary_- = this * -1F

  def -(other1: Value | Double): Value = {
    val other = other1 match { case v: Value => v case f: Double => Value(f) }
    return this + (-other)
  }

  def /(other1: Value | Double): Value = {
    val other = other1 match { case v: Value => v case f: Double => Value(f) }
    return this * other.pow(-1F)
  }
