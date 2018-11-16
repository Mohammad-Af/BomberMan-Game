package UI;

import main.Game;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

public class SaveFrame extends JFrame implements Saveable {

    private String path;
    private Game game;

    SaveFrame(Game game) {
        this.game=game;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setDialogTitle("select folder");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            path= String.valueOf(fileChooser.getSelectedFile());
            save();
        } else {
            JOptionPane.showMessageDialog(new JFrame(),"No folder selected ! ");
        }

    }

    public void save(){

            try {
                saveBoard();
                saveBomberman();
                saveLevel();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            setVisible(false);
            dispose();

            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(500, 110);
            frame.setResizable(false);
            frame.setLocation(500, 500);
            frame.setLayout(new FlowLayout());
            frame.getContentPane().add(new JLabel("main.Game saved"));
            frame.setVisible(true);
        }


    @Override
    public void saveLevel() throws FileNotFoundException {
        PrintStream printStream=new PrintStream(new File(path+File.separator+"levelSetting.txt"));
        printStream.print(Game.level+" "+game.getGameFrame().getRemainTime()+" ");
        if(game.getGameFrame().hasVibration()){
            printStream.print(1+" ");
        }else
            printStream.print(0+" ");
    }

    @Override
    public void saveBomberman() throws IOException {
        game.getBomberMan().save(path);
    }

    @Override
    public void saveBoard() throws IOException {
        game.getBoard().save(path);
    }

}
