package com.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.game.Main;
import com.game.Manager.*;
import org.json.JSONObject;

import java.util.ArrayList;

public class GameScreen implements Screen {
    private Main game;
    private Stage stage;
    private Texture background;
    private BitmapFont font;
    private TextureAtlas taButtonsSettings, taButtonsDefault, taDialogBack;
    private Skin images, images_default, dialog, images_map;
    private TextButton bLeftResolution, bRightResolution, bBack, bSave, bLeftLanguage, bRightLanguage, bBackDialog;
    private Table table_map;
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
    private Dialog pauseDialog;
    private WorldManager worldManager;
    private JSONObject actualGame;
    private int[][] worldIntArr;
    private boolean isPauseDialog = false;

    public GameScreen (Main game, JSONObject save){
        this.game = game;
        resolutionsClass = new Resolutions();
        fileReader = new FileReader();
        fileReader.downloadSettings();
        if(fileReader.getLanguageValue() != null){languageManager = new LanguageManager(fileReader.getLanguageValue());}else{languageManager = new LanguageManager("English");}

        actualGame = save;
        //reading stats etc
        if(actualGame.getString("difficulty").equals("normal")){
            table_map = worldManager.createWorld(actualGame.getInt("seed"), 46);
        }else if(actualGame.getString("difficulty").equals("hard")){
            table_map = worldManager.createWorld(actualGame.getInt("seed"), 36);
        }else if(actualGame.getString("difficulty").equals("easy")){
            table_map = worldManager.createWorld(actualGame.getInt("seed"), 51);
        }



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

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Texture bg = new Texture(new FileHandle("assets/dialog/skin_dialog.png"));
        pauseDialog = new Dialog("", new Window.WindowStyle(font, Color.WHITE, new TextureRegionDrawable(new TextureRegion(bg)))) {
            public void result(Object obj) {
                pauseDialog.cancel();
            }
        };


        //t.add(new Image(new Texture(new FileHandle(icon)))).width(t.getHeight()/20).height(t.getHeight()/20).align(Align.right);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    if(!isPauseDialog) {
                        isPauseDialog = true;
                        pauseDialog.show(stage);
                        //pause game
                    }
                    else
                    {
                        isPauseDialog = false;
                        pauseDialog.hide();
                        //resume game
                    }
                    return true;
                }
                return super.keyDown(event, keycode);
            }
        });

        stage.addActor(table_map);

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
    private void initSettingsUI(){
        background = new Texture("background.png");
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
        taDialogBack = new TextureAtlas("assets/dialog/skin_dialog.pack");
        images = new Skin(taButtonsSettings);
        images_default = new Skin(taButtonsDefault);
        dialog = new Skin(taDialogBack);

        images_map = new Skin(new TextureAtlas("assets/icons/map_sprites.pack"));
        //table_map = new Table(images);
        
        textButtonStyle_bLeft = new TextButton.TextButtonStyle();
        textButtonStyle_bRight = new TextButton.TextButtonStyle();
        textButtonStyle_bBack = new TextButton.TextButtonStyle();
        textButtonStyle_bSave = new TextButton.TextButtonStyle();
        textFieldStyle = new TextField.TextFieldStyle();
        sliderStyle = new Slider.SliderStyle();

        backgroundMusic = game.getMusic();

    }
}