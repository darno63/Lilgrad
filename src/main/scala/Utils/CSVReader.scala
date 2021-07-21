package Utils
import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import reflect.Selectable.reflectiveSelectable

object CSVReader {
  // each row is an array of strings (the columns in the csv file)
  val rows = ArrayBuffer[Array[String]]()

  // (1) read the csv data
  using(Source.fromFile("datasets/ml_test.csv")) { source =>
    for (line <- source.getLines) {
      rows += line.split(",").map(_.trim)
    }
  }

  // (2) print the results
  for (row <- rows) {
    println(s"${row(0)}|${row(1)}|${row(2)}|${row(3)}")
  }

  def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B =
    try {
      f(resource)
    } finally {
      resource.close()
    }
}