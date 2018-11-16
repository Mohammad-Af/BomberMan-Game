package components.cell;

import UI.Images;

import java.awt.*;

import static java.lang.Thread.sleep;

public class BackgroundCell extends Cell {

    private boolean burning =false;

    public BackgroundCell(int xLocation,int yLocation) {
        super(Images.background, new Point(xLocation,yLocation));


    }

    public void paint(int x, int y, Graphics g) {

        if (location.x > x - cellSize && location.x < x + g.getClipBounds().width
                && location.y > y - cellSize && location.y < y + g.getClipBounds().height) {
            g.drawImage(Images.background, location.x - x,location.y - y, cellSize, cellSize, null);

            if(burning){
                g.drawImage(Images.fire, location.x - x, location.y - y, cellSize , cellSize , null);
            }

        }
        burning =false;
    }

    public void setBurning(boolean burning) {
        this.burning = burning;
    }
}
