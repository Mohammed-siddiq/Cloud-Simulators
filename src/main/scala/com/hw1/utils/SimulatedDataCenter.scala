package com.hw1.utils

import com.typesafe.config.ConfigFactory


/**
  * Re-usable datacenter that can be simulated by providing the values in the config file
  *
  */
class SimulatedDataCenter(which: Int, dataCenterNumber: Int) {

  val conf = ConfigFactory.load("SimulationValues")
  val path = "simulation" + which + "." + "dataCenter" + dataCenterNumber + "."


  val arch = conf.getString(path + "arch")
  val os = conf.getString(path + "os")
  val vmm = conf.getString(path + "vmm")
  val timeZone = conf.getDouble(path + "timeZone")
  val cost = conf.getDouble(path + "cost")
  val costPerMemory = conf.getDouble(path + "costPerMemory")
  val costPerStorage = conf.getDouble(path + "costPerStorage")
  val costPerBandWidth = conf.getDouble(path + "costPerBandWidth")


}
