package components.power;

import components.BomberMan;
import components.cell.Cell;

import java.io.PrintWriter;

public class GhostPower extends PowerChanger{
    public GhostPower(Cell currentCell) {
        super(currentCell);
    }

    @Override
    public void doJob(BomberMan bomberMan) {
        bomberMan.setGhostMood(true);
    }

    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(getClass().getCanonicalName()+" "+location.x+" "+location.y+" ");
    }
}
