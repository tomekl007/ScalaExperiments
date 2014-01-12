/**
 * Created with IntelliJ IDEA.
 * User: tomaszlelek
 * Date: 12/3/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
import scala.collection.mutable.ArrayBuffer

object StackableTraitPattern {
  def main(args: Array[String]) {
    val q = new BasicIntQueue
    q.put(10)
    q.put(20)

    println(q.get())
    println(q.get())

    class MyQueue extends BasicIntQueue with Doubling

    val queue2 = new BasicIntQueue with Doubling
    //same
    val queue = new MyQueue
    queue.put(10)
    println("doubled " + queue.get())


    /*The order of mixins is significant. (Once a trait is mixed into a class, you can alternatively call it a mixin.)
    Roughly speaking, traits further to the right take effect first. When you call a method on a class with mixins, the
     method in the trait furthest to the right is called first. If that method calls super, it invokes the method in the next trait to its left, and so on.
     */
    val queueFirstFiltering = (new BasicIntQueue
       with Incrementing with Filtering)
    queueFirstFiltering.put(-1); queueFirstFiltering.put(0); queueFirstFiltering.put(1)
    println(queueFirstFiltering.get())

    val queueFirstIncrementing = (new BasicIntQueue
          with Filtering with Incrementing)
    queueFirstIncrementing.put(-1); queueFirstIncrementing.put(0); queueFirstIncrementing.put(1)

    println(queueFirstIncrementing.get())

  }

  abstract class IntQueue {
    def get(): Int
    def put(x: Int)
  }

  class BasicIntQueue extends IntQueue {
    private val buf = new ArrayBuffer[Int]
    def get() = buf.remove(0)
    def put(x: Int) { buf += x }
  }

  trait Doubling extends IntQueue {/*This declaration means that the trait can only be mixed into a class that also extends IntQueue.*/
    abstract override def put(x: Int) { super.put(2 * x) } /*means that the trait must be mixed into some class that has a concrete definition of the method in question.*/
  }

  trait Incrementing extends IntQueue {
    abstract override def put(x: Int) { super.put(x + 1) }
  }

  trait Filtering extends IntQueue {
    abstract override def put(x: Int) {
      if (x >= 0) super.put(x)
    }
  }

}
