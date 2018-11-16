package components.power;

import UI.Images;
import components.Board;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PowerChanger {
    protected BufferedImage image ;
    protected Point location;
    protected boolean used;


    private int imageCounter = 4;
    private int ID;


    protected PowerChanger(int x, int y){
        location=new Point(x,y);
    }

    public void paint(int x, int y, Graphics g) {
        if (location.x > x - Board.cellsSize && location.x < x + g.getClipBounds().width
                && location.y > y - Board.cellsSize && location.y < y + g.getClipBounds().height) {
            g.drawImage(image, location.x - x, location.y - y, Board.cellsSize, Board.cellsSize, null);

            if(imageCounter!=4) {
                g.drawImage(Images.burning[3 - imageCounter], location.x - x, location.y - y, Board.cellsSize, Board.cellsSize, null);
            }


        }
    }

    private boolean burnCalled=false;
    public void burn() {
        if(!burnCalled) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (imageCounter > 0) {
                        try {
                            Thread.sleep(200);
                            imageCounter--;

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    imageCounter = 3;
                    used = true;
                }
            });
            thread.start();
        }

        burnCalled = true;


    }


    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
