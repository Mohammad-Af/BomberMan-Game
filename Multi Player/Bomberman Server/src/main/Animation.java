package main;

import components.Board;
import components.Bomb;
import components.BomberMan;
import components.cell.BackgroundCell;
import components.cell.Cell;
import components.cell.WallCell;
import components.monster.Monster;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class Animation {

    private Game game;

    public Animation(Game game) {


        this.game =game;
        Board board = game.getBoard();

        Thread animThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (game.isRunning()) {
                    try {
                        sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (Bomb bomb : board.getBombs()) {
                        bomb.animate();


                        if (bomb.isExploded() && !bomb.isBurned()) {
                            bomb.burn(board);


                            if (bomb.getToExplode() == -1 && game.getBomberMansByID(bomb.getBombermanID()) != null)
                                game.getBomberMansByID(bomb.getBombermanID()).increaseBombLimit();
                        }


                        if (!bomb.isAutoExplode() && !bomb.isExploded() && !game.getBomberMansByID(bomb.getBombermanID()).getBombsCanControl().contains(bomb)) {             //used for bomberMan bombs after game loaded
                            game.getBomberMansByID(bomb.getBombermanID()).getBombsCanControl().add(bomb);
                        }

                    }

                    for (int i = board.getMonsters().size() - 1; i > -1; i--) {
                        Monster monster = board.getMonsters().get(i);
                        monster.animate();
                        monster.move(board);

                    }


                    for (int i = game.getBomberMans().size() - 1; i > -1; i--) {
                        BomberMan bomberMan = game.getBomberMans().get(i);

                        bomberMan.increaseImageCounter();
                        bomberMan.allowToMove();

                    }


                    for (int i = board.getBurningCells().size() - 1; i >= 0; i--) {
                        Cell cell = board.getBurningCells().get(i);
                        if (cell.getClass() == BackgroundCell.class) {
                            if (!((BackgroundCell) cell).isBurning()) {
                                board.getBurningCells().remove(cell);
                            }
                        } else if (cell.getClass() == WallCell.class) {
                            if (((WallCell) cell).isDestroyed()) {
                                board.getBurningCells().remove(cell);
                            }
                        }
                    }


                    board.update(game);

                    for (int i = game.getBomberMans().size() - 1; i > -1; i--) {
                        game.getBomberMans().get(i).repaint();
                    }


                }
            }
        });
        animThread.start();

        activeTimer();


    }


    private void activeTimer() {

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {                             //while ( gameisRunning ) !!!!!!!!!!!!!!
            @Override
            public void run() {
                int remainTime = game.getRemainTime();
                if (remainTime > 0) {
                    remainTime--;
                    game.setRemainTime(remainTime);
                }
                if (remainTime == 0){
                    for (int i = game.getBomberMans().size() - 1; i >-1 ; i--) {
                        game.getBomberMans().get(i).decreasePoint(1);
                    }
                    if(game.getBomberMans().size()==0)
                        game.setRunning(false);
                }
            }
        };
        timer.schedule(timerTask, 1000, 1000);

    }
}
