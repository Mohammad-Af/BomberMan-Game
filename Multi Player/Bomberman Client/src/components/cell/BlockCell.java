package components.cell;

import UI.Images;

import java.awt.*;

public class BlockCell extends Cell {

    public BlockCell(int xLocation, int yLocation) {
        super(Images.blockImage, new Point(xLocation, yLocation));

    }

    @Override
    public void paint(int x, int y, Graphics g) {
        if (location.x > x - cellSize && location.x < x + g.getClipBounds().width
                && location.y > y - cellSize && location.y < y + g.getClipBounds().height) {
            g.drawImage(Images.blockImage, location.x - x, location.y - y, cellSize, cellSize, null);
        }
    }

}
