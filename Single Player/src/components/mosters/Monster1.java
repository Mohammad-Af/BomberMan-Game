package components.mosters;

import UI.Images;
import components.Board;
import main.Side;

import java.awt.*;
import java.io.PrintStream;

public class Monster1 extends Monster {

    public Monster1(int x, int y) {
        super(x, y);
        speed = 80;
        lastArrayImage= Images.monster1WU;
    }


    private int blocked=0;
    @Override
    public void move(Board board) {
        if (imageCounter % speed == 0) {                            //sorAtesho inja mishe tanzim kard
            if (alive) {
                setCurrentCell(board);
                while (!makeMove(board, lastMove)) {
                    blocked++;
                    lastMove = Side.values()[(int) (Math.random() * Side.values().length)];
                    if(blocked==10) {
                        break;
                    }
                }
                blocked=0;
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



    private boolean makeMove(Board board, Side side) {
        if (side == Side.RIGHT_SIDE) {
            int x = location.x + Board.cellsSize;
            int y = location.y;
            if (!board.wallInLocation(x, y) && !board.bombInLocation(x, y) && !board.blockInLocation(x, y)) {
                lastArrayImage = Images.monster1WR;
                location = new Point(location.x + movementSize, location.y);
                if(lastMove== Side.RIGHT_SIDE)
                    setImageIndex();
                else
                    lastIndexImage=0;
                return true;
            }
        }
        if (side == Side.LEFT_SIDE) {
            int x = location.x - Board.cellsSize;
            int y = location.y;
            if (!board.wallInLocation(x, y) && !board.bombInLocation(x, y) && !board.blockInLocation(x, y)) {
                lastArrayImage = Images.monster1WL;
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
            if (!board.wallInLocation(x, y) && !board.bombInLocation(x, y) && !board.blockInLocation(x, y)) {
                lastArrayImage = Images.monster1WU;
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
            if (!board.wallInLocation(x, y) && !board.bombInLocation(x, y) && !board.blockInLocation(x, y)) {
                lastArrayImage = Images.monster1WD;
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

    @Override
    public void save(PrintStream printStream, int cellIndex) {
       printStream.print(getClass().getCanonicalName()+" "+location.x+" "+location.y+" "+lastMove.ordinal()+" ");
    }

}
