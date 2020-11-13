package servers.PowerStationServer;

import java.io.IOException;
import java.net.*;

import servers.PortNumber;

public class PowerStationServer {
    public static void main(String[] args) {
        try {
            ServerSocket powerStationServerSocket = new ServerSocket(PortNumber.POWER_STATION_PORT_NUMBER);
            while (true) {
                Socket clientSocket = powerStationServerSocket.accept();
                (new PowerStationServerThread(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("Failure in server socket creation please try again\n\t" +e.getMessage());
            //e.printStackTrace();
        }
    }
}
