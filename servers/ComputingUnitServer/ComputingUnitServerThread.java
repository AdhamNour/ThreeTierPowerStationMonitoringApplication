package servers.ComputingUnitServer;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import models.Client;
import servers.AbstractServerThread;
import servers.Constants;

/**
 * ComputingUnitServerThread
 */
public class ComputingUnitServerThread extends AbstractServerThread {

    private static HashMap<String, ArrayList<Float>> sensorAverages;
    private static HashMap<String, Integer> nos_calls;

    public ComputingUnitServerThread(Socket socket) throws IOException {
        super(socket);
        System.out.println("\t from Computing Thread");
        sensorAverages = new HashMap<>();
        nos_calls = new HashMap<>();
    }

    @Override
    public void run() {
        super.run();
        try {
            System.out.println("loading the IDs of the station from the power station server ...");
            Client powerStationServerClient = new Client("localhost", 5000);
            String pwMsg = powerStationServerClient.recieveMessage();
            client.sendMessage(pwMsg);

            Constants x = Constants.valueOf(pwMsg);
            ArrayList<String> ids = new ArrayList<>();
            if (x.equals(Constants.BEGIN_OF_MESSAGE_STREAM))
                while (!x.equals(Constants.END_OF_MESSAGE_STREAM)) {
                    pwMsg = powerStationServerClient.recieveMessage();
                    client.sendMessage(pwMsg);
                    try {
                        x = Constants.valueOf(pwMsg);
                    } catch (IllegalArgumentException e) {
                        ids.add(pwMsg);
                    }
                }
            System.out.println("finished Loading");
            String myClientMessage;
            while (true) {
                myClientMessage = client.recieveMessage();
                x = gettingOrderType(x, myClientMessage);
                myClientMessage = client.recieveMessage();
                if (x.equals(Constants.GET_NUMBER_OF_SENSORS_FOR_THE_STATION)
                        || x.equals(Constants.GET_POWER_STATION_NAME_FOR_THE_STATION)) {
                    powerStationServerClient.sendMessage(x.toString());
                    powerStationServerClient.sendMessage(myClientMessage);
                    pwMsg = powerStationServerClient.recieveMessage();
                    client.sendMessage(pwMsg);
                }
                if (x.equals(Constants.GET_ALL_SENSORS_READING_FOR_THE_STATION)) {
                    client.sendMessage(getAllSensorsReadingsforTheStation(powerStationServerClient, myClientMessage).toString());
                }
                if (x.equals(Constants.GET_THE_I_TH_SENSOR_FROM_THE_STATION)) {
                    getAllSensorsReadingsforTheStation(powerStationServerClient, myClientMessage);
                    String i = client.recieveMessage();
                    int index = Integer.parseInt(i);
                    String result = Float
                            .toString(sensorAverages.get(myClientMessage).get(index) / nos_calls.get(myClientMessage));
                    client.sendMessage(result);
                }

            }
        } catch (IOException e) {
            System.out.println("Connection with The Power Station Server is lost");
        }

    }

    private Constants gettingOrderType(Constants x, String myClientMessage) throws IOException {
        while (true) {
            try {
                x = Constants.valueOf(myClientMessage);
                break;
            } catch (Exception e) {
                myClientMessage = client.recieveMessage();
            }
        }
        return x;
    }

    private static ArrayList<Float> getAllSensorsReadingsforTheStation(Client client, String stationID)
            throws IOException {
        client.sendMessage(Constants.GET_ALL_SENSORS_READING_FOR_THE_STATION.toString());
        client.sendMessage(stationID);

        String sensorResults = client.recieveMessage();
        StringBuilder stringBuilder = new StringBuilder(sensorResults);
        stringBuilder.deleteCharAt(0);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        String[] split = stringBuilder.toString().split(",");
        ArrayList<Float> sensorsReadings = new ArrayList<>();
        for (String string : split) {
            Float f = Float.valueOf(string);
            sensorsReadings.add(f);
        }
        acumelating(sensorsReadings, stationID);

        return sensorsReadings;
    }

    private static void acumelating(ArrayList<Float> newReadings, String stationID) {
        Integer no_calls = nos_calls.get(stationID);
        if (no_calls == null) {
            sensorAverages.put(stationID, newReadings);
            nos_calls.put(stationID, 1);
        } else {
            no_calls++;
            ArrayList<Float> sensorCurrentReadings = sensorAverages.get(stationID);
            for (int i = 0; i < sensorCurrentReadings.size(); i++) {
                Float x = sensorCurrentReadings.get(i);
                x += newReadings.get(i);
            }
        }

    }

}