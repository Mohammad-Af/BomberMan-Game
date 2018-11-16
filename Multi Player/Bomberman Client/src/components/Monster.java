package components;

import UI.Images;
import main.Side;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Monster {

    protected Point location;
    private BufferedImage[] lastArrayImage;
    private int lastIndexImage = 0;
    private int ID;


    public Monster(int x, int y, int imageArrayID) {
        location = new Point(x, y);
        lastArrayImage = Images.monsterWU[imageArrayID];
    }

    public void paint(int x, int y, Graphics g) {
        if (location.x > x - Board.cellsSize && location.x < x + g.getClipBounds().width
                && location.y > y - Board.cellsSize && location.y < y + g.getClipBounds().height) {
            g.drawImage(lastArrayImage[lastIndexImage%12], location.x - x, location.y - y, Board.cellsSize, Board.cellsSize, null);

        }
    }


    public void setLocation(Point location) {
        this.location = location;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }


    public void initLastMove(int lastMove, int imageArrayID){
        if(lastMove == 5){
            lastArrayImage = Images.monsterDeath;
        }else {
            Side lastMove1 = Side.values()[lastMove];

            if (lastMove1 == Side.UP_SIDE) {
                lastArrayImage = Images.monsterWU[imageArrayID];
            }
            if (lastMove1 == Side.DOWN_SIDE) {
                lastArrayImage = Images.monsterWD[imageArrayID];
            }
            if (lastMove1 == Side.RIGHT_SIDE) {
                lastArrayImage = Images.monsterWR[imageArrayID];
            }
            if (lastMove1 == Side.LEFT_SIDE) {
                lastArrayImage = Images.monsterWL[imageArrayID];
            }
        }
    }

    public void setLastIndexImage(int lastIndexImage) {
        this.lastIndexImage = lastIndexImage;
    }
}
