package components.power;

import components.BomberMan;
import components.cell.Cell;

import java.io.PrintWriter;

public class BombDecreaser extends PowerChanger {

    public BombDecreaser(Cell currentCell) {
        super(currentCell);

    }

    @Override
    public void doJob(BomberMan bomberMan) {
        if(bomberMan.getBombLimit()>1)
        bomberMan.decreaseBombLimit();
        currentCell.removeInsideObj(this);
    }

    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(getClass().getCanonicalName()+" "+location.x+" "+location.y+" ");
    }


}
