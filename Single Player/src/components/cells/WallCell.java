package components.cells;

import UI.Images;
import components.Bomb;
import components.BomberMan;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class WallCell extends components.cells.Cell {

    private boolean destroyed = false;
    private int timeToBurn = 501;


    public WallCell(int xLocation, int yLocation) {
        super(Images.wallImage, new Point(xLocation, yLocation));
        destroyable = true;

    }


    @Override
    public void paint(int x, int y, Graphics g) {

        if (location.x > x - cellSize && location.x < x + g.getClipBounds().width
                && location.y > y - cellSize && location.y < y + g.getClipBounds().height) {
            if (timeToBurn != 501) {
                g.drawImage(Images.wallBurning[2 - (timeToBurn / 167)], location.x - x, location.y - y, cellSize, cellSize, null);
            } else
                g.drawImage(Images.wallImage, location.x - x, location.y - y, cellSize, cellSize, null);
        }
    }

    @Override
    public synchronized void burn() {
        timeToBurn--;
        if (timeToBurn <=1)
            destroyed = true;


        //for goast Power...........

        List<Object> objectToRemove=new ArrayList<>();
        for (Object object :insideObjs) {
            if (object.getClass() == Bomb.class) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (((Bomb) object).getToExplode() > 0) {
                            ((Bomb) object).setToExplode(0);
                            ((Bomb) object).setExploded(true);
                            ((Bomb) object).setToFinishBurning(5);
                        }
                    }
                });
                thread.start();

            }

            if (object.getClass() == BomberMan.class) {
                ((BomberMan) object).setAlive(false);
            }
        }
        for (Object object:objectToRemove){
            insideObjs.remove(object);
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
