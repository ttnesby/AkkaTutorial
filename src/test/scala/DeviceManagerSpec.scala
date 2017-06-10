import akka.actor.ActorSystem
import akka.testkit.{TestKit, TestProbe}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}

/**
  * Created by torsteinnesby on 10/06/2017.
  */
class DeviceManagerSpec(_system: ActorSystem)
  extends TestKit(_system)
      with Matchers
      with FlatSpecLike
      with BeforeAndAfterAll {


  def this() = this(ActorSystem("iot-supervisor-test"))

  override def afterAll: Unit = {
    shutdown(system)
  }

  "DeviceManager actor" should "be able to register a device actor" in {
    val probe = TestProbe()
    val managerActor = system.actorOf(DeviceManager.props())

    managerActor.tell(DeviceManager.RequestTrackDevice("group", "device1"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)
    val deviceActor1 = probe.lastSender

    managerActor.tell(DeviceManager.RequestTrackDevice("group", "device2"), probe.ref)
    probe.expectMsg(DeviceManager.DeviceRegistered)
    val deviceActor2 = probe.lastSender

    deviceActor1 should !==(deviceActor2)

    // Check that the device actors are working
    deviceActor1.tell(Device.RecordTemperature(requestId = 0, 1.0), probe.ref)
    probe.expectMsg(Device.TemperatureRecorded(requestId = 0))

    deviceActor2.tell(Device.RecordTemperature(requestId = 1, 2.0), probe.ref)
    probe.expectMsg(Device.TemperatureRecorded(requestId = 1))
  }

}

