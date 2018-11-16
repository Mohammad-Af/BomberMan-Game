package components.powers;

import UI.Images;
import components.Board;
import components.BomberMan;
import components.cells.Cell;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.PrintStream;

public abstract class PowerChanger {
    protected BufferedImage image ;
    protected Cell currentCell;
    protected Point location;
    protected boolean used=false;

    public PowerChanger(Cell currentCell){
        this.currentCell=currentCell;
        location=currentCell.getLocation();
        currentCell.addInsideObj(this);

    }
    public abstract void doJob(BomberMan bomberMan);
    public abstract void save(PrintStream printStream, int cellIndex);

    public void paint(int x, int y, Graphics g) {
        if (location.x > x - Board.cellsSize && location.x < x + g.getClipBounds().width
                && location.y > y - Board.cellsSize && location.y < y + g.getClipBounds().height) {
            g.drawImage(image, location.x - x, location.y - y, Board.cellsSize, Board.cellsSize, null);

            if(imageCounter!=4)
                g.drawImage(Images.burning[3-imageCounter],location.x - x, location.y - y, Board.cellsSize, Board.cellsSize, null);


        }
    }


    private int imageCounter = 4;
    public void burn() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (imageCounter > 0){
                    try {
                        Thread.sleep(200);
                        imageCounter--;

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                imageCounter=3;
                used=true;
            }
        });
        thread.start();
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
