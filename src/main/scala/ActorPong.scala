import ActorPong.Command
import akka.actor.{Actor, Props}
class ActorPong extends Actor {
  override def receive: Receive = {
    case Command.IsALive =>
    sender() ! scala.util.Random.nextBoolean()
    case Command.Pong =>
      println("Сообщение от актора ПИНГ получено, отправляю ПИНГ актору ПИНГ")

      sender() ! ActorPing.Command.Ping
  }
}

object ActorPong {
  val name = "ActorPong"

  def props(): Props = Props(new ActorPong())

  sealed trait Command

  // Внешнее состояние актора, на которое он реагирует(внешние команды)
  object Command {
    case object Pong extends Command
    case object IsALive extends Command
  }
}
