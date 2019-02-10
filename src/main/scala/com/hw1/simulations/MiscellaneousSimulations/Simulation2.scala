package com.hw1.simulations.MiscellaneousSimulations

import java.util.Calendar

import com.hw1.utils._
import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim._
import org.cloudbus.cloudsim.core.CloudSim
import org.slf4j.{Logger, LoggerFactory}

/**
  * Simulating: Different cloudlets on different VMS.
  * Running multiple instances of the same cloudlet on multiple instances of same VMS
  */
object Simulation2 {

  def runSimulation = {


    val dataCenterHelper: DataCenterHelper = new DataCenterHelper();
    val myUtil: ConversionUtil = new ConversionUtil();


    val conf = ConfigFactory.load("SimulationsProp")

    val logger: Logger = LoggerFactory.getLogger(this.getClass)

    logger.info("Starting miscellaneous simulation 2")

    val numberOfUsers: Int = 2
    val calendar: Calendar = Calendar.getInstance();
    val traceFlag: Boolean = false

    logger.info("Initialising cloudsim")
    CloudSim.init(numberOfUsers, calendar, traceFlag);

    val host1: SimulatedHost = new SimulatedHost(2, 1, "Miscellaneous")
    val dataCenter1: SimulatedDataCenter = new SimulatedDataCenter(2, 1, "Miscellaneous")
    val dataCenter: Datacenter = dataCenterHelper.createDataCenter("DataCenter1", host1 :: Nil, dataCenter1)
    val dataCenterBroker: DatacenterBroker = dataCenterHelper.createBroker("SecondSimulationBroker")

    val brokerId = dataCenterBroker.getId

    // VM 1
    val sVm1: SimulatedVm = new SimulatedVm(2, 1, "Miscellaneous")
    sVm1.brokerID = brokerId
    val vm1: Vm = dataCenterHelper.createVM(sVm1)


    val sVm2: SimulatedVm = new SimulatedVm(2, 1, "Miscellaneous")
    sVm2.brokerID = brokerId
    sVm2.vmId = sVm1.vmId + 1; // overwriting the id to create a different VM
    val vm2: Vm = dataCenterHelper.createVM(sVm2)


    val sVm3: SimulatedVm = new SimulatedVm(2, 1, "Miscellaneous")
    sVm3.brokerID = brokerId
    sVm3.vmId = sVm2.vmId + 1;
    val vm3: Vm = dataCenterHelper.createVM(sVm3)


    val sVm4: SimulatedVm = new SimulatedVm(2, 1, "Miscellaneous")
    sVm4.brokerID = brokerId
    sVm4.vmId = sVm3.vmId + 1;
    val vm4: Vm = dataCenterHelper.createVM(sVm4)

    val sVm5: SimulatedVm = new SimulatedVm(2, 1, "Miscellaneous")
    sVm5.brokerID = brokerId
    sVm5.vmId = sVm4.vmId + 1;

    val vm5: Vm = dataCenterHelper.createVM(sVm5)


    //vm2

    //  val sVm2: SimulatedVm = new SimulatedVm(2, 2)
    //  sVm2.brokerID = brokerId
    //  val vm2: Vm = dataCenterHelper.createVM(sVm2)

    val vmList: List[Vm] = List(vm1, vm2, vm3, vm4, vm5)

    dataCenterBroker.submitVmList(myUtil.toJList(vmList))


    val simCloudLet1: SimulatedCloudlet = new SimulatedCloudlet(1, 1, "Miscellaneous")
    val simCloudLet2: SimulatedCloudlet = new SimulatedCloudlet(1, 1, "Miscellaneous")
    simCloudLet2.id = simCloudLet1.id + 1

    val simCloudLet3: SimulatedCloudlet = new SimulatedCloudlet(1, 1, "Miscellaneous")
    simCloudLet3.id = simCloudLet2.id + 1

    val simCloudLet4: SimulatedCloudlet = new SimulatedCloudlet(1, 1, "Miscellaneous")
    simCloudLet4.id = simCloudLet3.id + 1;

    val simCloudLet5: SimulatedCloudlet = new SimulatedCloudlet(1, 1, "Miscellaneous")
    simCloudLet5.id = simCloudLet4.id + 1;


    val cloudLet1: Cloudlet = dataCenterHelper.createCloudLet(simCloudLet1, new UtilizationModelFull)
    cloudLet1.setUserId(brokerId)
    cloudLet1.setVmId(sVm1.vmId)

    val cloudLet2: Cloudlet = dataCenterHelper.createCloudLet(simCloudLet2, new UtilizationModelFull)
    cloudLet2.setUserId(brokerId)
    cloudLet2.setVmId(sVm2.vmId)

    val cloudLet3: Cloudlet = dataCenterHelper.createCloudLet(simCloudLet3, new UtilizationModelFull)
    cloudLet3.setUserId(brokerId)
    cloudLet3.setVmId(sVm3.vmId)

    val cloudLet4: Cloudlet = dataCenterHelper.createCloudLet(simCloudLet4, new UtilizationModelFull)
    cloudLet4.setUserId(brokerId)
    cloudLet4.setVmId(sVm4.vmId)

    val cloudLet5: Cloudlet = dataCenterHelper.createCloudLet(simCloudLet5, new UtilizationModelFull)
    cloudLet5.setUserId(brokerId)
    cloudLet5.setVmId(sVm5.vmId)


    val cloudLetList: List[Cloudlet] = List(cloudLet1, cloudLet2, cloudLet3, cloudLet4, cloudLet5)
    dataCenterBroker.submitCloudletList(myUtil.toJList(cloudLetList))

    CloudSim.startSimulation()
    CloudSim.stopSimulation()


    dataCenterHelper.printCloudLets(myUtil.toSList(dataCenterBroker.getCloudletReceivedList()))

    logger.info("Overall Cost for this simulation - " + cloudLetList.map(dataCenterHelper.getOverallCost).sum)

  }

}
