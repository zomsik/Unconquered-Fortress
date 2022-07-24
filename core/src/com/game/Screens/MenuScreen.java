package com.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.game.Main;

public class MenuScreen implements Screen  {
    private Main game;
    private Texture background;
    public TextButton bExit;
    public TextButton bPlay;
    public TextButton bSettings;
    public BitmapFont font;
    public TextureAtlas buttons;
    public Skin images;
    public Stage stage;
    public Table table_bExit, table_bPlay, table_bSettings;
    private SettingsScreen settingsScreen;
    public MenuScreen(Main game){
        this.game = game;
        background = new Texture("background_menu.png");
        settingsScreen = new SettingsScreen(game);
    }
    @Override
    public void show() {
        font = new BitmapFont();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        buttons = new TextureAtlas("assets/buttons/buttons_menu.pack");
        images = new Skin(buttons);
        table_bExit = new Table(images);
        table_bPlay = new Table(images);
        table_bSettings = new Table(images);
        table_bExit.setBounds(290,280,170, 90);
        table_bPlay.setBounds(280,530,190,90);
        table_bSettings.setBounds(280,420,190,90);
        TextButton.TextButtonStyle textButtonStyle_bExit = new TextButton.TextButtonStyle();
        textButtonStyle_bExit.up = images.getDrawable("bExit_up");
        textButtonStyle_bExit.down = images.getDrawable("bExit_down"); //TODO add pressed buttons
        textButtonStyle_bExit.pressedOffsetX = 1;
        textButtonStyle_bExit.pressedOffsetY = -1;
        textButtonStyle_bExit.font = font;
        TextButton.TextButtonStyle textButtonStyle_bPlay = new TextButton.TextButtonStyle();
        textButtonStyle_bPlay.up = images.getDrawable("bPlay_up");
        textButtonStyle_bPlay.down = images.getDrawable("bPlay_down"); //TODO add pressed buttons
        textButtonStyle_bPlay.pressedOffsetX = 1;
        textButtonStyle_bPlay.pressedOffsetY = -1;
        textButtonStyle_bPlay.font = font;
        TextButton.TextButtonStyle textButtonStyle_bSettings = new TextButton.TextButtonStyle();
        textButtonStyle_bSettings.up = images.getDrawable("bSettings_up");
        textButtonStyle_bSettings.down = images.getDrawable("bSettings_down"); //TODO add pressed buttons
        textButtonStyle_bSettings.pressedOffsetX = 1;
        textButtonStyle_bSettings.pressedOffsetY = -1;
        textButtonStyle_bSettings.font = font;
        bExit = new TextButton("", textButtonStyle_bExit);
        bExit.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y){
               System.exit(0);
           }
        });
        bPlay = new TextButton("", textButtonStyle_bPlay);
        bSettings = new TextButton("", textButtonStyle_bSettings);
        bSettings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new SettingsScreen(game));
                dispose();
            }
        });
        table_bExit.add(bExit);
        table_bPlay.add(bPlay);
        table_bSettings.add(bSettings);
        table_bExit.debug();
        table_bPlay.debug();
        table_bSettings.debug();
        stage.addActor(table_bExit);
        stage.addActor(table_bSettings);
        stage.addActor(table_bPlay);
        /*
        initButton(font, stage, images, table_bPlay, 280, 530, 190, 90, "bPlay_up", "bPlay_down", bPlay);
        initButton(font, stage, images, table_bSettings, 280, 420, 190, 90, "bSettings_up", "bSettings_down", bSettings);
        initButton(font, stage, images, table_bExit, 290, 280, 170, 90, "bExit_up", "bExit_down", bExit);
        */
    }
    /*public void initButton(BitmapFont font, Stage stage, Skin images, Table table, int x, int y, int w, int h, String button_up, String button_down, TextButton button){
        table.setBounds(x,y,w,h);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = images.getDrawable(button_up);
        textButtonStyle.down = images.getDrawable(button_down);
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = font;
        button = new TextButton("", textButtonStyle);
        table.add(button);
        stage.addActor(table);

    }*/
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
}
