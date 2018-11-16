package components;

import components.cell.BackgroundCell;
import components.cell.BlockCell;
import components.cell.WallCell;
import components.power.PowerChanger;

import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

public class Board implements Serializable {
    private int width;
    private int height;
    public static int cellsSize;
    private List<WallCell> wallCells = new ArrayList<>();
    private List<BlockCell> blockCells = new ArrayList<>();
    private List<BackgroundCell> backgroundCells = new ArrayList<>();
    private List<Bomb> bombs = new ArrayList<>();
    private HashMap<Integer, Bomb> bombHashMap = new HashMap<>();
    private List<Monster> monsters = new ArrayList<>();
    private HashMap<Integer, Monster> monsterHashMap = new HashMap<>();
    private List<PowerChanger> powerChangers = new ArrayList<>();
    private Gate gate;
    private HashMap<Integer, PowerChanger> powerChangersHashMap = new HashMap<>();


    public Board(int width, int height, int cellsSize) {

        this.width = width;
        this.height = height;
        Board.cellsSize = cellsSize;

        createBackgroundGrassCells();
        createBlockCells();


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


    public void loadObjects(Scanner scanner) {

        loadWallPoints(scanner);
        //  loadBombs(scanner);
        loadPowerChangers(scanner);
        loadGate(scanner);


    }

    private void loadGate(Scanner scanner) {
        gate = new Gate(new Point(scanner.nextInt(), scanner.nextInt()));
    }


    private void loadPowerChangers(Scanner scanner) {
        try {
            int powers = scanner.nextInt();
            for (int i = 0; i < powers; i++) {
                Class c = Class.forName(scanner.next());
                powerChangers.add((PowerChanger) c.getDeclaredConstructors()[0].newInstance(scanner.nextInt(), scanner.nextInt()));
                powerChangers.get(i).setID(scanner.nextInt());
                powerChangersHashMap.put(powerChangers.get(i).getID(),powerChangers.get(i));
            }
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {
        }
    }


    private void loadWallPoints(Scanner scanner) {
        int walls = scanner.nextInt();
        for (int i = 0; i < walls; i++) {
            WallCell wallCell = new WallCell(scanner.nextInt(), scanner.nextInt());
            wallCell.setTimeToBurn(scanner.nextInt());
            wallCells.add(wallCell);
        }
    }


    public void paint(int x, int y, Graphics g) {

        for (BackgroundCell backgroundCell : backgroundCells) {
            backgroundCell.paint(x, y, g);
        }
        for (BlockCell blockCell : blockCells) {
            blockCell.paint(x, y, g);
        }
        for (int i = powerChangers.size() - 1; i >= 0; i--) {
            if (!powerChangers.get(i).isUsed()) {
                powerChangers.get(i).paint(x, y, g);
            } else {
                powerChangers.remove(powerChangers.get(i));
            }
        }


        if (gate != null)
            gate.paint(x, y, g, monsters.size() == 0);                          //paints gate here


        for (int i = wallCells.size() - 1; i >= 0; i--) {
            if (wallCells.get(i).isDestroyed()) {
                wallCells.remove(wallCells.get(i));
            } else {
                wallCells.get(i).paint(x, y, g);
            }
        }

        for (int i = bombs.size() - 1; i >= 0; i--) {
            if (!bombs.get(i).isBurned()) {
                bombs.get(i).paint(x, y, g);
            } else {
               // bombHashMap.remove(bombs.get(i).getID());
                bombs.remove(bombs.get(i));
            }
        }

        for (int i = monsters.size() - 1; i >= 0; i--) {
            monsters.get(i).paint(x, y, g);

        }

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Monster getMonsterByID(int id) {
        return monsterHashMap.get(id);
    }


    public void addMonster(Monster monster) {
        monsters.add(monster);
        monsterHashMap.put(monster.getID(), monster);
    }

    public Bomb getBombByID(int id) {
        return bombHashMap.get(id);
    }

    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
        bombHashMap.put(bomb.getID(), bomb);
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void removeMonster(int j) {
        monsterHashMap.remove(monsters.remove(j).getID());
    }

    public void removeBomb(int j) {
        bombHashMap.remove(bombs.remove(j).getID());
    }

    public void removeAllMonster() {
        monsters.clear();
        monsterHashMap.clear();
    }

    public void removeAllBombs() {
        bombs.clear();
        bombHashMap.clear();
    }

    public void burnWallCell(int cellIndex, int timeToBurn) {
        wallCells.get(cellIndex).setTimeToBurn(timeToBurn);
    }

    public void burnBackgroundCell(int cellIndex) {
        backgroundCells.get(cellIndex).setBurning(true);
    }

    public void burnPower(int id) {
        PowerChanger powerChanger = powerChangersHashMap.remove(id);
        if(powerChanger!=null)
        powerChanger.burn();
    }

    public void removePower(int id) {
        PowerChanger powerChanger = powerChangersHashMap.remove(id);
        if(powerChanger!=null)
        powerChanger.setUsed(true);
    }
}
