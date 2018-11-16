package UI;

import client.Client;
import components.Board;
import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameFrame extends JFrame implements KeyListener {

    private int remainTime = 300;
    private JLabel levelLabel;
    private JLabel monsterLabel;
    private JLabel timeLabel;
    private JLabel pointLabel;
    private Client client;
    public static int height;
    public static int width;
    private Game game;
    private JPanel content;
    private int x = 0, y = 0;
    private Board board;
    private ChatFrame chatFrame;


    public GameFrame(Client client, Game game) {


        this.game = game;
        this.client = client;
        board = game.getBoard();
        setTitle(client.getClientName());

        setSize(width, height);
        setLocation(300, 300);
        content = (JPanel) getContentPane();
        content.setLayout(new BorderLayout());


        JPanel paintPanel = new JPanel() {

            private Point location;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                //x v y frame Painting......


                if (client.isPlayer()) {
                    location = game.getMainBomberman().getLocation();


                    if (location.x > content.getWidth() / 2) {
                        x = location.x - content.getWidth() / 2;
                    }
                    if (location.y > content.getHeight() / 2) {
                        y = location.y - content.getHeight() / 2;
                    }
                    if (location.x > (2 * board.getHeight() + 3) * Board.cellsSize - content.getWidth() / 2) {
                        x = (2 * board.getHeight() + 3) * Board.cellsSize - getWidth();
                    }
                    if (location.y > (2 * board.getWidth() + 3) * Board.cellsSize - content.getHeight() / 2) {
                        y = (2 * board.getWidth() + 3) * Board.cellsSize - getHeight();
                    }
                    //        ...........


                    updateTimeAndPoint();

                }

                board.paint(x, y, g);


                for (int i = game.getBomberMans().size() - 1; i > -1; i--) {
                    game.getBomberMans().get(i).paint(x, y, g);
                }


            }
        };


        content.add(paintPanel);

        if (client.isPlayer()) {

            JPanel info = new JPanel();
            info.setBackground(Color.cyan);
            info.setLayout(new BoxLayout(info, BoxLayout.LINE_AXIS));


            JPanel panel1 = new JPanel();
            panel1.setBackground(Color.cyan);
            panel1.setLayout(new GridBagLayout());
            timeLabel = new JLabel("Remain Time :   " + getTimeFormat(remainTime));
            panel1.add(timeLabel);

            JPanel panel2 = new JPanel();
            panel2.setBackground(Color.cyan);
            panel2.setLayout(new GridBagLayout());
            pointLabel = new JLabel("Point :  " + game.getMainBomberman().getPoint());
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

            info.add(jPanel);

            content.add(info, BorderLayout.NORTH);

            activePointCounter();
            chatFrame = new ChatFrame(client);
        }

        addKeyListener(this);

        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                client.disconnect();
            }
        });



    }

    private void updateTimeAndPoint() {

        timeLabel.setText("Remain Time :   " + getTimeFormat(remainTime));
        pointLabel.setText("Point :  " + game.getMainBomberman().getPoint());
        monsterLabel.setText("Remain Monsters : " + board.getMonsters().size() + " ");
        levelLabel.setText("Level : " + Game.level);

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
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {                             //while ( gameisRunning ) !!!!!!!!!!!!!!
            @Override
            public void run() {
                if (remainTime > 0)
                    remainTime--;
            }
        };
        timer.schedule(timerTask, 1000, 1000);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_H){
            new HelpFrame();
        }else {
            if (client.isPlayer() && client.isConnected())
                client.getSender().sendAction(e.getKeyCode());
            else
                moveFrame(e.getKeyCode());
        }
    }

    private void moveFrame(int e) {
        if (e == KeyEvent.VK_RIGHT && x < (2 * board.getHeight() + 3) * Board.cellsSize - getWidth()) {
            x += 5;
        }
        if (e == KeyEvent.VK_LEFT && x > 0) {
            x -= 5;
        }
        if (e == KeyEvent.VK_UP && y > 0) {
            y -= 5;
        }
        if (e == KeyEvent.VK_DOWN && y < (2 * board.getWidth() + 3) * Board.cellsSize - getHeight()) {
            y += 5;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }

    public ChatFrame getChatFrame() {
        return chatFrame;
    }

    public void exit() {
        dispose();
        chatFrame.dispose();
        JFrame frame = new JFrame();
        frame.setSize(300,300);
        frame.setLocation(500,500);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel contentPane = (JPanel) frame.getContentPane();
        contentPane.setLayout(new GridBagLayout());
        contentPane.add(new JLabel("main.Game finished"));


        frame.setVisible(true);
    }
}
