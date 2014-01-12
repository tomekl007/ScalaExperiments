/**
 * Created with IntelliJ IDEA.
 * User: tomaszlelek
 * Date: 12/19/13
 * Time: 1:41 PM
 * To change this template use File | Settings | File Templates.
 */
object FlatMapExplanation {

  //def f(x: ) = if  Some(v) else None

  def main(args: Array[String]) {

    val l  = List(List(1,2,3), List(2,3,4))

    println(l.map(_.toString)) // changes type from list to string
    // prints List(List(1, 2, 3), List(2, 3, 4))

    println(l.flatMap(x => x)) // "changes" type list to iterable
    // prints List(1, 2, 3, 2, 3, 4)

    //flatMap will reduce Option(none)


    val list = List[Option[String]](Some("first"), None, Some("third"))
    println(list.map(_.toString))
    println(list.flatMap{
      case Some(x) => x
      case None => ""

    })
    println(list.flatten)
    val listOfLists = List[List[String]]( List("a", "b"), List("c", "d"))
    println(listOfLists.map(_.toString()))
    println(listOfLists.flatten)


  }

}
