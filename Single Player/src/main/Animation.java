package main;

import components.Board;
import components.Bomb;
import components.mosters.Monster;

import static java.lang.Thread.sleep;

public class Animation {

    public Animation(Game game) {


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
                            if(game.getGameFrame().hasVibration())
                            game.getGameFrame().shake();

                            if(bomb.getToExplode()== -1 )
                            game.getBomberMan().increaseBombLimit();
                        }



                        if(!bomb.isAutoExplode() && !bomb.isExploded() && !game.getBomberMan().getBombsCanControl().contains(bomb)){             //used for bomberMan bombs after game loaded
                            game.getBomberMan().getBombsCanControl().add(bomb);
                        }

                    }

                    for (Monster monster : board.getMonsters()) {
                        monster.animate();
                        monster.move(board);

                    }


                    game.getBomberMan().increaseImageCounter();
                    game.getBomberMan().allowToMove();

                    game.getBomberMan().increasePoint(game.getBoard().getExplodedWallCells()*10);
                    game.getBomberMan().increasePoint(game.getBoard().getExplodedMonsters()*20);
                    game.getBoard().setExplodedMonsters(0);
                    game.getBoard().setExplodedWallCells(0);

                    game.getGameFrame().repaint();
                }
                if(game.isFinished())
                game.getGameFrame().exit();
            }
        });
        animThread.start();






    }
}
