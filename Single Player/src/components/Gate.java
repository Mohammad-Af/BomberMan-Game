package components;

import UI.Images;
import components.cells.Cell;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Gate {
    private Cell gateCell;
    private BufferedImage image;
    private boolean open = false;

    public Gate(Cell cell){
        gateCell=cell;
        image= Images.closeGate;
        gateCell.addInsideObj(this);

    }

    public void paint(int x, int y, Graphics g,boolean levelCompeleted) {
        if(levelCompeleted) {
            image= Images.openGate;
            open=true;
        }
        Point location = gateCell.getLocation();
        if (location.x > x - Board.cellsSize && location.x < x + g.getClipBounds().width
                && location.y > y - Board.cellsSize && location.y < y + g.getClipBounds().height) {
            g.drawImage(image, location.x - x, location.y - y, Board.cellsSize, Board.cellsSize, null);

        }
    }

    public Cell getGateCell() {
        return gateCell;
    }

    public boolean isOpen() {
        return open;
    }

}
