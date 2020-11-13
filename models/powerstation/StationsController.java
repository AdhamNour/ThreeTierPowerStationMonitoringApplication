package models.powerstation;

import java.util.*;

public class StationsController {
    private static StationsController controller;
    private HashMap<String,PowerStation> stations;
    
    private StationsController(){
        stations = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            PowerStation ps = new PowerStation(10, "Power Station#"+Integer.toString(i));
            stations.put(ps.getID(), ps);
        }
    }

    public static StationsController getInstance(){
        if (controller == null){
            controller = new StationsController();
        }
        return controller;
    }

    public void addStation(){
        PowerStation station = new PowerStation(10, "New Station");
        stations.put(station.getID(), station);
    }
    public ArrayList<Float> getAllSensorsReadingForTheStation(String stationID){
        return stations.get(stationID).getSensorsReading();
    }
    public int getNumberofSensorForTheStation(String stationID){
        return stations.get(stationID).getNumberOfSensors();
    }
    public void addSensorForTheStation(String stationID , float maxReading){
        stations.get(stationID).addSensor(maxReading);
    }
    public String getPowerStationNameForTheStation(String stationID){
        return stations.get(stationID).getPowerPlantName();
    }

    public void removeSensorFromTheStation(String stationID,int index){
        stations.get(stationID).removeSensor(index);
    }

    public ArrayList<String>getAllIds(){
        ArrayList<String> result = new ArrayList<>();
        Object[] x = stations.keySet().toArray();
        for (int i = 0; i < x.length; i++) {
            result.add(x[i].toString());
            //bug may exist here
        }
        return result;
    }
    public void setSensorMaxValueForTheSensor(String stationID,int i , float maxValue){
        stations.get(stationID).setSensorMaxValue(i, maxValue);
    }
}
