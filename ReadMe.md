
**Name: Mohammed Siddiq**

**Steps to run:**
1. Clone this project 
2. Run the command ``` sbt test run ``` : Runs all the test cases , if they pass, runs all the simulations.



**Simulations** :
 
 A _small-scale simulation_ of few important models and few important common scenarios

**Software as a Service (Saas)**: As the customer is unaware of the underlying hardware/resources , this simulation runs the exact same cloudlet(a service that runs continously - for a long time in the simulation). As a cloud broker, to find the right set of hardware/resources on which to run this software, couple of  ploicies are implemented with different VM's and different providers running the same cloudlets, thereby the right/optimal provider can be chosen to run the SaaS.


**Important data center config** :  
Two simulations with very minute cost difference are selected one with Xen Vm and other with Vmware Vm. 

```
dataCenter1 = {
    arch = "x86"
    os = "Linux"
    vmm = "Xen"
    timeZone = 10.0
    cost = 3.0
    costPerMemory = 0.05
    costPerStorage = 0.001
    costPerBandWidth = 0
  }
  vm1 = {
    vmId = 1
    mips = 3000.00
    size = 1000
    ram = 512
    bandWith = 1000
    pesNumber = 1
    vmm = "Xen"
  }

```
**Simulation Results** :

 Cloudlet ID    STATUS    Data center ID    VM ID    Time    Start Time    Finish Time
 
     1        SUCCESS        2            1        300        0.1        300.1
     
 OverallCost for this Saas  model: 900.0

  ```
  dataCenter1 = {
    arch = "x86"
    os = "Linux"
    vmm = "Vmware"
    timeZone = 10.0
    cost = 2.5
    costPerMemory = 0.07
    costPerStorage = 0.002
    costPerBandWidth = 0
  }
  vm1 = {
    vmId = 1
    mips = 2000.00
    size = 10000
    ram = 1024
    bandWith = 100
    pesNumber = 1
    vmm = "Vmware"
  }
  
```
**Simulation Results** :

 Cloudlet ID    STATUS    Data center ID    VM ID    Time    Start Time    Finish Time
     1        SUCCESS        2            1        450        0.1        450.1
     
OverallCost for this Saas  model: 1125.0


**Conclusion**:

Though the cost of using processing in the datacenter housing Xen vmware is 3.0$ it runs the simulation of our SaaS model incurring 900$ overall, while datacenter running Vmware's machine at cost of 2.5$ run's the same SaaS model at 1125$. For this SaaS model choosing the first datacenter would be  economical.



**Iaas** : The entire infrastructure is exposed to the user, which means every user will have its own stack of the entire infra. which also means different users can have different stack of infrastructures. On the same lines, different users will be having different services or applications. 

datacenter config: please refer the Iaas Conf file in the project.

**Simulation Results**:

The following describes the simulation results with various Infastractures.


 Cloudlet ID    STATUS    Data center ID    VM ID    Time    Start Time    Finish Time
 
     1        SUCCESS        3            1        16.67        0.2        16.87
 OverallCost for this IaaS  model for 1st customer:42.5

Cloudlet ID    STATUS    Data center ID    VM ID    Time    Start Time    Finish Time

    2        SUCCESS        3            2        400        0.2        400.2
[main] INFO com.hw1.simulations.IaaS.Simulation1Iaas$ - OverallCost for this IaaS  model for 2nd customer: 1019.9915

 Cloudlet ID    STATUS    Data center ID    VM ID    Time    Start Time    Finish Time
 
     3        SUCCESS        4            3        280        0.3        280.3
 OverallCost for this IaaS  model for 3rd customer: 350.0
 
 Cloudlet ID    STATUS    Data center ID    VM ID    Time    Start Time    Finish Time
 
     4        SUCCESS        4            4        952.38        0.3        952.68
 OverallCost for this IaaS  model for 4th customer: 1190.4761904761906
 
Cloudlet ID    STATUS    Data center ID    VM ID    Time    Start Time    Finish Time

    5        SUCCESS        4            5        633.33        0.3        633.63
OverallCost for this IaaS  model for 5th customer: 791.6666666666667

**Conclusion** : A demonstration simulating 5 users owning different set of infrasture each running their own set of services was simulated. This gives a clarity on varied resources running varied servies thereby incurring varied costs. 


**Miscellaneous Simulations:**

 1. A straightforward simulation where 1 software runs on a single VM
 
 Refer 1st simulation config under miscellaneous confs.
 
 **Simulation Results:**
 

Cloudlet ID    STATUS    Data center ID    VM ID    Time    Start Time    Finish Time

    1        SUCCESS        2            1        400        0.1        400.1
Overall Cost for this simulation 1200.0


2.Simulating: Different services on different VMS.Scenario simulated : Running multiple instances of the same software on multiple instances of same VMS.

 Refer 2nd simulation config under miscellaneous confs.

 **Simulation Results:**
 
 
Cloudlet ID    STATUS    Data center ID    VM ID    Time    Start Time    Finish Time

    1        SUCCESS        2            1        400        0.1        400.1
    2        SUCCESS        2            2        400        0.1        400.1
    3        SUCCESS        2            3        400        0.1        400.1
    4        SUCCESS        2            4        400        0.1        400.1
Overall Cost for this simulation  4800.0

3.Simulation with a dataCenter with one host one vm and 2 cloudlets running in parallel,Scenario simulated: software that run and share the cpu time


 Refer 3rd simulation config under miscellaneous confs.
 
 **Simulation Result**
 
 
 Cloudlet ID    STATUS    Data center ID    VM ID    Time    Start Time    Finish Time
 
     2        SUCCESS        2            1        15        0.1        15.1
     1        SUCCESS        2            1        25        0.1        25.1
 Overall Cost for this simulation  120.0








  
  



