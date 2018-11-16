package UI;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Saveable {

    public void saveLevel() throws FileNotFoundException;
    public void saveBomberman() throws IOException;
    public void saveBoard() throws IOException;


}
