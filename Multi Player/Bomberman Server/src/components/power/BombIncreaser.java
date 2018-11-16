package components.power;

import components.BomberMan;
import components.cell.Cell;

import java.io.PrintWriter;

public class BombIncreaser extends PowerChanger {


    public BombIncreaser(Cell currentCell) {
        super(currentCell);

    }

    @Override
    public void doJob(BomberMan bomberMan) {
        bomberMan.increaseBombLimit();
        currentCell.removeInsideObj(this);
    }

    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(getClass().getCanonicalName()+" "+location.x+" "+location.y+" ");
    }


}
