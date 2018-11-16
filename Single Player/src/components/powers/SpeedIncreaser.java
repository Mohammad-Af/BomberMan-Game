package components.powers;

import UI.Images;
import components.BomberMan;
import components.cells.Cell;

import java.io.PrintStream;

public class SpeedIncreaser extends PowerChanger {

    public SpeedIncreaser(Cell currentCell) {
        super(currentCell);
        image= Images.speedUp;
    }

    @Override
    public void doJob(BomberMan bomberMan) {
        bomberMan.increaseSpeed();
        currentCell.removeInsideObj(this);
    }

    @Override
    public void save(PrintStream printStream, int cellIndex) {
        printStream.print(getClass().getCanonicalName()+" "+cellIndex+" ");
    }

}
