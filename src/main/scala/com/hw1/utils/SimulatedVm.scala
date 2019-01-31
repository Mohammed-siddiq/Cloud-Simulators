package com.hw1.utils

import com.typesafe.config.ConfigFactory

class SimulatedVm {

  var vmId: Int = _
  var mips: Double = _
  var size: Int = _
  var ram: Int = _
  var bandWith: Int = _
  var pesNumber: Int = _
  var vmm: String = _

  val conf = ConfigFactory.load("SimulationValues")


  def load(which: Int, vmNumber: Int) = {
    val path = "simulation" + which + "." + "vm" + vmNumber + "."
    vmId = conf.getInt(path + "id")
    mips = conf.getDouble(path + "mips")
    size = conf.getInt(path + "size")
    ram = conf.getInt(path + "ram")
    bandWith = conf.getInt(path + "bandWith")
    pesNumber = conf.getInt(path + "pesNumber")
    vmm = conf.getString(path + "vmm")
  }
}
