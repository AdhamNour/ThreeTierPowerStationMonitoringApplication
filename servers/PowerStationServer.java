package servers;

import java.io.IOException;

import communicationhandlers.ServerCommunicationHandler;
import powerstationnode.powerstation.PowerStation;

/**
 * PowerStationServer
 */
public class PowerStationServer {


    public static void main(String[] args) throws IOException {
        PowerStation powerStation = new PowerStation(10, "Hydro");

        ServerCommunicationHandler powerStationServerCommunicationHandler = new ServerCommunicationHandler(2000);
        while (true) {
            powerStationServerCommunicationHandler.acceptConnection();
            String message = powerStationServerCommunicationHandler.recieveMessage();
            while (!message.equals(Constants.END_REQUESTS)) {
                if (message.equals(Constants.GET_ALL_SENSORS)) {
                    powerStationServerCommunicationHandler.sendMessage(powerStation.getSensorsReading().toString());
                }
                if(message.equals(Constants.GET_POWER_STATION_NUMBER)){
                    powerStationServerCommunicationHandler.sendMessage(powerStation.getStationNumber().toString());
                }
                if (message.equals(Constants.GET_POWER_STATION_NAME)) {
                    powerStationServerCommunicationHandler.sendMessage(powerStation.getPowerPlantName());
                }
                message = powerStationServerCommunicationHandler.recieveMessage();
            }
            
            
        }
    }
}