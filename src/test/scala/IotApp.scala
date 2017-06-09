import akka.actor.ActorSystem

import scala.io.StdIn

/**
  * Created by torsteinnesby on 09/06/2017.
  */

object IotApp {

  def main(args: Array[String]): Unit = {

    val system = ActorSystem("iot-system")

    try {
      // Create top level supervisor
      val supervisor = system.actorOf(IotSupervisor.props(), "iot-supervisor")

      // Exit the system after ENTER is pressed
      StdIn.readLine()
    } finally {
      system.terminate()
    }
  }

}
