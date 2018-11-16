package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


class HelpFrame extends JFrame {

    private List<String>texts = new ArrayList<>();
    private List<BufferedImage> images = new ArrayList<>();
    private List<List<String>>labels = new ArrayList<>();

    HelpFrame(){

        init();

        setTitle("Help");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(400,500);
        setLocation(10,10);
        JPanel content = (JPanel) getContentPane();
        setResizable(false);

        JPanel scrollPanel = new JPanel();
                                            //for scrollPane
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.PAGE_AXIS));
        scrollPanel.setBackground(Color.cyan);

        for (int i = 0; i < images.size(); i++) {
            int finalI = i;

            JPanel paintPanel = new JPanel(){
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(images.get(finalI),0,0,100,100,null);
                }
            };
            paintPanel.setPreferredSize(new Dimension(150,100));
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(content.getWidth(),100));
            panel.setLayout(new BoxLayout(panel,BoxLayout.LINE_AXIS));
            panel.add(paintPanel);

            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BoxLayout(textPanel,BoxLayout.PAGE_AXIS));
            textPanel.setPreferredSize(new Dimension(250,100));

            for (int j = 0; j < labels.get(i).size(); j++) {
                JLabel label = new JLabel(labels.get(i).get(j));
                label.setPreferredSize(new Dimension(250,100));
                textPanel.add(label);
            }
            panel.add(textPanel);

            scrollPanel.add(panel);


        }

        JScrollPane scrollPane = new JScrollPane(scrollPanel);

        content.add(scrollPane, BorderLayout.CENTER);

        setVisible(true);

    }

    private void init() {


        texts.add(" components.mosters.Monster : ");
        texts.add(" level : 1 ");
        texts.add(" speed : 0.5 ");
        texts.add(" movement : random");
        texts.add(" It can't cross walls and rocks");

        labels.add(new ArrayList<>(texts));
        images.add(Images.monster1WD[0]);
        texts.clear();

        //....................

        texts.add(" components.mosters.Monster : ");
        texts.add(" level : 2 ");
        texts.add(" speed : 0.5 ");
        texts.add(" movement : knows where you are !");
        texts.add(" It can't cross walls and rocks");

        labels.add(new ArrayList<>(texts));
        images.add(Images.monster2WD[0]);
        texts.clear();

        //....................

        texts.add(" components.mosters.Monster : ");
        texts.add(" level : 3 ");
        texts.add(" speed : 1 ");
        texts.add(" movement : knows where you are !");
        texts.add(" It can't cross walls and rocks");

        labels.add(new ArrayList<>(texts));
        images.add(Images.monster3WD[0]);
        texts.clear();

        //....................

        texts.add(" components.mosters.Monster : ");
        texts.add(" level : 4 ");
        texts.add(" speed : 1 ");
        texts.add(" movement : knows where you are !");
        texts.add(" It can cross walls and rocks");

        labels.add(new ArrayList<>(texts));
        images.add(Images.monster4WD[0]);
        texts.clear();

        //....................

        texts.add(" Power : ");
        texts.add(" Increases your bomb limit ");

        labels.add(new ArrayList<>(texts));
        images.add(Images.bombIncreaser);
        texts.clear();

        //....................

        texts.add(" Power : ");
        texts.add(" Decreases your bomb limit ");

        labels.add(new ArrayList<>(texts));
        images.add(Images.bombDecrease);
        texts.clear();

        //....................

        texts.add(" Power : ");
        texts.add(" Increases your speed ");

        labels.add(new ArrayList<>(texts));
        images.add(Images.speedUp);
        texts.clear();

        //....................

        texts.add(" Power : ");
        texts.add(" Decreases your speed ");

        labels.add(new ArrayList<>(texts));
        images.add(Images.speedDown);
        texts.clear();

        //....................

        texts.add(" Power : ");
        texts.add(" Increases your bomb radius ");

        labels.add(new ArrayList<>(texts));
        images.add(Images.radiusUp);
        texts.clear();

        //....................

        texts.add(" Power : ");
        texts.add(" Decreases your bomb radius ");

        labels.add(new ArrayList<>(texts));
        images.add(Images.radiusDown);
        texts.clear();

        //....................

        texts.add(" Power : ");
        texts.add(" Increases your point ");

        labels.add(new ArrayList<>(texts));
        images.add(Images.pointUp);
        texts.clear();

        //....................

        texts.add(" Power : ");
        texts.add(" Decreases your point ");

        labels.add(new ArrayList<>(texts));
        images.add(Images.pointDown);
        texts.clear();

        //....................

        texts.add(" Power : ");
        texts.add(" You can control your bombs ");

        labels.add(new ArrayList<>(texts));
        images.add(Images.bombControl);
        texts.clear();

        //....................

        texts.add(" Power : (Ghost Mood)");
        texts.add(" You can cross the walls and rocks ");

        labels.add(new ArrayList<>(texts));
        images.add(Images.ghostPower);
        texts.clear();

        //....................







    }

}
