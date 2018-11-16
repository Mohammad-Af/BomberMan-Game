package main;

import UI.Images;
import components.Board;
import components.BomberMan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Game {
    private String name;
    private Board board;
    private List<BomberMan> bombermans = new ArrayList<>();
    private HashMap<Integer, BomberMan> bombermansMap = new HashMap<>();
    public static int level;
    private BomberMan mainBomberman;
    private String clientName;
    private int remainTime=300;

    public Game(String name, int level, String clientName) throws IOException {

        this.clientName = clientName;
        this.name = name;
        Game.level = level;
        new Images();


    }

    public Board getBoard() {
        return board;
    }


    public void setBoard(Board board) {
        this.board = board;
    }

    public List<BomberMan> getBomberMans() {
        return bombermans;
    }

    public BomberMan getBomberMansByID(int id) {
        return bombermansMap.get(id);
    }

    public void addBomberMan(BomberMan bomberMan) {
        bombermans.add(bomberMan);
        bombermansMap.put(bomberMan.getID(), bomberMan);
        if (bomberMan.getName().equals(clientName))
            mainBomberman = bomberMan;
    }


    public void removeBomberman(int j) {
        bombermansMap.remove(bombermans.remove(j).getID());
    }

    public void removeAllBomberMans() {
        bombermans.clear();
        bombermansMap.clear();
    }

    public String getName() {
        return name;
    }

    public BomberMan getMainBomberman() {
        return mainBomberman;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }

    public int getRemainTime() {
        return remainTime;
    }
}
