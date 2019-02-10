package com.hw1

import com.hw1.simulations.IaaS.Simulation1Iaas
import com.hw1.simulations.MiscellaneousSimulations.{Simulation1, Simulation2, Simulation3}
import com.hw1.simulations.SaaS.{Simulation1SaaS, Simulation2SaaS}
import org.slf4j.{Logger, LoggerFactory}

object SimulationDriver extends App {

  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  logger.info("Starting Hw1 Simulations")
  logger.info("SaaS Model Simulation")
  Simulation1SaaS.runSimulation

  //  Thread.sleep(2000)
  Simulation2SaaS.runSimulation

  //  Thread.sleep(2000)
  logger.info("IaaS Model Simulations")
  Simulation1Iaas.runSimulation

  //  Thread.sleep(2000)
  logger.info("Miscellaneus Simulations")
  Simulation1.runSimulation

  //  Thread.sleep(2000)
  Simulation2.runSimulation

  //  Thread.sleep(2000)
  Simulation3.runSimulation
  

}
