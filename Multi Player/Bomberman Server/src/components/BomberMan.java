package components;

import UI.StartFrame;
import components.cell.BlockCell;
import components.cell.Cell;
import components.cell.WallCell;
import components.power.PowerChanger;
import main.Game;
import main.Side;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class BomberMan {
    private String name;
    private Point realLocation;
    private Game game;
    public static int movementSize = Board.cellsSize / 5;
    private Side lastMove = Side.RIGHT_SIDE;
    private int lastIndexImage = 0;
    private static int crossGap;
    private Cell currentCell;
    private boolean alive = true;
    private int speed = 40;

    private int bombLimit = 1;
    private boolean canControlBombs = false;
    private int bombRadius = 1;
    private int point = 0;
    private List<Bomb> bombsCanControl = new ArrayList<>();
    private boolean ghostMood = false;

    private int ID;
    private List<Object> bomberManPowers = new ArrayList<>();
    private int killerID;


    public BomberMan(String name, Game game) {

        this.name = name;
        this.game = game;
        crossGap = Board.cellsSize / 3;
        ID = hashCode();
        //realLocation = new Point(components.Board.cellsSize, components.Board.cellsSize);
        realLocation = new Point(Board.cellsSize, Board.cellsSize);


    }


    private int imageCounter = 0;
    private int eventToDO = -1;

    public void shouldMove(int e) {
        eventToDO = e;
    }

    public void allowToMove() {

        if (alive && imageCounter % speed == 0 && eventToDO != -1) {                                                       //change speed !!!!!!!!!!!!!!!!
            int e = eventToDO;
            eventToDO = -1;                                                          //agar in nabashe mishe modele snake kheili jalebtare!!!!
            int cellsSize = Board.cellsSize;

            Point oldLocation = realLocation;

            if (e == KeyEvent.VK_RIGHT) {


                if (lastMove == Side.RIGHT_SIDE) {
                    lastIndexImage++;
                } else
                    lastIndexImage = 0;
                lastMove = Side.RIGHT_SIDE;

                if (realLocation.x < (game.getBoard().getHeight() * 2 + 1) * cellsSize) {

                    if (ghostMood) {
                        realLocation = new Point(realLocation.x + movementSize, realLocation.y);
                    } else {

                        if ((realLocation.y - cellsSize) % (2 * cellsSize) <= crossGap) {
                            realLocation = (new Point(realLocation.x + movementSize, realLocation.y - (realLocation.y - cellsSize) % (2 * cellsSize)));
                        } else if ((realLocation.y - cellsSize) % (2 * cellsSize) >= 2 * cellsSize - crossGap) {
                            realLocation = (new Point(realLocation.x + movementSize, realLocation.y - ((realLocation.y - cellsSize) % (2 * cellsSize)) + 2 * cellsSize));
                        }


                        if (game.getBoard().wallInLocation(realLocation.x - movementSize + cellsSize, realLocation.y)) {                   // for walls...........
                            realLocation = oldLocation;
                        }
                    }

                    if (game.getBoard().bombInLocation((realLocation.x - movementSize + cellsSize), realLocation.y)) {                   // for Bombs............
                        realLocation = oldLocation;
                    }

                }

            }
            if (e == KeyEvent.VK_LEFT) {

                if (lastMove == Side.LEFT_SIDE) {
                    lastIndexImage++;
                } else
                    lastIndexImage = 0;
                lastMove = Side.LEFT_SIDE;


                if (realLocation.x > cellsSize) {

                    if (ghostMood) {
                        realLocation = new Point(realLocation.x - movementSize, realLocation.y);
                    } else {

                        if ((realLocation.y - cellsSize) % (2 * cellsSize) <= crossGap) {
                            realLocation = (new Point(realLocation.x - movementSize, realLocation.y - (realLocation.y - cellsSize) % (2 * cellsSize)));
                        } else if ((realLocation.y - cellsSize) % (2 * cellsSize) >= 2 * cellsSize - crossGap) {
                            realLocation = (new Point(realLocation.x - movementSize, realLocation.y - ((realLocation.y - cellsSize) % (2 * cellsSize)) + 2 * cellsSize));
                        }
                        if (game.getBoard().wallInLocation((realLocation.x + movementSize - cellsSize), realLocation.y)) {               //for walls...............
                            realLocation = oldLocation;
                        }
                    }
                    if (game.getBoard().bombInLocation(realLocation.x + movementSize - cellsSize, realLocation.y)) {               //for bombs...............
                        realLocation = oldLocation;
                    }

                }

            }
            if (e == KeyEvent.VK_UP) {

                if (lastMove == Side.UP_SIDE) {
                    lastIndexImage++;
                } else
                    lastIndexImage = 0;
                lastMove = Side.UP_SIDE;

                if (realLocation.y > cellsSize) {

                    if (ghostMood) {
                        realLocation = new Point(realLocation.x, realLocation.y - movementSize);
                    } else {
                        if ((realLocation.x - cellsSize) % (2 * cellsSize) <= crossGap) {
                            realLocation = (new Point(realLocation.x - (realLocation.x - cellsSize) % (2 * cellsSize), realLocation.y - movementSize));
                        } else if ((realLocation.x - cellsSize) % (2 * cellsSize) >= 2 * cellsSize - crossGap) {
                            realLocation = (new Point(realLocation.x - ((realLocation.x - cellsSize) % (2 * cellsSize)) + 2 * cellsSize, realLocation.y - movementSize));
                        }
                        if (game.getBoard().wallInLocation(realLocation.x, realLocation.y + movementSize - cellsSize)) {          //for walls
                            realLocation = oldLocation;
                        }
                    }
                    if (game.getBoard().bombInLocation(realLocation.x, realLocation.y + movementSize - cellsSize)) {          //for bombs
                        realLocation = oldLocation;
                    }

                }
            }
            if (e == KeyEvent.VK_DOWN) {

                if (lastMove == Side.DOWN_SIDE) {
                    lastIndexImage++;
                } else
                    lastIndexImage = 0;
                lastMove = Side.DOWN_SIDE;

                if (realLocation.y < (game.getBoard().getWidth() * 2 + 1) * cellsSize) {
                    if (ghostMood) {
                        realLocation = new Point(realLocation.x, realLocation.y + movementSize);
                    } else {
                        if ((realLocation.x - cellsSize) % (2 * cellsSize) <= crossGap) {
                            realLocation = (new Point(realLocation.x - (realLocation.x - cellsSize) % (2 * cellsSize), realLocation.y + movementSize));
                        } else if ((realLocation.x - cellsSize) % (2 * cellsSize) >= 2 * cellsSize - crossGap) {
                            realLocation = (new Point(realLocation.x - ((realLocation.x - cellsSize) % (2 * cellsSize)) + 2 * cellsSize, realLocation.y + movementSize));

                        }
                        if (game.getBoard().wallInLocation(realLocation.x, realLocation.y - movementSize + cellsSize)) {          //for walls......
                            realLocation = oldLocation;
                        }
                    }
                    if (game.getBoard().bombInLocation(realLocation.x, realLocation.y - movementSize + cellsSize)) {          //for bombs......
                        realLocation = oldLocation;
                    }

                }


            }


            if (lastIndexImage == 4) {
                lastIndexImage = 0;
            }
            game.getBoard().changeBomberManPlace(ID, realLocation);


            loadCell();


            for (int i = currentCell.getInsideObjs().size()-1 ; i> -1;i--) {
                Object object = currentCell.getInsideObjs().get(i);

                if (object instanceof PowerChanger && !((PowerChanger) object).isUsed()) {
                    ((PowerChanger) object).setUsed(true);
                    game.getBoard().addUsedPower(((PowerChanger) object));
                    ((PowerChanger) object).doJob(this);

                }
                if (object instanceof Gate) {
                    if (((Gate) object).isOpen()) {
                        if(game.getBomberMans().size()==0)
                        game.setRunning(false);

                        game.removeBomberman(this);
                        int level = game.getLevel() + 1;

                        point+=100;

                                                                                               //Default : monstersCount = 0 ; board.getMonstersCount()
                        StartFrame.NewGameFrame.init(game.getName(), level, game.getBoard().getWidth(), game.getBoard().getHeight(), Board.cellsSize, 0, getBomberManPowers());
                    }
                }
            }

        }


    }

    public Point getRealLocation() {
        return realLocation;
    }

    public void setRealLocation(Point realLocation) {
        game.getBoard().changeBomberManPlace(ID, realLocation);
        this.realLocation = realLocation;
    }

    private void setLastMove(Side lastMove) {
        this.lastMove = lastMove;
    }


    public void save(PrintWriter printWriter) {

        printWriter.print(hashCode() + " "+name+" " + realLocation.x + " " + realLocation.y + " " + point + " " + lastIndexImage + " ");
        if (dieCalled)
            printWriter.print(6 + " ");
        else if (lastMove == null)
            printWriter.print(5 + " ");
        else
            printWriter.println(lastMove.ordinal() + " ");

    }



    private void loadCell() {
        int cellsSize = game.getBoard().getCellsSize();
        if (currentCell != null)
            currentCell.removeInsideObj(this);
        currentCell = game.getBoard().getBackgroundCells().get(((realLocation.y + cellsSize / 2) / cellsSize) * (2 * game.getBoard().getHeight() + 3) + (realLocation.x + cellsSize / 2) / cellsSize);
        if (ghostMood) {
            for (WallCell wallCell : game.getBoard().getWallCells()) {
                if (wallCell.getLocation().equals(currentCell.getLocation()))
                    currentCell = wallCell;
            }
            for (BlockCell blockCell : game.getBoard().getBlockCells()) {
                if (blockCell.getLocation().equals(currentCell.getLocation()))
                    currentCell = blockCell;
            }
        }
        currentCell.addInsideObj(this);
    }


    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public String getName() {
        return name;
    }

    public void repaint() {
        if (!alive) {
            if (!dieCalled) {
                dieCalled = true;
                lastIndexImage = 0;
            }
            die();

        }
    }


    private boolean dieCalled = false;
    private int counter = 0;

    private void die() {
        if (lastIndexImage < 5) {
            if (counter == 30) {                                                 //speed of death!!!!
                lastIndexImage++;
                counter = 0;
            }
            counter++;
        } else {
            if(killerID != 0 && killerID != ID){
                game.getBomberMansByID(killerID).increasePoint(100);
            }
            game.removeBomberman(this);
            game.addDeadBomberMan(this);
        }


    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void increaseImageCounter() {
        imageCounter++;
    }


    public void increaseBombLimit() {
        bombLimit++;
    }

    public void decreaseBombLimit() {
        if (bombLimit >= 1)
            bombLimit--;
    }

    public void increaseSpeed() {
        if (speed > 10)
            speed -= 10;
    }

    public void decreaseSpeed() {
        speed += 10;
    }

    public synchronized void increasePoint(int point) {
        this.point += point;
    }

    public synchronized void decreasePoint(int point) {
        this.point -= point;
        if (this.point < 0)
            alive = false;
    }

    private int getBombRadius() {
        return bombRadius;
    }

    public void increaseBombRadius() {
        bombRadius++;
    }

    public void decreaseBombRadius() {
        if (bombRadius > 1)
            bombRadius--;
    }

    public void setCanControlBombs(boolean canControlBombs) {
        this.canControlBombs = canControlBombs;
    }

    private void addBomb(Bomb bomb) {
        bombsCanControl.add(bomb);
    }

    private boolean canControlBombs() {
        return canControlBombs;
    }

    public synchronized void explodeBomb() {
        if (bombsCanControl.size() != 0) {
            Bomb bomb = bombsCanControl.remove(0);
            bomb.setToExplode(0);
            bomb.setExploded(true);
        }
    }

    public int getBombLimit() {
        return bombLimit;
    }

    public int getPoint() {
        return point;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setBombLimit(int bombLimit) {
        this.bombLimit = bombLimit;
    }

    public void setBombRadius(int bombRadius) {
        this.bombRadius = bombRadius;
    }

    public List<Bomb> getBombsCanControl() {
        return bombsCanControl;
    }

    public void setGhostMood(boolean ghostMood) {
        this.ghostMood = ghostMood;
    }

    public int getID() {
        return ID;
    }

    public void throwBomb() {
        Board board = game.getBoard();
        int cellsSize = Board.cellsSize;
        Point location = currentCell.getLocation();
        if (alive && getBombLimit() != 0
                && !board.wallInLocation(location.x, location.y)
                && !board.blockInLocation(location.x, location.y)) {
            decreaseBombLimit();
            setLastMove(null);
            int x = cellsSize * ((realLocation.x + cellsSize / 2) / cellsSize);             //bomberman place!!!!!!!
            int y = cellsSize * ((realLocation.y + cellsSize / 2) / cellsSize);              //bom...
            if (!board.bombInLocation(x, y)) {
                Bomb bomb = new Bomb(x, y, getBombRadius(), !canControlBombs(), getID());
                if (canControlBombs()) {
                    addBomb(bomb);
                }
                if (currentCell == null)
                    bomb.setCurrentCell(board.getBackgroundCells().get(((realLocation.y + cellsSize / 2) / cellsSize) * (2 * game.getBoard().getHeight() + 3) + (realLocation.x + cellsSize / 2) / cellsSize));
                else
                    bomb.setCurrentCell(currentCell);


                board.addBomb(bomb);
            }
        }
    }

    public List<Object> getBomberManPowers() {
        bomberManPowers.clear();

        bomberManPowers.add(name);
        bomberManPowers.add(speed);
        bomberManPowers.add(bombLimit);
        bomberManPowers.add(canControlBombs);
        bomberManPowers.add(bombRadius);
        bomberManPowers.add(ghostMood);
        bomberManPowers.add(point);
        return bomberManPowers;
    }

    public List<Object> getBomberManDefaultPowers() {
        List<Object>bomberManDefaultPowers=new ArrayList<>();
        bomberManDefaultPowers.add(name);
        bomberManDefaultPowers.add(40);
        bomberManDefaultPowers.add(1);
        bomberManDefaultPowers.add(false);
        bomberManDefaultPowers.add(1);
        bomberManDefaultPowers.add(false);
        bomberManDefaultPowers.add(0);
        return bomberManDefaultPowers;
    }

    public void setKillerID(int killerID) {
        this.killerID = killerID;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
