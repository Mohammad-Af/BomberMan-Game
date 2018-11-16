package main;

import UI.GameFrame;
import UI.Images;
import components.Board;
import components.BomberMan;

import java.io.IOException;

public class Game {
    private Board board;
    private BomberMan bomberMan;
    private GameFrame gameFrame;
    private boolean running = true;
    public static int level;
    private boolean finished = false;

    public Game(int level) throws IOException {

        Game.level =level;
        new Images();

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

    public BomberMan getBomberMan() {
        return bomberMan;
    }

    public void setBomberMan(BomberMan bomberMan) {
        this.bomberMan = bomberMan;
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }


    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }
}
