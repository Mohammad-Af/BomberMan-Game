package components.powers;

import UI.Images;
import components.BomberMan;
import components.cells.Cell;

import java.io.PrintStream;

public class GhostPower extends PowerChanger {

    public GhostPower(Cell currentCell) {
        super(currentCell);
        image = Images.ghostPower;
    }

    @Override
    public void doJob(BomberMan bomberMan) {
        bomberMan.setGhostMood(true);
    }

    @Override
    public void save(PrintStream printStream, int cellIndex) {
        printStream.print(getClass().getCanonicalName()+" "+cellIndex+" ");
    }
}
