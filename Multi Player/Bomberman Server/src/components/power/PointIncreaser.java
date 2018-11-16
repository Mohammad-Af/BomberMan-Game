package components.power;

import components.BomberMan;
import components.cell.Cell;

import java.io.PrintWriter;

public class PointIncreaser extends PowerChanger {

    public PointIncreaser(Cell currentCell) {
        super(currentCell);

    }

    @Override
    public void doJob(BomberMan bomberMan) {
        bomberMan.increasePoint(100);
        currentCell.removeInsideObj(this);
    }

    @Override
    public void save(PrintWriter printWriter)  {
        printWriter.print(getClass().getCanonicalName()+" "+location.x+" "+location.y+" ");
    }

}
