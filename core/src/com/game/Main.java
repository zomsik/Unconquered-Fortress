package com.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.Manager.FileReader;
import com.game.Manager.LanguageManager;
import com.game.Screens.MenuScreen;

import java.awt.*;
import java.util.Arrays;


public class Main extends Game {
	public SpriteBatch batch;
	private FileReader fileReader;
	@Override
	public void create () {
		batch = new SpriteBatch();
		fileReader = new FileReader();
		fileReader.downloadSettings();
		if(fileReader.getResolutionValue() != null){
			switch(fileReader.getResolutionValue()){
				case "1600 X 900 Fullscreen":
				case "1280 X 720 Fullscreen":
				case "1920 X 1080 Fullscreen":{
					Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
				}break;
				case "1920 X 1080 Windowed":{
					Gdx.graphics.setWindowedMode(1920,1080);
				}break;
				case "1280 X 720 Windowed":{
					Gdx.graphics.setWindowedMode(1280,720);
				}break;
				case "1600 X 900 Windowed":{
					Gdx.graphics.setWindowedMode(1600,900);
				}break;
			}
		}
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
