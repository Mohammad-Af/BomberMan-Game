package components.powers;

import UI.Images;
import components.BomberMan;
import components.cells.Cell;

import java.io.PrintStream;

public class BombController extends PowerChanger {

    public BombController(Cell currentCell) {
        super(currentCell);
        image= Images.bombControl;
    }

    @Override
    public void doJob(BomberMan bomberMan) {
        bomberMan.setCanControlBombs(true);
        currentCell.removeInsideObj(this);
    }

    @Override
    public void save(PrintStream printStream, int cellIndex) {
        printStream.print(getClass().getCanonicalName()+" "+cellIndex+" ");
    }


}
