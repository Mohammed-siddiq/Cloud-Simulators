package com.hw1.utils

import com.typesafe.config.ConfigFactory

/**
  * Re-usable VM that can be simulated by providing the values in the config file
  *
  */
class SimulatedVm(which: Int, vmNumber: Int,cloudModel:String) {
  var brokerID: Int = _ // needs to be variable since generated later
  val conf = ConfigFactory.load(cloudModel)
  val path = "simulation" + which + "." + "vm" + vmNumber + "."
  var vmId = conf.getInt(path + "vmId") // Needed to modify to avoid redundancy in the configuration values
  val mips = conf.getDouble(path + "mips")
  val size = conf.getInt(path + "size")
  val ram = conf.getInt(path + "ram")
  val bandWith = conf.getInt(path + "bandWith")
  val pesNumber = conf.getInt(path + "pesNumber")
  val vmm = conf.getString(path + "vmm")

}
