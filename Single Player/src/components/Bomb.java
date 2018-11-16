package components;

import UI.Images;
import components.cells.Cell;
import main.Side;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import java.util.Scanner;

public class Bomb {
    private Point location;
    private BufferedImage currentImage;
    private int currentSize;
    private Cell currentCell;
    private int toExplode = 3500;
    private int toFinishBurning = 4;
    private int speed = 100;                             //change speed here!!!
    private boolean burned;
    private boolean exploded = false;

    private int bombRadius;
    private boolean autoExplode;


    public Bomb(int x, int y, int bombRadius, boolean autoExplode) {
        location = new Point(x, y);
        currentImage = Images.bomb[0];
        currentSize = Board.cellsSize - 20;
        this.bombRadius = bombRadius;
        this.autoExplode = autoExplode;
        if (!autoExplode)
            toFinishBurning = 5;
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

    public void save(PrintStream printStream) {

        if (autoExplode)
            printStream.print(1 + " ");
        else
            printStream.print(0 + " ");

        printStream.print(location.x + " " + location.y + " " + bombRadius + " ");


        if (exploded)
            printStream.print(1 + " ");
        else
            printStream.print(0+" ");

        printStream.print(toExplode+" ");

        printStream.print(toFinishBurning+" ");

        printStream.print(currentSize+" ");

        if (Images.bomb[0].equals(currentImage)) {
            printStream.print(0 + " ");
        } else {
            printStream.print(2 + " ");
        }




    }

    public void load(Scanner scanner) {
        currentCell.addInsideObj(this);

        boolean exploded=false;
        if(scanner.nextInt()==1)
            exploded=true;

        this.exploded=exploded;

        toExplode=scanner.nextInt();

        toFinishBurning=scanner.nextInt();

        currentSize=scanner.nextInt();

        currentImage= Images.bomb[scanner.nextInt()];
    }

    public void paint(int x, int y, Graphics g) {
        if (location.x > x - Board.cellsSize && location.x < x + g.getClipBounds().width
                && location.y > y - Board.cellsSize && location.y < y + g.getClipBounds().height) {
            if (!exploded)
                g.drawImage(currentImage, location.x - x, location.y - y, currentSize, currentSize, null);
        }
    }

    public void animate() {


        if (toExplode % (speed) == 0) {
            if (!exploded) {
                if (currentSize == Board.cellsSize) {
                    currentSize -= 5;
                    currentImage = Images.bomb[2];
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

        currentCell.burn();

        Cell cell = currentCell;
        for (int i = 0; i < bombRadius; i++) {
            cell = cell.getNearbyCell(board, Side.RIGHT_SIDE);
            if (!board.blockInLocation(cell.getLocation().x, cell.getLocation().y))
                cell.burn();
            else
                break;
            if (board.wallInLocation(cell.getLocation().x, cell.getLocation().y))
                break;
        }

        cell = currentCell;
        for (int i = 0; i < bombRadius; i++) {
            cell = cell.getNearbyCell(board, Side.LEFT_SIDE);
            if (!board.blockInLocation(cell.getLocation().x, cell.getLocation().y))
                cell.burn();
            else
                break;
            if (board.wallInLocation(cell.getLocation().x, cell.getLocation().y))
                break;
        }

        cell = currentCell;
        for (int i = 0; i < bombRadius; i++) {
            cell = cell.getNearbyCell(board, Side.UP_SIDE);
            if (!board.blockInLocation(cell.getLocation().x, cell.getLocation().y))
                cell.burn();
            else
                break;
            if (board.wallInLocation(cell.getLocation().x, cell.getLocation().y))
                break;
        }

        cell = currentCell;
        for (int i = 0; i < bombRadius; i++) {
            cell = cell.getNearbyCell(board, Side.DOWN_SIDE);
            if (!board.blockInLocation(cell.getLocation().x, cell.getLocation().y))
                cell.burn();
            else
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

}
