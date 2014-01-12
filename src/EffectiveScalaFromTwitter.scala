import java.net.{InetSocketAddress, SocketAddress, Socket}
import java.util.concurrent.{ConcurrentLinkedQueue, ConcurrentHashMap}
import scala.collection.mutable.HashSet
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits._

/**
 * Created with IntelliJ IDEA.
 * User: tomaszlelek
 * Date: 12/19/13
 * Time: 2:19 PM
 * To change this template use File | Settings | File Templates.
 */
object EffectiveScalaFromTwitter {
  trait Collection[+T] {
    def add[U >: T](other: U): Collection[U]
  }
  //dont do this
  trait HashSet[+T] {
    def add[U >: T](item: U)
  }
  trait Mammal
  trait Dog extends Mammal
  trait Cat extends Mammal

  class ConcurrentPool[K, V] {
    type Queue = ConcurrentLinkedQueue[V]
    type Map   = ConcurrentHashMap[K, Queue]

  }
  trait SocketFactory extends (SocketAddress => Socket)
  //better way
  type SocketFactoryT = SocketAddress => Socket

  def main(args: Array[String]) {

    val addrToInet: SocketAddress => Long = {
      (x:SocketAddress) =>
      1l
    }
    val inetToSocket: Long => Socket = {
      (l:Long) =>
        new Socket
    }
    val factory: SocketFactoryT = addrToInet andThen inetToSocket


    val votes = Seq(("scala", 1), ("java", 4), ("scala", 10), ("scala", 1), ("python", 10))

    val votesByLang = votes groupBy { case (lang, _) => lang }
    println(votesByLang)
    val sumByLang = votesByLang map { case (lang, counts) =>
      val countsOnly = counts map { case (_, count) => count }
      (lang, countsOnly.sum)
    }
    val orderedVotes = sumByLang.toSeq
      .sortBy { case (_, count) => count }
      .reverse
    println(orderedVotes)


    /*val dogs: HashSet[Dog] = HashSet[Dog]
    val mammals: HashSet[Mammal] = dogs
    mammals.add(new Cat{})*/

    //use java convert collections
    import scala.collection.JavaConverters._

    val list: java.util.List[Int] = Seq(1,2,3,4).asJava
    val buffer: scala.collection.mutable.Buffer[Int] = list.asScala

    def suffix(i: Int):String = {
      if      (i == 1) return "st"
      else if (i == 2) return "nd"
      else if (i == 3) return "rd"
      else             return "th"
    }
    //prefer:

    def suffix2(i: Int) =
      if      (i == 1) "st"
      else if (i == 2) "nd"
      else if (i == 3) "rd"
      else             "th"
    //but using a match expression is superior to either:

    def suffix3(i: Int) = i match {
      case 1 => "st"
      case 2 => "nd"
      case 3 => "rd"
      case _ => "th"
    }

   /* seq foreach { elem =>
      if (elem.isLast)
        return

      // process...
    }*/
    //this is implemented in bytecode as an exception catching/throwing
    // pair which, used in hot code, has performance implications !!!


    def fib(n: Int) = {
      require(n > 0)

    }

    /*val stream = getClass.getResourceAsStream("someclassdata")
    assert(stream != null)*/


    sealed trait Tree[T]
    case class Node[T](left: Tree[T], right: Tree[T]) extends Tree[T]
    case class Leaf[T](value: T) extends Tree[T]

    def findMin[T <: Ordered[T]](tree: Tree[T]):T = tree match {
      case Node(left, right) => Seq(findMin(left), findMin(right)).min
      case Leaf(value) => value
    }
  }

  /*def collect(results: List[String] = Nil): Future[List[String]] =
    doRpc() flatMap { result =>
      if (results.length < 9)
        collect(result :: results)
      else
        result :: results
    }

  collect() onSuccess { results =>
    printf("Got results %s\n", results.mkString(", "))
  }

  def doRpc():Future[List[String]] = {
           null
  }   */

  //Conditional execution on an Option value should be done with foreach; instead of

  val opt:Option[String] = Some("some")
  if (opt.isDefined)
    operate(opt.get)
  //write

  opt foreach { value =>
    operate(value)
  }
  def operate(s:String){
    println(s"operate on $s")
  }

  //getOrElse on list.head
  val list:List[Option[String]] = List(None)
  val x = list.headOption getOrElse ""

  val pf: PartialFunction[Int, String] = {
    case i if i%2 == 0 => "even"
  }

  val tf: (Int => String) = pf orElse { case _ => "odd"}


  tf(1) == "odd"
  tf(2) == "even"


  trait Publisher[T] {
    def subscribe(f: PartialFunction[T, Unit])
    var count = 0

  }

  /*val publisher: Publisher[Int] =
  publisher.subscribe {

    case i if (i == 1) => println("found prime", i)
    case i if i%2 == 0 => publisher.count += 2
    /* ignore the rest */
  } */

  // Attempt to classify the the throwable for logging.
  //type Classifier = Throwable => Option[java.util.logging.Level]

  /*type Classifier = PartialFunction[Throwable, java.util.logging.Level]

  val classifier1: Classifier
  val classifier2: Classifier

  val classifier = classifier1 orElse classifier2 orElse { x:Throwable => java.util.logging.Level.FINEST }*/

  //deconstructing
  val tuple = ('a', 1)
  val (char, digit) = tuple

  val tweet = Tweet("just tweeting", 1000)
  val Tweet(text, timestamp) = tweet

  case class Tweet(text:String, time:Int)

  def computation():String = {
    println("some expensive computation")
    "value"
  }
  lazy val field = computation()
  //is (roughly) short-hand for

  /*var _theField = None
  def field = if (_theField.isDefined) _theField.get else {
    _theField = Some(computation())
    _theField.get
  } */

  def something() = {
    println("calling something")
    1 // return value
  }

  def callByValue(x: Int) = {
    println("x1=" + x)
    println("x2=" + x)
  }

  def callByName(x: => Int) = {
    println("x1=" + x)
    println("x2=" + x)
  }
  //This is because call-by-value functions compute the passed-in expression's
  // value before calling the function, thus the same value is accessed every time.
  // However, call-by-name functions recompute the passed-in expression's value every time it is accessed.

  println("callByValue")
  callByValue(something())

  println("callByName")
  callByName(something())

  val chars = 'a' to 'z'
  val perms = chars flatMap { a =>
    chars flatMap { b =>
      if (a != b) Seq("%c%c".format(a, b))
      else Seq()
    }
  }
  println(perms)
  //which is equivalent to the more concise for-comprehension (which is — roughly — syntactical sugar for the above):
  val perms2 = for {
    a <- chars
    b <- chars
    if a != b
  } yield "%c%c".format(a, b)
  println(perms2)

  val host: Option[String] = Some("localhost")
  val port: Option[Int] = Some(8080)

  val addr: Option[InetSocketAddress] =
    host flatMap { h =>
      port map { p =>
        new InetSocketAddress(h, p)
      }
    }
  println(addr)

  val addr2: Option[InetSocketAddress] = for {
    h <- host
    p <- port
  } yield new InetSocketAddress(h, p)

  println(addr2)

  /*
  trait TweetStream {
    def subscribe(f: Tweet => Unit)
  }
  class HosebirdStream extends TweetStream {}
  class FileStream extends TweetStream {}

  class TweetCounter(stream: TweetStream) {
    stream.subscribe { tweet => count += 1 }
  }


  class FilteredTweetCounter(mkStream: Filter => TweetStream) {
    mkStream(PublicTweets).subscribe { tweet => publicCount += 1 }
    mkStream(DMs).subscribe { tweet => dmCount += 1 }
  }*/

  //function composition
  /* (g∘f)(x) = g(f(x)) — the result of applying x to f first,
  and then the result of that to g — can be written in Scala:*/

  val f = (i: Int) => i.toString
  val g = (s: String) => s+s+s
  val h = g compose f  // : Int => String
  println(h(123))

  val result: Future[Int] = Future { 2 }
  val resultStr: Future[String] = result map { i => i.toString }
  val listOfList: List[List[Int]] = List(List(2))
  val listF: List[Int] = listOfList.flatten


  abstract class Term
  case class Var(name: String) extends Term
  case class Fun(arg: String, body: Term) extends Term
  case class App(f: Term, v: Term) extends Term

  def printTerm(term: Term) {
    term match {
      case Var(n) =>
        print(n)
      case Fun(x, b) =>
        print("^" + x + ".")
        printTerm(b)
      case App(f, v) =>
        Console.print("(")
        printTerm(f)
        print(" ")
        printTerm(v)
        print(")")
    }
  }



    def isIdentityFun(term: Term): Boolean = term match {
      case Fun(x, Var(y)) if x == y => true
      case _ => false
    }
    val id = Fun("x", Var("x"))
    val t = Fun("x", Fun("y", App(Var("x"), Var("y"))))
    printTerm(t)
    println
    println(isIdentityFun(id))
    println(isIdentityFun(t))


}




package object net {
  type SocketFactory = (SocketAddress) => Socket
}
