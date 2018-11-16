package components.cell;

import java.awt.*;

public class BlockCell extends Cell {

    public BlockCell(int xLocation, int yLocation) {
        super( new Point(xLocation, yLocation));

        destroyable = false;
    }


    @Override
    public void burn(int bombermanID) {

    }
}
