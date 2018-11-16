package components.cell;

import components.Board;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class Cell {
    protected BufferedImage image;
    protected Point location;
    protected List<Object> insideObjs = new ArrayList<>();
    protected boolean destroyable;
    protected int cellSize;


    protected Cell(BufferedImage image, Point location) {
        this.image = image;
        this.location = location;
        cellSize = Board.cellsSize;
    }


    public abstract void paint(int x, int y, Graphics g);


}
