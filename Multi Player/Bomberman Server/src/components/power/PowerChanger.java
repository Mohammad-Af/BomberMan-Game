package components.power;

import components.BomberMan;
import components.cell.Cell;

import java.awt.*;
import java.io.PrintWriter;

public abstract class PowerChanger {

    protected Cell currentCell;
    protected Point location;
    private boolean used=false;

    protected PowerChanger(Cell currentCell){
        this.currentCell=currentCell;
        location=currentCell.getLocation();
        currentCell.addInsideObj(this);

    }
    public abstract void doJob(BomberMan bomberMan);
    public abstract void save(PrintWriter printWriter);




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

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
