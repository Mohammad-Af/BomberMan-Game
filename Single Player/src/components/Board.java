package components;

import components.cells.BackgroundCell;
import components.cells.BlockCell;
import components.cells.WallCell;
import components.mosters.*;
import components.powers.*;
import main.Game;
import main.Side;

import java.awt.*;
import java.io.*;
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
    private Point bomberManPlace;
    private int explodedWallCells = 0;
    private int explodedMonsters = 0;
    private int gameLevel;
    private Gate gate;
    private int monstersCount=0;


    public Board(String path) throws IOException {

        loadBoardSize(path);
        createBlockCells();
        createBackgroundGrassCells();
        loadObjects(path);
    }


    public Board(int width, int height, int cellsSize, int monstersCount) throws IOException, IllegalAccessException, InstantiationException, InvocationTargetException {
        this.width = width;
        this.height = height;
        Board.cellsSize = cellsSize;
        bomberManPlace = new Point(cellsSize, cellsSize);
        gameLevel = Game.level;
        this.monstersCount=monstersCount;

        createBlockCells();
        createWallCells();
        createBackgroundGrassCells();
        createMonsters();
        createPowerChangers();


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


        if(monstersCount==0)
        monstersCount = Math.min(width, height);

        if (gameLevel > 4) {
            monstersCount = (int) (monstersCount * Math.pow(1.05, gameLevel - 4));
        }
        List<Class> avilableMonsters = new ArrayList<>();
        if (gameLevel >= 1) {
            avilableMonsters.add(Monster1.class);
        }
        if (gameLevel >= 2) {
            avilableMonsters.add(Monster2.class);
        }
        if (gameLevel >= 3) {
            avilableMonsters.add(Monster3.class);
        }
        if (gameLevel >= 4) {
            avilableMonsters.add(Monster4.class);
        }

        List<Point> monstersLocation = new ArrayList<>();
        for (int i = 0; i < monstersCount; i++) {
            for (BackgroundCell backgroundCell : backgroundCells) {
                Point location = backgroundCell.getLocation();
                if (!wallInLocation(location.x, location.y)
                        && !blockInLocation(location.x, location.y)
                        && !bomberManInLocation(location.x, location.y)) {
                    monstersLocation.add(new Point(location.x, location.y));
                }
            }
        }
        Collections.shuffle(monstersLocation);


        for (int i = 0; i < monstersCount; i++) {
            Collections.shuffle(avilableMonsters);
            Class c = avilableMonsters.get(0);

            monsters.add((Monster) c.getConstructors()[0].newInstance(monstersLocation.remove(0).x, monstersLocation.remove(0).y));

        }


    }

    private void createPowerChangers() throws IllegalAccessException, InvocationTargetException, InstantiationException {


        int powerChangersCount = Math.min(2 * monsters.size(), wallCells.size() / 3);
        Class cc = GhostPower.class;
        Class[] classes = {GhostPower.class,BombController.class, BombIncreaser.class, PointIncreaser.class, RadiusIncreaser.class, SpeedIncreaser.class};
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


    public void save(String path) throws IOException {
        saveBoardSize(path);
        saveWallPoints(path);
        saveBombs(path);
        saveMonsters(path);
        savePowerChangers(path);
        saveGate(path);
    }

    private void saveGate(String path) throws FileNotFoundException {
        PrintStream printStream = new PrintStream(new File(path + File.separator+"gate.txt"));
        printStream.print(backgroundCells.indexOf(gate.getGateCell()));
    }

    private void savePowerChangers(String path) throws IOException {
        PrintStream printStream = new PrintStream(new File(path + File.separator+"powerChangers.txt"));
        for (PowerChanger powerChanger : powerChangers) {
            powerChanger.save(printStream, backgroundCells.indexOf(powerChanger.getCurrentCell()));

        }
    }

    private void saveMonsters(String path) throws FileNotFoundException {
        PrintStream printStream = new PrintStream(new File(path + File.separator+"monsters.txt"));
        for (Monster monster : monsters) {
            monster.save(printStream, backgroundCells.indexOf(monster.getCurrentCell()));
        }
    }

    private void saveBombs(String path) throws IOException {
        PrintStream printStream = new PrintStream(new File(path + File.separator+"bombs.txt"));
        for (Bomb bomb : bombs) {
            bomb.save(printStream);
        }
    }

    private void saveWallPoints(String path) throws FileNotFoundException {
        PrintStream printStream = new PrintStream(new File(path + File.separator+"wallPoints.txt"));
        for (WallCell wallCell : wallCells) {
            printStream.print(wallCell.getLocation().x + " " + wallCell.getLocation().y + " " + wallCell.getTimeToBurn() + " ");
        }
    }

    private void saveBoardSize(String path) throws FileNotFoundException {
        PrintStream printStream = new PrintStream(new File(path + File.separator+"board.txt"));
        printStream.print(width + " " + height + " " + cellsSize);
    }


    private void loadObjects(String path) throws IOException {

        loadWallPoints(path);
        loadBombs(path);
        loadPowerChangers(path);
        loadGate(path);
        loadMonsters(path);

    }

    private void loadGate(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(path + File.separator+"gate.txt"));
        gate = new Gate(backgroundCells.get(scanner.nextInt()));
    }

    private void loadMonsters(String path) {
        try {
            Scanner scanner = new Scanner(new FileInputStream(path + File.separator+"monsters.txt"));
            while (scanner.hasNext()) {
                Class c = Class.forName(scanner.next());
                Monster monster = (Monster) c.getDeclaredConstructors()[0].newInstance(scanner.nextInt(), scanner.nextInt());
                monster.setLastMove(Side.values()[scanner.nextInt()]);
                monsters.add(monster);
            }
        } catch (FileNotFoundException | ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void loadPowerChangers(String path) {
        try {
            Scanner scanner = new Scanner(new FileInputStream(path + File.separator+"powerChangers.txt"));
            while (scanner.hasNext()) {
                Class c = Class.forName(scanner.next());
                powerChangers.add((PowerChanger) c.getDeclaredConstructors()[0].newInstance(backgroundCells.get(scanner.nextInt())));
            }
        } catch (FileNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ignored) {
        }
    }

    private void loadBombs(String path) throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(path + File.separator+"bombs.txt"));
        while (scanner.hasNextInt()) {
            boolean autoExplode = false;
            if (scanner.nextInt() == 1)
                autoExplode = true;
            Bomb bomb = new Bomb(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), autoExplode);
            bomb.setCurrentCell(backgroundCells.get((bomb.getLocation().y / cellsSize) * (2 * height + 3) + (bomb.getLocation().x / cellsSize)));
            bomb.load(scanner);
            bombs.add(bomb);

        }
    }

    private void loadWallPoints(String path) throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(path + File.separator+"wallPoints.txt"));
        while (scanner.hasNextInt()) {
            WallCell wallCell = new WallCell(scanner.nextInt(), scanner.nextInt());
            wallCell.setTimeToBurn(scanner.nextInt());
            wallCells.add(wallCell);

        }
    }

    private void loadBoardSize(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(path + File.separator+"board.txt"));
        width = scanner.nextInt();
        height = scanner.nextInt();
        cellsSize = scanner.nextInt();
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

    private boolean bomberManInLocation(int x, int y) {
        return (x == bomberManPlace.x) && (y == bomberManPlace.y);
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


        gate.paint(x, y, g, monsters.size() == 0);                          //paints gate here


        for (int i = wallCells.size() - 1; i >= 0; i--) {
            if (wallCells.get(i).isDestroyed()) {
                wallCells.remove(wallCells.get(i));
                explodedWallCells++;                                              //used to calculate point in bomberman class
            } else {
                wallCells.get(i).paint(x, y, g);
            }
        }

        for (int i = bombs.size() - 1; i >= 0; i--) {
            if (!bombs.get(i).isBurned()) {
                bombs.get(i).paint(x, y, g);
            } else {
                bombs.remove(bombs.get(i));
            }
        }

        for (int i = monsters.size() - 1; i >= 0; i--) {
            if (!monsters.get(i).deadCompeletly()) {
                monsters.get(i).paint(x, y, g);
            } else {
                if (monsters.get(i).getClass() == Monster1.class)                           //used to calculate point in bomberman class
                    explodedMonsters += 1;
                if (monsters.get(i).getClass() == Monster2.class)
                    explodedMonsters += 2;
                if (monsters.get(i).getClass() == Monster3.class)
                    explodedMonsters += 3;
                if (monsters.get(i).getClass() == Monster4.class)
                    explodedMonsters += 4;

                monsters.remove(monsters.get(i));
            }
        }


    }

    public int getExplodedWallCells() {
        return explodedWallCells;
    }

    public int getExplodedMonsters() {
        return explodedMonsters;
    }

    public void setExplodedWallCells(int explodedWallCells) {
        this.explodedWallCells = explodedWallCells;
    }

    public void setExplodedMonsters(int explodedMonsters) {
        this.explodedMonsters = explodedMonsters;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<WallCell> getWallCells() {
        return wallCells;
    }

    public Point getBomberManPlace() {
        return bomberManPlace;
    }

    public void setBomberManPlace(Point bomberManPlace) {
        this.bomberManPlace = bomberManPlace;
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

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    public void setCellsSize(int cellsSize) {
        Board.cellsSize = cellsSize;
    }

    public List<BackgroundCell> getBackgroundCells() {
        return backgroundCells;
    }

}
