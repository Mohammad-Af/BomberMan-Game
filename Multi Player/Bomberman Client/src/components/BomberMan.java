package components;

import UI.Images;
import main.Side;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BomberMan {
    private boolean alive = true;
    private Side lastMove ;
    private Point location;
    private int point;
    private int lastIndexImage;
    private BufferedImage lastArrayImage [];
    private int ID;
    private String name;


    public BomberMan(int id,String name ,int x, int y, int point, int indexImage, int lastMove) {

        this.name = name;
        ID =id;

        location = new Point(x,y);
        this.point=point;
        lastIndexImage = indexImage;

        initLastMove(lastMove);




    }
    private void initLastMove(int lastMove){
        if(lastMove ==6){
            alive=false;
            lastArrayImage = Images.bomberManDeath;
        }else if(lastMove==5){
            this.lastMove=null;
        }else {
            this.lastMove= Side.values()[lastMove];

            if(this.lastMove == Side.UP_SIDE){
                lastArrayImage = Images.bomberManWU;
            }
            if(this.lastMove == Side.DOWN_SIDE){
                lastArrayImage = Images.bomberManWD;
            }
            if(this.lastMove == Side.RIGHT_SIDE){
                lastArrayImage = Images.bomberManWR;
            }
            if(this.lastMove == Side.LEFT_SIDE){
                lastArrayImage = Images.bomberManWL;
            }

        }


    }

    public void paint(int x, int y, Graphics g) {
        if (alive) {
            if (lastMove == null) {
                g.drawImage(Images.throwBomb, location.x - x, location.y - y, Board.cellsSize, Board.cellsSize, null);
            } else {
                g.drawImage(lastArrayImage[lastIndexImage%5], location.x - x, location.y - y, Board.cellsSize, Board.cellsSize, null);
            }
        } else {
            g.drawImage(lastArrayImage[lastIndexImage%5],location.x - x, location.y - y, Board.cellsSize, Board.cellsSize, null);

        }
    }


    public Point getLocation() {
        return location;
    }

    public int getID() {
        return ID;
    }

    public void setLastMove(int lastMove) {
        initLastMove(lastMove);
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setLastIndexImage(int lastIndexImage) {
        this.lastIndexImage = lastIndexImage;
    }

    public int getPoint() {
        return point;
    }

    public String getName() {
        return name;
    }
}
