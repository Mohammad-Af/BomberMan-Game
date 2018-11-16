package main;

import components.Board;
import components.BomberMan;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Game {

    private String name;
    private Board board;
    private List<BomberMan> bombermans = new ArrayList<>();
    private List<BomberMan> deadBombermans = new ArrayList<>();                   //used to bring dead bomberMans back at next level
    private HashMap<Integer, BomberMan> bombermansMap = new HashMap<>();
    private boolean running = true;
    private int level;
    private int remainTime = 300;
    private List<String> messages = new ArrayList<>();

    public Game(String name, int level)  {

        this.name = name;
        this.level = level;


    }

    public Board getBoard() {
        return board;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
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
    }


    public void removeBomberman(BomberMan bomberMan) {
        bombermans.remove(bomberMan);
        bombermansMap.remove(bomberMan.getID());
        getBoard().removeBomberman(bomberMan);

    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public List<BomberMan> getDeadBombermans() {
        return deadBombermans;
    }

    public void addDeadBomberMan(BomberMan bomberMan) {
        deadBombermans.add(bomberMan);
    }

    public int getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }

    public synchronized void addMessage(String message) {
        messages.add(message);
    }

    public synchronized int sendMessages(int lastSentMessageIndex, PrintWriter writer) {

        for (int i = lastSentMessageIndex; i < messages.size(); i++) {
            writer.println(messages.get(i));
        }
        writer.println("END");
        return messages.size();
    }

    public void stop() {
        running = false;
    }


}
