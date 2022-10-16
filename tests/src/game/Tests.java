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
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

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
                    fail();
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
                fail();
            }

        }
        assertTrue(true);
    }



    @Test
    public void thisAlwaysPasses()
    {
        assertTrue(true);
    }

}
