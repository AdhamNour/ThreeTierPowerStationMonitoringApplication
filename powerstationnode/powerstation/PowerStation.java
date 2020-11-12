package powerstationnode.powerstation;

import java.util.*;

import javax.swing.Action;

public class PowerStation {
    private ArrayList<Sensor> stationSensors;
    private final String powerStationName ;
    private final Long stationNumber; 

    public PowerStation(int noOfSensors, String name) {
        stationSensors = new ArrayList<Sensor>();
        for (int i = 0; i < noOfSensors; i++) {
            stationSensors.add(new Sensor(25));
        }
        powerStationName = name;
        stationNumber= (new Random()).nextLong();
    }
    public Long getStationNumber(){
        return stationNumber;
    }

    public int getNumberOfSensors() {
        return stationSensors.size();
    }

    public void addSensor(float maxReading) {
        stationSensors.add(new Sensor(maxReading));
    }

    public ArrayList<Float> getSensorsReading() {
        ArrayList<Float> readings = new ArrayList<>();
        stationSensors.forEach(n -> readings.add(Float.valueOf(n.getSensorReading())));
        return readings;
    }
    public String getPowerPlantName(){
        return powerStationName;
    }
}
