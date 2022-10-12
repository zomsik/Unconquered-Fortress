import com.game.Main;
import com.game.Manager.ProfileManager;
import com.game.Manager.WorldManager;
import com.game.Screens.GameScreen;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ExampleTests {
    private GameScreen gameScreen;


    @Before
    public void setUpScreen() {
        ProfileManager profileManager = new ProfileManager();
        Main main = new Main();
        gameScreen = new GameScreen(main,profileManager.createEmptySave("normal",1,"") ,true);
    }


    @Test
    public void testCreate() {

        WorldManager worldManager = new WorldManager();
        worldManager.createWorld(gameScreen,20,1);
    }




}
