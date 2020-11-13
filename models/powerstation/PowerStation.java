package models.powerstation;

import java.util.ArrayList;
import java.util.UUID;

public class PowerStation {
    private ArrayList<Sensor> stationSensors;
    private final String powerStationName ;
    private final String powerStationID; 

    public PowerStation(int noOfSensors, String name) {
        stationSensors = new ArrayList<Sensor>();
        for (int i = 0; i < noOfSensors; i++) {
            stationSensors.add(new Sensor(25));
        }
        powerStationName = name;
        powerStationID = UUID.randomUUID().toString();
    }
    public String getID(){
        return powerStationID;
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
    
    public void removeSensor(int index){
        stationSensors.remove(index);
    }
    public void setSensorMaxValue(int i,float maxValue){
        stationSensors.get(i).setMaxReading(maxValue);
    }
}