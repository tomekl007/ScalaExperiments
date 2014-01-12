/**
 * Created with IntelliJ IDEA.
 * User: tomaszlelek
 * Date: 12/17/13
 * Time: 4:08 PM
 * To change this template use File | Settings | File Templates.
 */
object PartialExamples {
  class Bird
 /* class Cat {
    def capture(b:Bird) : Unit = {println("capture " + b)}
    def eat():Unit = { println("eat")}
  }*/

  class Cat
  //class Bird
  trait Catch
  trait FullStomach
  def capure(prey:Bird, hunter:Cat): Cat with Catch = { new Cat with Catch}
  def eat (consumer:Cat with Catch): Cat with FullStomach = { new Cat with FullStomach}


  def main(args: Array[String]) {
    /*val cat = new Cat
    val bird = new Bird
    cat.capture(bird)
    cat.eat()*/

    /*val story = (capure _, _) andThen ( eat(_ ))
    story(new Bird, new Cat)*/


  }

}
