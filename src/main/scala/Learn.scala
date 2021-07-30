import com.github.tototoshi.csv._

import java.io.File
import scala.io._

object Learn {
  def loss(model: Network, inputs: List[List[Float]], y: List[Float]): (Value, Float) = {
    //val inputs = df.foreach(f => f.map(Value(_)))

    /** forward the model to get scores */
    val scores = inputs.map(model(_).last)

    /** svm "max-margin" loss */
    val losses = (scores zip y).map({ case (score, yi) => (score * -yi + 1F).relu()})
    //val losses = (scores zip y).map({ case (scores, yi) => scores.map(score => score * -yi.toFloat + 1F)})
    //val losses1 = (scores zip y).map({ case (a, b) => (a.last * -b.toFloat + 1F).relu().data})
    val dataLoss: Value = losses.reduce((a,b) => a + b) * (1F / losses.length.toFloat)
    /** L2 regularization */
    val alpha = 1e-4
    val regLoss = model.parameters.map(_.pow(2F)).reduce(_ + _)
    val totalLoss = dataLoss //+ regLoss

    /** also get accuracy */
    //val res = (scores zip y).filter({ case (score, y) => (score.data > 0) == (y > 0)})
    val acc = (scores zip y).filter({ case (score, y) => (score.data > 0) == (y > 0)}).length.toFloat / y.length.toFloat

    return  (totalLoss, acc)
  }

  def optimize(epochs: Int): Float = {
    /** import data */
    val (inputs, targets) = data()
    /** create model */
    val model = new Network(Vector(2, 16, 16, 1))

    var accuracy: Float = 0
    for k <- (0 until epochs) do {

      /** forward */
      val (totalLoss, acc) = loss(model, inputs, targets)

      /** backward */
      model.zeroGrad
      totalLoss.backward()

      /** update (sgd) */
      val learningRate = 0.3F //1F - 0.9F * k / 100
      val params = model.parameters
      model.parameters.foreach(p => p.data -= learningRate * p.grad)

      accuracy = acc * 100
      if k % 1 == 0 then println(s"step $k loss ${totalLoss.data}, accuracy ${acc * 100}")
    }
    return accuracy
  }

  def data(): (List[List[Float]], List[Float]) = {
    /** read csv */
    val reader = CSVReader.open(new File("datasets/ml_test.csv"))
    val df = reader.all()
    reader.close()

    val inputs = df.reverse.map(_.toVector.slice(1,3).toList.map(_.toFloat))
    val targets = df.reverse.map(_.last.toFloat * 2F - 1F)

    return (inputs, targets)
  }

  def data2(): (List[List[Float]], List[Float]) = {
    /** read csv */
    val reader = CSVReader.open(new File("datasets/wheat-seeds.csv"))
    val df = reader.all()
    reader.close()

    val inputs = df.reverse.map(_.toVector.slice(0,7).toList.map(_.toFloat))
    val targets = df.reverse.map(_.last.toFloat)

    return (inputs, targets)
  }
}