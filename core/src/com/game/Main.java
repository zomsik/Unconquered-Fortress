package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.Manager.ConnectionManager;
import com.game.Manager.FileReader;
import com.game.Screens.MenuScreen;
import com.game.Server.SavesRepository;
import com.game.Server.UsersRepository;
import org.json.JSONObject;

public class Main extends Game {
	public SpriteBatch batch;
	private Music music, cleanSound, sellSound, buySound;
	private boolean isLogged;
	private String login;
	private ConnectionManager connectionManager;

	private UsersRepository usersRepository;
	private SavesRepository savesRepository;

	public Main(UsersRepository usersRepository, SavesRepository savesRepository) {
		this.usersRepository = usersRepository;
		this.savesRepository = savesRepository;
	}

	public UsersRepository getUsersRepository() {
		return usersRepository;
	}

	public void setUsersRepository(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	public SavesRepository getSavesRepository() {
		return savesRepository;
	}

	public void setSavesRepository(SavesRepository savesRepository) {
		this.savesRepository = savesRepository;
	}

	public void setIsLogged(boolean isLogged)
	{
		this.isLogged = isLogged;
	}
	public boolean getIsLogged()
	{
		return isLogged;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getLogin()
	{
		return login;
	}
	@Override
	public void create () {

		connectionManager = new ConnectionManager(this);
		new Thread(() -> connectionManager.requestSend(new JSONObject(), "api/ping")).start();

		batch = new SpriteBatch();
		FileReader fileReader = new FileReader();
		fileReader.downloadSettings();
		isLogged = false;
		if(fileReader.getResolutionValue() != null){
			switch (fileReader.getResolutionValue()) {
				case "1600 X 900 Fullscreen", "1280 X 720 Fullscreen", "1920 X 1080 Fullscreen" -> Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
				case "1920 X 1080 Windowed" -> Gdx.graphics.setWindowedMode(1920, 1080);
				case "1280 X 720 Windowed" -> Gdx.graphics.setWindowedMode(1280, 720);
				case "1600 X 900 Windowed" -> Gdx.graphics.setWindowedMode(1600, 900);
			}
		}
		music = Gdx.audio.newMusic(Gdx.files.internal("assets/sound/backgroundMusic.ogg"));
		cleanSound = Gdx.audio.newMusic(Gdx.files.internal("assets/sound/cleanSound.ogg"));
		sellSound = Gdx.audio.newMusic(Gdx.files.internal("assets/sound/sellSound.ogg"));
		buySound = Gdx.audio.newMusic(Gdx.files.internal("assets/sound/buySound.ogg"));
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
	public Music getCleanSound() {
		return cleanSound;
	}
	public Music getSellSound() {
		return sellSound;
	}
	public Music getBuySound() {
		return buySound;
	}
}