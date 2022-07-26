package com.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.game.Main;
import com.game.Manager.Resolutions;
import jdk.internal.org.jline.utils.Display;

import java.util.ArrayList;
import java.util.Objects;

public class SettingsScreen implements Screen {
    private Main game;
    private Stage stage;
    private Texture background;
    private BitmapFont font;
    private TextureAtlas buttons_settings, buttons_default;
    private Skin images, images_default;
    private TextButton bLeft, bRight, bBack, bSave;
    private Table table_resolution, table_default;
    private TextField resolution_field, resolution_field_text;
    private ArrayList<String> resolutions;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private TextButton.TextButtonStyle textButtonStyle_bLeft, textButtonStyle_bRight, textButtonStyle_bBack, textButtonStyle_bSave;
    private TextField.TextFieldStyle textFieldStyle;

    private Resolutions resolutionsClass;

    public SettingsScreen(Main game){
        this.game = game;
        resolutionsClass = new Resolutions();
        initSettingsUI();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        parameter.size = 15;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);
        table_resolution.setBounds(800,800, 500,32);
        table_default.setBounds(700, 100, 500,200);
        textButtonStyle_bLeft.up = images.getDrawable("resolutionButtonLeft_up");
        textButtonStyle_bLeft.down = images.getDrawable("resolutionButtonLeft_down");
        textButtonStyle_bLeft.pressedOffsetX = 1;
        textButtonStyle_bLeft.pressedOffsetY = -1;
        textButtonStyle_bLeft.font = font;
        textButtonStyle_bRight.up = images.getDrawable("resolutionButtonRight_up");
        textButtonStyle_bRight.down = images.getDrawable("resolutionButtonRight_down");
        textButtonStyle_bRight.pressedOffsetX = 1;
        textButtonStyle_bRight.pressedOffsetY = -1;
        textButtonStyle_bRight.font = font;
        textButtonStyle_bBack.up = images_default.getDrawable("defaultButton");
        textButtonStyle_bBack.down = images_default.getDrawable("defaultButton"); //TODO add pressed buttons
        textButtonStyle_bBack.pressedOffsetX = 1;
        textButtonStyle_bBack.pressedOffsetY = -1;
        textButtonStyle_bBack.font = font;
        textButtonStyle_bSave.up = images_default.getDrawable("defaultButton");
        textButtonStyle_bSave.down = images_default.getDrawable("defaultButton"); //TODO add pressed buttons
        textButtonStyle_bSave.pressedOffsetX = 1;
        textButtonStyle_bSave.pressedOffsetY = -1;
        textButtonStyle_bSave.font = font;
        textFieldStyle.background = images.getDrawable("resolutionTextBar");
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.WHITE;
        bLeft = new TextButton("", textButtonStyle_bLeft);
        bRight = new TextButton("", textButtonStyle_bRight);
        bRight.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(resolutions.size() == 3){
                    if(resolution_field.getText().equals(resolutions.get(0)))
                    {
                        resolution_field.setText(resolutions.get(1));
                    } else if (resolution_field.getText().equals(resolutions.get(1))) {
                        resolution_field.setText(resolutions.get(2));
                    }else if (Objects.equals(resolution_field.getText(), resolutions.get(2))) {
                        resolution_field.setText(resolutions.get(0));
                    }
                }else if(resolutions.size() == 2){
                    if(resolution_field.getText().equals(resolutions.get(0)))
                    {
                        resolution_field.setText(resolutions.get(1));
                    } else if (resolution_field.getText().equals(resolutions.get(1))) {
                        resolution_field.setText(resolutions.get(0));
                    }
                }
                else if(resolutions.size() == 1){
                    if(resolution_field.getText().equals(resolutions.get(0))) {
                        resolution_field.setText(resolutions.get(0));
                    }
                }

            }
        });
        bLeft.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(resolutions.size() == 3){
                    if(resolution_field.getText().equals(resolutions.get(0)))
                    {
                        resolution_field.setText(resolutions.get(2));
                    } else if (resolution_field.getText().equals(resolutions.get(2))) {
                        resolution_field.setText(resolutions.get(1));
                    }else if (Objects.equals(resolution_field.getText(), resolutions.get(1))) {
                        resolution_field.setText(resolutions.get(0));
                    }
                }else if(resolutions.size() == 2){
                    if(resolution_field.getText().equals(resolutions.get(0)))
                    {
                        resolution_field.setText(resolutions.get(1));
                    }else if (Objects.equals(resolution_field.getText(), resolutions.get(1))) {
                        resolution_field.setText(resolutions.get(0));
                    }
                }else if(resolutions.size() == 1){
                    if(resolution_field.getText().equals(resolutions.get(0)))
                    {
                        resolution_field.setText(resolutions.get(0));
                    }
                }

            }
        });
        bBack = new TextButton("Back", textButtonStyle_bBack);
        bSave = new TextButton("Save", textButtonStyle_bSave);
        bBack.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new MenuScreen(game));
                dispose();
            }
        });
        System.out.println(resolutions);
        bSave.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //Problem z ustawieniem właściwego displaymode-a, tablica mode-ów jest zależna od monitora
                //1920x1080 tylko w fullscreenie, reszta rozdzielczoci windowed
                //Graphics.DisplayMode[] displayModes = Gdx.graphics.getDisplayModes();

                if(resolution_field.getText().equals("1920 X 1080 Fullscreen") || resolution_field.getText().equals("1280 X 720 Fullscreen") || resolution_field.getText().equals("1600 X 900 Fullscreen"))
                {
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                } else if (resolution_field.getText().equals("1920 X 1080 Windowed")) {
                    Gdx.graphics.setWindowedMode(1920,1080);
                }else if (resolution_field.getText().equals("1280 X 720 Windowed")) {
                    Gdx.graphics.setWindowedMode(1280,720);
                }else if (resolution_field.getText().equals("1600 X 900 Windowed")) {
                    Gdx.graphics.setWindowedMode(1600,900);
                }
            }
        });
        resolution_field = new TextField(resolutions.get(0),textFieldStyle);
        resolution_field_text = new TextField("Resolution", textFieldStyle);
        resolution_field_text.setAlignment(Align.center);
        resolution_field.setAlignment(Align.center);
        table_resolution.add(resolution_field_text).padRight(100);
        table_resolution.add(bLeft);
        table_resolution.add(resolution_field).width(320);
        table_resolution.add(bRight);
        table_default.add(bBack);
        table_default.add(bSave);
        //table_resolution.debug();
        table_default.debug();
        stage.addActor(table_resolution);
        stage.addActor(table_default);
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
        stage.getViewport().update(width, height);
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
    public void initSettingsUI(){
        background = new Texture("background.png");
        resolutions = new ArrayList<>();
        resolutions = resolutionsClass.getResolutionsArrayList();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        stage = new Stage();
        font = new BitmapFont();
        buttons_settings = new TextureAtlas("assets/buttons/buttons_settings.pack");
        buttons_default = new TextureAtlas("assets/buttons/buttons_default.pack");
        images = new Skin(buttons_settings);
        images_default = new Skin(buttons_default);
        table_resolution = new Table(images);
        table_default = new Table(images_default);
        textButtonStyle_bLeft = new TextButton.TextButtonStyle();
        textButtonStyle_bRight = new TextButton.TextButtonStyle();
        textButtonStyle_bBack = new TextButton.TextButtonStyle();
        textButtonStyle_bSave = new TextButton.TextButtonStyle();
        textFieldStyle = new TextField.TextFieldStyle();
    }
}
