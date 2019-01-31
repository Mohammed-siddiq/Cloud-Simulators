package com.hw1.simulations

import java.util.Calendar

import com.hw1.utils.{ConversionUtil, DataCenterHelper}
import org.cloudbus.cloudsim._
import org.cloudbus.cloudsim.core.CloudSim
import org.slf4j.{Logger, LoggerFactory}
import com.typesafe.config.ConfigFactory


object FirstSimulator extends App {


  val dataCenterHelper: DataCenterHelper = new DataCenterHelper();
  val myUtil: ConversionUtil = new ConversionUtil();

  import com.typesafe.config.Config
  import com.typesafe.config.ConfigFactory

  val conf = ConfigFactory.load("SimulationsProp")

  val logger: Logger = LoggerFactory.getLogger(this.getClass)


  logger.info("Starting my first simulation")


  val numberOfUsers: Int = 1
  val calendar: Calendar = Calendar.getInstance();
  val traceFlag: Boolean = false

  logger.debug("Initialising cloudsim")
  CloudSim.init(numberOfUsers, calendar, traceFlag);
  val hostParams: Map[String, Int] = Map(conf.getString("hostKeys.id") -> 1, "ramSize" -> 1024 * 2, "storage" -> 1000000, "bandWidth" -> 10000, "mips" -> 1000)
  val dataCenterParams: Map[String, Any] = Map("arch" -> "x86", "os" -> "Linux", "vmm" -> "Xen", "timeZone" -> 10.0, "cost" -> 3.0, "costPerMem" -> 0.05, "costPerStorage" -> 0.001, "costPerBw" -> 0.0)

  val dataCenter: Datacenter = dataCenterHelper.createDataCenter("DataCenter1", hostParams, dataCenterParams)
  val dataCenterBroker: DatacenterBroker = dataCenterHelper.createBroker("First_Simulation_broker")

  val brokerId = dataCenterBroker.getId

  val vmParams: Map[String, Any] = Map("vmId" -> 1, "brokerId" -> brokerId, "mips" -> 1000.00, "size" -> 10000L, "ram" -> 512, "bw" -> 1000L, "pesNumber" -> 1,
    "vmm" -> "Xen")

  val vm: Vm = dataCenterHelper.createVM(vmParams)
  val vmList: List[Vm] = List(vm)

  dataCenterBroker.submitVmList(myUtil.toJList(vmList))


  val cloudLetParams: Map[String, Int] = Map("id" -> 1, "length" -> 400000, "fileSize" -> 300, "outputSize" -> 300, "pesNumber" -> 1)
  val cloudLet: Cloudlet = dataCenterHelper.createCloudLet(cloudLetParams, new UtilizationModelFull)
  cloudLet.setUserId(brokerId)
  cloudLet.setVmId(1)

  val cloudLetList: List[Cloudlet] = List(cloudLet)
  dataCenterBroker.submitCloudletList(myUtil.toJList(cloudLetList))

  CloudSim.startSimulation()
  CloudSim.stopSimulation()


  dataCenterHelper.printCloudLets(myUtil.toSList(dataCenterBroker.getCloudletReceivedList()))


}
