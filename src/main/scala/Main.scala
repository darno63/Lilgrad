import Utils.CSVReader

@main def hello: Unit = {
  //Gradtest.test1()
  /** Testing on micrograd data */
  //Learn.optimize(100)
  //LossTest.test
  /** Testing on wheat seeds data */
  val (df, targets) = Learn.data2()
  val newdf = df.transpose.map(Data.normalize)
  for i <- (0 until 7) do {
    println("-------")
    println(s"min: ${df.transpose.toVector(i).min}, max: ${df.transpose.toVector(i).max}")
    println(s"None: ${df.transpose.toVector(i).toVector.slice(0,10)}")
    println(s"Normalized: ${newdf.toVector(i).toVector.slice(0,10)}")
    //println(newdf.last.toVector(i))
  }
  /*
  val (inputs, targets) = Learn.data()
  inputs.foreach(_.foreach(println))
  targets.foreach(println)
   */
}


def msg = "I was compiled by Scala 3."
val n1: Neuron = new Neuron(4)
val l1: Layer = new Layer(4, 5)

val x1 = Value(3) / 2F
val x2 = Value(3).pow(3F)
val x3 = -Value(2)
val x4 = x1 + x2 * x3