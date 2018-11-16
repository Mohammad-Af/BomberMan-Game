package UI;

import components.Board;
import components.BomberMan;
import components.cell.Cell;
import main.Animation;
import main.Game;
import server.ClientManager;
import server.Server;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class StartFrame extends JFrame implements ActionListener {

    private JButton newGameButton;
    private JButton showRunningGamesButton;
    private BufferedImage bombermanImage = ImageIO.read(new File("bomberman"+File.separator+"start.png"));
    private ImageObserver observer = this;
    private static Server server = new Server();

    public StartFrame() throws IOException {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocation(500, 400);
        setResizable(false);
        JPanel content = (JPanel) getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));

        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(500, 20));

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bombermanImage, 140, 30, 240, 240, observer);
            }
        };
        panel.setPreferredSize(new Dimension(500, 300));

        newGameButton = new JButton("Create new game");
        newGameButton.setPreferredSize(new Dimension(200, 30));
        jPanel.add(newGameButton);
        showRunningGamesButton = new JButton("Show Running games");
        showRunningGamesButton.setPreferredSize(new Dimension(200, 30));
        jPanel.add(showRunningGamesButton);

        jPanel.setBackground(Color.red);

        newGameButton.addActionListener(this);
        showRunningGamesButton.addActionListener(this);

        content.add(jPanel);
        content.add(panel);


        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            // dispose();
            new NewGameFrame();

        }
        if (e.getSource() == showRunningGamesButton) {

            //   dispose();

            JFrame gamesFrame = new JFrame("Running games");
            gamesFrame.setLocation(300, 300);
            gamesFrame.setSize(500, 500);
            JPanel content = (JPanel) gamesFrame.getContentPane();
            content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));

            for (int i = server.getGames().size() - 1; i > -1; i--) {
                if (server.getGames().get(i).getBomberMans().size() == 0 && server.getGames().get(i).getRemainTime() <= 0) {
                    server.getGames().remove(i).stop();
                } else {
                    JPanel jPanel = new JPanel();
                    jPanel.setLayout(new GridBagLayout());
                    JButton button = new JButton(server.getGames().get(i).getName());
                    int finalI = i;
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            gamesFrame.dispose();
                            JFrame clientsFrame = new JFrame("Clients");
                            clientsFrame.setLocation(300, 300);
                            clientsFrame.setSize(500, 500);
                            JPanel content = (JPanel) clientsFrame.getContentPane();
                            content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));

                            List<BomberMan> bombermans = server.getGames().get(finalI).getBomberMans();
                            for (BomberMan bomberman : bombermans) {
                                JPanel panel = new JPanel();
                                panel.setLayout(new GridBagLayout());
                                panel.add(new JLabel(bomberman.getName() + "  Point : " + bomberman.getPoint()));
                                content.add(panel);
                            }

                            JButton addMonsterButton = new JButton("Add new components.monster.Monster to game");
                            addMonsterButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    Game game = server.getGames().get(finalI);
                                    clientsFrame.dispose();
                                    try {
                                        new ReflectionFrame(game);
                                    } catch (IOException | ClassNotFoundException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            });
                            JButton endGameButton = new JButton("End main.Game");
                            endGameButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    server.getGames().remove(finalI).stop();
                                    clientsFrame.dispose();
                                }
                            });
                            JPanel panel = new JPanel();
                            panel.setLayout(new GridBagLayout());
                            panel.add(addMonsterButton);
                            panel.add(endGameButton);

                            JPanel panel1 = new JPanel();
                            panel1.setLayout(new BorderLayout());
                            panel1.add(panel, BorderLayout.SOUTH);

                            content.add(panel1);

                            clientsFrame.setVisible(true);
                        }
                    });
                    jPanel.add(button);


                    content.add(jPanel);


                }


                gamesFrame.setVisible(true);
            }

        }
    }


    public static class NewGameFrame extends JFrame implements ActionListener {


        private JPanel getContent;
        private JButton start;
        private JTextField textField;
        private JTextField textField1;
        private JTextField textField2;
        private JTextField textField3;
        private JComboBox<String> comboBox;
        private int width;
        private int height;
        private int monstersCount = 0;
        private int res = 0;


        NewGameFrame() {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setSize(500, 500);
            setLocation(300, 300);
            getContent = (JPanel) getContentPane();
            getContent.setLayout(new BoxLayout(getContent, BoxLayout.PAGE_AXIS));

            getContent.setBackground(Color.cyan);
            JPanel panel1 = new JPanel();             //board size
            JPanel panel2 = new JPanel();             //board size
            JPanel panel3 = new JPanel();             //resolution
            JPanel panel4 = new JPanel();
            JPanel panel5 = new JPanel();
            JPanel panel6 = new JPanel();


            Font font = new Font("SansSerif", Font.BOLD, 20);

            start = new JButton("Start");
            start.setFont(font);
            start.setPreferredSize(new Dimension(90, 30));
            start.addActionListener(this);
            panel5.setLayout(new GridBagLayout());
            panel5.add(start);


            JLabel label = new JLabel("Enter board height : ");
            label.setFont(new Font("SansSerif", Font.BOLD, 20));
            panel1.add(label);
            textField = new JTextField();
            textField.addActionListener(this);
            textField.setPreferredSize(new Dimension(60, 30));
            textField.setHorizontalAlignment(JTextField.CENTER);
            textField.setFont(font);
            panel1.setLayout(new GridBagLayout());
            panel1.add(textField);
            panel1.setBackground(Color.red);

            JLabel label1 = new JLabel("Enter board width : ");
            label1.setFont(font);
            panel2.add(label1);
            textField1 = new JTextField();
            textField1.addActionListener(this);
            textField1.setPreferredSize(new Dimension(60, 30));
            textField1.setFont(font);
            textField1.setHorizontalAlignment(JTextField.CENTER);
            panel2.setLayout(new GridBagLayout());
            panel2.add(textField1);
            panel2.setBackground(Color.GREEN);


            JLabel label2 = new JLabel("Choose resolution : ");
            label2.setFont(font);
            panel3.add(label2);
            panel3.setLayout(new GridBagLayout());
            String[] items = {" ", "very high", "high", "medium", "low", "very low"};
            comboBox = new JComboBox<>(items);
            comboBox.setPreferredSize(new Dimension(100, 30));
            comboBox.setFont(new Font("SansSerif", Font.BOLD, 13));
            comboBox.addActionListener(this);
            panel3.add(comboBox);
            panel3.setBackground(Color.yellow);

            JLabel label3 = new JLabel("Number of Monsters : ");
            label3.setFont(font);
            panel4.add(label3);
            panel4.setLayout(new GridBagLayout());
            textField2 = new JTextField("Default");
            textField2.addActionListener(this);
            textField2.setPreferredSize(new Dimension(80, 30));
            textField2.setFont(font);
            textField2.setHorizontalAlignment(JTextField.CENTER);
            panel4.add(textField2);
            panel4.setBackground(Color.cyan);

            JLabel label4 = new JLabel("main.Game Name : ");
            label4.setFont(font);
            panel6.add(label4);
            panel6.setLayout(new GridBagLayout());
            textField3 = new JTextField();
            textField3.addActionListener(this);
            textField3.setPreferredSize(new Dimension(200, 30));
            textField3.setFont(font);
            textField3.setHorizontalAlignment(JTextField.CENTER);
            panel6.add(textField3);
            panel6.setBackground(Color.cyan);


            getContent.add(panel6);
            getContent.add(panel1);
            getContent.add(panel2);
            getContent.add(panel3);
            getContent.add(panel4);
            getContent.add(panel5);


            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == start) {
                try {
                    width = Integer.parseInt(textField.getText());
                    height = Integer.parseInt(textField1.getText());
                    if (!textField2.getText().equals("Default")) {
                        monstersCount = Integer.parseInt(textField2.getText());
                        if (monstersCount <= 0)
                            JOptionPane.showMessageDialog(new JFrame(), "Enter positive number");

                    }

                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(new JFrame(), "Invalid input");
                }
                if (res == 0 || res == 10) {
                    JOptionPane.showMessageDialog(new JFrame(), "Choose resolution");
                } else {
                    dispose();

                    init(textField3.getText(), 1, width, height, res, monstersCount, null);
                }

            }
            if (e.getSource() == comboBox) {
                res = (10 + comboBox.getSelectedIndex() * 10);
            }

        }

        static Game game;

        public static Game init(String name, int level, int width, int height, int res, int monstersCount, List<Object> bombermanPowerChangers) {

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        game = new Game(name, level);

                        Board board = new Board(width, height, res, monstersCount, level);
                        game.setBoard(board);

                        new Animation(game);

                        server.addGame(game);


                        if (bombermanPowerChangers != null) {                                   //just used while going to next level

                            BomberMan bomberMan = initBomberMan(game, bombermanPowerChangers);

                            ClientManager cm = server.getClientsMap().get(bombermanPowerChangers.get(0));    //get by name

                            Game game1 = cm.getGame();        //old game used to get other clients

                            for (int i = game1.getBomberMans().size() - 1; i > -1; i--) {      //moves other clients to next level
                                String name = game1.getBomberMans().get(i).getName();
                                ClientManager clientManager = server.getClientsMap().get(name);

                                List<Object> bomberManPowers = game1.getBomberMans().get(i).getBomberManPowers();
                                BomberMan bomberMan1 = initBomberMan(game, bomberManPowers);

                                clientManager.setGame(game);
                                clientManager.setBomberMan(bomberMan1);
                                clientManager.setGoingToNextLevel(true);
                            }

                            for (int i = 0; i < game1.getDeadBombermans().size(); i++) {             //moves dead clients to next level
                                String name = game1.getDeadBombermans().get(i).getName();
                                ClientManager clientManager = server.getClientsMap().get(name);

                                List<Object> bomberManPowers = game1.getDeadBombermans().get(i).getBomberManDefaultPowers();

                                BomberMan bomberMan1 = initBomberMan(game, bomberManPowers);

                                clientManager.setGame(game);
                                clientManager.setBomberMan(bomberMan1);
                                clientManager.setGoingToNextLevel(true);

                            }

                            server.getGames().remove(game1);

                            cm.setGame(game);
                            cm.setBomberMan(bomberMan);
                            cm.setGoingToNextLevel(true);

                        }

                        if (!server.isRunning()) {
                            server.start();
                        }


                    } catch ( IOException | IllegalAccessException | InstantiationException | InvocationTargetException e1) {
                        e1.printStackTrace();
                    }

                }

                BomberMan initBomberMan(Game game1, List<Object> bomberManPowers) {
                    BomberMan bomberMan = new BomberMan((String) bomberManPowers.get(0), game1);
                    bomberMan.setSpeed((Integer) bomberManPowers.get(1));
                    bomberMan.setBombLimit((Integer) bomberManPowers.get(2));
                    bomberMan.setCanControlBombs((Boolean) bomberManPowers.get(3));
                    bomberMan.setBombRadius((Integer) bomberManPowers.get(4));
                    bomberMan.setGhostMood((Boolean) bomberManPowers.get(5));
                    bomberMan.setPoint((Integer) bomberManPowers.get(6));
                    Cell cell = game1.getBoard().getFreeCellForBomberman();
                  //  components.cell.Cell components.cell = (game1.getBoard().getFreeCells().remove(0));
                    bomberMan.setRealLocation(new Point(cell.getLocation().x, cell.getLocation().y));         // chera setRealLocation(components.cell.location) kharab mishe !!??
                    game1.addBomberMan(bomberMan);
                    game1.getBoard().getBombermanPlacesArray().add(bomberMan.getRealLocation());
                    game1.getBoard().initBombermans(bomberMan.hashCode());

                    return bomberMan;
                }
            });

            thread.start();

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            return game;
        }
    }
}
