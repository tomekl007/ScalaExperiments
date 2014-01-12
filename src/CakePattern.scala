/**
 * Created with IntelliJ IDEA.
 * User: tomaszlelek
 * Date: 12/3/13
 * Time: 11:39 AM
 * To change this template use File | Settings | File Templates.
 */
object CakePattern {

  trait FooAble{
    def foo():String
  }
  trait MyFooAble extends FooAble {
    override def foo():String = "foo impl"
  }

  class BarUsingFooAble {
    this: FooAble => //it means that this class declares that it will eventually extend FooAble somehow
    def bar() = "bar calls foo: " + foo() //see note #2
  }

    def main(args: Array[String]) {
      val barWithFoo = new BarUsingFooAble with MyFooAble//see note #3
      println(barWithFoo.bar())

      val barWithFoo2 =  new BarUsingFooAble2 with MyFooAble with BazAble
      println(barWithFoo2.bar())

      val barWithFoo3 = new BarUsingFooAble3 with FooAbleComponent with BazAbleComponent {
        val bazAble = new BazAble() //or any other implementation
        val fooAble = new FooAble() //or any other implementation
      }
      println(barWithFoo3.bar())
    }

  trait BazAble{
    def baz() = "baz too"
  }

  class BarUsingFooAble2 {
    this: FooAble with BazAble =>
    def bar() = s"bar calls foo: ${foo()} and baz: ${baz()}"
  }

  trait FooAbleComponent {
    val fooAble: FooAble
    class FooAble {
      def foo() = "here is your foo"
    }
  }

  trait BazAbleComponent {
    val bazAble: BazAble
    class BazAble {
      def baz() = "baz too"
    }
  }

  class BarUsingFooAble3 {
    this: FooAbleComponent with BazAbleComponent =>
    def bar() = s"bar calls foo: ${fooAble.foo()} and baz: ${bazAble.baz()}"
  }


}
