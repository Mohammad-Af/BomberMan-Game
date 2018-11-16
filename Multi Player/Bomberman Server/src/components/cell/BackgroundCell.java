package components.cell;

import components.Bomb;
import components.BomberMan;
import components.Gate;
import components.monster.Monster;
import components.power.PowerChanger;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class BackgroundCell extends Cell {

    private boolean burning = false;
    private List<PowerChanger> burningPowers = new ArrayList<>();

    public BackgroundCell(int xLocation, int yLocation) {
        super(new Point(xLocation, yLocation));
        destroyable = false;

    }

    public void repaint() {
        burning = false;
    }

    @Override
    public void burn(int bombermanID) {


        burning = true;

        List<Object> objectToRemove = new ArrayList<>();
        for (Object object : insideObjs) {
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
            if (object instanceof Monster) {
                ((Monster)object).setKillerID(bombermanID);
                ((Monster) object).setAlive(false);
            }
            if (object instanceof PowerChanger) {
                ((PowerChanger) object).burn();
                burningPowers.add((PowerChanger) object);
            }

            if (!(object instanceof Gate))
                objectToRemove.add(object);
        }

        for (Object object : objectToRemove) {
            insideObjs.remove(object);
        }


    }

    public boolean isBurning() {
        return burning;
    }

    public List<PowerChanger> getBurningPowers() {
        return burningPowers;
    }
}
