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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.game.Main;
import com.game.Manager.*;

import java.util.ArrayList;
import java.util.List;

public class ProfileLocalScreen implements Screen {
    private Main game;
    private FileReader fileReader;
    private ButtonStyleManager buttonStyleManager;

    private TextFieldStyleManager textFieldStyleManager;
    private List<User_save> profiles;
    private Stage stage;
    private Texture background;
    private BitmapFont font;
    private TextureAtlas taButtonsDefault, taProfileBanner, taEmptyTextfield, taButtonsProfile;
    private Skin images_default, images_empty, image_profiles, images_settings;
    private TextButton bBack, bPlay, bOtherScreen, bNewProfile01, bNewProfile02, bNewProfile03, bDialogCancel, bDialogCreate, cDialogNormalDifficulty, cDialogHardDifficulty;
    private Table table_profile_01, table_profile_02, table_profile_03, table_default, table_next, table_Dialog;
    private TextField tDifficulty01, tDifficulty02,tDifficulty03,
                      tFinishedMaps01, tFinishedMaps02, tFinishedMaps03,
                      tWave01, tWave02, tWave03,
                      tGold01, tGold02, tGold03,
                      tDiamonds01, tDiamonds02, tDiamonds03,
                      tDialogNormalDifficulty, tDialogHardDifficulty;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private TextButton.TextButtonStyle textButtonStyle_bBack,textButtonStyle_bSave, textButtonStyle_bNext, textButtonStyle_bNewProfile, textButtonStyle_cDialogDifficultyChecked, textButtonStyle_cDialogDifficultyUnchecked;
    private TextField.TextFieldStyle textFieldStyle;
    private Music backgroundMusic;
    private LanguageManager languageManager;
    private ConnectionManager connectionManager;

    private Dialog newGameDialog;
    private String chosenDifficulty = null;
    private int chosenProfileToCreate;

    public ProfileLocalScreen(Main game){
        this.game = game;
        fileReader = new FileReader();
        fileReader.downloadSettings();
        if(fileReader.getLanguageValue() != null){
            languageManager = new LanguageManager(fileReader.getLanguageValue());
        }else{
            languageManager = new LanguageManager("English");
        }
        initProfileLocalUI();
        profiles = new ArrayList<>();
        buttonStyleManager = new ButtonStyleManager();
        textFieldStyleManager = new TextFieldStyleManager();
        buttonStyleManager.setTextButtonStyle(textButtonStyle_bBack, images_default, font, "defaultButton", "defaultButton");
        bBack = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bBack"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bBack));
        buttonStyleManager.setTextButtonStyle(textButtonStyle_bSave, images_default, font, "defaultButton", "defaultButton");
        bPlay = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bPlay"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bSave));

        bDialogCreate = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bDialogCreate"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bSave));
        bDialogCancel = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bDialogCancel"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bSave));

        //TODO Dialog Seed input

        buttonStyleManager.setTextButtonStyle(textButtonStyle_cDialogDifficultyChecked, images_settings, font, "checkbox_on", "checkbox_on");
        buttonStyleManager.setTextButtonStyle(textButtonStyle_cDialogDifficultyUnchecked, images_settings, font, "checkbox_off", "checkbox_off");

        cDialogNormalDifficulty = new TextButton(null, buttonStyleManager.returnTextButtonStyle(textButtonStyle_cDialogDifficultyUnchecked));
        cDialogHardDifficulty = new TextButton(null, buttonStyleManager.returnTextButtonStyle(textButtonStyle_cDialogDifficultyUnchecked));

        buttonStyleManager.setTextButtonStyle(textButtonStyle_bNext, image_profiles, font, "next_screen_button", "next_screen_button");
        bOtherScreen = new TextButton("", buttonStyleManager.returnTextButtonStyle(textButtonStyle_bNext));

        buttonStyleManager.setTextButtonStyle(textButtonStyle_bNewProfile, image_profiles, font, "new_profile_button_up", "new_profile_button_down");
        bNewProfile01 = new TextButton("", buttonStyleManager.returnTextButtonStyle(textButtonStyle_bNewProfile));
        bNewProfile02 = new TextButton("", buttonStyleManager.returnTextButtonStyle(textButtonStyle_bNewProfile));
        bNewProfile03 = new TextButton("", buttonStyleManager.returnTextButtonStyle(textButtonStyle_bNewProfile));
        textFieldStyleManager.setTextFieldStyle(textFieldStyle, images_empty, font, "empty_background", Color.WHITE);
    }

    @Override
    public void show() {
        if(game.getIsLogged()){
            table_next.setBounds(Gdx.graphics.getWidth()/10*9, Gdx.graphics.getWidth()/10*2,Gdx.graphics.getHeight()/10, Gdx.graphics.getWidth()/10*2);
            table_next.add(bOtherScreen);
            table_next.debug();
            stage.addActor(table_next);
            bOtherScreen.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    //testy
                    game.setScreen(new ProfileCloudScreen(game));

                    //produkcja
                    /*
                    JSONObject pingResponse = connectionManager.requestSend(new JSONObject(), "http://localhost:9000/api/ping");
                    if (pingResponse.getInt("status") == 200 || pingResponse.getInt("status") == 201) {
                        game.setScreen(new ProfileCloudScreen(game));
                    }
                    else {
                        System.out.println(languageManager.getValue(languageManager.getLanguage(), pingResponse.getString("message")));
                    }
                    */


                }
            });
        }
        Gdx.input.setInputProcessor(stage);
        Texture bg = new Texture(new FileHandle("assets/profile_banner.png"));
        Texture dialogBg = new Texture(new FileHandle("assets/dialog/skin_dialog.png"));
        Texture icon = new Texture(new FileHandle("assets/icons/local.png"));
        Image local01 = new Image(icon);
        Image local02 = new Image(icon);
        Image local03 = new Image(icon);


        newGameDialog = new Dialog("", new Window.WindowStyle(font, Color.WHITE, new TextureRegionDrawable(new TextureRegion(dialogBg)))) {
            public void result(Object obj) {
                newGameDialog.cancel();
            }
        };

        tDialogNormalDifficulty  = new TextField(languageManager.getValue(languageManager.getLanguage(), "tNormalDifficulty"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tDialogHardDifficulty = new TextField(languageManager.getValue(languageManager.getLanguage(), "tHardDifficulty"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));




        table_Dialog.setWidth(350);
        table_Dialog.setX(200);
        table_Dialog.setY(300);
        table_Dialog.add(cDialogNormalDifficulty);
        table_Dialog.add(tDialogNormalDifficulty);
        table_Dialog.row();
        table_Dialog.add(cDialogHardDifficulty);
        table_Dialog.add(tDialogHardDifficulty);
        newGameDialog.addActor(table_Dialog);

        newGameDialog.button(bDialogCancel);
        newGameDialog.button(bDialogCreate);

        cDialogNormalDifficulty.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                cDialogNormalDifficulty.setStyle(textButtonStyle_cDialogDifficultyChecked);
                cDialogHardDifficulty.setStyle(textButtonStyle_cDialogDifficultyUnchecked);
                chosenDifficulty = "normal";
            }
        });

        cDialogHardDifficulty.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                cDialogNormalDifficulty.setStyle(textButtonStyle_cDialogDifficultyUnchecked);
                cDialogHardDifficulty.setStyle(textButtonStyle_cDialogDifficultyChecked);
                chosenDifficulty = "hard";
            }
        });


        bDialogCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                newGameDialog.hide();
            }
        });

        bDialogCreate.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (chosenDifficulty !=null)
                {
                    System.out.println("Stworzono gre na profilu " +chosenProfileToCreate + "o poziomie trudnosci " + chosenDifficulty);
                }
            }
        });


        //


        table_profile_01.setBounds(Gdx.graphics.getWidth()/10*2, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10*3, Gdx.graphics.getWidth()/10*3);
        table_profile_01.setBackground(new TextureRegionDrawable(new TextureRegion(bg)));
        table_profile_02.setBounds(Gdx.graphics.getWidth()/10*4, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10*3, Gdx.graphics.getWidth()/10*3);
        table_profile_02.setBackground(new TextureRegionDrawable(new TextureRegion(bg)));
        table_profile_03.setBounds(Gdx.graphics.getWidth()/10*6, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10*3, Gdx.graphics.getWidth()/10*3);
        table_profile_03.setBackground(new TextureRegionDrawable(new TextureRegion(bg)));

        table_default.setBounds(Gdx.graphics.getWidth()/10*3, Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/10*4,50);
        if(fileReader.fileExists("save/save01l.json")){
            User_save user_save_01 = new User_save();
            fileReader.downloadSave("save/save01l.json");
            user_save_01.seed = fileReader.getSeed();
            user_save_01.difficulty = fileReader.getDifficulty();
            user_save_01.finishedMaps = fileReader.getFinishedMaps();
            user_save_01.wave = fileReader.getWave();
            user_save_01.gold = fileReader.getGold();
            user_save_01.diamonds = fileReader.getDiamonds();
            profiles.add(user_save_01);
            tDifficulty01 = new TextField(languageManager.getValue(languageManager.getLanguage(), "difficulty_field") + user_save_01.difficulty, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
            tDifficulty01.setAlignment(Align.left);
            tFinishedMaps01 = new TextField(languageManager.getValue(languageManager.getLanguage(), "finishedmaps_field") + user_save_01.finishedMaps, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
            tFinishedMaps01.setAlignment(Align.left);
            tWave01 = new TextField(languageManager.getValue(languageManager.getLanguage(), "wave_field") + user_save_01.wave, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
            tWave01.setAlignment(Align.left);
            tGold01 = new TextField(languageManager.getValue(languageManager.getLanguage(), "gold_field") + user_save_01.gold, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
            tGold01.setAlignment(Align.left);
            tDiamonds01 = new TextField(languageManager.getValue(languageManager.getLanguage(), "diamonds_field") + user_save_01.diamonds, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
            tDiamonds01.setAlignment(Align.left);
            table_profile_01.add(local01).width(table_profile_01.getHeight()/20).height(table_profile_01.getHeight()/20).align(Align.right);
            table_profile_01.row();
            table_profile_01.add(tDifficulty01).width(table_profile_01.getWidth()).height(table_profile_01.getHeight()/10);
            table_profile_01.row();
            table_profile_01.add(tFinishedMaps01).width(table_profile_01.getWidth()).height(table_profile_01.getHeight()/10);
            table_profile_01.row();
            table_profile_01.add(tWave01).width(table_profile_01.getWidth()).height(table_profile_01.getHeight()/10);
            table_profile_01.row();
            table_profile_01.add(tGold01).width(table_profile_01.getWidth()).height(table_profile_01.getHeight()/10);
            table_profile_01.row();
            table_profile_01.add(tDiamonds01).width(table_profile_01.getWidth()).height(table_profile_01.getHeight()/10).padBottom(table_profile_01.getHeight()/2-table_profile_01.getHeight()/10);
            table_profile_01.debug();
            table_profile_01.setTouchable(Touchable.enabled);
            table_profile_01.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("zostałem wybrany");
                    //wyłapuje tylko na textfieldach, a nie na całym table_profile
                }
            });

        }else{
            table_profile_01.add(bNewProfile01).height(table_profile_01.getHeight()/4).width(table_profile_01.getHeight()/4).padBottom(table_profile_01.getHeight()/4);
            table_profile_01.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    chosenProfileToCreate = 1;
                    newGameDialog.show(stage);
                }
            });
        }

        if(fileReader.fileExists("save/save02l.json")){
            User_save user_save_02 = new User_save();
            fileReader.downloadSave("save/save02l.json");
            user_save_02.seed = fileReader.getSeed();
            user_save_02.difficulty = fileReader.getDifficulty();
            user_save_02.finishedMaps = fileReader.getFinishedMaps();
            user_save_02.wave = fileReader.getWave();
            user_save_02.gold = fileReader.getGold();
            user_save_02.diamonds = fileReader.getDiamonds();
            profiles.add(user_save_02);
            tDifficulty02 = new TextField(languageManager.getValue(languageManager.getLanguage(), "difficulty_field") + user_save_02.difficulty, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
            tDifficulty02.setAlignment(Align.left);
            tFinishedMaps02 = new TextField(languageManager.getValue(languageManager.getLanguage(), "finishedmaps_field") + user_save_02.finishedMaps, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
            tFinishedMaps02.setAlignment(Align.left);
            tWave02 = new TextField(languageManager.getValue(languageManager.getLanguage(), "wave_field") + user_save_02.wave, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
            tWave02.setAlignment(Align.left);
            tGold02 = new TextField(languageManager.getValue(languageManager.getLanguage(), "gold_field") + user_save_02.gold, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
            tGold02.setAlignment(Align.left);
            tDiamonds02 = new TextField(languageManager.getValue(languageManager.getLanguage(), "diamonds_field") + user_save_02.diamonds, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
            tDiamonds02.setAlignment(Align.left);
            table_profile_02.add(local02).width(table_profile_02.getHeight()/20).height(table_profile_02.getHeight()/20).align(Align.right);
            table_profile_02.row();
            table_profile_02.add(tDifficulty02).width(table_profile_02.getWidth()).height(table_profile_02.getHeight()/10);
            table_profile_02.row();
            table_profile_02.add(tFinishedMaps02).width(table_profile_02.getWidth()).height(table_profile_02.getHeight()/10);
            table_profile_02.row();
            table_profile_02.add(tWave02).width(table_profile_02.getWidth()).height(table_profile_02.getHeight()/10);
            table_profile_02.row();
            table_profile_02.add(tGold02).width(table_profile_02.getWidth()).height(table_profile_02.getHeight()/10);
            table_profile_02.row();
            table_profile_02.add(tDiamonds02).width(table_profile_02.getWidth()).height(table_profile_02.getHeight()/10).padBottom(table_profile_02.getHeight()/2-table_profile_02.getHeight()/10);
            table_profile_02.debug();
            table_profile_02.setTouchable(Touchable.enabled);
            table_profile_02.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("zostałem wybrany");
                    //wyłapuje tylko na textfieldach, a nie na całym table_profile
                }
            });
        }else{
            table_profile_02.add(bNewProfile02).height(table_profile_02.getHeight()/4).width(table_profile_02.getHeight()/4).padBottom(table_profile_02.getHeight()/4);;
            table_profile_02.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    chosenProfileToCreate = 2;
                    newGameDialog.show(stage);
                }
            });
        }
        if(fileReader.fileExists("save/save03l.json")){
            User_save user_save_03 = new User_save();
            fileReader.downloadSave("save/save03l.json");
            user_save_03.seed = fileReader.getSeed();
            user_save_03.difficulty = fileReader.getDifficulty();
            user_save_03.finishedMaps = fileReader.getFinishedMaps();
            user_save_03.wave = fileReader.getWave();
            user_save_03.gold = fileReader.getGold();
            user_save_03.diamonds = fileReader.getDiamonds();
            profiles.add(user_save_03);
            tDifficulty03 = new TextField(languageManager.getValue(languageManager.getLanguage(), "difficulty_field") + user_save_03.difficulty, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
            tDifficulty03.setAlignment(Align.left);
            tFinishedMaps03 = new TextField(languageManager.getValue(languageManager.getLanguage(), "finishedmaps_field") + user_save_03.finishedMaps, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
            tFinishedMaps03.setAlignment(Align.left);
            tWave03 = new TextField(languageManager.getValue(languageManager.getLanguage(), "wave_field") + user_save_03.wave, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
            tWave03.setAlignment(Align.left);
            tGold03 = new TextField(languageManager.getValue(languageManager.getLanguage(), "gold_field") + user_save_03.gold, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
            tGold03.setAlignment(Align.left);
            tDiamonds03 = new TextField(languageManager.getValue(languageManager.getLanguage(), "diamonds_field") + user_save_03.diamonds, textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
            tDiamonds03.setAlignment(Align.left);
            table_profile_03.add(local03).width(table_profile_03.getHeight()/20).height(table_profile_03.getHeight()/20).align(Align.right);
            table_profile_03.row();
            table_profile_03.add(tDifficulty01).width(table_profile_03.getWidth()).height(table_profile_03.getHeight()/10);
            table_profile_03.row();
            table_profile_03.add(tFinishedMaps01).width(table_profile_03.getWidth()).height(table_profile_03.getHeight()/10);
            table_profile_03.row();
            table_profile_03.add(tWave01).width(table_profile_03.getWidth()).height(table_profile_03.getHeight()/10);
            table_profile_03.row();
            table_profile_03.add(tGold01).width(table_profile_03.getWidth()).height(table_profile_03.getHeight()/10);
            table_profile_03.row();
            table_profile_03.add(tDiamonds01).width(table_profile_03.getWidth()).height(table_profile_03.getHeight()/10).padBottom(table_profile_03.getHeight()/2-table_profile_03.getHeight()/10);
            table_profile_03.debug();
            table_profile_03.setTouchable(Touchable.enabled);
            table_profile_03.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("zostałem wybrany");
                    //wyłapuje tylko na textfieldach, a nie na całym table_profile
                }
            });
        }else{
            table_profile_03.add(bNewProfile03).height(table_profile_03.getHeight()/4).width(table_profile_03.getHeight()/4).padBottom(table_profile_03.getHeight()/4);
            table_profile_03.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    chosenProfileToCreate = 3;
                    newGameDialog.show(stage);
                }
            });

        }

        table_default.add(bBack).padRight(200);
        table_default.add(bPlay).padLeft(90);


        stage.addActor(table_profile_01);
        stage.addActor(table_profile_02);
        stage.addActor(table_profile_03);
        stage.addActor(table_default);


        bBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
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

    private void initProfileLocalUI(){
        stage = new Stage();
        background = new Texture("background.png");
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Silkscreen.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        if(Gdx.graphics.getWidth() < 1281){
            parameter.size = 12;
        }else{
            parameter.size = 15;
        }
        parameter.color = Color.WHITE;
        parameter.characters = "ąćęłńóśżźabcdefghijklmnopqrstuvwxyzĄĆĘÓŁŃŚŻŹABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        font = new BitmapFont();
        font = generator.generateFont(parameter);
        taButtonsDefault = new TextureAtlas("assets/buttons/buttons_default.pack");
        //TODO dodaj jeden więcej przycisk >
        taEmptyTextfield = new TextureAtlas("assets/buttons/buttons_settings.pack");
        taButtonsProfile = new TextureAtlas("assets/buttons/buttons_profile.pack");

        connectionManager = new ConnectionManager();
        images_default = new Skin(taButtonsDefault);
        images_empty = new Skin(taEmptyTextfield);
        image_profiles = new Skin(taButtonsProfile);
        images_settings = new Skin(new TextureAtlas("assets/buttons/buttons_settings.pack"));
        table_default = new Table(images_default);
        table_next = new Table(images_default);
        table_Dialog = new Table(images_settings);
        table_profile_01 = new Table();
        table_profile_02 = new Table();
        table_profile_03 = new Table();

        textButtonStyle_bBack = new TextButton.TextButtonStyle();
        textButtonStyle_bSave = new TextButton.TextButtonStyle();
        textButtonStyle_bNext = new TextButton.TextButtonStyle();
        textButtonStyle_bNewProfile = new TextButton.TextButtonStyle();
        textFieldStyle = new TextField.TextFieldStyle();
        backgroundMusic = game.getMusic();
        textButtonStyle_cDialogDifficultyChecked = new TextButton.TextButtonStyle();
        textButtonStyle_cDialogDifficultyUnchecked  = new TextButton.TextButtonStyle();

    }
}
