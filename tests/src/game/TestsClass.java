package game;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.Main;
import com.game.Manager.ProfileManager;
import com.game.Manager.WorldManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;
import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

@RunWith(HeadlessLauncher.class)
public class TestsClass {

    @Test
    public void equalWordsWithSameSeedTest()
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
    public void thisAlwaysPasses()
    {
        assertTrue(true);
    }

}
