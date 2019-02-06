package com.hw1.simulations

import java.util.Calendar

import com.hw1.utils._
import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim._
import org.cloudbus.cloudsim.core.CloudSim
import org.slf4j.{Logger, LoggerFactory}


/**
  * Simulation with a dataCenter with one host one vm and 2 cloudlets running in parallel
  *
  */

object ThirdSimulation extends App {
  val dataCenterHelper: DataCenterHelper = new DataCenterHelper();
  val myUtil: ConversionUtil = new ConversionUtil();


  val conf = ConfigFactory.load("SimulationsProp")

  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  logger.info("Starting third simulation")

  val numberOfUsers: Int = 1
  val calendar: Calendar = Calendar.getInstance();
  val traceFlag: Boolean = false

  logger.info("Initialising cloudSim")
  CloudSim.init(numberOfUsers, calendar, traceFlag);

  val host1: SimulatedHost = new SimulatedHost(3, 1)
  val dataCenter1: SimulatedDataCenter = new SimulatedDataCenter(3, 1)
  val dataCenter: Datacenter = dataCenterHelper.createDataCenter("DataCenter1", host1 :: Nil, dataCenter1)
  val dataCenterBroker: DatacenterBroker = dataCenterHelper.createBroker("ThirdSimulationBroker")

  val brokerId = dataCenterBroker.getId

  // VM 1
  val sVm1: SimulatedVm = new SimulatedVm(3, 1)
  sVm1.brokerID = brokerId
  val vm1: Vm = dataCenterHelper.createVM(sVm1)

  //  vm2

  //  val sVm2: SimulatedVm = new SimulatedVm(2, 2)
  //  sVm2.brokerID = brokerId
  //  val vm2: Vm = dataCenterHelper.createVM(sVm2)

  val vmList: List[Vm] = List(vm1)

  dataCenterBroker.submitVmList(myUtil.toJList(vmList))


  val simCloudLet1: SimulatedCloudlet = new SimulatedCloudlet(3, 1)
  val simCloudLet2: SimulatedCloudlet = new SimulatedCloudlet(3, 2)

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
