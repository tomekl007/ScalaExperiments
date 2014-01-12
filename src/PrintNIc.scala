import java.net.NetworkInterface
import scala.collection.JavaConversions._
import collection.JavaConverters._


/**
 * Created with IntelliJ IDEA.
 * User: tomaszlelek
 * Date: 12/19/13
 * Time: 1:38 PM
 * To change this template use File | Settings | File Templates.
 */
object PrintNIc {
  def main(args: Array[String]) {
    NetworkInterface
      .getNetworkInterfaces
      .flatMap(nic => Option(nic.getHardwareAddress))
      .map(_ map ("%02x" format _) mkString ":")
      .foreach(println(_))

    //or

    val nicaddresses = for {
      nic <- NetworkInterface.getNetworkInterfaces.asScala
      addrbytes <- Option(nic.getHardwareAddress)
    } yield {
      addrbytes map { "%02x" format _ } mkString ":"
    }
    nicaddresses foreach println
  }

}
