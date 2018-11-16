package server;

import main.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Server {

    private ServerSocket mServer;
    private ArrayList<Thread> threads = new ArrayList<Thread>();
    private HashMap<String, ClientManager> clientsMap = new HashMap<String, ClientManager>();
    private List<Game> games = new ArrayList<>();
    private boolean running = false;

    public Server() {

        try {

            int serverPort = 9090;
            mServer = new ServerSocket(serverPort);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void start() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                running = true;
                while (true) {

                    Socket client = null;
                    try {
                        client = mServer.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Scanner scanner = null;
                    try {
                        scanner = new Scanner(client.getInputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(!scanner.next().equals("CHECKING")) {

                        System.out.println("Connected to New Client!");


                        Thread t = null;
                        try {
                            t = new Thread(new ClientManager(Server.this, client));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        threads.add(t);

                        t.start();
                    }

                }
            }
        });
        thread.start();

    }

    public void addClientManager(String clientName, ClientManager cm) {
        clientsMap.put(clientName, cm);
    }

    public List<Game> getGames() {
        return games;
    }

    public void addGame(Game game) {
        if(!games.contains(game))
        games.add(game);
    }

    public boolean isRunning() {
        return running;
    }

    public HashMap<String, ClientManager> getClientsMap() {
        return clientsMap;
    }
}
