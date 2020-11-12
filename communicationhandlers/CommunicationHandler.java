package communicationhandlers;

import java.io.*;
import java.net.*;

public abstract class CommunicationHandler {
    private Socket clientSocket;
    private PrintWriter printer;
    private BufferedReader reader;
    private static final String ACKNOWLEDGMENT ="ack";
    
    protected void acceptConnection(Socket ClientSocket) throws IOException {
        clientSocket = ClientSocket;
        InputStreamReader serverInputStreamReader = new InputStreamReader(clientSocket.getInputStream());
        reader = new BufferedReader(serverInputStreamReader);
        printer = new PrintWriter(clientSocket.getOutputStream());
    }
    public void sendMessage(String message) throws IOException {
        //TODO: try not to forget to handle the case where the client socket is not exist
        printer.println(message);
        printer.flush();
        long start = System.currentTimeMillis();
        while(System.currentTimeMillis()- start <0.1);
    }
    public String recieveMessage() throws IOException {
        String inputMessage = reader.readLine();
        return inputMessage;
    }
}
