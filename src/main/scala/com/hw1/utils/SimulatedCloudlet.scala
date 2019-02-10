package com.hw1.utils

import com.typesafe.config.ConfigFactory

/**
  * Re-usable cloudlet that can be simulated by providing the  values in the config file
  *
  */
class SimulatedCloudlet(which: Int, number: Int, cloudModel: String) {
  val conf = ConfigFactory.load(cloudModel)
  val path = "simulation" + which + "." + "cloudLet" + number + "."
  var id = conf.getInt(path + "id") //Id needs to be overwritten in order to avoid duplication of values in the conf
  val length = conf.getInt(path + "length")
  val fileSize = conf.getInt(path + "fileSize")
  val outputSize = conf.getInt(path + "outputSize")
  val pesNumber = conf.getInt(path + "pesNumber")

}
