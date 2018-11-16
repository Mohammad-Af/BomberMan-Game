package components.power;

import components.BomberMan;
import components.cell.Cell;

import java.io.PrintWriter;

public class BombController extends PowerChanger {

    public BombController(Cell currentCell) {
        super(currentCell);

    }

    @Override
    public void doJob(BomberMan bomberMan) {
        bomberMan.setCanControlBombs(true);
        currentCell.removeInsideObj(this);
    }

    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(getClass().getCanonicalName()+" "+location.x+" "+location.y+" ");
    }


}
