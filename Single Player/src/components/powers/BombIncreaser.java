package components.powers;

import UI.Images;
import components.BomberMan;
import components.cells.Cell;

import java.io.PrintStream;

public class BombIncreaser extends PowerChanger {


    public BombIncreaser(Cell currentCell) {
        super(currentCell);
        image = Images.bombIncreaser;
    }

    @Override
    public void doJob(BomberMan bomberMan) {
        bomberMan.increaseBombLimit();
        currentCell.removeInsideObj(this);
    }

    @Override
    public void save(PrintStream printStream, int cellIndex) {
        printStream.print(getClass().getCanonicalName()+" "+cellIndex+" ");
    }


}
