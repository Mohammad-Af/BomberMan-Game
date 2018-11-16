package components.powers;

import UI.Images;
import components.BomberMan;
import components.cells.Cell;

import java.io.PrintStream;

public class RadiusDecreaser extends PowerChanger {

    public RadiusDecreaser(Cell currentCell) {
        super(currentCell);
        image= Images.radiusDown;
    }

    @Override
    public void doJob(BomberMan bomberMan) {
        bomberMan.decreaseBombRadius();
        currentCell.removeInsideObj(this);
    }

    @Override
    public void save(PrintStream printStream, int cellIndex) {
        printStream.print(getClass().getCanonicalName()+" "+cellIndex+" ");
    }

}
