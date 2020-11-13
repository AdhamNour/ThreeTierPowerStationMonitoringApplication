
import java.io.*;
import java.net.*;
import java.util.*;

import models.Client;
import servers.Constants;
import servers.PortNumber;

/**
 * DummyClient
 */
public class DummyClient {

    public static void main(String[] args) throws UnknownHostException, IOException {
        Client client = new Client("localhost", PortNumber.COMPUTING_UNIT_PORT_NUMBER);
        String msg = client.recieveMessage();
        Constants x = Constants.valueOf(msg);
        ArrayList<String> ids = new ArrayList<>();
        if (x.equals(Constants.BEGIN_OF_MESSAGE_STREAM))
            while (!x.equals(Constants.END_OF_MESSAGE_STREAM)) {
                msg = client.recieveMessage();
                try {
                    x = Constants.valueOf(msg);
                } catch (IllegalArgumentException e) {
                    ids.add(msg);
                    System.out.println(msg);
                }
            }
        System.out.println("finished Loading");
        Serve(client, ids);
    }

    private static void Serve(Client client, ArrayList<String> ids) throws IOException {
        String msg;
        Constants[] testers = { Constants.GET_ALL_SENSORS_READING_FOR_THE_STATION,
                Constants.GET_NUMBER_OF_SENSORS_FOR_THE_STATION, Constants.GET_POWER_STATION_NAME_FOR_THE_STATION };
        Random rand = new Random();
        while (true) {
            Constants constant = testers[rand.nextInt(testers.length)];
            String stationID = ids.get(rand.nextInt(ids.size()));
            System.out.println("I am sending to the Station with ID\n\t" + stationID + "\nRequest of type:\n\t"
                    + constant.toString());
            client.sendMessage(constant.toString());
            client.sendMessage(stationID);
            msg= client.recieveMessage();
            System.out.println("I recieved\n\t"+msg);
            System.out.println();

            constant = Constants.GET_NUMBER_OF_SENSORS_FOR_THE_STATION;
            client.sendMessage(constant.toString());
            client.sendMessage(stationID);
            msg= client.recieveMessage();
            System.out.println("The Station with the ID \n\t"+stationID+" "+msg+" sensor");
            System.out.println("Checking them off");

            int sensorNum = Integer.parseInt(msg);
            for (int i = 0; i < sensorNum; i++) {
                client.sendMessage(Constants.GET_THE_I_TH_SENSOR_FROM_THE_STATION.toString());
                client.sendMessage(stationID);
                client.sendMessage(Integer.toString(i));
                msg = client.recieveMessage();
                System.out.println(msg);
                if (Float.parseFloat(msg) < 10) {
                    client.sendMessage(Constants.SET_THE_MAX_VALUE_OF_I_TH_SENSOR_OF_THE_STATION.toString());
                    client.sendMessage(stationID);
                    client.sendMessage(Integer.toString(i));
                    client.sendMessage(Float.toString(rand.nextFloat()%101));
                }
                if (Float.parseFloat(msg) > 10) {
                    client.sendMessage(Constants.SET_THE_MAX_VALUE_OF_I_TH_SENSOR_OF_THE_STATION.toString());
                    client.sendMessage(stationID);
                    client.sendMessage(Integer.toString(i));
                    client.sendMessage(Float.toString(-1*rand.nextFloat()%101));
                }
            }
            
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start <= 1000) ;
        }
    }
}