package com.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.game.Main;
import com.game.Manager.*;
import com.game.State.GameState;

import java.util.ArrayList;
import java.util.Objects;

public class SettingsScreen implements Screen {
    private Main game;
    private Stage stage;
    private Texture background, backgroundForDialog;
    private BitmapFont font;
    private TextureAtlas taButtonsSettings, taButtonsDefault;
    private Skin images, images_default;
    private TextButton bLeftResolution, bRightResolution, bBack, bSave, bLeftLanguage, bRightLanguage, bBackDialog;
    private Table table_resolution, table_default, table_volume, table_language;
    private TextField tResolutionField, tResolutionFieldText, tVolumeFieldText, tLanguageFieldText, tLanguageField;
    private ArrayList<String> resolutions;
    private ArrayList<String> languages;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private TextButton.TextButtonStyle textButtonStyle_bLeft, textButtonStyle_bRight, textButtonStyle_bBack, textButtonStyle_bSave;
    private TextField.TextFieldStyle textFieldStyle;
    private Music backgroundMusic;
    private Slider volumeSlider;
    private Slider.SliderStyle sliderStyle;
    private Resolutions resolutionsClass;
    private ButtonStyleManager buttonStyleManager;
    private TextFieldStyleManager textFieldStyleManager;
    private FileReader fileReader;
    private LanguageManager languageManager;
    private Dialog backDialog;

    private boolean isDialog = false;
    public SettingsScreen (Main game){
        this.game = game;
        resolutionsClass = new Resolutions();
        fileReader = new FileReader();
        fileReader.downloadSettings();
        if(fileReader.getLanguageValue() != null){languageManager = new LanguageManager(fileReader.getLanguageValue());}else{languageManager = new LanguageManager("English");}
        initSettingsUI();
        buttonStyleManager = new ButtonStyleManager();
        textFieldStyleManager = new TextFieldStyleManager();

        buttonStyleManager.setTextButtonStyle(textButtonStyle_bLeft, images, font, "resolutionButtonLeft_up", "resolutionButtonLeft_down");
        bLeftResolution = new TextButton("", buttonStyleManager.returnTextButtonStyle(textButtonStyle_bLeft));
        buttonStyleManager.setTextButtonStyle(textButtonStyle_bRight, images, font, "resolutionButtonRight_up","resolutionButtonRight_down" );
        bRightResolution = new TextButton("", buttonStyleManager.returnTextButtonStyle(textButtonStyle_bRight));
        buttonStyleManager.setTextButtonStyle(textButtonStyle_bBack, images_default, font, "defaultButton", "defaultButton");
        bBack = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bBack"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bBack));
        buttonStyleManager.setTextButtonStyle(textButtonStyle_bSave, images_default, font, "defaultButton", "defaultButton");
        bSave = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bSave"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bSave));

        buttonStyleManager.setTextButtonStyle(textButtonStyle_bBack, images_default, font, "defaultButton", "defaultButton");
        bBackDialog = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bBackDialog"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bBack));

        textFieldStyleManager.setTextFieldStyle(textFieldStyle, images, font, "textBar", Color.WHITE);
        tResolutionFieldText = new TextField(languageManager.getValue(languageManager.getLanguage(), "resolution_field_text"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));

        //dialog_field = new TextField(languageManager.getValue(languageManager.getLanguage(), "dialog_field_text"),textFieldStyleManager.returnTextFieldStyle(textFieldStyle));

        tVolumeFieldText = new TextField(languageManager.getValue(languageManager.getLanguage(), "volume_field_text"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        sliderStyle.disabledBackground = images.getDrawable("slider_background");
        sliderStyle.disabledKnob = images.getDrawable("slider_knob");
        sliderStyle.background = images.getDrawable("slider_background");
        sliderStyle.knob = images.getDrawable("slider_knob");
        volumeSlider = new Slider(0,100,1,false, sliderStyle);
        volumeSlider.setValue(fileReader.getVolumeValue());
        tLanguageFieldText = new TextField(languageManager.getValue(languageManager.getLanguage(), "language_field_text"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        bLeftLanguage = new TextButton("", buttonStyleManager.returnTextButtonStyle(textButtonStyle_bLeft));
        bRightLanguage = new TextButton("", buttonStyleManager.returnTextButtonStyle(textButtonStyle_bRight));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        table_resolution.setBounds(Gdx.graphics.getWidth()/10*3,Gdx.graphics.getHeight()/10*8, Gdx.graphics.getWidth()/10*4,32);
        table_default.setBounds(Gdx.graphics.getWidth()/10*3, Gdx.graphics.getHeight()/10, Gdx.graphics.getWidth()/10*4,50);
        table_volume.setBounds(Gdx.graphics.getWidth()/10*3, Gdx.graphics.getHeight()/10*7, Gdx.graphics.getWidth()/10*4, 32);
        table_language.setBounds(Gdx.graphics.getWidth()/10*3,Gdx.graphics.getHeight()/10*6, Gdx.graphics.getWidth()/10*4,32);
        bRightResolution.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(resolutions.size() == 3){
                    if(tResolutionField.getText().equals(resolutions.get(0)))
                    {
                        tResolutionField.setText(resolutions.get(1));
                    } else if (tResolutionField.getText().equals(resolutions.get(1))) {
                        tResolutionField.setText(resolutions.get(2));
                    }else if (Objects.equals(tResolutionField.getText(), resolutions.get(2))) {
                        tResolutionField.setText(resolutions.get(0));
                    }
                }else if(resolutions.size() == 2){
                    if(tResolutionField.getText().equals(resolutions.get(0)))
                    {
                        tResolutionField.setText(resolutions.get(1));
                    } else if (tResolutionField.getText().equals(resolutions.get(1))) {
                        tResolutionField.setText(resolutions.get(0));
                    }
                }
                else if(resolutions.size() == 1){
                    if(tResolutionField.getText().equals(resolutions.get(0))) {
                        tResolutionField.setText(resolutions.get(0));
                    }
                }

            }
        });

        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                backgroundMusic.setVolume(volumeSlider.getValue()/100);

            }
        });

        bLeftResolution.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(resolutions.size() == 3){
                    if(tResolutionField.getText().equals(resolutions.get(0)))
                    {
                        tResolutionField.setText(resolutions.get(2));
                    } else if (tResolutionField.getText().equals(resolutions.get(2))) {
                        tResolutionField.setText(resolutions.get(1));
                    }else if (Objects.equals(tResolutionField.getText(), resolutions.get(1))) {
                        tResolutionField.setText(resolutions.get(0));
                    }
                }else if(resolutions.size() == 2){
                    if(tResolutionField.getText().equals(resolutions.get(0)))
                    {
                        tResolutionField.setText(resolutions.get(1));
                    }else if (Objects.equals(tResolutionField.getText(), resolutions.get(1))) {
                        tResolutionField.setText(resolutions.get(0));
                    }
                }else if(resolutions.size() == 1){
                    if(tResolutionField.getText().equals(resolutions.get(0)))
                    {
                        tResolutionField.setText(resolutions.get(0));
                    }
                }

            }
        });
        bRightLanguage.addListener(new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){
                if(tLanguageField.getText().equals(languages.get(0))){
                    tLanguageField.setText(languages.get(1));
                }else{
                    tLanguageField.setText(languages.get(0));
                }
            }
        });
        bLeftLanguage.addListener(new ClickListener(){
            @Override
            public  void clicked(InputEvent event, float x, float y){
                if(tLanguageField.getText().equals(languages.get(1))){
                    tLanguageField.setText(languages.get(0));
                }else{
                    tLanguageField.setText(languages.get(1));
                }
            }
        });
        bBack.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println(tLanguageField.getText() + " " + fileReader.getLanguageValue());
                System.out.println(tResolutionField.getText() + " " + fileReader.getResolutionValue());
                System.out.println(Math.round(volumeSlider.getValue()) + " " + fileReader.getVolumeValue());
                if(tLanguageField.getText().equals(fileReader.getLanguageValue()) && tResolutionField.getText().equals(fileReader.getResolutionValue()) && Math.round(volumeSlider.getValue()) == fileReader.getVolumeValue()){
                    switch(GameState.getPreviousGameState()){
                        case MENU:{
                            game.setScreen(new MenuScreen(game));
                            dispose();
                        }break;
                        case PLAYING: {
                            //TODO
                        }break;
                    }

                }else {
                    //TODO second back button with setScreenem i disposem dodać, wyrownać, zrobić ramkę i ogarnąć tekst
                    isDialog = true;
                    Texture bg = new Texture(new FileHandle("assets/dialog/settings_dialog.png"));
                    backDialog = new Dialog("", new Window.WindowStyle(font, Color.WHITE, new TextureRegionDrawable(new TextureRegion(bg)))) {
                        public void result(Object obj) {
                            System.out.println("result " + obj);
                        }
                    };

                    Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
                    backDialog.text(languageManager.getValue(languageManager.getLanguage(), "dialog_field_text"), labelStyle);
                    backDialog.button(bBackDialog).padBottom(16);
                    backDialog.button(bSave).padBottom(16);
                    backDialog.show(stage);
                    System.out.println("Dialog width: " + backDialog.getWidth() + " height: " + backDialog.getHeight());
                    table_default.remove();
                }
            }
        });
        bBackDialog.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                switch(GameState.getGameState()){
                    case MENU:{
                        game.setScreen(new MenuScreen(game));
                        dispose();
                    }break;
                    case PLAYING: {
                        //TODO
                    }break;
                }
            }
        });

        System.out.println(resolutions);
        bSave.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                //Problem z ustawieniem właściwego displaymode-a, tablica mode-ów jest zależna od monitora
                //1920x1080 tylko w fullscreenie, reszta rozdzielczoci windowed
                //Graphics.DisplayMode[] displayModes = Gdx.graphics.getDisplayModes();

                if(tResolutionField.getText().equals("1920 X 1080 Fullscreen") || tResolutionField.getText().equals("1280 X 720 Fullscreen") || tResolutionField.getText().equals("1600 X 900 Fullscreen"))
                {
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                } else if (tResolutionField.getText().equals("1920 X 1080 Windowed")) {
                    Gdx.graphics.setWindowedMode(1920,1080);
                }else if (tResolutionField.getText().equals("1280 X 720 Windowed")) {
                    Gdx.graphics.setWindowedMode(1280,720);
                }else if (tResolutionField.getText().equals("1600 X 900 Windowed")) {
                    Gdx.graphics.setWindowedMode(1600,900);
                }
                fileReader.setSettings(tResolutionField.getText(), volumeSlider.getValue(), tLanguageField.getText());
                switch(GameState.getPreviousGameState()){
                    case MENU:{
                        game.setScreen(new MenuScreen(game));
                        dispose();
                    }break;
                    case PLAYING: {
                        //TODO
                    }break;
                }
            }
        });
        if(fileReader.getResolutionValue() != null){
            tResolutionField = new TextField(fileReader.getResolutionValue(), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        }else
        {
            if(Gdx.graphics.getWidth() == 1920){
                if(resolutions.contains("1920 X 1080 Fullscreen")){
                    tResolutionField = new TextField(resolutions.get(resolutions.indexOf("1920 X 1080 Fullscreen")), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
                }else if(resolutions.contains("1920 X 1080 Windowed")){
                    tResolutionField = new TextField(resolutions.get(resolutions.indexOf("1920 X 1080 Windowed")), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
                }

            }else if(Gdx.graphics.getWidth() == 1280){
                if(resolutions.contains("1280 X 720 Fullscreen")){
                    tResolutionField = new TextField(resolutions.get(resolutions.indexOf("1280 X 720 Fullscreen")), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
                }else if(resolutions.contains("1280 X 720 Windowed")){
                    tResolutionField = new TextField(resolutions.get(resolutions.indexOf("1280 X 720 Windowed")), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
                }
            }else{
                if(resolutions.contains("1600 X 900 Fullscreen")){
                    tResolutionField = new TextField(resolutions.get(resolutions.indexOf("1600 X 900 Fullscreen")), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
                }else if(resolutions.contains("1600 X 900 Windowed")){
                    tResolutionField = new TextField(resolutions.get(resolutions.indexOf("1600 X 900 Windowed")), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
                }
            }
        }
        if(fileReader.getLanguageValue() != null){
            tLanguageField = new TextField(fileReader.getLanguageValue(), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        }else{
            tLanguageField = new TextField(languages.get(0), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        }

        //to po lewo
        tResolutionFieldText.setAlignment(Align.center);
        tVolumeFieldText.setAlignment(Align.center);
        tLanguageFieldText.setAlignment(Align.center);
        //to po prawo
        tResolutionField.setAlignment(Align.center);
        table_resolution.add(tResolutionFieldText).padRight(100).width(320);
        table_resolution.add(bLeftResolution).width(32).height(32);
        table_resolution.add(tResolutionField).width(320).height(32);
        table_resolution.add(bRightResolution).width(32).height(32);
        table_default.add(bBack).padRight(200);
        table_default.add(bSave).padLeft(90);

        table_volume.add(tVolumeFieldText).padRight(100).width(320);
        table_volume.add(volumeSlider).width(384).height(32);


        tLanguageField.setAlignment(Align.center);
        table_language.add(tLanguageFieldText).padRight(100).width(320);
        table_language.add(bLeftLanguage).width(32).height(32);
        table_language.add(tLanguageField).width(320).height(32);
        table_language.add(bRightLanguage).width(32).height(32);
        //table_resolution.debug();
        //table_default.debug();
        stage.addActor(table_resolution);
        stage.addActor(table_default);
        stage.addActor(table_volume);
        stage.addActor(table_language);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        if(isDialog){
            game.batch.draw(backgroundForDialog, 0,0);
            //Gdx.gl.glClearColor(0,0,0,1);
            //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            table_language.setVisible(false);
            table_volume.setVisible(false);
            table_resolution.setVisible(false);
        }else{
            game.batch.draw(background, 0,0);
            table_language.setVisible(true);
            table_volume.setVisible(true);
            table_resolution.setVisible(true);
        }
        game.batch.end();
        tVolumeFieldText.setText(languageManager.getValue(languageManager.getLanguage(), "volume_field_text") + Math.round(volumeSlider.getValue()));
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
        backgroundForDialog = new Texture("tempBackground.png");
        resolutions = new ArrayList<>();
        resolutions = resolutionsClass.getResolutionsArrayList();
        languages = new ArrayList<>();
        languages.add("English");
        languages.add("Polski");
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Silkscreen.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        stage = new Stage();
        parameter.size = 15;
        parameter.color = Color.WHITE;
        parameter.characters = "ąćęłńóśżźabcdefghijklmnopqrstuvwxyzĄĆĘÓŁŃŚŻŹABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        font = new BitmapFont();
        font = generator.generateFont(parameter);

        taButtonsSettings = new TextureAtlas("assets/buttons/buttons_settings.pack");
        taButtonsDefault = new TextureAtlas("assets/buttons/buttons_default.pack");
        images = new Skin(taButtonsSettings);
        images_default = new Skin(taButtonsDefault);
        table_resolution = new Table(images);
        table_default = new Table(images_default);
        table_volume = new Table(images);
        table_language = new Table(images);
        textButtonStyle_bLeft = new TextButton.TextButtonStyle();
        textButtonStyle_bRight = new TextButton.TextButtonStyle();
        textButtonStyle_bBack = new TextButton.TextButtonStyle();
        textButtonStyle_bSave = new TextButton.TextButtonStyle();
        textFieldStyle = new TextField.TextFieldStyle();
        sliderStyle = new Slider.SliderStyle();

        backgroundMusic = game.getMusic();

    }
}