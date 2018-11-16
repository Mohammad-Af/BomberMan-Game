package components.powers;

import UI.Images;
import components.BomberMan;
import components.cells.Cell;

import java.io.PrintStream;

public class SpeedDecreaser extends PowerChanger {

    public SpeedDecreaser(Cell currentCell) {
        super(currentCell);
        image= Images.speedDown;
    }

    @Override
    public void doJob(BomberMan bomberMan) {
        bomberMan.decreaseSpeed();
        currentCell.removeInsideObj(this);
    }

    @Override
    public void save(PrintStream printStream, int cellIndex) {
        printStream.print(getClass().getCanonicalName()+" "+cellIndex+" ");
    }

}
