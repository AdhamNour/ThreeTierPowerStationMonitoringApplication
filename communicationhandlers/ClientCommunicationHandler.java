package communicationhandlers;

import java.io.IOException;
import java.net.*;

public class ClientCommunicationHandler extends CommunicationHandler {
    public ClientCommunicationHandler(String address, int port) throws UnknownHostException, IOException {
        Socket socket = new Socket(address,port);
        super.acceptConnection(socket);
    }
}
