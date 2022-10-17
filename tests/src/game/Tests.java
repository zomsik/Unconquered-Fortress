package game;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Entity.Base;
import com.game.Entity.Enemy.Enemy;
import com.game.Manager.EnemyManager;
import com.game.Manager.FileReader;
import com.game.Manager.ProfileManager;
import com.game.Manager.WorldManager;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(HeadlessLauncher.class)
public class Tests {

    @Test
    public void equalWorldsWithSameSeedTest()
    {
        WorldManager worldManager;

        Random random = new Random();
        int seed = random.nextInt();

        worldManager = new WorldManager();
        Image[][] a = worldManager.createWorld(null, seed, 46);
        worldManager = new WorldManager();
        Image[][] b = worldManager.createWorld(null, seed, 46);

        for (int i=0; i<10; i++) {
            for (int j = 0; j < 15; j++) {
                if (!Objects.equals(a[i][j].getName(), b[i][j].getName()))
                    fail("Tablice nie są równe.");
            }
        }
        assertTrue(true);
    }



    @Test
    public void BossWaveEveryTenWavesTest()
    {
        ProfileManager profileManager = new ProfileManager();
        Base base = new Base(profileManager.createEmptySave("normal",1,""), null);

        FileReader fileReader = new FileReader();
        JSONObject enemiesJSONObject = fileReader.downloadFileAsJSONObject("assets/enemies.json");

        EnemyManager enemyManager = new EnemyManager(base, 1, null, enemiesJSONObject);

        for (int i=0; i<10; i++) {
            boolean hasBoss = false;

            base.increaseWave(10);
            enemyManager.createRandomEnemyWave();
            ArrayList<Enemy> enemies = enemyManager.getLastCreatedWave();

            for (Enemy e: enemies)
            {
                if (Objects.equals(e.getName(), "boss"))
                {

                 hasBoss=true;
                 break;
                }
            }

            if (!hasBoss)
            {
                fail("Fala nie posiada bossa.");
            }

        }
        assertTrue(true);
    }


    @Test
    public void seedInputTest(){
        ProfileManager profileManager = new ProfileManager();
        Assertions.assertEquals(10, profileManager.stringToSeed("10"));
        Assertions.assertEquals(-10, profileManager.stringToSeed("-10"));
        Assertions.assertEquals(1954875433, profileManager.stringToSeed("Abs209"));
        Assertions.assertEquals(1507332, profileManager.stringToSeed("10-2"));
    }

    @Test
    public void deleteProfileTest() throws IOException {
        FileReaderTest fileReaderTest = new FileReaderTest();
        if(fileReaderTest.fileExists("../save/save01l.json")){
            File src = new File("../save/save01l.json");
            File target = new File("../save/save04l.json");

            Files.copy(src.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
            fileReaderTest.deleteSave(4);
            if(fileReaderTest.fileExists("../save/save04l.json")){
                fail("Plik nie został usunięty.");
            }
        }else{
            fail("Brak pliku testowego.");
        }

    }


    @Test
    public void thisAlwaysPasses()
    {
        assertTrue(true);
    }

}
