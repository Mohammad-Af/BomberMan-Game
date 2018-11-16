package components;

import UI.Images;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Gate {

    private BufferedImage image;
    private Point location;

    public Gate(Point location){
        image = Images.closeGate;
        this.location = location;
    }

    public void paint(int x, int y, Graphics g,boolean levelCompeleted) {
        if(levelCompeleted) {
            image= Images.openGate;
        }else {
            image = Images.closeGate;
        }
        if (location.x > x - Board.cellsSize && location.x < x + g.getClipBounds().width
                && location.y > y - Board.cellsSize && location.y < y + g.getClipBounds().height) {
            g.drawImage(image, location.x - x, location.y - y, Board.cellsSize, Board.cellsSize, null);

        }
    }

}
