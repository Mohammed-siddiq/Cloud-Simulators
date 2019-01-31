package com.hw1.utils

import com.typesafe.config.ConfigFactory

class SimulatedDataCenter {
  var arch: String = _
  var os: String = _
  var vmm: String = _
  var timeZone: Double = _
  var cost: Double = _
  var costPerMemory: Double = _
  var costPerStorage: Double = _
  var costPerBandWidth: Double = _
  val conf = ConfigFactory.load("SimulationValues")

  def load(which: Int, dataCenterNumber: Int) = {
    val path = "simulation" + which + "." + "host" + dataCenterNumber + "."
    arch = conf.getString(path + "arch")
    os = conf.getString(path + "os")
    vmm = conf.getString(path + "vmm")
    timeZone = conf.getDouble(path + "timeZone")
    cost = conf.getDouble(path + "cost")
    costPerMemory = conf.getDouble(path + "costPerMemory")
    costPerStorage = conf.getDouble(path + "costPerStorage")
    costPerBandWidth = conf.getDouble(path + "costPerBandWidth")

  }

}
