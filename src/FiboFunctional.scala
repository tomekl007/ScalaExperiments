/**
 * Created with IntelliJ IDEA.
 * User: tomaszlelek
 * Date: 12/19/13
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
object FiboFunctional {

  val fibs:Stream[BigInt] =
    0 #::
      1 #::
      (fibs zip fibs.tail).map{ case (a,b) => a+b }

  def main(args: Array[String]) {

    println(fibs(1000))
  }

}
