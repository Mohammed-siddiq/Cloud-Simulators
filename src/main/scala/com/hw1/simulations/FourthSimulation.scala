package com.hw1.simulations

import java.util.Calendar

import com.hw1.utils._
import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim._
import org.cloudbus.cloudsim.core.CloudSim
import org.slf4j.{Logger, LoggerFactory}

/**
  * A data center with two host , each host with 1 vm and 1 cloudlet each. [different hosts, same vm and same cloudlet]
  */

object FourthSimulation extends App {
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

  val host1: SimulatedHost = new SimulatedHost(4, 1,"SimulationValues")

  val dataCenter1: SimulatedDataCenter = new SimulatedDataCenter(4, 1,"SimulationValues")
  val dataCenter: Datacenter = dataCenterHelper.createDataCenter("DataCenter1", host1 :: Nil, dataCenter1)
  val dataCenterBroker: DatacenterBroker = dataCenterHelper.createBroker("FourthSimulationBroker")

  val brokerId = dataCenterBroker.getId

  // VM 1
  val sVm1: SimulatedVm = new SimulatedVm(4, 1,"SimulationValues")

  //vm2
  val sVm2: SimulatedVm = new SimulatedVm(4, 2,"SimulationValues")
  sVm1.brokerID = brokerId
  sVm2.brokerID = brokerId

  val vm1: Vm = dataCenterHelper.createVM(sVm1)
  val vm2: Vm = dataCenterHelper.createVM(sVm1)


  val vmList: List[Vm] = List(vm1, vm2)

  dataCenterBroker.submitVmList(myUtil.toJList(vmList))


  val simCloudLet1: SimulatedCloudlet = new SimulatedCloudlet(4, 1,"SimulationValues")
  val simCloudLet2: SimulatedCloudlet = new SimulatedCloudlet(4, 2,"SimulationValues")

  val cloudLet1: Cloudlet = dataCenterHelper.createCloudLet(simCloudLet1, new UtilizationModelFull)
  cloudLet1.setUserId(brokerId)
  //  cloudLet1.setVmId(sVm1.vmId)


  val cloudLet2: Cloudlet = dataCenterHelper.createCloudLet(simCloudLet2, new UtilizationModelFull)
  cloudLet2.setUserId(brokerId)
  //  cloudLet2.setVmId(sVm1.vmId)

  val cloudLetList: List[Cloudlet] = List(cloudLet1, cloudLet2)
  dataCenterBroker.submitCloudletList(myUtil.toJList(cloudLetList))

  dataCenterBroker.bindCloudletToVm(cloudLet1.getCloudletId, vm1.getId)
  dataCenterBroker.bindCloudletToVm(cloudLet2.getCloudletId, vm2.getId)


  CloudSim.startSimulation()
  CloudSim.stopSimulation()


  dataCenterHelper.printCloudLets(myUtil.toSList(dataCenterBroker.getCloudletReceivedList()))

}
