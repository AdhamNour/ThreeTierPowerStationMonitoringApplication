package servers;

import java.io.IOException;
import java.net.Socket;

import models.Client;

/**
 * AbstractServerThread
 */
public abstract class AbstractServerThread extends Thread {
    protected Client client;
    protected AbstractServerThread(Socket socket) throws IOException {
        client = new Client(socket);
        System.out.println("Successfull Connextion with" + client.getID());
    }
}