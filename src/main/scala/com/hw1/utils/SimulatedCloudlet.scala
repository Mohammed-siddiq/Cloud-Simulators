package com.hw1.utils

import com.typesafe.config.ConfigFactory

class SimulatedCloudlet {
  var id: Int = _
  var length: Int = _
  var fileSize: Int = _
  var outPutSize: Int = _
  var pesNumber: Int = _
  val conf = ConfigFactory.load("SimulationValues")

  def load(which: Int, number: Int) = {
    val path = "simulation" + which + "." + "cloudLet" + number + "."
    id = conf.getInt(path + "id")
    length = conf.getInt(path + "length")
    fileSize = conf.getInt(path + "fileSize")
    outPutSize = conf.getInt(path + "outPutSize")
    pesNumber = conf.getInt(path + "pesNumber")


  }

}
