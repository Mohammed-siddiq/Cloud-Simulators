package com.hw1.simulations.MiscellaneousSimulations

import java.util.Calendar

import com.hw1.utils._
import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim._
import org.cloudbus.cloudsim.core.CloudSim
import org.slf4j.{Logger, LoggerFactory}

object Simulation1 {


  def runSimulation = {

    val dataCenterHelper: DataCenterHelper = new DataCenterHelper();
    val myUtil: ConversionUtil = new ConversionUtil();


    val conf = ConfigFactory.load("SimulationsProp")

    val logger: Logger = LoggerFactory.getLogger(this.getClass)

    logger.info("Starting miscellaneous simulation 1")


    val numberOfUsers: Int = 1
    val calendar: Calendar = Calendar.getInstance();
    val traceFlag: Boolean = false

    logger.info("Initialising cloudsim")
    CloudSim.init(numberOfUsers, calendar, traceFlag);

    val host1: SimulatedHost = new SimulatedHost(1, 1, "Miscellaneous")

    val dataCenter1: SimulatedDataCenter = new SimulatedDataCenter(1, 1, "Miscellaneous")

    val dataCenter: Datacenter = dataCenterHelper.createDataCenter("DataCenter1", host1 :: Nil, dataCenter1)
    val dataCenterBroker: DatacenterBroker = dataCenterHelper.createBroker("First_Simulation_broker")


    val brokerId = dataCenterBroker.getId


    val vm1: SimulatedVm = new SimulatedVm(1, 1, "Miscellaneous")
    vm1.brokerID = brokerId
    val vm: Vm = dataCenterHelper.createVM(vm1)
    val vmList: List[Vm] = List(vm)

    dataCenterBroker.submitVmList(myUtil.toJList(vmList))


    val cloudlet1: SimulatedCloudlet = new SimulatedCloudlet(1, 1, "Miscellaneous")

    val cloudLet: Cloudlet = dataCenterHelper.createCloudLet(cloudlet1, new UtilizationModelFull)
    cloudLet.setUserId(brokerId)
    cloudLet.setVmId(1)

    val cloudLetList: List[Cloudlet] = List(cloudLet)
    dataCenterBroker.submitCloudletList(myUtil.toJList(cloudLetList))

    CloudSim.startSimulation()

    CloudSim.stopSimulation()

    dataCenterHelper.printCloudLets(myUtil.toSList(dataCenterBroker.getCloudletReceivedList()))

    logger.info("Overall Cost for this simulation - " + cloudLetList.map(dataCenterHelper.getOverallCost).sum)


  }

}
