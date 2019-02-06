package com.hw1.simulations

import java.util.Calendar

import com.hw1.utils._
import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim._
import org.cloudbus.cloudsim.core.CloudSim
import org.slf4j.{Logger, LoggerFactory}



/**
  * Simulating: Two cloudlets on the same VM.
  * Cloudlet1 will have less instructions to be executed, while cloudlet2 will have high number of instructions
 */
object SecondSimulation extends App {
  val dataCenterHelper: DataCenterHelper = new DataCenterHelper();
  val myUtil: ConversionUtil = new ConversionUtil();


  val conf = ConfigFactory.load("SimulationsProp")

  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  logger.info("Starting Second simulation")

  val numberOfUsers: Int = 2
  val calendar: Calendar = Calendar.getInstance();
  val traceFlag: Boolean = false

  logger.info("Initialising cloudsim")
  CloudSim.init(numberOfUsers, calendar, traceFlag);

  val host1: SimulatedHost = new SimulatedHost(2, 1)
  val dataCenter1: SimulatedDataCenter = new SimulatedDataCenter(2, 1)
  val dataCenter: Datacenter = dataCenterHelper.createDataCenter("DataCenter1", host1, dataCenter1)
  val dataCenterBroker: DatacenterBroker = dataCenterHelper.createBroker("SecondSimulationBroker")

  val brokerId = dataCenterBroker.getId

  // VM 1
  val sVm1: SimulatedVm = new SimulatedVm(2, 1)
  sVm1.brokerID = brokerId
  val vm1: Vm = dataCenterHelper.createVM(sVm1)

  //vm2

  //  val sVm2: SimulatedVm = new SimulatedVm(2, 2)
  //  sVm2.brokerID = brokerId
  //  val vm2: Vm = dataCenterHelper.createVM(sVm2)

  val vmList: List[Vm] = List(vm1)

  dataCenterBroker.submitVmList(myUtil.toJList(vmList))


  val simCloudLet1: SimulatedCloudlet = new SimulatedCloudlet(1, 1)
  val simCloudLet2: SimulatedCloudlet = new SimulatedCloudlet(2, 1)

  val cloudLet1: Cloudlet = dataCenterHelper.createCloudLet(simCloudLet1, new UtilizationModelFull)
  cloudLet1.setUserId(brokerId)
  cloudLet1.setVmId(sVm1.vmId)


  val cloudLet2: Cloudlet = dataCenterHelper.createCloudLet(simCloudLet2, new UtilizationModelFull)
  cloudLet2.setUserId(brokerId)
  cloudLet2.setVmId(sVm1.vmId)

  val cloudLetList: List[Cloudlet] = List(cloudLet1, cloudLet2)
  dataCenterBroker.submitCloudletList(myUtil.toJList(cloudLetList))

  CloudSim.startSimulation()
  CloudSim.stopSimulation()


  dataCenterHelper.printCloudLets(myUtil.toSList(dataCenterBroker.getCloudletReceivedList()))


}