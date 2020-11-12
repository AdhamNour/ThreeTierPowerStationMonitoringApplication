import java.io.IOException;
import java.net.UnknownHostException;

import communicationhandlers.*;
import servers.Constants;

/**
 * Client
 */
public class Client {

    public static void main(String[] args) throws UnknownHostException, IOException {
        ClientCommunicationHandler clientCommunicationHandler = new ClientCommunicationHandler("localhost",2500);
        String incomingMessage = clientCommunicationHandler.recieveMessage();
        System.out.println(incomingMessage);
        clientCommunicationHandler.sendMessage(Constants.GET_POWER_STATION_NAME);
        incomingMessage = clientCommunicationHandler.recieveMessage();
        System.out.println(incomingMessage);
        clientCommunicationHandler.sendMessage(Constants.GET_CERTAIN_SENSOR);
        clientCommunicationHandler.sendMessage(Integer.valueOf(1).toString());
        incomingMessage = clientCommunicationHandler.recieveMessage();
        System.out.println(incomingMessage);
        clientCommunicationHandler.sendMessage(Constants.END_REQUESTS);


        
        
    }
}