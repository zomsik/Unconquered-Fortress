package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.game.Main;
import com.game.Manager.ProfileManager;
import com.game.Manager.WorldManager;
import com.game.Screens.GameScreen;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

//@RunWith(HeadlessLauncher.class)
public class Tests {

    @Test
    public void creationAppTest()
    {

        WorldManager worldManager = new WorldManager();
        ProfileManager profileManager = new ProfileManager();
        Main game = new Main();

        worldManager.createWorld(null,22,1);

        assertTrue(true);
    }


    @Test
    public void thisAlwaysPasses()
    {
        assertTrue(true);
    }

}
