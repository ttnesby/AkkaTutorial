import akka.actor.{Actor, ActorLogging, Props}

/**
  * Created by torsteinnesby on 09/06/2017.
  */


//Companion object as factory
object IotSupervisor {
  def props(): Props = Props(new IotSupervisor)
}



class IotSupervisor extends Actor with ActorLogging {

  // invoked after the actor has started but before it processes its first message
  override def preStart(): Unit = log.info("IoT Application started")

  //invoked just before the actor stops. No messages are processed after this point
  override def postStop(): Unit = log.info("IoT Application stopped")

  // No need to handle any messages
  override def receive = Actor.emptyBehavior

}