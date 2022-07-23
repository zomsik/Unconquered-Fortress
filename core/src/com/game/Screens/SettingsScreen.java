package com.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.game.Main;

import java.util.ArrayList;
import java.util.Objects;

public class SettingsScreen implements Screen {
    private Main game;
    private Stage stage;
    private Texture background;
    private BitmapFont font;
    private TextureAtlas buttons;
    private Skin images;
    private TextButton bLeft, bRight;
    private Table table_resolution;
    private TextField resolution_field;
    private ArrayList<String> resolutions;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    public SettingsScreen(Main game){
        this.game = game;
        background = new Texture("background.png");

    }

    @Override
    public void show() {
        resolutions = new ArrayList<String>();
        resolutions.add("1920x1080");
        resolutions.add("1600x900");
        resolutions.add("1280x720");
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 15;
        parameter.color = Color.WHITE;
        font = new BitmapFont();
        font = generator.generateFont(parameter);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        buttons = new TextureAtlas("assets/buttons/buttons_settings.pack");
        images = new Skin(buttons);
        table_resolution = new Table(images);
        table_resolution.setBounds(200,800, 208,32);
        TextButton.TextButtonStyle textButtonStyle_bLeft = new TextButton.TextButtonStyle();
        TextButton.TextButtonStyle textButtonStyle_bRight = new TextButton.TextButtonStyle();
        textButtonStyle_bLeft.up = images.getDrawable("resolutionButtonLeft");
        textButtonStyle_bLeft.down = images.getDrawable("resolutionButtonLeft"); //TODO add pressed buttons
        textButtonStyle_bLeft.pressedOffsetX = 1;
        textButtonStyle_bLeft.pressedOffsetY = -1;
        textButtonStyle_bLeft.font = font;
        textButtonStyle_bRight.up = images.getDrawable("resolutionButtonRight");
        textButtonStyle_bRight.down = images.getDrawable("resolutionButtonRight"); //TODO add pressed buttons
        textButtonStyle_bRight.pressedOffsetX = 1;
        textButtonStyle_bRight.pressedOffsetY = -1;
        textButtonStyle_bRight.font = font;
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background = images.getDrawable("resolutionTextBar");
        textFieldStyle.font = font;
        textFieldStyle.fontColor = Color.WHITE;
        bLeft = new TextButton("", textButtonStyle_bLeft);

        bRight = new TextButton("", textButtonStyle_bRight);
        bRight.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(resolution_field.getText().equals(resolutions.get(0)))
                {
                    resolution_field.setText(resolutions.get(1));
                } else if (resolution_field.getText().equals(resolutions.get(1))) {
                    resolution_field.setText(resolutions.get(2));
                }else if (Objects.equals(resolution_field.getText(), resolutions.get(2))) {
                    resolution_field.setText(resolutions.get(0));
                }


            }
        });
        bLeft.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(resolution_field.getText().equals(resolutions.get(0)))
                {
                    resolution_field.setText(resolutions.get(2));
                } else if (resolution_field.getText().equals(resolutions.get(2))) {
                    resolution_field.setText(resolutions.get(1));
                }else if (Objects.equals(resolution_field.getText(), resolutions.get(1))) {
                    resolution_field.setText(resolutions.get(0));
                }
            }
        });
        resolution_field = new TextField(resolutions.get(0),textFieldStyle);
        resolution_field.setAlignment(Align.center);
        table_resolution.add(bLeft);
        table_resolution.add(resolution_field);
        table_resolution.add(bRight);
        //table_resolution.debug();
        stage.addActor(table_resolution);
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

}
