package client;

import UI.StartFrame;
import components.BomberMan;
import main.Game;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {

    private Sender sender ;
    private Socket mySocket;
    private boolean connected = false;
    private PrintWriter printWriter;
    private Scanner scanner;
    private String clientName ;
    private boolean player;


    public Client(String clientName, boolean player, String IP, int port) throws IOException {

        this.clientName=clientName;
        this.player=player;

        try {
            mySocket = new Socket(IP, port);
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            printWriter = new PrintWriter(mySocket.getOutputStream(),true);
            scanner = new Scanner(mySocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        printWriter.println("Connecting");                    // not CHECKING IP range anymore

        int games = scanner.nextInt();
        List<Game> serverGames = new ArrayList<>();
        for (int i = 0; i < games; i++) {

            scanner.nextLine();

            Game game = new Game(scanner.nextLine(),scanner.nextInt(),clientName);

            int bombermans = scanner.nextInt();
            for (int j = 0; j < bombermans; j++) {
                int id =scanner.nextInt();
                scanner.nextLine();
                String name = scanner.nextLine();
                int point = scanner.nextInt();
                BomberMan bomberMan =new BomberMan(id,name,0,0,point,0,0);
                game.addBomberMan(bomberMan);
            }

            serverGames.add(game);

        }


         new StartFrame(this, serverGames);


    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public boolean isConnected() {
        return connected;
    }

    public Sender getSender() {
        return sender;
    }

    public void requestNewGame(String name, int level, int width, int height, int res, int monstersCount) {
        printWriter.println(clientName);
        printWriter.println("NEW GAME");

        printWriter.println(name);
        printWriter.println(level+" "+width+" "+height+" "+res+" "+monstersCount+" ");

        if(player)
            printWriter.println("PLAYER");
        else
            printWriter.println("VIEWER");

        start();


    }

    public void joinGame(Game game) {

        printWriter.println(clientName);
        printWriter.println("JOIN GAME");

        printWriter.println(game.getName());

        if(player)
            printWriter.println("PLAYER");
        else
            printWriter.println("VIEWER");

        start();

    }

    private void start() {


        if(player)
        sender  = new Sender(this);

        Receiver receiver = new Receiver(this);

        Thread t = new Thread(receiver);
        t.start();
    }

    public String getClientName() {
        return clientName;
    }

    public boolean isPlayer() {
        return player;
    }

    public void disconnect() {
        sender.exit();
    }

    public void setConnect(boolean connect) {
        this.connected = connect;
    }
}
