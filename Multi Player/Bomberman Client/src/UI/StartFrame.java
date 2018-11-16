package UI;

import client.Client;
import components.BomberMan;
import main.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class StartFrame extends JFrame {

    public StartFrame(Client client, List<Game> games) {


        setSize(500, 500);
        setLocation(300, 300);
        setTitle("Running Games");
        JPanel content = (JPanel) getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));

        for (int i = 0; i < games.size(); i++) {
            JPanel panel = new JPanel();
            panel.setLayout(new GridBagLayout());
            JButton button = new JButton(games.get(i).getName());
            int finalI = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    JFrame clientsFrame = new JFrame("Players");
                    clientsFrame.setLocation(300, 300);
                    clientsFrame.setSize(500, 500);
                    JPanel content = (JPanel) clientsFrame.getContentPane();
                    content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));


                    for (int j = 0; j < games.get(finalI).getBomberMans().size(); j++) {
                        BomberMan bomberMan = games.get(finalI).getBomberMans().get(j);
                        JPanel panel = new JPanel();
                        panel.setLayout(new GridBagLayout());
                        panel.add(new JLabel(bomberMan.getName() + "  Point : " + bomberMan.getPoint()));
                        content.add(panel);
                    }


                    JPanel panel1 = new JPanel();
                    panel1.setLayout(new BorderLayout());
                    JButton joinButton = new JButton("Join");
                    joinButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            StartFrame.this.dispose();
                            clientsFrame.dispose();
                            client.joinGame(games.get(finalI));
                        }
                    });
                    panel1.add(joinButton,BorderLayout.SOUTH);

                    content.add(panel1);

                    clientsFrame.setVisible(true);

                }
            });
            panel.add(button);
            content.add(panel);
        }

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.cyan);
        JButton createGameButton = new JButton("Request New main.Game");
        createGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new NewGameFrame(client);
            }
        });
        panel.add(createGameButton);
        content.add(panel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


    }

    static class NewGameFrame extends JFrame implements ActionListener {

        private Client client;

        private int width;
        private int height;
        private int monstersCount;
        private int res;

        private JPanel getContent;
        private JButton start;
        private JTextField textField;
        private JTextField textField1;
        private JTextField textField2;
        private JTextField textField3;
        private JComboBox<String> comboBox;

        NewGameFrame(Client client) {

            this.client = client;

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
                    client.requestNewGame(textField3.getText(), 1, width, height, res, monstersCount);
                }

            }
            if (e.getSource() == comboBox) {
                res = (10 + comboBox.getSelectedIndex() * 10);
            }

        }
    }
}



