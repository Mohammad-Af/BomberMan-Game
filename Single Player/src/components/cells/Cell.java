package components.cells;

import components.Board;
import main.Side;

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


    public Cell(BufferedImage image, Point location) {
        this.image = image;
        this.location = location;
        cellSize = Board.cellsSize;
    }

    public boolean isDestroyable() {
        return destroyable;
    }

    public List<Object> getInsideObjs() {
        return insideObjs;
    }

    public void addInsideObj(Object object) {
        insideObjs.add(object);
    }

    public void removeInsideObj(Object object) {
        insideObjs.remove(object);
    }

    public abstract void paint(int x, int y, Graphics g);

    public abstract void burn();

    public Cell getNearbyCell(Board board, Side side){
        List<Cell>gameCells=new ArrayList<>();
        gameCells.addAll(board.getWallCells());
        gameCells.addAll(board.getBlockCells());
        gameCells.addAll(board.getBackgroundCells());

        if(side== Side.UP_SIDE){
            return getUpperCell(gameCells);
        }
        if(side== Side.LEFT_SIDE){
            return getLeftSideCell(gameCells);
        }
        if(side== Side.RIGHT_SIDE){
            return getRightSideCell(gameCells);
        }
        if(side== Side.DOWN_SIDE){
            return getBottomCell(gameCells);
        }
        return null;
    }

    private Cell getUpperCell(List<Cell> gameCells) {
        for (Cell cell : gameCells) {
            if (cell.location.y == location.y - cellSize && cell.location.x == location.x) {
                return cell;
            }
        }
        return null;
    }

    private Cell getRightSideCell(List<Cell> gameCells) {
        for (Cell cell : gameCells) {
            if (cell.location.x == location.x + cellSize && cell.location.y == location.y) {
                return cell;
            }
        }
        return null;
    }

    private Cell getLeftSideCell(List<Cell> gameCells) {
        for (Cell cell : gameCells) {
            if (cell.location.x == location.x - cellSize && cell.location.y == location.y) {
                return cell;
            }
        }
        return null;
    }

    private Cell getBottomCell(List<Cell> gameCells) {
        for (Cell cell : gameCells) {
            if (cell.location.y == location.y + cellSize && cell.location.x == location.x) {
                return cell;
            }
        }
        return null;
    }

    public Point getLocation() {
        return location;
    }


}
