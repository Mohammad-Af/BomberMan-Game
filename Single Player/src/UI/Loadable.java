package UI;

import components.Board;
import components.BomberMan;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Loadable {

    int loadLevel() throws FileNotFoundException;
    BomberMan loadBomberman() throws IOException;
    Board loadBoard() throws IOException;
}
