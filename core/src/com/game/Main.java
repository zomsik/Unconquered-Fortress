package com.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.game.Screens.MenuScreen;

import java.util.Arrays;

public class Main extends Game {
	public SpriteBatch batch;

	public Graphics.DisplayMode getDisplayMode() {
		return displayMode;
	}

	public Graphics.DisplayMode displayMode;
	@Override
	public void create () {
		batch = new SpriteBatch();
		 displayMode = Gdx.graphics.getDisplayMode();
		setScreen(new MenuScreen(this));
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
