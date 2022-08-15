package com.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.game.Main;
import com.game.Manager.*;

import java.util.ArrayList;

public class CreditsScreen implements Screen {
    private Main game;
    private Stage stage;
    private Texture background;
    private BitmapFont font;
    private TextureAtlas buttons_settings, buttons_default, dialog_back;
    private Skin images, images_default, dialog;

    private Table table_credits;


    private ArrayList<String> languages;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private TextButton.TextButtonStyle textButtonStyle_bLeft, textButtonStyle_bRight, textButtonStyle_bBack, textButtonStyle_bSave;
    private TextField.TextFieldStyle textTitleFieldStyle, textFieldStyle;
    private Music backgroundMusic;
    private Slider volumeSlider;
    private Slider.SliderStyle sliderStyle;
    private Resolutions resolutionsClass;
    private ButtonStyleManager buttonStyleManager;
    private TextFieldStyleManager textFieldStyleManager;
    private FileReader fileReader;
    private LanguageManager languageManager;
    private Dialog backDialog;
    public CreditsScreen(Main game){
        this.game = game;
        resolutionsClass = new Resolutions();
        fileReader = new FileReader();
        fileReader.downloadSettings();
        if(fileReader.getLanguageValue() != null){languageManager = new LanguageManager(fileReader.getLanguageValue());}else{languageManager = new LanguageManager("English");}
        initSettingsUI();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
                dispose();
            }


        });

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    game.setScreen(new MenuScreen(game));
                    dispose();
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
    private void initSettingsUI(){
        background = new Texture("background.png");
        languages = new ArrayList<>();
        languages.add("English");
        languages.add("Polski");
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Silkscreen.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        stage = new Stage();
        parameter.size = 15;
        parameter.color = Color.WHITE;
        parameter.characters = "ąćęłóśżźabcdefghijklmnopqrstuvwxyzĄĆĘÓŁŚŻŹABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        font = new BitmapFont();
        font = generator.generateFont(parameter);

        buttons_settings = new TextureAtlas("assets/buttons/buttons_settings.pack");
        buttons_default = new TextureAtlas("assets/buttons/buttons_default.pack");
        dialog_back = new TextureAtlas("assets/dialog/skin_dialog.pack");
        images = new Skin(buttons_settings);
        images_default = new Skin(buttons_default);
        dialog = new Skin(dialog_back);

        backgroundMusic = game.getMusic();

    }
}
