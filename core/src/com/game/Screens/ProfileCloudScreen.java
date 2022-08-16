package com.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.game.Main;
import com.game.Manager.ConnectionManager;
import com.game.Manager.FileReader;
import com.game.Manager.LanguageManager;
import org.json.JSONObject;

public class ProfileCloudScreen implements Screen {
    private Main game;
    private Stage stage;
    private Texture background;
    private FileReader fileReader;
    private ConnectionManager connectionManager;
    private LanguageManager languageManager;

    public ProfileCloudScreen(Main game){
        this.game = game;
        stage = new Stage();
        fileReader = new FileReader();
        fileReader.downloadSettings();
        if(fileReader.getLanguageValue() != null){
            languageManager = new LanguageManager(fileReader.getLanguageValue());
        }else{
            languageManager = new LanguageManager("English");
        }


        initProfileCloudUI();

        background = new Texture("background.png");
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        JSONObject loadSaves = new JSONObject();

        loadSaves.put("login", game.getLogin());

        JSONObject loadResponse = connectionManager.requestSend(loadSaves, "http://localhost:9000/api/downloadSaves");

        System.out.println(loadResponse);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    game.setScreen(new MenuScreen(game));
                    dispose();
                    return true;
                }

                if (keycode == Input.Keys.S) {
                    JSONObject saveData = new JSONObject();
                    saveData.put("login", game.getLogin());
                    saveData.put("profileNumber", 1);
                    saveData.put("seed", 123);
                    saveData.put("difficulty", "normal");
                    saveData.put("finishedMaps", 123);
                    saveData.put("wave", 10);
                    saveData.put("gold", 12233);
                    saveData.put("diamonds", 0);

                    JSONObject saveResponse = connectionManager.requestSend(saveData, "http://localhost:9000/api/uploadSave");
                    System.out.println(saveResponse);

                    return true;
                }


                return super.keyDown(event, keycode);
            }
        });


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, 0,0);
        game.batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void initProfileCloudUI() {
        connectionManager = new ConnectionManager();
    }
}
