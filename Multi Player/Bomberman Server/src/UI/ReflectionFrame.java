package UI;

import main.Game;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class ReflectionFrame {


    ReflectionFrame(Game game) throws IOException, ClassNotFoundException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select your .class file");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".class files", "class");
        fileChooser.setFileFilter(filter);

        String className ="";
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File source = fileChooser.getSelectedFile();
            className = source.getName();
            String fs = File.separator;
            File dest = new File("out"+fs+"production"+fs+"SwingPainting"+fs+className);
            Files.copy(source.toPath(), dest.toPath());
        }


        Class NewMonster = Class.forName(className.replace(".class",""));
        game.getBoard().addMonster(NewMonster);



    }

}
