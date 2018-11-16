package components.powers;

import UI.Images;
import components.BomberMan;
import components.cells.Cell;

import java.io.PrintStream;

public class RadiusIncreaser extends PowerChanger {

    public RadiusIncreaser(Cell currentCell) {
        super(currentCell);
        image= Images.radiusUp;
    }

    @Override
    public void doJob(BomberMan bomberMan) {
        bomberMan.increaseBombRadius();
        currentCell.removeInsideObj(this);
    }

    @Override
    public void save(PrintStream printStream, int cellIndex) {
        printStream.print(getClass().getCanonicalName()+" "+cellIndex+" ");
    }

}
