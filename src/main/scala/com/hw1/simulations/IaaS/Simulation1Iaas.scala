package com.hw1.simulations.IaaS

import java.util.Calendar

import com.hw1.utils._
import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim._
import org.cloudbus.cloudsim.core.CloudSim
import org.slf4j.{Logger, LoggerFactory}

/**
  * The entire infrastructure is exposed to the user, which means every user will have its own stack of the entire infra.
  * which also means different users can have different stack of infrastructures.
  * On the same lines, different users will be having different services or applications.
  * datacenter config: please refer the IaaS Conf file in the Resources.
  */
object Simulation1Iaas {

  def runSimulation = {

    val dataCenterHelper: DataCenterHelper = new DataCenterHelper();
    val myUtil: ConversionUtil = new ConversionUtil();


    val conf = ConfigFactory.load("SimulationsProp")

    val logger: Logger = LoggerFactory.getLogger(this.getClass)

    logger.info("Starting Iaas simulation ")


    val numberOfUsers: Int = 5
    val calendar: Calendar = Calendar.getInstance()
    val traceFlag: Boolean = false

    logger.debug("Initialising")
    CloudSim.init(numberOfUsers, calendar, traceFlag)

    val ids: List[Int] = List(1, 2, 3, 4, 5)

    //creating 5 different hosts
    val hosts: List[SimulatedHost] = ids.map(new SimulatedHost(1, _, "IaaS"))


    val sDataCenters: List[SimulatedDataCenter] = ids.map(new SimulatedDataCenter(1, _, "IaaS"))

    logger.debug("creating different data centers with different hosts")


    // creating 5 different data centers with 5 different hosts
    val dataCenter: Datacenter = dataCenterHelper.createDataCenter("DataCenter1", hosts(0) :: Nil, sDataCenters(0))
    val dataCenter2: Datacenter = dataCenterHelper.createDataCenter("DataCenter2", hosts(1) :: Nil, sDataCenters(1))
    val dataCenter3: Datacenter = dataCenterHelper.createDataCenter("DataCenter3", hosts(2) :: Nil, sDataCenters(2))
    val dataCenter4: Datacenter = dataCenterHelper.createDataCenter("DataCenter4", hosts(3) :: Nil, sDataCenters(3))
    val dataCenter5: Datacenter = dataCenterHelper.createDataCenter("DataCenter5", hosts(4) :: Nil, sDataCenters(4))


    logger.debug("creating  different datacenter brokers for each user")

    //creating  different datacenter brokers for each user
    val dataCenterBroker1: DatacenterBroker = dataCenterHelper.createBroker("First_Simulation_broker")
    val dataCenterBroker2: DatacenterBroker = dataCenterHelper.createBroker("Second_Simulation_broker")
    val dataCenterBroker3: DatacenterBroker = dataCenterHelper.createBroker("Third_Simulation_broker")
    val dataCenterBroker4: DatacenterBroker = dataCenterHelper.createBroker("Fourth_Simulation_broker")
    val dataCenterBroker5: DatacenterBroker = dataCenterHelper.createBroker("Fifth_Simulation_broker")
    val brokerId1 = dataCenterBroker1.getId
    val brokerId2 = dataCenterBroker2.getId
    val brokerId3 = dataCenterBroker3.getId
    val brokerId4 = dataCenterBroker4.getId
    val brokerId5 = dataCenterBroker5.getId

    logger.debug("creating  different vms for each user")

    //creating  different vms for each user
    val simulatedVms: List[SimulatedVm] = ids.map(new SimulatedVm(1, _, "IaaS"))
    simulatedVms(0).brokerID = brokerId1
    simulatedVms(1).brokerID = brokerId2
    simulatedVms(2).brokerID = brokerId3
    simulatedVms(3).brokerID = brokerId4
    simulatedVms(4).brokerID = brokerId5
    val vm1: Vm = dataCenterHelper.createVM(simulatedVms(0))
    val vm2: Vm = dataCenterHelper.createVM(simulatedVms(1))
    val vm3: Vm = dataCenterHelper.createVM(simulatedVms(2))
    val vm4: Vm = dataCenterHelper.createVM(simulatedVms(3))
    val vm5: Vm = dataCenterHelper.createVM(simulatedVms(4))
    val vmList1: List[Vm] = List(vm1)
    val vmList2: List[Vm] = List(vm2)
    val vmList3: List[Vm] = List(vm3)
    val vmList4: List[Vm] = List(vm4)
    val vmList5: List[Vm] = List(vm5)


    logger.debug("submitting respective user's vms to brokers")

    //submitting respective user's vms to brokers
    dataCenterBroker1.submitVmList(myUtil.toJList(vmList1))
    dataCenterBroker2.submitVmList(myUtil.toJList(vmList2))
    dataCenterBroker3.submitVmList(myUtil.toJList(vmList3))
    dataCenterBroker4.submitVmList(myUtil.toJList(vmList4))
    dataCenterBroker5.submitVmList(myUtil.toJList(vmList5))


    logger.debug("creating cloudlets")

    //creating cloudlets
    val sCloudLetList: List[SimulatedCloudlet] = ids.map(new SimulatedCloudlet(1, _, "IaaS"))

    val cloudLet1: Cloudlet = dataCenterHelper.createCloudLet(sCloudLetList(0), new UtilizationModelFull)
    cloudLet1.setUserId(brokerId1)

    val cloudLet2: Cloudlet = dataCenterHelper.createCloudLet(sCloudLetList(1), new UtilizationModelFull)
    cloudLet2.setUserId(brokerId2)

    val cloudLet3: Cloudlet = dataCenterHelper.createCloudLet(sCloudLetList(2), new UtilizationModelFull)
    cloudLet3.setUserId(brokerId3)

    val cloudLet4: Cloudlet = dataCenterHelper.createCloudLet(sCloudLetList(3), new UtilizationModelFull)
    cloudLet4.setUserId(brokerId4)

    val cloudLet5: Cloudlet = dataCenterHelper.createCloudLet(sCloudLetList(4), new UtilizationModelFull)
    cloudLet5.setUserId(brokerId5)
    val cloudLetList1: List[Cloudlet] = List(cloudLet1)
    val cloudLetList2: List[Cloudlet] = List(cloudLet2)
    val cloudLetList3: List[Cloudlet] = List(cloudLet3)
    val cloudLetList4: List[Cloudlet] = List(cloudLet4)
    val cloudLetList5: List[Cloudlet] = List(cloudLet5)

    logger.debug("submitting cloudlets")
    //submitting cloudlets
    dataCenterBroker1.submitCloudletList(myUtil.toJList(cloudLetList1))
    dataCenterBroker2.submitCloudletList(myUtil.toJList(cloudLetList2))
    dataCenterBroker3.submitCloudletList(myUtil.toJList(cloudLetList3))
    dataCenterBroker4.submitCloudletList(myUtil.toJList(cloudLetList4))
    dataCenterBroker5.submitCloudletList(myUtil.toJList(cloudLetList5))

    CloudSim.startSimulation()


    CloudSim.stopSimulation()


    //Caluclating and printing the cost and the result of 1st Iaas
    dataCenterHelper.printCloudLets(myUtil.toSList(dataCenterBroker1.getCloudletReceivedList()))
    logger.info("OverallCost for this IaaS  model for 1st customer:" + cloudLetList1.map(dataCenterHelper.getOverallCost).sum)


    //Caluclating and printing the cost and the result of 2nd Iaas

    dataCenterHelper.printCloudLets(myUtil.toSList(dataCenterBroker2.getCloudletReceivedList()))
    logger.info("OverallCost for this IaaS  model for 2nd customer: " + cloudLetList2.map(dataCenterHelper.getOverallCost).sum)

    //Caluclating and printing the cost and the result of 3rd Iaas

    dataCenterHelper.printCloudLets(myUtil.toSList(dataCenterBroker3.getCloudletReceivedList()))
    logger.info("OverallCost for this IaaS  model for 3rd customer: " + cloudLetList3.map(dataCenterHelper.getOverallCost).sum)

    //Caluclating and printing the cost and the result of 4th Iaas

    dataCenterHelper.printCloudLets(myUtil.toSList(dataCenterBroker4.getCloudletReceivedList()))
    logger.info("OverallCost for this IaaS  model for 4th customer: " + cloudLetList4.map(dataCenterHelper.getOverallCost).sum)

    //Caluclating and printing the cost and the result of 5th Iaas
    dataCenterHelper.printCloudLets(myUtil.toSList(dataCenterBroker5.getCloudletReceivedList()))
    logger.info("OverallCost for this IaaS  model for 5th customer: " + cloudLetList5.map(dataCenterHelper.getOverallCost).sum)


  }


}
