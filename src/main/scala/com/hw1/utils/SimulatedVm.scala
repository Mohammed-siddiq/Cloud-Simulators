package com.hw1.utils

import com.typesafe.config.ConfigFactory

/**
  * Re-usable VM that can be simulated by providing the values in the config file
  *
  */
class SimulatedVm(which: Int, vmNumber: Int) {
  var brokerID: Int = _ // needs to be variable since generated later
  val conf = ConfigFactory.load("SimulationValues")
  val path = "simulation" + which + "." + "vm" + vmNumber + "."
  val vmId = conf.getInt(path + "vmId")
  val mips = conf.getDouble(path + "mips")
  val size = conf.getInt(path + "size")
  val ram = conf.getInt(path + "ram")
  val bandWith = conf.getInt(path + "bandWith")
  val pesNumber = conf.getInt(path + "pesNumber")
  val vmm = conf.getString(path + "vmm")

}