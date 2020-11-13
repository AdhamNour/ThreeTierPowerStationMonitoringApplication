package servers.PowerStationServer;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.print.DocFlavor.STRING;

import models.powerstation.StationsController;
import servers.AbstractServerThread;
import servers.Constants;

public class PowerStationServerThread extends AbstractServerThread {

    public PowerStationServerThread(Socket socket) throws IOException {
        super(socket);
        System.out.println("\t from the PowerStatonServerThread");
    }

    @Override
    public void run() {
        super.run();
        StationsController stationsController = StationsController.getInstance();
        try {

            client.sendMessage(Constants.BEGIN_OF_MESSAGE_STREAM.toString());
            ArrayList<String> ids = stationsController.getAllIds();
            for (int i = 0; i < ids.size(); i++) {
                client.sendMessage(ids.get(i));
            }
            client.sendMessage(Constants.END_OF_MESSAGE_STREAM.toString());

            String msg = client.recieveMessage();
            String stationID;
            Constants orderType;
            while (true) {
                while (true) {
                    try {
                        orderType = Constants.valueOf(msg);
                        break;
                    } catch (Exception e) {
                        msg = client.recieveMessage();
                    }
                }
                switch (orderType) {
                    case GET_ALL_SENSORS_READING_FOR_THE_STATION:
                        stationID = client.recieveMessage();
                        client.sendMessage(stationsController.getAllSensorsReadingForTheStation(stationID).toString());
                        ;
                        break;
                    case GET_NUMBER_OF_SENSORS_FOR_THE_STATION:
                        stationID = client.recieveMessage();
                        client.sendMessage(
                                Integer.toString(stationsController.getNumberofSensorForTheStation(stationID)));
                        
                        break;
                    case GET_POWER_STATION_NAME_FOR_THE_STATION:
                        stationID = client.recieveMessage();
                        client.sendMessage(stationsController.getPowerStationNameForTheStation(stationID));
                        break;
                    case SET_THE_MAX_VALUE_OF_I_TH_SENSOR_OF_THE_STATION:
                        stationID = client.recieveMessage();
                        int i = Integer.parseInt(client.recieveMessage());
                        float maxValue = Float.parseFloat(client.recieveMessage());
                        stationsController.setSensorMaxValueForTheSensor(stationID, i, maxValue);
                        break;

                    default:
                        System.out.println("I am not supposed to reach that case");
                        client.sendMessage("I am not supposed to reach that case");
                        break;
                }
                msg = client.recieveMessage();

            }
        } catch (IOException e) {
            System.out.println("Connection lost with the client " + client.getID());
        }

    }

}
