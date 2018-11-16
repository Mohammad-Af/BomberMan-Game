package UI;

import components.Board;
import components.Bomb;
import components.BomberMan;
import main.Animation;
import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class GameFrame extends JFrame {

    private JPanel content;
    private Board board;
    private int cellsSize;
    private Game game;
    private static int x = 0;
    private static int y = 0;
    private int remainTime = 300;
    private JLabel timeLabel;
    private JLabel pointLabel;
    private JLabel monsterLabel;
    private JLabel levelLabel;
    private Timer timer;
    private boolean vibration = false;


    public GameFrame(Game game) {
        this.game = game;
        board = game.getBoard();
        game.setGameFrame(this);
        cellsSize = Board.cellsSize;


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(15 * cellsSize, 11 * cellsSize);
        if (board.getHeight() < 6) {
            setSize((2 * board.getHeight() + 3) * cellsSize, getHeight());
        }
        if (board.getWidth() < 5) {
            setSize(getWidth(), (2 * board.getWidth() + 3) * cellsSize);
        }

        setLocation(800, 20);
        setResizable(false);
        content = (JPanel) getContentPane();
        content.setLayout(new BorderLayout());
        content.setBackground(Color.cyan);


        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);


//x v y frame Painting......
                if (board.getBomberManPlace().x > content.getWidth() / 2) {
                    x = board.getBomberManPlace().x - content.getWidth() / 2;
                }
                if (board.getBomberManPlace().y > content.getHeight() / 2) {
                    y = board.getBomberManPlace().y - content.getHeight() / 2;
                }
                if (board.getBomberManPlace().x > (2 * board.getHeight() + 3) * cellsSize - content.getWidth() / 2) {
                    x = (2 * board.getHeight() + 3) * cellsSize - getWidth();
                }
                if (board.getBomberManPlace().y > (2 * board.getWidth() + 3) * cellsSize - content.getHeight() / 2) {
                    y = (2 * board.getWidth() + 3) * cellsSize - getHeight();
                }
                //...........


                board.paint(x, y, g);

                game.getBomberMan().paint(x, y, g);

                updateTimeAndPoint();


            }
        };


        panel.setBackground(new Color(11, 127, 15));
        content.add(panel);

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.cyan);
        panel1.setLayout(new GridBagLayout());
        timeLabel = new JLabel("Remain Time :   " + getTimeFormat(remainTime));
        panel1.add(timeLabel);

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.cyan);
        panel2.setLayout(new GridBagLayout());
        pointLabel = new JLabel("Point :  " + game.getBomberMan().getPoint());
        panel2.add(pointLabel);

        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.cyan);
        panel3.setLayout(new GridBagLayout());
        monsterLabel = new JLabel("Remain Monsters : " + board.getMonsters().size() + " ");
        panel3.add(monsterLabel);

        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        panel4.setBackground(Color.cyan);
        levelLabel = new JLabel("Level : " + Game.level);
        panel4.add(levelLabel);

        JPanel panel5 = new JPanel();
        panel5.setLayout(new GridBagLayout());
        panel5.setBackground(Color.cyan);
        panel5.add(new JLabel("Press H for help"));


        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.LINE_AXIS));
        jPanel.add(panel1);
        jPanel.add(panel2);
        jPanel.add(panel3);
        jPanel.add(panel4);
        jPanel.add(panel5);

        content.add(jPanel, BorderLayout.NORTH);


        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {


            }

            @Override
            public void keyPressed(KeyEvent e) {
                BomberMan bm = game.getBomberMan();

                if (e.getKeyCode() == KeyEvent.VK_H) {
                    new HelpFrame();
                } else if (e.getKeyCode() == KeyEvent.VK_B) {
                    Point location = bm.getCurrentCell().getLocation();
                    if (BomberMan.alive && bm.getBombLimit() != 0
                            && !board.wallInLocation(location.x, location.y)
                            && !board.blockInLocation(location.x, location.y)) {
                        bm.decreaseBombLimit();
                        bm.setLastMove(null);
                        int x = cellsSize * ((board.getBomberManPlace().x + cellsSize / 2) / cellsSize);
                        int y = cellsSize * ((board.getBomberManPlace().y + cellsSize / 2) / cellsSize);
                        if (!board.bombInLocation(x, y)) {
                            Bomb bomb = new Bomb(x, y, bm.getBombRadius(), !bm.canControlBombs());
                            if (bm.canControlBombs()) {
                                bm.addBomb(bomb);
                            }
                            if (bm.getCurrentCell() == null)
                                bomb.setCurrentCell(board.getBackgroundCells().get(2 * game.getBoard().getHeight() + 4));
                            else
                                bomb.setCurrentCell(bm.getCurrentCell());


                            board.addBomb(bomb);
                        }
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_S && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0 && bm.isAlive()) {
                    game.setRunning(false);
                    setVisible(false);
                    dispose();
                    timer.cancel();
                    new SaveFrame(game);

                } else if (e.getKeyCode() == KeyEvent.VK_O && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {

                    game.setRunning(false);
                    setVisible(false);
                    dispose();
                    try {
                        new LoadFrame();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


                } else {
                    //  bm.move(e);
                    bm.shouldMove(e);

                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    game.getBomberMan().explodeBomb();

                }

            }

        });


        setVisible(true);

        new Animation(game);

        activePointCounter();


    }

    private void updateTimeAndPoint() {
        timeLabel.setText("Remain Time :   " + getTimeFormat(remainTime));
        pointLabel.setText("Point :  " + game.getBomberMan().getPoint());
        monsterLabel.setText("Remain Monsters : " + board.getMonsters().size() + " ");
        levelLabel.setText("Level : " + Game.level);
    }


    public int getCellsSize() {
        return cellsSize;
    }

    private String getTimeFormat(int time) {
        String s = "0";
        s += (time / 60);
        s += ":";
        if (time % 60 < 10) {
            s += "0";
        }
        s += (time % 60);
        return s;
    }


    private void activePointCounter() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {                             //while ( gameisRunning ) !!!!!!!!!!!!!!
            @Override
            public void run() {
                if (remainTime > 0)
                    remainTime--;
                if (remainTime == 0)
                    game.getBomberMan().decreasePoint(1);
            }
        };
        timer.schedule(timerTask, 1000, 1000);

    }

    public int getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }

    public boolean hasVibration() {
        return vibration;
    }

    public void setVibration(boolean vibration) {
        this.vibration = vibration;
    }

    private int shakeStep = 4;

    public void shake() {

        if (shakeStep == 4) {
            x += 5;
        }
        if (shakeStep == 3) {
            y -= 5;
        }
        if (shakeStep == 2) {
            x -= 5;
        }
        if (shakeStep == 1) {
            y += 5;
        }
        shakeStep--;
        if (shakeStep == 0) {
            shakeStep = 4;
        }

    }


    public void exit() {
        dispose();
        JFrame frame = new JFrame();
        frame.setSize(300, 300);
        frame.setLocation(300, 500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel content = (JPanel) frame.getContentPane();
        content.setLayout(new GridBagLayout());
        content.add(new JLabel("main.Game finished"));
        frame.setVisible(true);
    }
}
