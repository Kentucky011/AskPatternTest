import akka.actor._

object Boot extends App {
  val system = ActorSystem("MySystem")
  val pong = system.actorOf(ActorPong.props(), ActorPong.name)
  system.actorOf(ActorPing.props(pong), ActorPing.name)
}
