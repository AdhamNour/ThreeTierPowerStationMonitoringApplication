import java.io.IOException;

import communicationhandlers.*;

/**
 * Server
 */
public class Server {

    public static void main(String[] args) throws IOException {
        ServerCommunicationHandler serverCommunicationHandler = new ServerCommunicationHandler(5000);
        serverCommunicationHandler.acceptConnection();
        serverCommunicationHandler.sendMessage("Hello World");
        for (int i = 0; i < 20000; i++) {
            String inCommingMsg = serverCommunicationHandler.recieveMessage();
            System.out.println(inCommingMsg);
        }
    }
}