import Utils.CSVReader

@main def hello: Unit = {
  println("Hello world!")
  Gradtest.svmtest()
  //Loss.main
  //LayerTest //test potential layer folding
  /*
  println(x1.data)
  println(x2.data)
  println(x3.data)
  println(x4.backward())
  println(x4._backward())
  println(x4.grad)
  x4._prev.map(x => print(x.toString + "\n"))
   */
}


def msg = "I was compiled by Scala 3."
val n1: Neuron = new Neuron(4)
val l1: Layer = new Layer(4, 5)

val x1 = Value(3) / 2F
val x2 = Value(3).pow(3F)
val x3 = -Value(2)
val x4 = x1 + x2 * x3