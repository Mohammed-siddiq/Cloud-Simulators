package com.hw1.utils

import com.typesafe.config.ConfigFactory


class SimulatedHost {

  var id: Int = _
  var ram: Int = _
  var storage: Int = _
  var bw: Int = _
  var mips: Int = _
  val conf = ConfigFactory.load("SimulationValues")

  def load(which: Int, hostNumber: Int) = {
    val path = "simulation" + which + "." + "host" + hostNumber + "."
    id = conf.getInt(path + "id")
    ram = conf.getInt(path + "ram")
    storage = conf.getInt(path + "storage")
    bw = conf.getInt(path + "bw")
    mips = conf.getInt(path + "mips")
  }

}
