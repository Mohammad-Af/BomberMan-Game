package UI;

import components.Board;
import components.BomberMan;
import main.Game;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class LoadFrame extends JFrame implements Loadable {

    private String path;
    private Game game = new Game(1);


    @Override
    public int loadLevel() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream(path + File.separator + "levelSetting.txt"));
        Game.level = scanner.nextInt();
        int remainTime = scanner.nextInt();
        if (scanner.nextInt() == 1)
            game.getGameFrame().setVibration(true);
        return remainTime;
    }

    @Override
    public BomberMan loadBomberman() throws FileNotFoundException {
        BomberMan bomberMan = new BomberMan(game);
        bomberMan.load(path);
        return bomberMan;
    }

    @Override
    public Board loadBoard() throws IOException {
        return new Board(path);
    }


    LoadFrame() throws IOException {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        fileChooser.setDialogTitle("select folder");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            path = String.valueOf(fileChooser.getSelectedFile());
            start();
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "No folder selected ! ");
        }

    }


    public void start() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Board board = loadBoard();
                    game.setBoard(board);
                    BomberMan bomberMan = loadBomberman();
                    board.setBomberManPlace(bomberMan.getRealLocation());
                    game.setBomberMan(bomberMan);

                    GameFrame gameFrame = new GameFrame(game);
                    gameFrame.setRemainTime(loadLevel());

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        thread.start();

    }
}
