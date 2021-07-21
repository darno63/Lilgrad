/**
 * Goal of this object is to test the interactivity of layers between eachother
 */

import scala.util.Random

object LayerTest {

  /**
   * dot multiplication on the input vector with every set of weights
   * @param inputs
   * @return
   */
  def Lfun(inputs: Vector[Int]): Int = {
    //var w = List.fill(inputs.length)(Random.nextInt(10))
    var w = List.fill(inputs.length)(Random.nextInt(10))
    val act: Int = (inputs zip w)
      .map({ case (a, b) => a * b })
      .reduce((a, b) => a + b)
    return if act > 0 then act else 0 // ReLU activation function
  }

  // create layers
  def Mlayer(inputs: Vector[Int]): Vector[Int] = Vector.fill(inputs.length)(Lfun(inputs))


  // print layer result
  Mlayer(Vector(1, 2, 3, 5)).foreach(println)

  val L1 = Mlayer
  val L2 = Mlayer
  val L3 = Mlayer
  val L4 = Mlayer
  val layers = List(L1, L2, L3, L4)

  val startVector = Vector(1, 2, 4, 5)

  val res = layers.foldLeft(startVector)((a, b) => b(a))
  res.foreach(println)
  //println(Seq(3, 4, 5).fold(4)((a, b) => a + b).toString)
}
