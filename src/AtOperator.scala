/**
 * Created with IntelliJ IDEA.
 * User: tomaszlelek
 * Date: 1/2/14
 * Time: 2:38 PM
 * To change this template use File | Settings | File Templates.
 */
object AtOperator {

  def main(args: Array[String]) {
    val o:Option[Int] = Some(2)

    //use @ if you want Option itself, not its content
    o match {
      case x @ Some(_) => println(x)
      case None =>
    }
    val d@(c@Some(a), Some(b)) = (Some(1), Some(2))
    println(d)
    println(c)
    println(a)
    println(b)


  }

}
