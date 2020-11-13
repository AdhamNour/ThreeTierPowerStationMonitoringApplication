package servers.ComputingUnitServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;

import models.Client;
import servers.Constants;
import servers.PortNumber;

public class ComputingUnitServer {
    public static void main(String[] args) throws UnknownHostException, IOException {

        try {
            ServerSocket computingUnitServerSocket = new ServerSocket(PortNumber.COMPUTING_UNIT_PORT_NUMBER);
            while (true) {
                Socket clientSocket = computingUnitServerSocket.accept();
                (new ComputingUnitServerThread(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("Failure in server socket creation please try again\n\t" + e.getMessage());
            // e.printStackTrace();
        }
    }
}
