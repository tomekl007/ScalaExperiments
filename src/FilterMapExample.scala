/**
 * Created with IntelliJ IDEA.
 * User: tomaszlelek
 * Date: 12/19/13
 * Time: 2:01 PM
 * To change this template use File | Settings | File Templates.
 */
object FilterMapExample {


  def main(args: Array[String]) {
  // An implementation of filterMap which can be applied uniformly to things which are, or which are
  // convertible to, a GenTraversable (ie. standard collections, but also Arrays and Strings).
  //
  // The result type of this implementation of filterMap will be the most precise possible, in the
  // same way that you would expect for plain map on the same receiver.
  //
  // The ElementType stuff is a workaround for deficiencies in Scala's type inference, and it
  // requires -Ydependent-method-types or scala 2.10-SNAPSHOT.

  import scala.collection.GenTraversable
  import scala.collection.generic.CanBuildFrom
  import scala.collection.mutable.Builder

  class FilterMap[CC, A](cc : CC)(implicit ev : CC => GenTraversable[A]) {
    def filterMap[B, That](f : A => Option[B])(implicit cbf : CanBuildFrom[CC, B, That]) : That = {
      val builder: Builder[B, That] = cbf()
      cc foreach { x => val y = f(x); if (y.nonEmpty) builder += y.get }
      builder.result
    }
  }

  trait ElementType[CC] {
    type E
    val ev : CC => GenTraversable[E]
  }

  implicit def elementType[A, CC[_]](implicit ev0 : CC[A] => GenTraversable[A]) = new ElementType[CC[A]] {
    type E = A
    val ev = ev0
  }

  implicit val stringElementType = new ElementType[String] {
    type E = Char
    val ev = implicitly[String => GenTraversable[Char]]
  }

  implicit def filterMap[CC, A](c : CC)(implicit et : ElementType[CC]) = new FilterMap[CC, et.E](c)(et.ev)

  val l = List(1, 2, 3, 4, 5)
  val a = Array(1, 2, 3, 4, 5)
  val s = "Hello World"

  def even(i : Int) = if(i % 2 == 0) Some(i) else None
  def evenc(c : Char) = if(c % 2 == 0) Some(c.toInt) else None
  def upper(c : Char) = if(c >= 'A' && c <= 'Z') Some(c) else None

  val fml = l.filterMap(even)   // type is List[Int]
  assert(fml == List(2, 4))

  val fma:Array[Int] = a.filterMap(even)   // type is Array[Int]
  //fma : Array[Int]
  assert(fma sameElements Array(2, 4))

  val fms1 = s.filterMap(upper) // type is String
  assert(fms1 == "HW")

  val fms2 = s.filterMap(evenc) // type is IndexedSeq[Int]
  assert(fms2 == IndexedSeq(72, 108, 108, 32, 114, 108, 100))

  }

}
