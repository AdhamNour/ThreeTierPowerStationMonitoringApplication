package models;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;

public class Client {

    private Socket clientSocket;
    private PrintWriter writer;
    private BufferedReader reader;
    private UUID id;
    public String getID(){
        return id.toString();   
    }

    protected void initialize() throws IOException {
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        writer = new PrintWriter(clientSocket.getOutputStream());
        id = UUID.randomUUID();
    }

    public void sendMessage(String message) throws IOException {
        // TODO: try not to forget to handle the case where the client socket is not
        // exist
        writer.println(message);
        writer.flush();
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 0.1)
            ;
    }

    public String recieveMessage() throws IOException {
        String inputMessage = reader.readLine();
        return inputMessage;
    }

    public Client(String Address, int Port) throws UnknownHostException, IOException {
        clientSocket = new Socket(Address, Port);
        initialize();
    }

    public Client(Socket incommingClientSocket) throws IOException {
        clientSocket = incommingClientSocket;
        initialize();
    }

}
