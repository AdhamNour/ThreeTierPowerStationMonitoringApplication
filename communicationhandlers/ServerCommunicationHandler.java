package communicationhandlers;
import java.io.*;
import java.net.*;

/**
 * ServerCommunicationHandler
 */
public class ServerCommunicationHandler extends CommunicationHandler {

    /**
     * this class is supposed to handle any 
     * communication between the server and their
     * clients using some shady bloody rocky darkwebish protocol
     */
    private ServerSocket serverSocket; //should be institiated in the constrcutor
    
    public ServerCommunicationHandler(int portNumber) throws IOException {
        serverSocket = new ServerSocket(portNumber);        
    }
    public void acceptConnection() throws IOException {
        Socket clientSocket = serverSocket.accept();
        super.acceptConnection(clientSocket);
    }
    
    
}