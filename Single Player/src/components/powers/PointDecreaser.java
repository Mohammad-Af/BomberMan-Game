package components.powers;

import UI.Images;
import components.BomberMan;
import components.cells.Cell;

import java.io.PrintStream;

public class PointDecreaser extends PowerChanger {

    public PointDecreaser(Cell currentCell) {
        super(currentCell);
        image = Images.pointDown;
    }

    @Override
    public void doJob(BomberMan bomberMan) {
        bomberMan.decreasePoint(100);
        currentCell.removeInsideObj(this);
    }

    @Override
    public void save(PrintStream printStream, int cellIndex) {
        printStream.print(getClass().getCanonicalName()+" "+cellIndex+" ");
    }

}
