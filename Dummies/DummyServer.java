package Dummies;

import java.io.IOException;
import java.net.*;

public class DummyServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            (new DummyServerThread(clientSocket)).start();
        }
    }
    
}

