package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.Manager.FileReader;
import com.game.Screens.MenuScreen;


public class Main extends Game {
	public SpriteBatch batch;
	private FileReader fileReader;
	private Music music;
	public boolean isLogged;

	public void setIsLogged(boolean newValue)
	{
		isLogged = newValue;
	}
	public boolean getIsLogged()
	{
		return isLogged;
	}


	@Override
	public void create () {
		batch = new SpriteBatch();
		fileReader = new FileReader();
		fileReader.downloadSettings();
		isLogged = false;
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
		music = Gdx.audio.newMusic(Gdx.files.internal("assets/sound/backgroundMusic.ogg"));
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

	public Music getMusic() {
		return music;
	}
}
