package UI;

import client.Client;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ChatFrame extends JFrame implements ActionListener {

    private Client client;
    private JTextField textField = new JTextField();
    private JScrollPane scrollPane;
    private JPanel scrollPanel;
    private List<JPanel> messageContainerPanels = new ArrayList<>();
    private int messages = 0;

    public ChatFrame(Client client) throws HeadlessException {

        this.client = client;

        setSize(500, 500);
        setLocation(1000, 300);
        JPanel content = (JPanel) getContentPane();
        setResizable(false);
        Font font = new Font("SansSerif", Font.BOLD, 20);
        setTitle(client.getClientName());



        scrollPanel = new JPanel();                                     //for scrollPane
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.PAGE_AXIS));
        scrollPanel.setBackground(Color.cyan);

        for (int i = 0; i < 20; i++) {
            JPanel messageContainerPanel = new JPanel();
            messageContainerPanel.setBackground(Color.cyan);
            messageContainerPanel.setLayout(new BorderLayout());
            messageContainerPanels.add(messageContainerPanel);
            scrollPanel.add(messageContainerPanel);
        }

        scrollPane = new JScrollPane(scrollPanel);

        content.add(scrollPane, BorderLayout.CENTER);

        JPanel jPanel = new JPanel();                                   //contains textField & button
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.PAGE_AXIS));

        textField.setPreferredSize(new Dimension(500, 50));
        textField.setFont(font);
        jPanel.add(textField);

        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.add(sendButton);
        jPanel.add(buttonPanel);

        content.add(jPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(client.isConnected()) {
            client.getSender().sendMessage(textField.getText());
            textField.setText(null);
        }

    }

    public void recieveMessage(String m) {

        JPanel messageContainerPanel;
        if (messages >= messageContainerPanels.size()) {
            messageContainerPanel = new JPanel();
            messageContainerPanel.setBackground(Color.cyan);
            messageContainerPanel.setLayout(new BorderLayout());
            messageContainerPanels.add(messageContainerPanel);
        }
        messageContainerPanel=messageContainerPanels.get(messages);


        JPanel messagePanel = new JPanel();
        JLabel message = new JLabel(m);
        messagePanel.add(message);
        messagePanel.setBackground(Color.white);
        messagePanel.setBorder(new LineBorder(Color.cyan, 5));

        if (m.startsWith(client.getClientName()))
            messageContainerPanel.add(messagePanel, BorderLayout.EAST);
        else
            messageContainerPanel.add(messagePanel, BorderLayout.WEST);

        scrollPanel.add(messageContainerPanel);

        scrollPane.validate();
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());

        messages++;
    }
}
