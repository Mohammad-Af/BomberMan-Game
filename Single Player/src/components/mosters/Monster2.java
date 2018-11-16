package components.mosters;

import UI.Images;
import components.Board;
import components.BomberMan;
import main.Side;

import java.awt.*;
import java.io.PrintStream;

public class Monster2 extends Monster {
    public Monster2(int x, int y)
    {
        super(x, y);
        speed=80;
        lastArrayImage= Images.monster2WU;
    }

    private int blocked = 0;
    private int randomMoves = 0;

    @Override
    public void move(Board board) {


        if (imageCounter % speed == 0) {

            if (alive) {
                setCurrentCell(board);

                if (!BomberMan.alive) {                       //vaghti bomberman mord random harekat kone
                    continueRandomMove(board, lastMove);

                } else if (randomMoves == 0) {
                    while (!makeMove(board)) {
                        blocked++;
                        if (blocked == 10) {
                            randomMoves++;
                            break;
                        }
                    }
                    blocked = 0;
                } else {

                    continueRandomMove(board, lastMove);                                         //vaghti donbale bombermane va tu ye khune gir mikone bayad 10 harekate random anjam bde
                    randomMoves++;
                    if (randomMoves == 20)
                        randomMoves = 0;
                }
                killBomberman();

            } else {
                if (toDeadCompeletly == 13) {
                    lastIndexImage = 0;
                    lastArrayImage = Images.monsterDeath;
                } else {
                    lastIndexImage++;
                }
                toDeadCompeletly--;
            }
        }

    }

    @Override
    public void save(PrintStream printStream, int cellIndex) {
        printStream.print(getClass().getCanonicalName()+" "+location.x+" "+location.y+" "+lastMove.ordinal()+" ");
    }


    private boolean makeMove(Board board) {

        int xBomberman = board.getBomberManPlace().x;
        int yBomberman = board.getBomberManPlace().y;

        double rand = Math.random();

        if (location.y % Board.cellsSize == 0 && location.x > xBomberman && rand < 0.5) {
            int x = location.x - Board.cellsSize;
            int y = location.y;
            if (!board.blockInLocation(x,y) && !board.wallInLocation(x,y) && !board.bombInLocation(x, y)) {
                lastArrayImage = Images.monster2WL;
                location = new Point(location.x - movementSize, location.y);
                if(lastMove== Side.LEFT_SIDE)
                    setImageIndex();
                else {
                    lastMove= Side.LEFT_SIDE;
                    lastIndexImage = 0;
                }
                return true;
            }
        } else if (location.y % Board.cellsSize == 0 && location.x < xBomberman && rand < 0.5) {
            int x = location.x + Board.cellsSize;
            int y = location.y;
            if (!board.blockInLocation(x,y) && !board.wallInLocation(x,y) && !board.bombInLocation(x, y)) {
                lastArrayImage = Images.monster2WR;
                location = new Point(location.x + movementSize, location.y);
                if(lastMove== Side.RIGHT_SIDE)
                    setImageIndex();
                else {
                    lastMove= Side.RIGHT_SIDE;
                    lastIndexImage = 0;
                }
                return true;
            }
        } else if (location.x % Board.cellsSize == 0 && location.y < yBomberman && rand > 0.5) {
            int x = location.x;
            int y = location.y + Board.cellsSize;
            if (!board.blockInLocation(x,y) && !board.wallInLocation(x,y) && !board.bombInLocation(x, y)) {
                lastArrayImage = Images.monster2WD;
                location = new Point(location.x, location.y + movementSize);
                if(lastMove== Side.DOWN_SIDE)
                    setImageIndex();
                else {
                    lastMove= Side.DOWN_SIDE;
                    lastIndexImage = 0;
                }
                return true;
            }
        } else if (location.x % Board.cellsSize == 0 && location.y > yBomberman && rand > 0.5) {
            int x = location.x;
            int y = location.y - Board.cellsSize;
            if (!board.blockInLocation(x,y) && !board.wallInLocation(x,y) && !board.bombInLocation(x, y)) {
                lastArrayImage = Images.monster2WU;
                location = new Point(location.x, location.y - movementSize);
                if(lastMove== Side.UP_SIDE)
                    setImageIndex();
                else {
                    lastMove= Side.UP_SIDE;
                    lastIndexImage = 0;
                }
                return true;
            }
        }
        return false;
    }


    private void continueRandomMove(Board board, Side side) {
        while (!makeRandomMove(board, lastMove)) {
            lastMove = Side.values()[(int) (Math.random() * 4)];
            blocked++;
            if (blocked == 10) {
                blocked = 0;
                break;
            }
        }
    }

    private boolean makeRandomMove(Board board, Side side) {
        if (side == Side.RIGHT_SIDE) {
            int x = location.x + Board.cellsSize;
            int y = location.y;
            if (!board.blockInLocation(x,y) && !board.wallInLocation(x,y)  && !board.bombInLocation(x, y) && location.y % Board.cellsSize ==0) {
                lastArrayImage = Images.monster2WR;
                location = new Point(location.x + movementSize, location.y);
                if(lastMove== Side.RIGHT_SIDE)
                    setImageIndex();
                else
                    lastIndexImage=0;
                return true;
            }
        }
        if ( side == Side.LEFT_SIDE) {
            int x = location.x - Board.cellsSize;
            int y = location.y;
            if (!board.blockInLocation(x,y) && !board.wallInLocation(x,y) && !board.bombInLocation(x, y)&& location.y % Board.cellsSize ==0) {
                lastArrayImage = Images.monster2WL;
                location = new Point(location.x - movementSize, location.y);
                if(lastMove== Side.LEFT_SIDE)
                    setImageIndex();
                else
                    lastIndexImage=0;
                return true;
            }
        }
        if (side == Side.UP_SIDE) {
            int x = location.x;
            int y = location.y - Board.cellsSize;
            if (!board.blockInLocation(x,y) && !board.wallInLocation(x,y) && !board.bombInLocation(x, y) && location.x % Board.cellsSize ==0 ) {
                lastArrayImage = Images.monster2WU;
                location = new Point(location.x, location.y - movementSize);
                if(lastMove== Side.UP_SIDE)
                    setImageIndex();
                else
                    lastIndexImage=0;
                return true;
            }
        }
        if (side == Side.DOWN_SIDE) {
            int x = location.x;
            int y = location.y + Board.cellsSize;
            if (!board.blockInLocation(x,y) && !board.wallInLocation(x,y) && !board.bombInLocation(x, y) && location.x % Board.cellsSize ==0) {
                lastArrayImage = Images.monster2WD;
                location = new Point(location.x, location.y + movementSize);
                if(lastMove== Side.DOWN_SIDE)
                    setImageIndex();
                else
                    lastIndexImage=0;
                return true;
            }
        }

        return false;
    }

}
