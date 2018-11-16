package components;

import components.cell.Cell;

public class Gate {
    private Cell gateCell;
    private boolean open = false;

    Gate(Cell cell){
        gateCell=cell;
        gateCell.addInsideObj(this);

    }

    public void repaint(boolean levelCompeleted) {
        if(levelCompeleted) {
            open=true;
        }
    }

    public Cell getGateCell() {
        return gateCell;
    }

    public boolean isOpen() {
        return open;
    }


}
