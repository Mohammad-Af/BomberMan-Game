package components;

import components.cell.Cell;
import main.Side;

import java.awt.*;
import java.io.PrintWriter;

public class Bomb {
    private Point location;
    private int currentImageIndex;
    private int currentSize;
    private Cell currentCell;
    private int toExplode = 3500;
    private int toFinishBurning = 4;
    private boolean burned;
    private boolean exploded = false;


    private int bombRadius;
    private boolean autoExplode;
    private int bombermanID;


    Bomb(int x, int y, int bombRadius, boolean autoExplode, int bombermanID) {
        location = new Point(x, y);
        currentImageIndex = 0;
        currentSize = Board.cellsSize - 20;
        this.bombRadius = bombRadius;
        this.autoExplode = autoExplode;
        if (!autoExplode)
            toFinishBurning = 5;

        this.bombermanID = bombermanID;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }


    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
        currentCell.addInsideObj(this);
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void save(PrintWriter printWriter) {

        printWriter.print(hashCode() + " ");

        printWriter.print(location.x + " " + location.y + " ");

        printWriter.print(currentSize + " ");

        printWriter.print(currentImageIndex+" ");


    }



    public void animate() {


        int speed = 100;
        if (toExplode % (speed) == 0) {
            if (!exploded) {
                if (currentSize == Board.cellsSize) {
                    currentSize -= 5;
                    currentImageIndex = 2;
                } else {
                    currentSize += 5;
                }
                if (toExplode == 0) {
                    if (autoExplode) {
                        exploded = true;
                    } else {
                        toExplode = 3500;
                    }
                }
            } else if (toFinishBurning != 0) {
                toFinishBurning--;
            } else {
                burned = true;
            }
        }

        toExplode--;

    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }


    public boolean isBurned() {
        return burned;
    }


    public void burn(Board board) {


        currentCell.burn(bombermanID);
        if (!board.getBurningCells().contains(currentCell))
            board.addBurningCell(currentCell);

        Cell cell = currentCell;
        for (int i = 0; i < bombRadius; i++) {
            cell = cell.getNearbyCell(board, Side.RIGHT_SIDE);
            if (!board.blockInLocation(cell.getLocation().x, cell.getLocation().y)) {
                cell.burn(bombermanID);
                if (!board.getBurningCells().contains(cell))
                    board.addBurningCell(cell);
            } else
                break;
            if (board.wallInLocation(cell.getLocation().x, cell.getLocation().y))
                break;
        }


        cell = currentCell;
        for (int i = 0; i < bombRadius; i++) {
            cell = cell.getNearbyCell(board, Side.LEFT_SIDE);
            if (!board.blockInLocation(cell.getLocation().x, cell.getLocation().y)) {
                cell.burn(bombermanID);
                if (!board.getBurningCells().contains(cell))
                    board.addBurningCell(cell);
            } else
                break;
            if (board.wallInLocation(cell.getLocation().x, cell.getLocation().y))
                break;
        }

        cell = currentCell;
        for (int i = 0; i < bombRadius; i++) {
            cell = cell.getNearbyCell(board, Side.UP_SIDE);
            if (!board.blockInLocation(cell.getLocation().x, cell.getLocation().y)) {
                cell.burn(bombermanID);
                if (!board.getBurningCells().contains(cell))
                    board.addBurningCell(cell);
            } else
                break;
            if (board.wallInLocation(cell.getLocation().x, cell.getLocation().y))
                break;
        }

        cell = currentCell;
        for (int i = 0; i < bombRadius; i++) {
            cell = cell.getNearbyCell(board, Side.DOWN_SIDE);
            if (!board.blockInLocation(cell.getLocation().x, cell.getLocation().y)) {
                cell.burn(bombermanID);
                if (!board.getBurningCells().contains(cell))
                    board.addBurningCell(cell);
            } else
                break;
            if (board.wallInLocation(cell.getLocation().x, cell.getLocation().y))
                break;
        }


    }

    public int getToExplode() {
        return toExplode;
    }

    public void setToExplode(int toExplode) {
        this.toExplode = toExplode;
    }

    public void setToFinishBurning(int toFinishBurning) {
        this.toFinishBurning = toFinishBurning;
    }

    public boolean isAutoExplode() {
        return autoExplode;
    }


    public int getBombermanID() {
        return bombermanID;
    }

}
