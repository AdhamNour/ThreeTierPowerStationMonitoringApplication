# ThreeTierPowerStationMonitoringApplication
## Running Instructure
1.install any prefared Java IDE (intelliJ , VSCode)
2. run the following file in order
    PowerStationServer.java -> ComputingUnitServer.java -> DummyClient.java
3. You can run DummyClient any number of times. these clone of DummyClients would interact with the same ComputingUnitServer.
4. if you wish to add extra server in the middle level You Can change the corresponding port number in the PortNumber.java file, changing the COMPUTING_UNIT_PORT_NUMBER value and then running the ComputingUnitServer.java file would create a new node in the middle level and make all new DummyClient - that are created after that modificaion - connect to that new node
5. the samething applys to the PowerStationServer.java file

best ragards
