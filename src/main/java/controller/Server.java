package controller;

import model.data;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class Server {
    private HashMap<String, ClientHandler> clients = new HashMap<>();
    public Server() throws Exception {
        ServerSocket serverSocket = new ServerSocket(8088);
        System.out.println("Server started");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Da ket noi den SERVER");
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                String username = in.readLine();
                ClientHandler clientHandler = new ClientHandler(username,in, out, clients);
            }
            catch (Exception e) {
                socket.close();
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new Server();
    }

}
