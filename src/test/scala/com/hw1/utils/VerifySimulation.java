package com.hw1.utils;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Integration Test: that verifies the simulation of entire data center integrating
 * different components/entities of the data center
 */
public class VerifySimulation {

    static DataCenterHelper dch;

    @BeforeClass
    public static void setUp() {
        dch = new DataCenterHelper(); // Common instance required by all the tests
    }

    @Test
    public void verifyDataCenterSimulation() {


        Calendar calendar = Calendar.getInstance();

        //initialiaze cloudsim
        CloudSim.init(1, calendar, false);

        //Add host to datacenter
        SimulatedHost simulatedHost = new SimulatedHost(1, 1,"Miscellaneous");
        dch.createNewHost(simulatedHost);

        //Add Vm simulation
        SimulatedVm simulatedVm = new SimulatedVm(1, 1,"Miscellaneous");
        ArrayList<Vm> vmList = new ArrayList<>();
        vmList.add(dch.createVM(simulatedVm));
        simulatedVm = new SimulatedVm(2, 1,"Miscellaneous");
        vmList.add(dch.createVM(simulatedVm));


        // create a broker
        DatacenterBroker testBroker = dch.createBroker("Test_broker");
        Assert.assertNotNull(testBroker); // verify broker creation

        testBroker.submitVmList(vmList); // submit vms to broker

        //Add cloudlet Simulation
        SimulatedCloudlet simulatedCloudlet = new SimulatedCloudlet(1, 1,"Miscellaneous");
        ArrayList<Cloudlet> cloudlets = new ArrayList<>();

        Cloudlet clouldlet1 = dch.createCloudLet(simulatedCloudlet, new UtilizationModelFull());
        clouldlet1.setUserId(1);

        simulatedCloudlet = new SimulatedCloudlet(2, 1,"Miscellaneous");

        Cloudlet clouldlet2 = dch.createCloudLet(simulatedCloudlet, new UtilizationModelFull());
        clouldlet2.setUserId(1);


        cloudlets.add(clouldlet1);
        cloudlets.add(clouldlet2);

        testBroker.submitCloudletList(cloudlets); // submit cloudlets to broker



        Assert.assertEquals(testBroker.getVmList().size(), 2); // verify Vm integration

        Assert.assertEquals(testBroker.getCloudletList().size(), 2); //verify cloudlet integration




    }

}
