package UI;

import client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class InitClientFrame extends JFrame implements ActionListener {


    private JTextField nameField;
    private JTextField ipField;
    private JTextField portField;
    private JCheckBox playerBox;
    private InitFrame initFrame;

    public InitClientFrame(){
        initFrame =new InitFrame();

        setSize(500, 500);
        setLocation(300, 300);
        JPanel content = (JPanel) getContentPane();
        content.setLayout(new BoxLayout(content,BoxLayout.PAGE_AXIS));


        portField = new JTextField();
        portField.setPreferredSize(new Dimension(50, 30));
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(new JLabel("Enter Port : "));
        panel.add(portField);
        content.add(panel);

        ipField = new JTextField();
        ipField.setPreferredSize(new Dimension(100, 30));
        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        panel3.add(new JLabel("Enter IP : "));
        panel3.add(ipField);
        content.add(panel3);

        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(100,30));
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.add(new JLabel("Enter Your Name : "));
        panel1.add(nameField);
        content.add(panel1);

        playerBox = new JCheckBox();
        JPanel panel2 =new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel2.add(new JLabel("Player mood : "));
        panel2.add(playerBox);
        content.add(panel2);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(this);
        JPanel panel4=new JPanel();
        panel4.setLayout(new GridBagLayout());
        panel4.add(startButton);
        content.add(panel4);






        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            initFrame.dispose();
            dispose();
            boolean player = playerBox.getSelectedObjects()!=null;
            new Client(nameField.getText(),player,ipField.getText(),Integer.parseInt(portField.getText()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


}
