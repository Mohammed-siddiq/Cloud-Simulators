package com.hw1.utils

import java.text.DecimalFormat

import com.hw1.simulations.MiscellaneousSimulations.Simulation1
import org.cloudbus.cloudsim._
import org.cloudbus.cloudsim.provisioners.{BwProvisionerSimple, PeProvisionerSimple, RamProvisionerSimple}
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable.ListBuffer


/**
  * A generic class that can be invoked by the simulations.
  * Creates all individual components of the Datacenter
  */
class DataCenterHelper {

  val logger: Logger = LoggerFactory.getLogger(Simulation1.getClass)

  val myUtil: ConversionUtil = new ConversionUtil();


  def createNewHost(host: SimulatedHost): Host = {

    var peList = new ListBuffer[Pe]()

    for (i <- 0 until host.numberOfCores) peList += new Pe(i, new PeProvisionerSimple(host.mips))


    //    val peList: List[Pe] = List(new Pe(0, new PeProvisionerSimple(host.mips)), new Pe(1, new PeProvisionerSimple(host.mips)))

    new Host(host.id, new RamProvisionerSimple(host.ram), new BwProvisionerSimple(host.bw), host.storage, myUtil.toJList(peList.toList), new VmSchedulerTimeShared(myUtil.toJList(peList.toList)))
  }

  /**
    * Creates a Data Center with given host parameters and data center parameters
    *
    * @param hostParams       : Map of string and Ints defining the characteristics of a host
    * @param dataCenterParams : Map of string and Any defining the characteristics of a data Center
    * @return Datacenter
    */
  def createDataCenter(name: String, simulatedHosts: List[SimulatedHost], sDataCenter: SimulatedDataCenter): Datacenter = {

    var hostList = new ListBuffer[Host]() // need to use var because list needs to be appended based on the passed param, had compromise in order to make this method generic
    for (sHost <- simulatedHosts) {
      hostList += createNewHost(sHost)
    }

    val characteristics = new DatacenterCharacteristics(sDataCenter.arch, sDataCenter.os, sDataCenter.vmm, myUtil.toJList(hostList.toList), sDataCenter.timeZone, sDataCenter.cost, sDataCenter.costPerMemory, sDataCenter.costPerStorage, sDataCenter.costPerBandWidth)

    var datacenter: Datacenter = null
    val storageList: List[Storage] = List()
    try {
      datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(myUtil.toJList(hostList.toList)), myUtil.toJList(storageList), 0)
    }
    catch {
      case e: Exception =>
        e.printStackTrace()
    }

    datacenter

  }

  def createVM(simulatedVm: SimulatedVm) = new Vm(simulatedVm.vmId, simulatedVm.brokerID, simulatedVm.mips, simulatedVm.pesNumber,
    simulatedVm.ram, simulatedVm.bandWith, simulatedVm.size, simulatedVm.vmm, new CloudletSchedulerTimeShared)


  def createBroker(brokerName: String): DatacenterBroker = {
    new DatacenterBroker(brokerName)
  }


  def createCloudLet(simulatedCloudlet: SimulatedCloudlet, utilizationModel: UtilizationModel): Cloudlet = {
    val cloudlet = new Cloudlet(simulatedCloudlet.id, simulatedCloudlet.length, simulatedCloudlet.pesNumber,
      simulatedCloudlet.fileSize, simulatedCloudlet.fileSize, utilizationModel, utilizationModel, utilizationModel)
    cloudlet
  }


  // calculates total cost incurred to run the cloudlet

  def getOverallCost(cloudlet: Cloudlet): Double = {

    var totalCost: Double = 0.0 // need to add the individual cost
    if (cloudlet.isFinished) {
      for (id <- cloudlet.getAllResourceId) {
        //        totalCost+= costPerSec * res.actualCPUTime
        totalCost += cloudlet.getCostPerSec(id) * cloudlet.getActualCPUTime(id)

      }
    }

    totalCost
  }

// prints the results of the cloudlets
  def printCloudLets(list: List[Cloudlet]) = {

    Thread.sleep(1000)
    val size = list.length

    val indent = "    "
    logger.info("*********** Simulation Result ***********")
    logger.info("Cloudlet ID" + indent + "STATUS" + indent + "Data center ID" + indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time")

    val dft = new DecimalFormat("###.##")
    for (cloudlet <- list) {
      if (cloudlet.getCloudletStatus == Cloudlet.SUCCESS) {
        logger.info(indent + cloudlet.getCloudletId + indent + indent + "SUCCESS" + indent + indent + cloudlet.getResourceId + indent +
          indent + indent + cloudlet.getVmId + indent + indent + dft.format(cloudlet.getActualCPUTime) + indent + indent +
          dft.format(cloudlet.getExecStartTime) + indent + indent + dft.format(cloudlet.getFinishTime))
      }

    }
  }


}
