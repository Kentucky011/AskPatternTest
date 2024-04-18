import ActorPing.{Command, Event}
import akka.actor._
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success}

class ActorPing(actorPong: ActorRef) extends Actor {
  implicit val timeout = Timeout(3.seconds)
  implicit val ec = context.dispatcher

  override def preStart(): Unit = {
    self ! Event.Start

  }

  override def receive: Receive = {
    case Event.Start =>
      println("Отправляю сообщение актору понг")
      actorPong.ask(ActorPong.Command.IsALive).mapTo[Boolean].map { isALive =>
        if (isALive) {
          actorPong ! ActorPong.Command.Pong
        } else {
          Thread.sleep(500)
          self ! Event.Start
        }
      }
    case Command.Ping =>
      println("Сообщение от актора ПОНГ получено, отправляю ПОНГ актору ПОНГ")
      actorPong ! ActorPong.Command.Pong
    case _ => println("test")

  }

}

object ActorPing {
  val name = "ActorPing"

  def props(actorPong: ActorRef): Props = Props(new ActorPing(actorPong))

  sealed trait Event

  // внутреннее состояние актора(внутренние команды)
  object Event {
    case object Start extends Event
  }

  sealed trait Command

  // Внешнее состояние актора, на которое он реагирует(внешние команды)
  object Command {
    case object Ping extends Command
  }
}
