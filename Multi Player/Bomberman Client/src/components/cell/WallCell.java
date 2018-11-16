package components.cell;

import UI.Images;

import java.awt.*;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class WallCell extends Cell {

    private boolean destroyed = false;
    private int timeToBurn = 501;


    public WallCell(int xLocation, int yLocation) {
        super(Images.wallImage, new Point(xLocation, yLocation));
        destroyable = true;

    }


    private int counter = 0;
    @Override
    public void paint(int x, int y, Graphics g) {

        if(counter == timeToBurn)
            destroyed=true;

        if(timeToBurn != 501){
            counter = timeToBurn;
        }
        if (location.x > x - cellSize && location.x < x + g.getClipBounds().width
                && location.y > y - cellSize && location.y < y + g.getClipBounds().height) {
            if (timeToBurn != 501) {
                g.drawImage(Images.wallBurning[2 - (timeToBurn / 167)], location.x - x, location.y - y, cellSize, cellSize, null);
            } else
                g.drawImage(Images.wallImage, location.x - x, location.y - y, cellSize, cellSize, null);
        }
    }



    public boolean isDestroyed() {
        return destroyed;
    }

    public int getTimeToBurn() {
        return timeToBurn;
    }

    public void setTimeToBurn(int timeToBurn) {
        this.timeToBurn = timeToBurn;
    }
}
