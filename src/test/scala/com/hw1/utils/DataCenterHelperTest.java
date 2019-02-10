package com.hw1.utils;

import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Calendar;


public class DataCenterHelperTest {


    static DataCenterHelper dch;

    @BeforeClass
    public static void setUp() {
        dch = new DataCenterHelper(); // Common instance required by all the tests
    }


    @Test
    public void verifyVmSimulation() {

        // Creating a simulated VM reading the configs
        SimulatedVm simulatedVm = new SimulatedVm(1, 1,"SimulationValues");
        Assert.assertNotNull(simulatedVm);
        Assert.assertEquals(simulatedVm.vmId(), 1);


        // Creating the above vm on the data center
        Vm vm = dch.createVM(simulatedVm);
        Assert.assertNotNull(vm);
        Assert.assertEquals(vm.getId(), 1);
    }

    @Test
    public void verifyHostSimulation() {

        // reading data from the simulation 1's host
        SimulatedHost simulatedHost = new SimulatedHost(2, 1,"SimulationValues");
        Assert.assertNotNull(simulatedHost);
        Assert.assertEquals(simulatedHost.id(), 0);
        Assert.assertEquals(simulatedHost.numberOfCores(), 1);


        //creating host in the Datacenter

        Host host = dch.createNewHost(simulatedHost);
        Assert.assertNotNull(host);
        Assert.assertEquals(host.getId(), 0);
        Assert.assertEquals(host.getNumberOfPes(), 1);
    }

    @Test
    public void verifyCloudLetSimulation() {

        // reading data from the simulation hosts
        SimulatedCloudlet simulatedCloudlet = new SimulatedCloudlet(1, 1,"SimulationValues");
        Assert.assertNotNull(simulatedCloudlet);
        Assert.assertEquals(simulatedCloudlet.id(), 1);

        //creating cloudlets in the DC
        Cloudlet cloudlet = dch.createCloudLet(simulatedCloudlet, new UtilizationModelFull());
        Assert.assertNotNull(cloudlet);
        Assert.assertEquals(cloudlet.getCloudletId(), 1);
        Assert.assertEquals(cloudlet.getNumberOfPes(), 1);
    }

    @Test
    public void verifyBrokerSimulation() {


        Calendar calendar = Calendar.getInstance();

        //Verify broker simulation
        CloudSim.init(1, calendar, false);
        DatacenterBroker testBroker = dch.createBroker("Test_broker");

        Assert.assertNotNull(testBroker);


    }



}