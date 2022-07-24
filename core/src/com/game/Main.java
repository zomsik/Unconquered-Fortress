package com.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.Screens.MenuScreen;

import java.util.Arrays;

public class Main extends Game {
	public SpriteBatch batch;
	public Graphics.DisplayMode displayMode;
	@Override
	public void create () {
		batch = new SpriteBatch();
		displayMode = Gdx.graphics.getDisplayMode();
		setScreen(new MenuScreen(this));
		Graphics.DisplayMode[] displayModes = Gdx.graphics.getDisplayModes();
		Gdx.app.log("DisplayModeTest",
				"Display mode (using Gdx.graphics.getDisplayMode(Gdx.graphics.getMonitor()) ) : " + Arrays.toString(displayModes) + "\n" + displayModes[19]);

	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

}
