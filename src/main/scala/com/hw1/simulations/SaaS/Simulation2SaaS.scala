package com.hw1.simulations.SaaS

import java.util.Calendar

import com.hw1.utils._
import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim._
import org.cloudbus.cloudsim.core.CloudSim
import org.slf4j.{Logger, LoggerFactory}

/**
  * Software as a Service (Saas): As the customer is unaware of the underlying hardware/resources ,
  * this simulation runs the exact same cloudlet(a service that runs continously - for a long time in the simulation).
  * As a cloud broker, to find the right set of hardware/resources on which to run this software,
  * couple of ploicies are implemented with different VM's and different providers running the same cloudlets,
  * thereby the right/optimal provider can be chosen to run the SaaS.
  *
  * This is a second policy simulation for SaaS
  */
object Simulation2SaaS {

  def runSimulation = {

    val dataCenterHelper: DataCenterHelper = new DataCenterHelper();
    val myUtil: ConversionUtil = new ConversionUtil();


    val conf = ConfigFactory.load("SimulationsProp")

    val logger: Logger = LoggerFactory.getLogger(this.getClass)

    logger.info("Starting SaaS simulation 2")


    val numberOfUsers: Int = 1
    val calendar: Calendar = Calendar.getInstance();
    val traceFlag: Boolean = false

    logger.info("Initialising")
    CloudSim.init(numberOfUsers, calendar, traceFlag);


    //creating a simulated host through the configs

    val host1: SimulatedHost = new SimulatedHost(2, 1, "SaaS")

    //creating a simulated Datacenter through the configs

    val dataCenter1: SimulatedDataCenter = new SimulatedDataCenter(2, 1, "SaaS")

    val dataCenter: Datacenter = dataCenterHelper.createDataCenter("DataCenter1", host1 :: Nil, dataCenter1)
    val dataCenterBroker: DatacenterBroker = dataCenterHelper.createBroker("First_Simulation_broker")


    val brokerId = dataCenterBroker.getId


    //creating a simulated VM through the configs

    val vm1: SimulatedVm = new SimulatedVm(2, 1, "SaaS")
    vm1.brokerID = brokerId
    val vm: Vm = dataCenterHelper.createVM(vm1)
    val vmList: List[Vm] = List(vm)

    dataCenterBroker.submitVmList(myUtil.toJList(vmList))


    //creating a cloudlet VM through the configs

    val cloudlet1: SimulatedCloudlet = new SimulatedCloudlet(2, 1, "SaaS")

    val cloudLet: Cloudlet = dataCenterHelper.createCloudLet(cloudlet1, new UtilizationModelFull)
    cloudLet.setUserId(brokerId)
    cloudLet.setVmId(1)

    val cloudLetList: List[Cloudlet] = List(cloudLet)
    dataCenterBroker.submitCloudletList(myUtil.toJList(cloudLetList))

    //starting simulation
    CloudSim.startSimulation()

    //stopping simulation
    CloudSim.stopSimulation()


    dataCenterHelper.printCloudLets(myUtil.toSList(dataCenterBroker.getCloudletReceivedList()))

    //calculating and printing the total cost incurred
    logger.info("OverallCost for this Saas  model: " + cloudLetList.map(dataCenterHelper.getOverallCost).sum)
  }
}
