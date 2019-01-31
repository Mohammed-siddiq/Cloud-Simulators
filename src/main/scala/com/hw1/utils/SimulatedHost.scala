package com.hw1.utils

import com.typesafe.config.ConfigFactory

/**
  * ReUsable host that can be simulated by providing the host values in the config file
  *
  */
class SimulatedHost(which: Int, hostNumber: Int) {

  val conf = ConfigFactory.load("SimulationValues")
  val path = "simulation" + which + "." + "host" + hostNumber + "."
  val id: Int = conf.getInt(path + "id")
  val ram: Int = conf.getInt(path + "ram")
  val storage: Int = conf.getInt(path + "storage")
  val bw: Int = conf.getInt(path + "bw")
  val mips: Int = conf.getInt(path + "mips")

}
