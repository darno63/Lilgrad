object Gradtest {
  def svmtest() = {
    def svm(score: Value, target: Int) = {
      score * -target.toFloat + 1F
    }
  }

  def test1() = {
    val x = Value(-4.0)
    val z = x * 2F + 2F + x
    val q = z.relu() + z * x
    val h = (z.pow(2F)).relu()
    val y = h + q + q * x
    y.backward()

    println("----------------")
    val iter = Seq(x, z, q, h, y) zip Seq("x", "z", "q", "h", "y")
    for (i, name) <- iter do
      println(name + " - Value: " + i.data.toString + " - Grad: " + i.grad.toString)
    println("----------------")
  }

  def test2() = {
    val x = Value(0.244)
    val z = x * 2D + 2D + x
    val q = z.tanh() + z * x
    q.backward()

    println("----------------")
    val iter = Seq(x, z, q) zip Seq("x", "z", "q")
    for (i, name) <- iter do
      println(name + " - Value: " + i.data.toString + " - Grad: " + i.grad.toString)
    println("----------------")
  }
}
