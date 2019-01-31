package com.hw1.utils

import com.typesafe.config.ConfigFactory

/**
  * Re-usable cloudlet that can be simulated by providing the  values in the config file
  *
  */
class SimulatedCloudlet(which: Int, number: Int) {
  val conf = ConfigFactory.load("SimulationValues")
  val path = "simulation" + which + "." + "cloudLet" + number + "."
  val id = conf.getInt(path + "id")
  val length = conf.getInt(path + "length")
  val fileSize = conf.getInt(path + "fileSize")
  val outputSize = conf.getInt(path + "outputSize")
  val pesNumber = conf.getInt(path + "pesNumber")

}
