package UI;

import components.Board;
import components.BomberMan;
import main.Game;

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
import java.util.ArrayList;
import java.util.List;

public class StartFrame extends JFrame implements ActionListener {

    private JButton newGameButton;
    private JButton loadGameButton;
    private BufferedImage bombermanImage = ImageIO.read(new File("bomberman"+File.separator+"start.png"));
    private ImageObserver observer = this;

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
                g.drawImage(bombermanImage, 90, 20, 300, 300, observer);
            }
        };
        panel.setPreferredSize(new Dimension(500, 300));
        panel.setBackground(Color.white);

        newGameButton = new JButton("New game");
        newGameButton.setPreferredSize(new Dimension(100, 30));
        jPanel.add(newGameButton);
        loadGameButton = new JButton("Load game");
        loadGameButton.setPreferredSize(new Dimension(100, 30));
        jPanel.add(loadGameButton);

        jPanel.setBackground(Color.red);
        // panel.setBackground(Color.blue);

        newGameButton.addActionListener(this);
        loadGameButton.addActionListener(this);

        content.add(jPanel);
        content.add(panel);


        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            dispose();
            new NewGameFrame();

        }
        if (e.getSource() == loadGameButton) {
            dispose();
            try {
                new LoadFrame();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


    public static class NewGameFrame extends JFrame implements ActionListener {

        private JPanel getContent;
        private JButton start;
        private JTextField textField;
        private JTextField textField1;
        private JTextField textField2;
        private JCheckBox vibration;
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


            panel6.setLayout(new GridBagLayout());
            panel6.setBackground(Color.GRAY);
            JLabel label4 = new JLabel("Vibration ");
            label4.setFont(font);
            panel6.add(label4);
            vibration = new JCheckBox();
            vibration.setBackground(Color.GRAY);
            panel6.add(vibration);


            getContent.add(panel1);
            getContent.add(panel2);
            getContent.add(panel3);
            getContent.add(panel4);
            getContent.add(panel6);
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
                    List<Object> bomberManPowerChangers = new ArrayList<>();
                    bomberManPowerChangers.add(40);                               //speed
                    bomberManPowerChangers.add(1);                               //bombLimit
                    bomberManPowerChangers.add(false);                           //can control bombs
                    bomberManPowerChangers.add(1);                               //bomb radius
                    bomberManPowerChangers.add(false);                         // goast power
                    boolean hasVibration = vibration.getSelectedObjects() != null;
                    init(1, width, height, res, monstersCount, bomberManPowerChangers, hasVibration);
                }

            }
            if (e.getSource() == comboBox) {
                res = (10 + comboBox.getSelectedIndex() * 10);
            }

        }

        public static void init(int level, int width, int height, int res, int monstersCount, List<Object> bombermanPowerChangers, boolean hasVibration) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Game game = new Game(level);
                        Board board = new Board(width, height, res, monstersCount);
                        game.setBoard(board);
                        BomberMan bomberMan = new BomberMan(game);
                        bomberMan.setSpeed((Integer) bombermanPowerChangers.get(0));
                        bomberMan.setBombLimit((Integer) bombermanPowerChangers.get(1));
                        bomberMan.setCanControlBombs((Boolean) bombermanPowerChangers.get(2));
                        bomberMan.setBombRadius((Integer) bombermanPowerChangers.get(3));
                        bomberMan.setGhostMood((Boolean) bombermanPowerChangers.get(4));
                        game.setBomberMan(bomberMan);
                        GameFrame gameFrame = new GameFrame(game);
                        gameFrame.setVibration(hasVibration);

                    } catch ( IOException | IllegalAccessException | InstantiationException | InvocationTargetException e1) {
                        e1.printStackTrace();
                    }
                }
            });
//            for(Thread t :Thread.getAllStackTraces().keySet()){
//                if(t.getState()==Thread.State.RUNNABLE){
//                    t.interrupt();
//                }
//            }
            thread.start();
        }
    }
}
