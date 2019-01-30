import java.util.{Calendar, LinkedList}
import org.cloudbus.cloudsim._
import org.cloudbus.cloudsim.core.CloudSim
import org.slf4j.{Logger, LoggerFactory}

object FirstSimulator extends App {


  val dataCenterHelper: DataCenterHelper = new DataCenterHelper();
  val myUtil: ConversionUtil = new ConversionUtil();


  val logger: Logger = LoggerFactory.getLogger(this.getClass)


  logger.debug("Starting my first simulation")


  val numberOfUsers: Int = 1;
  val calendar: Calendar = Calendar.getInstance();
  val traceFlag: Boolean = false

  logger.debug("Initialising cloudsim")
  CloudSim.init(numberOfUsers, calendar, traceFlag);


  val dataCenter: Datacenter = dataCenterHelper.createDataCenter("DataCenter1", Map("hostID" -> 1, "ramSize" -> 1024 * 2, "storage" -> 1024 * 4, "bandWidth" -> 256, "mips" -> 1000),
    Map("arch" -> "x86", "os" -> "Linux", "vmm" -> "Xen", "timeZone" -> 10.0, "cost" -> 3.0, "costPerMem" -> 0.05, "costPerStorage" -> 0.001, "costPerBw" -> 0.0))
  val dataCenterBroker: DatacenterBroker = dataCenterHelper.createBroker("First_Simulation_broker")


  val vm: Vm = dataCenterHelper.createVM(Map("vmId" -> 1, "brokerId" -> 1, "mips" -> 1000.00, "size" -> 10000L, "ram" -> 512, "bw" -> 1000L, "pesNumber" -> 1,
    "vmm" -> "Xen"))
  val vmList: List[Vm] = vm :: Nil

  dataCenterBroker.submitVmList(myUtil.toJList(vmList))


  val cloudLet: Cloudlet = dataCenterHelper.createCloudLet(Map("id" -> 1, "length" -> 400000, "fileSize" -> 300, "outputSize" -> 300,"pesNumber" -> 1), new UtilizationModelFull)

  val cloudLetList: List[Cloudlet] = cloudLet :: Nil
  dataCenterBroker.submitCloudletList(myUtil.toJList(cloudLetList))

  CloudSim.startSimulation()
  CloudSim.stopSimulation()


  dataCenterHelper.printCloudLets(myUtil.toSList(dataCenterBroker.getCloudletReceivedList()))


}
