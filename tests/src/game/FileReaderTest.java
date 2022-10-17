package game;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReaderTest {
    public FileReaderTest(){

    }

    public void deleteSave(int saveNumber) {

        //Wymagane podanie innej ścieżki
        Path path = FileSystems.getDefault().getPath("../save/save0"+saveNumber+"l.json");
        try {
            Files.deleteIfExists(path);
        } catch (IOException x) {
            System.err.println(x);
        }

    }

    public boolean fileExists(String savePath){
        File file = new File(savePath);
        return file.exists();
    }
}
