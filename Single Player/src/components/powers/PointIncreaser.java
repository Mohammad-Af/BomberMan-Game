package components.powers;

import UI.Images;
import components.BomberMan;
import components.cells.Cell;

import java.io.PrintStream;

public class PointIncreaser extends PowerChanger {

    public PointIncreaser(Cell currentCell) {
        super(currentCell);
        image = Images.pointUp;
    }

    @Override
    public void doJob(BomberMan bomberMan) {
        bomberMan.increasePoint(100);
        currentCell.removeInsideObj(this);
    }

    @Override
    public void save(PrintStream printStream, int cellIndex)  {
        printStream.print(getClass().getCanonicalName()+" "+cellIndex+" ");
    }

}
