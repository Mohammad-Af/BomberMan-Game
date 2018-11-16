package components.power;

import components.BomberMan;
import components.cell.Cell;

import java.io.PrintWriter;

public class RadiusDecreaser extends PowerChanger {

    public RadiusDecreaser(Cell currentCell) {
        super(currentCell);

    }

    @Override
    public void doJob(BomberMan bomberMan) {
        bomberMan.decreaseBombRadius();
        currentCell.removeInsideObj(this);
    }

    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(getClass().getCanonicalName()+" "+location.x+" "+location.y+" ");
    }

}
