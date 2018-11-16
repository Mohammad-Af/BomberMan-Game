package server;

import UI.StartFrame;
import components.Board;
import components.BomberMan;
import components.cell.Cell;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class ClientManager implements Runnable {

    private Server serverHolder;

    private Scanner reader;
    private PrintWriter writer;

    private Game game;
    private BomberMan bomberMan;

    private boolean goingToNextLevel = true;
    private int lastSentMessageIndex = 0;

    private boolean connect = true;


    ClientManager(Server server, Socket client) throws IOException {
        serverHolder = server;

        InputStream fromClientStream = client.getInputStream();

        OutputStream toClientStream = client.getOutputStream();

        reader = new Scanner(fromClientStream);
        writer = new PrintWriter(toClientStream, true);


        init();

    }

    public void run() {
        try {

            Thread receiver = new Thread(new Runnable() {
                @Override
                public void run() {

                    label:
                    while (connect && game.isRunning()) {

                        try {

                            String command = reader.next();
                            switch (command) {
                                case "ACTION":
                                    int e = reader.nextInt();
                                    if (e == KeyEvent.VK_B)
                                        bomberMan.throwBomb();
                                    else if (e == KeyEvent.VK_SPACE)
                                        bomberMan.explodeBomb();
                                    else
                                        bomberMan.shouldMove(e);
                                    break;
                                case "MESSAGE":
                                    String message = reader.nextLine();
                                    game.addMessage(bomberMan.getName() + " : " + message);
                                    break;
                                case "END":
                                    connect = false;
                                    game.removeBomberman(bomberMan);
                                    break label;
                            }
                        } catch (NoSuchElementException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
            receiver.start();


            Thread sender = new Thread(new Runnable() {
                @Override
                public void run() {


                    int width = 15 * Board.cellsSize;                            //frame width
                    int height = 11 * Board.cellsSize;                           //frame height
                    if (game.getBoard().getHeight() < 6) {
                        width = (2 * game.getBoard().getHeight() + 3) * Board.cellsSize;
                    }
                    if (game.getBoard().getWidth() < 5) {
                        height = (2 * game.getBoard().getWidth() + 3) * Board.cellsSize;
                    }

                    writer.println(width);
                    writer.println(height);


                    while (connect && game.isRunning()) {

                        if (goingToNextLevel) {
                            writer.println(1);
                            sendLevelSetting();
                            goingToNextLevel = false;
                        } else
                            writer.println(0);


                        try {
                            sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        writer.println(game.getBomberMans().size());
                        for (BomberMan bomberMan : game.getBomberMans()) {
                            bomberMan.save(writer);
                        }

                        game.getBoard().saveMonsters(writer);

                        game.getBoard().sendExplodedWalls(writer);

                        game.getBoard().sendUsedPowers(writer);

                        game.getBoard().sendBurningBackgroundCells(writer);

                        game.getBoard().saveBombs(writer);

                        lastSentMessageIndex = game.sendMessages(lastSentMessageIndex, writer);


                    }
                    writer.println(2);   // when game ends
                }


            });
            sender.start();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    private void sendLevelSetting() {
        writer.println(game.getLevel() + " " + game.getRemainTime() + " ");

        game.getBoard().save(writer);
    }

    private void init() {

        //first sends games information

        writer.println(serverHolder.getGames().size());

        for (int i = 0; i < serverHolder.getGames().size(); i++) {

            Game game = serverHolder.getGames().get(i);
            writer.println(game.getName());
            writer.println(game.getLevel());

            writer.println(game.getBomberMans().size());

            for (int j = 0; j < game.getBomberMans().size(); j++) {
                writer.println(game.getBomberMans().get(j).getID());
                writer.println(game.getBomberMans().get(j).getName());
                writer.println(game.getBomberMans().get(j).getPoint());
            }
        }

        //get requests from client and init game for it

        String name = reader.nextLine();
        serverHolder.addClientManager(name, this);

        String command = reader.nextLine();


        if (command.equals("NEW GAME")) {
            String gameName = reader.nextLine();
            int level = reader.nextInt();
            int boardWidth = reader.nextInt();
            int boardHeight = reader.nextInt();
            int res = reader.nextInt();
            int monstersCount = reader.nextInt();

            game = StartFrame.NewGameFrame.init(gameName, level, boardWidth, boardHeight, res, monstersCount, null);


        } else if (command.equals("JOIN GAME")) {
            String gameName = reader.nextLine();
            for (int i = 0; i < serverHolder.getGames().size(); i++) {
                Game game = serverHolder.getGames().get(i);
                if (game.getName().equals(gameName)) {
                    this.game = game;
                }
            }
        }


        if (!reader.next().equals("VIEWER")) {
            bomberMan = new BomberMan(name, game);
            bomberMan.setSpeed(40);
            bomberMan.setBombLimit(1);
            bomberMan.setCanControlBombs(false);
            bomberMan.setBombRadius(1);
            bomberMan.setGhostMood(false);
            Cell cell = (game.getBoard().getFreeCells().remove(0));
            bomberMan.setRealLocation(new Point(cell.getLocation().x, cell.getLocation().y));         // chera setRealLocation(components.cell.location) kharab mishe !!??
            game.addBomberMan(bomberMan);
            game.getBoard().getBombermanPlacesArray().add(bomberMan.getRealLocation());
            game.getBoard().initBombermans(bomberMan.hashCode());
        }

    }


    public void setGame(Game game) {
        this.game = game;
    }

    public void setBomberMan(BomberMan bomberMan) {
        this.bomberMan = bomberMan;
    }

    public void setGoingToNextLevel(boolean goingToNextLevel) {
        this.goingToNextLevel = goingToNextLevel;
    }

    public Game getGame() {
        return game;
    }
}
