package com.hw1.simulations.MiscellaneousSimulations

import java.util.Calendar

import com.hw1.utils._
import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim._
import org.cloudbus.cloudsim.core.CloudSim
import org.slf4j.{Logger, LoggerFactory}

/**
  * Simulation with a dataCenter with one host one vm and 2 cloudlets running in parallel,
  * Scenario simulated: software that run and share the cpu time
  * Refer 3rd simulation config under miscellaneous confs.
  *
  */

object Simulation3 {

  def runSimulation = {


    val dataCenterHelper: DataCenterHelper = new DataCenterHelper();
    val myUtil: ConversionUtil = new ConversionUtil();


    val conf = ConfigFactory.load("SimulationsProp")

    val logger: Logger = LoggerFactory.getLogger(this.getClass)

    logger.info("Starting miscellaneous simulation 3")

    val numberOfUsers: Int = 1
    val calendar: Calendar = Calendar.getInstance();
    val traceFlag: Boolean = false

    logger.info("Initialising cloudSim")
    CloudSim.init(numberOfUsers, calendar, traceFlag);

    //loading and creating Datacenter and host from confs
    val host1: SimulatedHost = new SimulatedHost(3, 1, "Miscellaneous")
    val dataCenter1: SimulatedDataCenter = new SimulatedDataCenter(3, 1, "Miscellaneous")
    val dataCenter: Datacenter = dataCenterHelper.createDataCenter("DataCenter1", host1 :: Nil, dataCenter1)
    val dataCenterBroker: DatacenterBroker = dataCenterHelper.createBroker("ThirdSimulationBroker")
    val brokerId = dataCenterBroker.getId

    // VM 1
    val sVm1: SimulatedVm = new SimulatedVm(3, 1, "Miscellaneous")
    sVm1.brokerID = brokerId
    val vm1: Vm = dataCenterHelper.createVM(sVm1)

    val vmList: List[Vm] = List(vm1)

    //sending created vms to datacenter
    dataCenterBroker.submitVmList(myUtil.toJList(vmList))

    //loading different cloudlets form the conf

    val simCloudLet1: SimulatedCloudlet = new SimulatedCloudlet(3, 1, "Miscellaneous")
    val simCloudLet2: SimulatedCloudlet = new SimulatedCloudlet(3, 2, "Miscellaneous")



    //creating cloudets from the loaded confs

    val cloudLet1: Cloudlet = dataCenterHelper.createCloudLet(simCloudLet1, new UtilizationModelFull)
    cloudLet1.setUserId(brokerId)
    cloudLet1.setVmId(sVm1.vmId)


    val cloudLet2: Cloudlet = dataCenterHelper.createCloudLet(simCloudLet2, new UtilizationModelFull)
    cloudLet2.setUserId(brokerId)
    cloudLet2.setVmId(sVm1.vmId)



    // submitted the cloudlet list to the datacenter broker

    val cloudLetList: List[Cloudlet] = List(cloudLet1, cloudLet2)
    dataCenterBroker.submitCloudletList(myUtil.toJList(cloudLetList))

    CloudSim.startSimulation()
    CloudSim.stopSimulation()


    // calculating and printing total cost incurred and the results
    dataCenterHelper.printCloudLets(myUtil.toSList(dataCenterBroker.getCloudletReceivedList()))

    logger.info("Overall Cost for this simulation - " + cloudLetList.map(dataCenterHelper.getOverallCost).sum)

  }

}
