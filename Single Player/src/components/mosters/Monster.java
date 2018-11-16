package components.mosters;

import components.Board;
import components.BomberMan;
import components.cells.Cell;
import main.Side;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.PrintStream;

public abstract class Monster {

    protected Point location;
    protected boolean alive = true;
    protected int speed;
    protected BufferedImage[] lastArrayImage;
    protected int lastIndexImage = 0;
    protected Side lastMove = Side.UP_SIDE;
    protected Cell currentCell;
    protected int size;
    protected int movementSize = BomberMan.movementSize;

    public Monster(int x, int y) {
        location = new Point(x, y);
        size = Board.cellsSize;
    }

    protected int imageCounter = 0;

    public void animate() {
        imageCounter++;
        if (alive) {
            //chage size every time and move
            if (imageCounter % 10 == 0) {
//            if (size == components.Board.cellsSize)
//                size -= 5;
//            else
//                size += 5;
            }

        }
    }

    protected int toDeadCompeletly = 13;


    public abstract void move(Board board);


    protected void killBomberman() {
        for (int i = currentCell.getInsideObjs().size() - 1; i > -1; i--) {
            Object object = currentCell.getInsideObjs().get(i);
            if (object.getClass() == BomberMan.class) {
                ((BomberMan) object).setAlive(false);
                ((BomberMan) object).getCurrentCell().removeInsideObj(object);
            }
        }
    }


    protected void setImageIndex() {
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

    public void paint(int x, int y, Graphics g) {
        if (location.x > x - Board.cellsSize && location.x < x + g.getClipBounds().width
                && location.y > y - Board.cellsSize && location.y < y + g.getClipBounds().height) {
            g.drawImage(lastArrayImage[lastIndexImage], location.x - x, location.y - y, size, size, null);

        }
    }

    public boolean isAlive() {
        return alive;
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

    public abstract void save(PrintStream printStream, int cellIndex);

    public void setLastMove(Side lastMove) {
        this.lastMove = lastMove;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }
}
