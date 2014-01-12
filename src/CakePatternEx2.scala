/**
 * Created with IntelliJ IDEA.
 * User: tomaszlelek
 * Date: 12/3/13
 * Time: 2:25 PM
 * To change this template use File | Settings | File Templates.
 */
object CakePatternEx2 {


  // a dummy service that is not persisting anything
  // solely prints out info
  class UserRepository {
    def authenticate(user: User): User = {
      println("authenticating user: " + user)
      user
    }
    def create(user: User) = println("creating user: " + user)
    def delete(user: User) = println("deleting user: " + user)
  }
  class User(val unername:String, val pass:String)


  /*

class UserService {
  def authenticate(username: String, password: String): User =
    userRepository.authenticate(username, password)

  def create(username: String, password: String) =
    userRepository.create(new User(username, password))

  def delete(user: User) = All is statically typed.
  userRepository.delete(user)

  }  */

  trait UserRepositoryComponent {
    val userRepository = new UserRepository
    class UserRepository {
      def authenticate(user: User): User = {
        println("authenticating user: " + user)
        user
      }
      def create(user: User) = println("creating user: " + user)
      def delete(user: User) = println("deleting user: " + user)
    }
  }
}
