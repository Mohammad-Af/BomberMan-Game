package components.cell;

import components.Bomb;
import components.BomberMan;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class WallCell extends Cell {

    private boolean destroyed = false;
    private int timeToBurn = 501;
    private int bombermanID;                    //who explode this wall (used to calculate bomberman point)


    public WallCell(int xLocation, int yLocation) {
        super(new Point(xLocation, yLocation));
        destroyable = true;

    }


    @Override
    public synchronized void burn(int bombermanID) {

        this.bombermanID=bombermanID;

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
                ((BomberMan)object).setKillerID(bombermanID);
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

    public int getBombermanID() {
        return bombermanID;
    }
}
