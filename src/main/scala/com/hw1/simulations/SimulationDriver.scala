package com.hw1.simulations

import com.hw1.simulations.SaaS.{Simulation1SaaS, Simulation2SaaS}

object SimulationDriver extends App {

  Simulation1SaaS.runSimulation
  Thread.sleep(5000)
  Simulation2SaaS.runSimulation

}
