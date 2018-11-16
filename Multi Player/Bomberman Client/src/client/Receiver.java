package client;

import UI.GameFrame;
import UI.Images;
import components.Board;
import components.Bomb;
import components.BomberMan;
import components.Monster;
import main.Game;

import java.awt.*;
import java.io.IOException;
import java.util.Scanner;

public class Receiver implements Runnable {


    private Client client;
    private Scanner scanner;
    private Game game;
    private Board board;
    private GameFrame gameFrame;

    Receiver(Client client) {
        this.client = client;
        scanner = client.getScanner();

    }

    @Override
    public void run() {

        GameFrame.width = scanner.nextInt();
        GameFrame.height = scanner.nextInt();


        while (client.isConnected()) {


            int command=scanner.nextInt();
            if(command == 1){
                receiveLevelSetting();
            }else if(command == 2){
                gameFrame.exit();
                client.disconnect();
                break;
            }


            int bomberMans = scanner.nextInt();
            int index = -1;
            int size = game.getBomberMans().size();
            for (int i = 0; i < bomberMans; i++) {
                int id = scanner.nextInt();
                BomberMan bomberMan = game.getBomberMansByID(id);
                if (bomberMan != null) {
                    scanner.next();
                    bomberMan.setLocation(new Point(scanner.nextInt(), scanner.nextInt()));
                    bomberMan.setPoint(scanner.nextInt());
                    bomberMan.setLastIndexImage(scanner.nextInt());
                    bomberMan.setLastMove(scanner.nextInt());

                    for (int j = index + 1; j < game.getBomberMans().indexOf(bomberMan); j++) {              //remove dead bombermans
                        game.removeBomberman(j);
                    }
                    index = game.getBomberMans().indexOf(bomberMan);

                } else {
                    bomberMan = new BomberMan(id, scanner.next(),scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                    game.addBomberMan(bomberMan);
                }

            }
            for (int i = index + 1; i < Math.min(size, game.getBomberMans().size()); i++) {
                game.removeBomberman(i);
            }
            if (size != 0 && index == -1) {                                             //if all bombermans are new
                game.removeAllBomberMans();
            }


            int monsters = scanner.nextInt();
            int size2 = board.getMonsters().size();
            index = -1;
            for (int i = 0; i < monsters; i++) {
                int id = scanner.nextInt();

                Monster monster = board.getMonsterByID(id);
                if (monster != null) {
                    int imageArrayID = scanner.nextInt();
                    monster.setLocation(new Point(scanner.nextInt(), scanner.nextInt()));
                    monster.setLastIndexImage( scanner.nextInt());
                    monster.initLastMove(scanner.nextInt(), imageArrayID);

                    for (int j = index + 1; j < board.getMonsters().indexOf(monster); j++) {           //remove dead monsters
                        board.removeMonster(j);
                    }
                    index = board.getMonsters().indexOf(monster);

                } else {
                        int imageArrayID=scanner.nextInt();
                        monster = new Monster(scanner.nextInt(),scanner.nextInt(),imageArrayID);
                        monster.setLastIndexImage(scanner.nextInt());
                        monster.initLastMove(scanner.nextInt(), imageArrayID);
                        monster.setID(id);

                        game.getBoard().addMonster(monster);

                }
            }
            for (int i = index + 1; i < Math.min(size2, game.getBoard().getMonsters().size()); i++) {
                board.removeMonster(i);
            }
            if (size2 != 0 && index == -1) {
                board.removeAllMonster();                     //if all monsters are new
            }




            while (true) {
                int cellIndex = scanner.nextInt();
                if (cellIndex == -111)
                    break;
                else {
                    if (cellIndex != -1)
                        game.getBoard().burnWallCell(cellIndex, scanner.nextInt());
                }

            }

            while (true){
                int id = scanner.nextInt();
                if(id == -222)
                    break;
                else {
                    game.getBoard().removePower(id);
                }
            }

            while (true){
                int id = scanner.nextInt();
                if(id == -333)
                    break;
                else
                    game.getBoard().burnPower(id);
            }

            while (true) {
                int cellIndex = scanner.nextInt();
                if (cellIndex == -444)
                    break;
                else {
                    if (cellIndex != -1) {
                        game.getBoard().burnBackgroundCell(cellIndex);
                    }
                }

            }

            while (true){
                int id =scanner.nextInt();
                if(id != -555){
                    Bomb bomb = board.getBombByID(id);

                    if (bomb != null) {
                        bomb.setLocation(new Point(scanner.nextInt(), scanner.nextInt()));
                    } else {
                        bomb = new Bomb(id, scanner.nextInt(), scanner.nextInt());
                        board.addBomb(bomb);
                    }
                    bomb.setCurrentSize(scanner.nextInt());
                    bomb.setCurrentImage(Images.bomb[scanner.nextInt()]);
                }else
                    break;
            }



            if(gameFrame == null) {
                gameFrame = new GameFrame(client, game);
                gameFrame.setRemainTime(game.getRemainTime());
            }

            scanner.nextLine();
            while (true){
                String message = scanner.nextLine();
                if(!message.equals("END") && client.isPlayer()){
                    gameFrame.getChatFrame().recieveMessage(message);
                }else
                    break;
            }



            gameFrame.repaint();

        }
    }

    private void receiveLevelSetting() {

        if(gameFrame!=null) {
            gameFrame.dispose();
            gameFrame.getChatFrame().dispose();
            gameFrame=null;
        }

        try {
            game = new Game(null,scanner.nextInt(), client.getClientName());
            game.setRemainTime(scanner.nextInt());

        } catch (IOException e) {
            e.printStackTrace();
        }



        board = new Board(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
        game.setBoard(board);


        board.loadObjects(scanner);


    }


}
