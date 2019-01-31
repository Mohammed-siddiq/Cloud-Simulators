package com.hw1.utils

import java.text.DecimalFormat

import com.hw1.simulations.FirstSimulator
import com.hw1.simulations.FirstSimulator.conf
import org.cloudbus.cloudsim._
import org.cloudbus.cloudsim.provisioners.{BwProvisionerSimple, PeProvisionerSimple, RamProvisionerSimple}
import org.slf4j.{Logger, LoggerFactory}

class DataCenterHelper {

  val logger: Logger = LoggerFactory.getLogger(FirstSimulator.getClass)

  def printCloudLets(list: List[Cloudlet]) = {

    val size = list.length

    val indent = "    "
    logger.info("")
    logger.info("========== OUTPUT ==========")
    logger.info("Cloudlet ID" + indent + "STATUS" + indent + "Data center ID" + indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time")

    val dft = new DecimalFormat("###.##")
    for (cloudlet <- list) {
      logger.info(indent + cloudlet.getCloudletId + indent + indent)
      if (cloudlet.getCloudletStatus == Cloudlet.SUCCESS) {
        logger.info("SUCCESS")
        logger.info(indent + indent + cloudlet.getResourceId + indent + indent + indent + cloudlet.getVmId + indent + indent + dft.format(cloudlet.getActualCPUTime) + indent + indent + dft.format(cloudlet.getExecStartTime) + indent + indent + dft.format(cloudlet.getFinishTime))
      }

    }
  }


  val myUtil: ConversionUtil = new ConversionUtil();


  private def createNewHost(myHostId: Int, ramSize: Int, storage: Int, bandWidth: Int, mips: Int): Host = {

    val peList: List[Pe] = List(new Pe(0, new PeProvisionerSimple(mips)))

    new Host(myHostId, new RamProvisionerSimple(ramSize), new BwProvisionerSimple(bandWidth), storage, myUtil.toJList(peList), new VmSchedulerTimeShared(myUtil.toJList(peList)))
  }

  /**
    * Creates a Data Center with given host parameters and data center parameters
    *
    * @param hostParams       : Map of string and Ints defining the characteristics of a host
    * @param dataCenterParams : Map of string and Any defining the characteristics of a data Center
    * @return Datacenter
    */
  def createDataCenter(name: String, hostParams: Map[String, Int], dataCenterParams: Map[String, Any]): Datacenter = {

    val hostList: List[Host] = createNewHost(hostParams(conf.getString("hostKeys.id")), hostParams("ramSize"), hostParams("storage"), hostParams("bandWidth"), hostParams("mips")) :: Nil

    val mips: Int = hostParams("mips");

    val arch: String = dataCenterParams("arch").toString
    val os: String = dataCenterParams("os").toString
    val vmm: String = dataCenterParams("vmm").toString

    val timeZone: Double = dataCenterParams("timeZone").asInstanceOf[Double]
    val cost: Double = dataCenterParams("cost").asInstanceOf[Double]
    val costPerMem: Double = dataCenterParams("costPerMem").asInstanceOf[Double]
    val costPerStorage: Double = dataCenterParams("costPerStorage").asInstanceOf[Double]
    val costPerBw: Double = dataCenterParams("costPerBw").asInstanceOf[Double]
    val characteristics = new DatacenterCharacteristics(arch, os, vmm, myUtil.toJList(hostList), timeZone, cost, costPerMem, costPerStorage, costPerBw)

    var datacenter: Datacenter = null
    val storageList: List[Storage] = List();
    try {
      datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(myUtil.toJList(hostList)), myUtil.toJList(storageList), 0)
    }
    catch {
      case e: Exception =>
        e.printStackTrace()
    }

    datacenter

  }

  def createVM(vmParams: Map[String, Any]) = new Vm(vmParams("vmId").asInstanceOf[Int], vmParams("brokerId").asInstanceOf[Int], vmParams("mips").asInstanceOf[Double], vmParams("pesNumber").asInstanceOf[Int], vmParams("ram").asInstanceOf[Int], vmParams("bw").asInstanceOf[Long], vmParams("size").asInstanceOf[Long], vmParams("vmm").asInstanceOf[String], new CloudletSchedulerTimeShared)


  def createBroker(brokerName: String): DatacenterBroker = {
    new DatacenterBroker(brokerName)
  }


  def createCloudLet(cloudLetProps: Map[String, Int], utilizationModel: UtilizationModel): Cloudlet = {
    val cloudlet = new Cloudlet(cloudLetProps("id"), cloudLetProps("length"), cloudLetProps("pesNumber"), cloudLetProps("fileSize"), cloudLetProps("outputSize"), utilizationModel, utilizationModel, utilizationModel)
    cloudlet
  }


}
