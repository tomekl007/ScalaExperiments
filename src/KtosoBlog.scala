/**
 * Created with IntelliJ IDEA.
 * User: tomaszlelek
 * Date: 1/9/14
 * Time: 10:32 AM
 * To change this template use File | Settings | File Templates.
 */
// remember, that we have to enable this language feature by importing it!
import scala.language.dynamics

object KtosoBlog {
  def main(args: Array[String]) {
    //immutable structures are covariant
    class Fruit
    case class Apple() extends Fruit
    case class Orange() extends Fruit

    val l1: List[Apple] = Apple() :: Nil
    val l2: List[Fruit] = Orange() :: l1

    // and also, it's safe to prepend with "anything",
    // as we're building a new list - not modifying the previous instance

    val l3: List[AnyRef] = "" :: l2



    //mutable structures like Array are invariant, if type is defined to Any, then you can not put
    //here Int althought it is subtype of Any
    // won't compile
    //val a: Array[Any] = Array[Int](1, 2, 3)


    class Base { def b = "bit" }
    trait Cool { def c = "con" }
    trait Awesome { def a ="awe" }

    class BA extends Base with Awesome
    class BC extends Base with Cool

    // as you might expect, you can upcast these instances into any of the traits they've mixed-in
    val ba: BA = new BA
    val bc: Base with Cool = new BC

    val b1: Base = ba
    val b2: Base = bc

    ba.a
    bc.c
    b1.b

    trait A { def common = "A" }

    trait B extends A { override def common = "B" }
    trait C extends A { override def common = "C" }

    class D1 extends B with C
    class D2 extends C with B

    /*In the case of D1, the "rightmost" trait providing an implementation of common
    is C, so it’s overriding the implementation provided by B.
    The result of calling common inside D1 would be "c
     */

    assert((new D1).common == "C")

    assert((new D2).common == "B")

    type User = String
    type Age = Int

    val data:  Map[User, Age] =  Map.empty


    trait SimplestContainer {
      type A      // Abstract Type Member

      def value: A
    }

    object IntContainer extends SimplestContainer {
      type A = Int

      def value = 42
    }


    /* it does not work
    // naive impl, Fruit is NOT self-recursively parameterised

    trait Fruit2 {
      final def compareTo(other: Fruit2): Boolean = true // impl doesn't matter in our example, we care about compile-time
    }

    class Apple2  extends Fruit2
    class Orange2 extends Fruit2

    val apple2 = new Apple2()
    val orange2 = new Orange2()

    apple2 compareTo orange2 // compiles, but we want to make this NOT compile!


    def solution  = {
      trait Fruit[T <: Fruit[T]] {
        final def compareTo(other: Fruit[T]): Boolean = true // impl doesn't matter in our example
      }

      class Apple  extends Fruit[Apple]
      class Orange extends Fruit[Orange]

      val apple = new Apple
      val orange = new Orange
      orange compareTo orange[Orange]
    }
    solution*/

    /*case class Meter(value: Double) extends AnyVal {
        def toFeet: Foot = Foot(value * 0.3048)
    }

    case class Foot(value: Double) extends AnyVal {
      def toMeter: Meter = Meter(value / 0.3048)
    } */
  }

  class ServiceInModule(){
    def doTheThings() = { println("do some things")}
  }

  trait Module {
    lazy val serviceInModule = new ServiceInModule
  }

  trait Service {
    this: Module =>

    def doTheThings() = serviceInModule.doTheThings()
  }

  type OpenerCloser = {
    def open(): Unit
    def close(): Unit
  }

  def on(it: OpenerCloser)(fun: OpenerCloser => Unit) = {
    it.open()
    fun(it)
    it.close()
  }

  // our example class structure
  class Outer {
    class Inner
  }

  // Type Projection (and alias) refering to Inner
  type OuterInnerProjection = Outer#Inner

  val out1 = new Outer
  val out1in = new out1.Inner


  // TODO: Has missing implementation
  class Json(s: String) extends Dynamic {

  }

  val jsonString = """
  {
    "name": "Konrad",
    "favLangs": ["Scala", "Go", "SML"]
  }
                   """

  val json = new Json(jsonString)

 // val name: Option[String] = json.name
  // will compile (once we implement)!

  // applyDynamic example
  object OhMy extends Dynamic {
    def applyDynamic(methodName: String)(args: Any*) {
      println(s"""|  methodName: $methodName,
                |args: ${args.mkString(",")}""".stripMargin)
    }
  }

  OhMy.dynamicMethod("with", "some", 1337)

  // applyDynamicNamed example
  object JSON extends Dynamic {
    def applyDynamicNamed(name: String)(args: (String, Any)*) {
      println(s"""Creating a $name, for:\n "${args.head._1}": "${args.head._2}" """)
    }
  }

  JSON.node(nickname = "ktoso")

  /*class Json(s: String) extends Dynamic {
    def selectDynamic(name: String): Option[String] =
      parse(s).get(name)
  }*/

  object MagicBox extends Dynamic {
    private var box = scala.collection.mutable.Map[String, Any]()

    def updateDynamic(name: String)(value: Any) { box(name) = value }
    def selectDynamic(name: String) = box(name)
  }
  /*class O {
    def m() = "stirng";
  } */

  MagicBox.banana = "banana"
  println(MagicBox.banana)
  MagicBox.obj = 12
  //MagicBox.unknown

  trait PubSubAction
  //scala existential types
  type PubSubActionSubclass = T forSome { type T <: PubSubAction }
  type SalatObject = T forSome { type T <: AnyRef }
  //val mot = Manifest.classType[SalatObject]

  /*object PubSubAction {
    def makeDAO[PubSubActionSubclass](msg: PubSubActionSubclass)
                                     (implicit coll: MongoCollection) = {
      val mot = Manifest.classType[SalatObject](msg.getClass)
      val mid = Manifest.classType[Int](classOf[Int])
      new SalatDAO(coll)(mot, mid, ctx){}
    }
  } */

}
