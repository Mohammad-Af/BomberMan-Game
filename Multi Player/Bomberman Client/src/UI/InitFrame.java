package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class InitFrame extends JFrame implements ActionListener {


    private List<String> avilableIPs = new ArrayList<>();
    private String IP = "";
    private JTextField startIPField1;
    private JTextField startIPField2;
    private JTextField startIPField3;
    private JTextField startIPField4;
    private JTextField endIPField1;
    private JTextField endIPField2;
    private JTextField endIPField3;
    private JTextField endIPField4;
    private JTextField portField;

    InitFrame() {
        setSize(500, 500);
        setLocation(300, 300);
        JPanel content = (JPanel) getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));


        portField = new JTextField();
        portField.setPreferredSize(new Dimension(50, 30));
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(new JLabel("Enter Port : "));
        panel.add(portField);
        content.add(panel);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());
        jPanel.add(new JLabel("Enter IP range"));
        content.add(jPanel);


        startIPField1 = new JTextField();
        startIPField1.setPreferredSize(new Dimension(40, 30));
        startIPField2 = new JTextField();
        startIPField2.setPreferredSize(new Dimension(40, 30));
        startIPField3 = new JTextField();
        startIPField3.setPreferredSize(new Dimension(40, 30));
        startIPField4 = new JTextField();
        startIPField4.setPreferredSize(new Dimension(40, 30));
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.add(new JLabel("From : "));
        panel1.add(startIPField1);
        panel1.add(new JLabel(" - "));
        panel1.add(startIPField2);
        panel1.add(new JLabel(" - "));
        panel1.add(startIPField3);
        panel1.add(new JLabel(" - "));
        panel1.add(startIPField4);

        content.add(panel1);

        endIPField1 = new JTextField();
        endIPField1.setPreferredSize(new Dimension(40, 30));
        endIPField2 = new JTextField();
        endIPField2.setPreferredSize(new Dimension(40, 30));
        endIPField3 = new JTextField();
        endIPField3.setPreferredSize(new Dimension(40, 30));
        endIPField4 = new JTextField();
        endIPField4.setPreferredSize(new Dimension(40, 30));
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel2.add(new JLabel("To : "));
        panel2.add(endIPField1);
        panel2.add(new JLabel(" - "));
        panel2.add(endIPField2);
        panel2.add(new JLabel(" - "));
        panel2.add(endIPField3);
        panel2.add(new JLabel(" - "));
        panel2.add(endIPField4);

        content.add(panel2);


        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        panel3.add(searchButton);
        content.add(panel3);


        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int s1 = Integer.parseInt(startIPField1.getText());
        int s2 = Integer.parseInt(startIPField2.getText());
        int s3 = Integer.parseInt(startIPField3.getText());
        int s4 = Integer.parseInt(startIPField4.getText());
        int e1 = Integer.parseInt(endIPField1.getText());
        int e2 = Integer.parseInt(endIPField2.getText());
        int e3 = Integer.parseInt(endIPField3.getText());
        int e4 = Integer.parseInt(endIPField4.getText());


        while (s1!=e1 || s2 != e2 || s3!=e3 || s4!=e4) {
            s4++;
            if(s4==256){
                s4=0;
                s3++;
                if(s3==256){
                    s3=0;
                    s2++;
                    if(s2==256){
                        s2=0;
                        s1++;
                    }
                }
            }
            IP=s1+"."+s2+"."+s3+"."+s4;
            check();
        }
        JFrame frame = new JFrame("Available IP ");
        frame.setSize(100,500);
        frame.setLocation(800,300);
        frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
        JPanel content = (JPanel) frame.getContentPane();

        JPanel scrollPanel = new JPanel();                                     //for scrollPane
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.PAGE_AXIS));
        scrollPanel.setBackground(Color.cyan);

        for (String ip : avilableIPs){
            scrollPanel.add(new JLabel(ip));
        }

        JScrollPane scrollPane = new JScrollPane(scrollPanel);

        content.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);

    }

    private void check() {
        try {

            Socket mySocket = new Socket();
            mySocket.connect(new InetSocketAddress(IP, Integer.parseInt(portField.getText())), 10);
          //  System.out.println("available  : " + IP);
            PrintWriter printWriter = new PrintWriter(mySocket.getOutputStream(), true);
            printWriter.println("CHECKING");
            mySocket.close();
            avilableIPs.add(IP);
        } catch (IOException exp) {
          //  System.out.println("not available  : " + IP);
        }


    }
}
