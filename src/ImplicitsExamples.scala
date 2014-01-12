import scala.math.Ordering

/**
 * Created with IntelliJ IDEA.
 * User: tomaszlelek
 * Date: 12/19/13
 * Time: 11:38 AM
 * To change this template use File | Settings | File Templates.
 */
object ImplicitsExamples {

  class A(val n: Int) {
    def +(other: A) = new A(n + other.n)
    override def toString():String =  String.valueOf(n)
  }
  object A {
    implicit def fromInt(n: Int) = new A(n)
  }

  def main(args: Array[String]) {
    // This becomes possible:
    1 + new A(1)
    // because it is converted into this:
    println(A.fromInt(1) + new A(1))

    //implicit def ord: Ordering[A] =  { def compare(o1: A, o2: A) = o1.n - o2.n }

    //println(List(new A(5), new A(2)).sorted)
    (1 to 5) foreach println
    (1 to 5).par foreach println

    (1 to 3) filter(_ > 0) map(_ * 2) foreach println
  }

}
