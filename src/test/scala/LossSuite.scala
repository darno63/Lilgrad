import org.scalatest.funsuite.AnyFunSuite
import Learn.loss

class LossSuite extends AnyFunSuite {

  test("Simple NN") {
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
    assert(inputs(0)(0) === 2F)
  }

  def optimize(model: Network, X: List[List[Float]], y: List[Float], epochs: Int) = {
    for k <- (0 until epochs) do {

      /** forward */
      val (totalLoss, acc) = loss(model, X, y)

      /** backward */
      model.zeroGrad
      totalLoss.backward()

      /** update (sgd) */
      val learningRate = 0.3F //1F - 0.9F * k / 100
      val params = model.parameters
      println("------")
      println(s"v1 : ${params(0).data.toString}, grad : ${params(0).grad.toString}")
      model.parameters.foreach(p => p.data -= learningRate * p.grad)
      println(s"after v1 : ${params(0).data.toString}")

      if k % 1 == 0 then println(s"step $k loss ${totalLoss.data}, accuracy ${acc * 100}")
    }
  }


}
