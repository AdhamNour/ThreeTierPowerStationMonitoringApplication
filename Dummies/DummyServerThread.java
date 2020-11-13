package Dummies;

import java.io.IOException;
import java.net.Socket;

import models.Client;

public class DummyServerThread extends Thread {
    private Client client;
    public DummyServerThread(Socket socket) throws IOException {
        client = new Client(socket);
        System.out.println("Successfull Connextion with" + client.getID());
    }
    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                String msg = client.recieveMessage();
                System.out.println("I am the server and received the following form the client\n\t"+msg);
                client.sendMessage("Hi there");
            } catch (IOException e) {
                System.out.println("Connection Ended");
                break;
            }
        }
    }
}