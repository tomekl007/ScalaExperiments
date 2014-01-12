/**
 * Created with IntelliJ IDEA.
 * User: tomaszlelek
 * Date: 11/28/13
 * Time: 12:35 PM
 * To change this template use File | Settings | File Templates.
 */
object Testing {
  def main(args: Array[String]) {
      val text = "a a a b b"
      def wordCount(text: String) =
        text
          .split(" ")
          .map(a => (a, 1))
          .groupBy(_._1)
          .map { a => a._1 -> a._2.map(_._2).sum }
        println(wordCount(text))


    val data = "1" :: "2,2" :: "3,3,3" :: Nil
    val numbers = data flatMap { line =>
      line.split(",")
      .map(_.toInt)
    }// map { _.toInt }
    println(numbers)

    val data2 = 1::2::30::42::Nil
    val groups = data2 groupBy { _ < 10 }
    println(groups)




  }

}
