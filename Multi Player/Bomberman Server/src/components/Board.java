package components;

import components.cell.BackgroundCell;
import components.cell.BlockCell;
import components.cell.Cell;
import components.cell.WallCell;
import components.monster.*;
import components.power.*;
import main.Game;
import main.Side;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

public class Board {
    private int width;
    private int height;
    public static int cellsSize;
    private List<WallCell> wallCells = new ArrayList<>();
    private List<BlockCell> blockCells = new ArrayList<>();
    private List<BackgroundCell> backgroundCells = new ArrayList<>();
    private List<Bomb> bombs = new ArrayList<>();
    private List<Monster> monsters = new ArrayList<>();
    private List<PowerChanger> powerChangers = new ArrayList<>();
    private HashMap<Integer, Point> bomberManPlaces = new HashMap<>();
    private List<Point> bomberManPlacesArray = new ArrayList<>();
    private int gameLevel;
    private Gate gate;
    private int monstersCount;
    private List<Cell> burningCells = new ArrayList<>();
    private List<PowerChanger> burningPowers = new ArrayList<>();
    private List<PowerChanger> usedPowers = new ArrayList<>();
    private List<Class> avilableMonsters = new ArrayList<>();


    public Board(int width, int height, int cellsSize, int monstersCount, int level) throws IOException, IllegalAccessException, InstantiationException, InvocationTargetException {
        this.width = width;
        this.height = height;
        Board.cellsSize = cellsSize;
        this.monstersCount = monstersCount;
        this.gameLevel = level;


        createBlockCells();
        createWallCells();
        createBackgroundGrassCells();

        //


        for (BackgroundCell backgroundCell : backgroundCells) {
            Point location = backgroundCell.getLocation();
            if (!wallInLocation(location.x, location.y)
                    && !blockInLocation(location.x, location.y)) {
                freeCells.add(backgroundCell);
            }
        }
        Collections.shuffle(freeCells);
        //

        createMonsters();
        createPowerChangers();


    }

    private List<BackgroundCell> freeCells = new ArrayList<>();

    public void initBombermans(int bombermansID) {

        bomberManPlaces.put(bombermansID, bomberManPlacesArray.get(bomberManPlacesArray.size() - 1));


    }


    private void createBackgroundGrassCells() {

        int x = 0;
        int y = 0;
        for (int i = 0; i < 2 * width + 3; i++) {
            for (int j = 0; j < 2 * height + 3; j++) {
                backgroundCells.add(new BackgroundCell(x, y));
                x += cellsSize;
            }
            x = 0;
            y += cellsSize;
        }
    }


    private void createWallCells() {
        int x = cellsSize;
        int y = cellsSize;
        int size;
        for (int i = 0; i < getWidth() + 1; i++) {
            for (int j = 0; j < getHeight() + 1; j++) {
                if (Math.random() > 0.7) {
                    wallCells.add(new WallCell(x, y));
                }
                x += 2 * cellsSize;

            }
            x = cellsSize;
            y += 2 * cellsSize;
        }
        size = wallCells.size();

        x = 2 * cellsSize;
        y = cellsSize;
        for (int i = 0; i < getWidth() + 1; i++) {
            for (int j = 0; j < getHeight(); j++) {
                if (Math.random() > 0.7) {
                    wallCells.add(new WallCell(x, y));
                }
                x += cellsSize * 2;

            }
            x = 2 * cellsSize;
            y += cellsSize * 2;
        }

        if (wallCells.size() > size)
            wallCells.remove(size);
        size = wallCells.size();

        x = cellsSize;
        y = 2 * cellsSize;
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight() + 1; j++) {
                if (Math.random() > 0.7) {
                    wallCells.add(new WallCell(x, y));
                }
                x += cellsSize * 2;

            }
            x = cellsSize;
            y += cellsSize * 2;
        }

        if (wallCells.size() > size)
            wallCells.remove(size);
        if (wallCells.size() == 0)
            createWallCells();
        else
            wallCells.remove(0);


    }

    private void createBlockCells() {
        int x = 2 * cellsSize;
        int y = 2 * cellsSize;
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                blockCells.add(new BlockCell(x, y));
                x += cellsSize * 2;

            }
            x = 2 * cellsSize;
            y += cellsSize * 2;
        }

        x = 0;
        y = 0;

        for (int i = 0; i < 2 * height + 2; i++) {
            blockCells.add(new BlockCell(x, 0));
            blockCells.add(new BlockCell(x, cellsSize * (2 * width + 2)));
            x += cellsSize;
        }
        for (int i = 0; i < 2 * width + 2; i++) {
            blockCells.add(new BlockCell(0, y));
            blockCells.add(new BlockCell(cellsSize * (2 * height + 2), y));
            y += cellsSize;
        }
        blockCells.add(new BlockCell(cellsSize * (2 * height + 2), cellsSize * (2 * width + 2)));

    }


    private void createMonsters() throws IllegalAccessException, InvocationTargetException, InstantiationException {


        if (monstersCount == 0)
            monstersCount = Math.min(width, height);

        if (gameLevel > 4) {
            monstersCount = (int) (monstersCount * Math.pow(1.05, gameLevel - 4));
        }


        if (gameLevel >= 1) {
            addMonster(Monster1.class);
          //  addMonster(components.monster.Monster6.class);
        }
        if (gameLevel >= 2) {
            addMonster(Monster2.class);
        }
        if (gameLevel >= 3) {
            addMonster(Monster3.class);
        }
        if (gameLevel >= 4) {
            addMonster(Monster4.class);
        }


        for (int i = 0; i < monstersCount; i++) {
            Collections.shuffle(avilableMonsters);
            Class c = avilableMonsters.get(0);
            Cell cell = freeCells.remove(0);
            monsters.add((Monster) c.getConstructors()[0].newInstance(cell.getLocation().x, cell.getLocation().y));

        }


    }

    private void createPowerChangers() throws IllegalAccessException, InvocationTargetException, InstantiationException {


        int powerChangersCount = Math.min(2 * monsters.size(), wallCells.size() / 3);
        Class[] classes = {GhostPower.class, BombController.class, BombIncreaser.class, PointIncreaser.class, RadiusIncreaser.class, SpeedIncreaser.class};
        List<Class> increasers = new ArrayList<>(Arrays.asList(classes));
        classes = new Class[]{BombDecreaser.class, PointDecreaser.class, RadiusDecreaser.class, SpeedDecreaser.class};
        List<Class> decreasers = new ArrayList<>(Arrays.asList(classes));

        List<BackgroundCell> cells = new ArrayList<>();
        for (BackgroundCell backgroundCell : backgroundCells) {
            if (wallInLocation(backgroundCell.getLocation().x, backgroundCell.getLocation().y)) {
                cells.add(backgroundCell);
            }
        }
        Collections.shuffle(cells);

        //creates gate here
        gate = new Gate(cells.remove(0));

        for (int i = 0; i < Math.min(powerChangersCount / 2, cells.size()); i++) {
            Collections.shuffle(increasers);
            Class c = increasers.get(0);
            powerChangers.add((PowerChanger) c.getDeclaredConstructors()[0].newInstance(cells.remove(0)));
            Collections.shuffle(decreasers);
            Class c2 = decreasers.get(0);
            powerChangers.add((PowerChanger) c2.getDeclaredConstructors()[0].newInstance(cells.remove(0)));          //chera getConstructors null mide???????
        }



    }


    public void save(PrintWriter printWriter) {
        saveBoardSize(printWriter);
        saveWallPoints(printWriter);
//        saveBombs(printWriter);
        //    saveMonsters(printWriter);
        savePowerChangers(printWriter);
        saveGate(printWriter);
    }

    private void saveGate(PrintWriter printWriter) {
        printWriter.print(gate.getGateCell().getLocation().x + " " + gate.getGateCell().getLocation().y + " ");
    }

    private void savePowerChangers(PrintWriter printWriter) {
        printWriter.print(powerChangers.size() + " ");
        for (PowerChanger powerChanger : powerChangers) {
            powerChanger.save(printWriter);
            printWriter.print(powerChanger.hashCode() + " ");

        }
    }

    public void saveMonsters(PrintWriter printWriter) {
        printWriter.print(monsters.size() + " ");
        for (Monster monster : monsters) {
            monster.save(printWriter);
        }
    }

    public void saveBombs(PrintWriter printWriter) {

        for (Bomb bomb : bombs) {
            if (!bomb.isExploded())
                bomb.save(printWriter);
        }

        printWriter.println(-555);
    }

    private void saveWallPoints(PrintWriter printWriter) {
        printWriter.println(wallCells.size() + " ");
        for (WallCell wallCell : wallCells) {
            printWriter.print(wallCell.getLocation().x + " " +
                    wallCell.getLocation().y + " " + wallCell.getTimeToBurn() + " ");
        }
    }

    private void saveBoardSize(PrintWriter printWriter) {
        printWriter.print(width + " " + height + " " + cellsSize + " ");
    }


    public boolean wallInLocation(int x, int y) {
        for (WallCell wallCell : wallCells) {
            if (wallCell.getLocation().x == x && wallCell.getLocation().y == y)
                return true;
        }
        return false;
    }

    public boolean blockInLocation(int x, int y) {
        for (BlockCell blockCell : blockCells) {
            if (blockCell.getLocation().x == x && blockCell.getLocation().y == y)
                return true;
        }
        return false;
    }

    public boolean bombInLocation(int x, int y) {
        for (Bomb bomb : bombs) {
            if (bomb.getLocation().x == x && bomb.getLocation().y == y)
                return true;
        }
        return false;
    }

    public boolean bomberManInLocation(int x, int y) {
        for (Point location : bomberManPlaces.values()) {
            if (location.x == x && location.y == y)
                return true;
        }
        return false;
    }


    public void update(Game game) {


        for (BackgroundCell backgroundCell : backgroundCells) {
            backgroundCell.repaint();
        }

        for (int i = powerChangers.size() - 1; i >= 0; i--) {
            if (powerChangers.get(i).isUsed()) {
                powerChangers.remove(powerChangers.get(i));
            }
        }


        gate.repaint(monsters.size() == 0);


        for (int i = wallCells.size() - 1; i >= 0; i--) {
            if (wallCells.get(i).isDestroyed()) {
                int killerID = wallCells.get(i).getBombermanID();
                BomberMan bomberMan = game.getBomberMansByID(killerID);
                if (bomberMan != null)
                    bomberMan.increasePoint(10);
                wallCells.remove(wallCells.get(i));
            }
        }


        for (int i = bombs.size() - 1; i >= 0; i--) {
            if (bombs.get(i).isBurned()) {
                bombs.remove(bombs.get(i));
            }
        }

        for (int i = monsters.size() - 1; i >= 0; i--) {
            if (monsters.get(i).deadCompeletly()) {
                int killerID = monsters.get(i).getKillerID();
                BomberMan bomberMan = game.getBomberMansByID(killerID);
                if (bomberMan != null)
                    bomberMan.increasePoint(monsters.get(i).getValue());

                monsters.remove(monsters.get(i));
            }
        }


    }


    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }


    public List<WallCell> getWallCells() {
        return wallCells;
    }


    public void changeBomberManPlace(int bombermanID, Point bomberManPlace) {
        int index = bomberManPlacesArray.indexOf(bomberManPlaces.get(bombermanID));
        if (index > -1) {
            bomberManPlacesArray.set(index, bomberManPlace);
            bomberManPlaces.get(bombermanID).setLocation(bomberManPlace);
        }
    }

    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
    }

    public List<Bomb> getBombs() {
        return bombs;
    }


    public int getCellsSize() {
        return cellsSize;
    }

    public List<BlockCell> getBlockCells() {
        return blockCells;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }


    public List<BackgroundCell> getBackgroundCells() {
        return backgroundCells;
    }

    public List<Point> getBombermanPlacesArray() {
        return bomberManPlacesArray;                                // used for monsters to prosecute bomberman
    }

    public List<BackgroundCell> getFreeCells() {
        return freeCells;
    }

    public void removeBomberman(BomberMan bomberMan) {
        bomberManPlacesArray.remove(bomberMan);
        bomberManPlaces.remove(bomberMan.getID());
    }

    public void sendExplodedWalls(PrintWriter writer) {
        for (int i = burningCells.size() - 1; i > -1; i--) {
            Cell cell = burningCells.get(i);
            if (cell.getClass() == WallCell.class) {
                writer.print(wallCells.indexOf(cell) + " " + ((WallCell) cell).getTimeToBurn() + " ");
            }
        }
        writer.println(-111);
    }

    public void sendBurningBackgroundCells(PrintWriter writer) {
        for (int i = burningCells.size() - 1; i > -1; i--) {
            Cell cell = burningCells.get(i);
            if (cell.getClass() == BackgroundCell.class)
                writer.print(backgroundCells.indexOf(cell) + " ");
        }
        writer.println(-444);
    }

    public void sendUsedPowers(PrintWriter writer) {

        for (int i = usedPowers.size() - 1; i > -1; i--) {                    //used powers
            writer.print(usedPowers.get(i).hashCode() + " ");
        }

        writer.println(-222);

        for (int i = burningPowers.size() - 1; i > -1; i--) {                       //exploded powers
            writer.print(burningPowers.get(i).hashCode() + " ");
        }
        writer.println(-333);
    }

    public void addBurningCell(Cell cell) {
        burningCells.add(cell);
        if (cell.getClass() == BackgroundCell.class) {                                                  //add burning powers
            for (int i = ((BackgroundCell) cell).getBurningPowers().size() - 1; i > -1; i--) {
                Object object = ((BackgroundCell) cell).getBurningPowers().get(i);
                if (!burningPowers.contains(object))
                    burningPowers.add((PowerChanger) object);

            }
        }
    }

    public List<Cell> getBurningCells() {
        return burningCells;
    }


    public void addUsedPower(PowerChanger powerChanger) {
        usedPowers.add(powerChanger);
    }


    public void addMonster(Class c) {
        if (!avilableMonsters.contains(c))
            avilableMonsters.add(c);
    }

    public Cell getFreeCellForBomberman() {
        Cell bombermanCell = null;
        for (Cell cell : freeCells) {
            if (getFreeNearbyCells(cell) > 1) {
                bombermanCell = cell;
                break;
            }
        }
        freeCells.remove(bombermanCell);
        return bombermanCell;
    }

    private int getFreeNearbyCells(Cell cell) {
        int ans = 0;

        Cell cell1 = cell.getNearbyCell(this, Side.UP_SIDE);
        if(!wallInLocation(cell1.getLocation().x,cell1.getLocation().y)
                && !blockInLocation(cell1.getLocation().x,cell1.getLocation().y))
            ans++;

        cell1 = cell.getNearbyCell(this, Side.RIGHT_SIDE);
        if(!wallInLocation(cell1.getLocation().x,cell1.getLocation().y)
                && !blockInLocation(cell1.getLocation().x,cell1.getLocation().y))
            ans++;

        cell1 = cell.getNearbyCell(this, Side.LEFT_SIDE);
        if(!wallInLocation(cell1.getLocation().x,cell1.getLocation().y)
                && !blockInLocation(cell1.getLocation().x,cell1.getLocation().y))
            ans++;

        cell1 = cell.getNearbyCell(this, Side.DOWN_SIDE);
        if(!wallInLocation(cell1.getLocation().x,cell1.getLocation().y)
                && !blockInLocation(cell1.getLocation().x,cell1.getLocation().y))
            ans++;

        return ans;
    }
}
