package components.monster;

import components.Board;
import components.BomberMan;
import components.cell.Cell;
import main.Side;

import java.awt.*;
import java.io.PrintWriter;

public abstract class Monster {

    protected Point location;
    boolean alive = true;
    int speed;
    int lastIndexImage = 0;
    Side lastMove = Side.UP_SIDE;
    protected Cell currentCell;
    protected int size;
    int movementSize = BomberMan.movementSize;
    private int killerID;
    int value;
    int imageArrayID;

    protected Monster(int x, int y) {
        location = new Point(x, y);
        size = Board.cellsSize;
    }

    int imageCounter = 0;

    public void animate() {
        imageCounter++;
//        if (alive) {
            //chage size every time and move
//            if (imageCounter % 10 == 0) {
//            if (size == components.Board.cellsSize)
//                size -= 5;
//            else
//                size += 5;
//            }
//
//        }
    }

    int toDeadCompeletly = 13;

    public abstract void move(Board board);


    void killBomberman() {
        for (int i=currentCell.getInsideObjs().size()-1 ; i>-1;i--) {
            Object object = currentCell.getInsideObjs().get(i);

            if (object.getClass() == BomberMan.class) {
                ((BomberMan) object).setAlive(false);
                ((BomberMan) object).getCurrentCell().removeInsideObj(object);
            }
        }
    }


    void setImageIndex() {
        lastIndexImage++;
        if (lastIndexImage == 4) {
            lastIndexImage = 0;
        }
    }


    protected void setCurrentCell(Board board) {

        if (currentCell != null) {
            currentCell.removeInsideObj(this);
        }
        currentCell = board.getBackgroundCells().get(((location.y + Board.cellsSize / 2) / Board.cellsSize) * (2 * board.getHeight() + 3) + (location.x + Board.cellsSize / 2) / Board.cellsSize);
        currentCell.addInsideObj(this);


    }



    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean deadCompeletly() {
        if (toDeadCompeletly == 0) {
            currentCell.removeInsideObj(this);
            return true;
        }
        return false;
    }

    public void save(PrintWriter printWriter){
        printWriter.print(hashCode()+" "+imageArrayID+" "+location.x+" "+location.y+" "+lastIndexImage+" ");

        if(alive)
            printWriter.print(lastMove.ordinal()+" ");
        else
            printWriter.print(5+" ");
    }


    public void setKillerID(int killerID) {
        this.killerID = killerID;
    }

    public int getKillerID() {
        return killerID;
    }

    public int getValue() {
        return value;
    }
}
