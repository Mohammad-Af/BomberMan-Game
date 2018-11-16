package components.cells;

import UI.Images;
import components.Bomb;
import components.BomberMan;
import components.Gate;
import components.mosters.Monster;
import components.powers.PowerChanger;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class BackgroundCell extends components.cells.Cell {

    private boolean burning =false;

    public BackgroundCell(int xLocation,int yLocation) {
        super(Images.background, new Point(xLocation,yLocation));

        destroyable=false;
    }

    public void paint(int x, int y, Graphics g) {

        if (location.x > x - cellSize && location.x < x + g.getClipBounds().width
                && location.y > y - cellSize && location.y < y + g.getClipBounds().height) {
            g.drawImage(Images.background, location.x - x,location.y - y, cellSize, cellSize, null);

            if(burning){
                g.drawImage(Images.fire, location.x - x, location.y - y, cellSize , cellSize , null);

                burning =false;
            }

        }
    }

    @Override
    public void burn() {

        burning =true;

        List<Object> objectToRemove=new ArrayList<>();
        for (Object object :insideObjs){
            if(object.getClass()== Bomb.class){
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(((Bomb)object).getToExplode()>0) {
                            ((Bomb) object).setToExplode(0);
                            ((Bomb) object).setExploded(true);
                            ((Bomb) object).setToFinishBurning(5);
                        }
                    }
                });
                thread.start();

            }

            if(object.getClass()== BomberMan.class){
                ((BomberMan)object).setAlive(false);
            }
            if(object instanceof Monster){
              ((Monster) object).setAlive(false);
            }
            if(object instanceof PowerChanger){
                ((PowerChanger)object).burn();
            }

            if(!(object instanceof Gate))
            objectToRemove.add(object);
        }

        for (Object object:objectToRemove){
            insideObjs.remove(object);
        }


    }


}
