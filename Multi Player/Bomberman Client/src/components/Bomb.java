package components;

import components.Board;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Thread.sleep;

public class Bomb {
    private Point location;
    private BufferedImage currentImage;
    private int currentSize;
    private int ID;
    private boolean exploded=false;
    private boolean timerStarted=false;





    public Bomb(int id, int x, int y) {
        ID = id;
        location = new Point(x, y);


    }



    int counter=0;
    public void paint(int x, int y, Graphics g) {
        counter++;
        if(counter>105)
            exploded=true;
        if (location.x > x - Board.cellsSize && location.x < x + g.getClipBounds().width
                && location.y > y - Board.cellsSize && location.y < y + g.getClipBounds().height) {

            if (!exploded) {
                g.drawImage(currentImage, location.x - x, location.y - y, currentSize, currentSize, null);
            }
        }
    }

    public int getID() {
        return ID;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public void setCurrentImage(BufferedImage currentImage) {
        this.currentImage = currentImage;
    }


    public boolean isBurned() {
        return exploded;
    }
}
