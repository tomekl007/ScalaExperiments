import java.util.concurrent.Callable

/**
 * Created with IntelliJ IDEA.
 * User: tomaszlelek
 * Date: 12/27/13
 * Time: 10:47 AM
 * To change this template use File | Settings | File Templates.
 */
object ImplicitsLecture {

  trait Specification { object spec {
    def that(name : String) = new {
      def in[A](f : => A) = "Spec " + name + " " + f
    } }
    trait Matcher[A]
    def behavior[A](f : => A) = new {
      def must(x : Matcher[A]) = null }
    def beMatching[A](f : => A) = new Matcher[A] {} }
}
          /*
object fromJava {

import java.util.concurrent.{Executor => JExecutor}
trait Executor[A] {
  protected val native : JExecutor
  def submit[A](f : () => A) = {
    native.submit(new Runnable[A] {
      def run() = f()
    })
  }
 }
object Executor {
  implicit def unwrap(e : Executor) = e.native
  def apply(j : JExecutor) = new Executor {
    override val native = j }
  }

}      */
