package components.powers;

import UI.Images;
import components.BomberMan;
import components.cells.Cell;

import java.io.PrintStream;

public class BombDecreaser extends PowerChanger {

    public BombDecreaser(Cell currentCell) {
        super(currentCell);
        image= Images.bombDecrease;
    }

    @Override
    public void doJob(BomberMan bomberMan) {
        if(bomberMan.getBombLimit()>1)
        bomberMan.decreaseBombLimit();
        currentCell.removeInsideObj(this);
    }

    @Override
    public void save(PrintStream printStream, int cellIndex) {
        printStream.print(getClass().getCanonicalName()+" "+cellIndex+" ");
    }


}
