package controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientHandler extends Thread {
    private String username;
    private BufferedReader in;
    private DataOutputStream out;
    private HashMap<String,ClientHandler> clients;
    public ClientHandler(String username, BufferedReader in, DataOutputStream out, HashMap<String,ClientHandler> clients) {
        this.in = in;
        this.out = out;
        this.clients = clients;
        clients.put(username,this);
        start();
    }

    public void send(String message) {
        try {
            out.writeBytes(message + "\n");
            out.flush(); // Ensure data is sent immediately
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendTo(String username, String message) {
        ClientHandler client = clients.get(username);
        if (client != null) {
            client.send(message);
        }
    }

    public void run() {
        while (true) {
            try {
                String username = in.readLine();
                String message = in.readLine();
                if (message == null) { // Client disconnected
                    break;
                }
                System.out.println(message);
                sendTo(username,message);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
