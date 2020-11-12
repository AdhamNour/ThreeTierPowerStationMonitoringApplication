package servers;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import communicationhandlers.ClientCommunicationHandler;
import communicationhandlers.ServerCommunicationHandler;
import servers.PowerStationServer;

/**
 * ComputingUnitServer
 */
public class ComputingUnitServer {

    private static int no_calls = 0;
    private static ArrayList<Float> sensorAverage;
    private static Long powerStationNumber;
    private static String powerStationName;

    public static void main(String[] args) throws UnknownHostException, IOException {
        ClientCommunicationHandler computingUnitClientCommunicationHandler = new ClientCommunicationHandler("localhost",
                2000);
        initializeClientAttributes(computingUnitClientCommunicationHandler);
        ServerCommunicationHandler computingUnitServerCommunicationHandler = new ServerCommunicationHandler(2500);
        computingUnitServerCommunicationHandler.acceptConnection();
        computingUnitServerCommunicationHandler.sendMessage("Hello from Computeing unit server");

        while (true) {
            String msg = computingUnitServerCommunicationHandler.recieveMessage();
            System.out.println(msg);
            if (msg.equals(Constants.GET_POWER_STATION_NAME)) {
                System.out.println(powerStationName);
                computingUnitServerCommunicationHandler.sendMessage(powerStationName);
            }
            if (msg.equals(Constants.GET_CERTAIN_SENSOR)) {
                msg = computingUnitServerCommunicationHandler.recieveMessage();
                System.out.println(msg);
                try {
                    int index = Integer.parseInt(msg);
                    ArrayList<Float> sensorvalues = getAllSensorsReadings(computingUnitClientCommunicationHandler);
                    computingUnitServerCommunicationHandler.sendMessage(sensorvalues.get(index).toString());
                } catch (Exception e) {
                    //TODO: handle exception
                }
            }
            if(msg.equals(Constants.END_REQUESTS)){
                break;
            }
            else {
                System.out.println("try again");
            }
        }
        // System.out.println(getAllSensorsReadings(computingUnitClientCommunicationHandler).toString());
        // //getting a single value
        // System.out.println("the folloeing is the value of single sensor");
        // System.out.println(getAllSensorsReadings(computingUnitClientCommunicationHandler).get(0).toString());

        computingUnitClientCommunicationHandler.sendMessage(Constants.END_REQUESTS);
    }

    private static void initializeClientAttributes(ClientCommunicationHandler computingUnitClientCommunicationHandler)
            throws IOException {
        computingUnitClientCommunicationHandler.sendMessage(Constants.GET_POWER_STATION_NUMBER);
        String result = computingUnitClientCommunicationHandler.recieveMessage();
        powerStationNumber = Long.parseLong(result);
        computingUnitClientCommunicationHandler.sendMessage(Constants.GET_POWER_STATION_NAME);
        powerStationName = computingUnitClientCommunicationHandler.recieveMessage();
    }

    private static ArrayList<Float> getAllSensorsReadings(
            ClientCommunicationHandler computingUnitClientCommunicationHandler) throws IOException {
        computingUnitClientCommunicationHandler.sendMessage(Constants.GET_ALL_SENSORS);
        String sensorResults = computingUnitClientCommunicationHandler.recieveMessage();
        StringBuilder stringBuilder = new StringBuilder(sensorResults);
        stringBuilder.deleteCharAt(0);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        String[] split = stringBuilder.toString().split(",");
        ArrayList<Float> sensorsReadings = new ArrayList<>();
        for (String string : split) {
            Float f = Float.valueOf(string);
            sensorsReadings.add(f);
        }
        acumelating(sensorsReadings);

        return sensorsReadings;
    }

    private static void acumelating(ArrayList<Float> newReadings) {
        if (no_calls == 0) {
            sensorAverage = newReadings;
        } else {
            for (int i = 0; i < sensorAverage.size(); i++) {
                Float currentValue = sensorAverage.get(i);
                Float newValue = newReadings.get(i);
                sensorAverage.set(i, currentValue + newValue);
            }
        }
        no_calls++;

    }

}