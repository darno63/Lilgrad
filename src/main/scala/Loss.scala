object Loss {
  def main: Unit = {
    val inputs = df.foreach(f => f.map(Value(_)))

    /** create model */
    val model = new Network(Vector(2, 4, 1))

    /** forward the model to get scores */
    val scores = df.map(x => model(x))

    /** svm "max-margin" loss */
    val losses = (scores zip y).map({ case (scores, yi) => scores.map(score => score * -yi.toFloat + 1F)})

    val losses1 = (scores zip y).map({ case (a, b) => (a.last * -b.toFloat + 1F).relu().data})
    losses1.foreach(println)
    scores.foreach(el => println(el.last.data))
  }

  val df: List[List[Float]] = List(
    List(2, 1), List(3, 1), List(5, 2)
  )
  val y: List[Int] = List(1, 0, 1)
}