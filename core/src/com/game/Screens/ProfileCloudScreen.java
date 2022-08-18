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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.game.Main;
import com.game.Manager.*;
import com.game.State.GameState;
import org.json.JSONObject;

public class ProfileCloudScreen implements Screen {
    private Main game;
    private Stage stage;
    private Texture background;
    private FileReader fileReader;
    private ConnectionManager connectionManager;
    private LanguageManager languageManager;
    private ButtonStyleManager buttonStyleManager;
    private TextFieldStyleManager textFieldStyleManager;
    private JSONObject save1, save2, save3;
    private BitmapFont font, font_profile;
    private TextureAtlas taButtonsDefault, taEmptyTextfield, taButtonsProfile;
    private Skin images_default, images_empty, image_profiles, images_settings;

    private TextButton bBack, bPlay, bOtherScreen, bNewProfile01, bNewProfile02, bNewProfile03, bDialogCancel, bDialogCreate, cDialogNormalDifficulty, cDialogHardDifficulty;
    private Table table_profile_01, table_profile_02, table_profile_03, table_default, table_previous, table_Dialog;
    private TextField tDialogNormalDifficulty, tDialogHardDifficulty;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private TextButton.TextButtonStyle textButtonStyle_bBack,textButtonStyle_bSave, textButtonStyle_bPrevious, textButtonStyle_bNewProfile, textButtonStyle_cDialogDifficultyChecked, textButtonStyle_cDialogDifficultyUnchecked;
    private TextField.TextFieldStyle textFieldStyle;
    private Music backgroundMusic;

    private Dialog newGameDialog;
    private String chosenDifficulty = null;
    private int chosenProfile;


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

        buttonStyleManager.setTextButtonStyle(textButtonStyle_bPrevious, image_profiles, font, "previous_screen_button", "previous_screen_button");
        bOtherScreen = new TextButton(null, buttonStyleManager.returnTextButtonStyle(textButtonStyle_bPrevious));


        buttonStyleManager.setTextButtonStyle(textButtonStyle_bNewProfile, image_profiles, font, "new_profile_button_up", "new_profile_button_down");
        bNewProfile01 = new TextButton(null, buttonStyleManager.returnTextButtonStyle(textButtonStyle_bNewProfile));
        bNewProfile02 = new TextButton(null, buttonStyleManager.returnTextButtonStyle(textButtonStyle_bNewProfile));
        bNewProfile03 = new TextButton(null, buttonStyleManager.returnTextButtonStyle(textButtonStyle_bNewProfile));
        textFieldStyleManager.setTextFieldStyle(textFieldStyle, images_empty, font, "empty_background", Color.WHITE);





        background = new Texture("background.png");
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Texture bg = new Texture(new FileHandle("assets/profile_banner.png"));
        Texture dialogBg = new Texture(new FileHandle("assets/dialog/skin_dialog.png"));
        Texture icon = new Texture(new FileHandle("assets/icons/local.png"));

        table_profile_01.setBounds(Gdx.graphics.getWidth()/10*2, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10*3, Gdx.graphics.getWidth()/10*3);
        table_profile_01.setBackground(new TextureRegionDrawable(new TextureRegion(bg)));
        table_profile_02.setBounds(Gdx.graphics.getWidth()/10*4, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10*3, Gdx.graphics.getWidth()/10*3);
        table_profile_02.setBackground(new TextureRegionDrawable(new TextureRegion(bg)));
        table_profile_03.setBounds(Gdx.graphics.getWidth()/10*6, Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/10*3, Gdx.graphics.getWidth()/10*3);
        table_profile_03.setBackground(new TextureRegionDrawable(new TextureRegion(bg)));


        JSONObject loadSaves = new JSONObject();

        loadSaves.put("login", game.getLogin());

        JSONObject loadResponse = connectionManager.requestSend(loadSaves, "http://localhost:9000/api/downloadSaves");
        System.out.println(loadResponse.getInt("status"));
        if (loadResponse.getInt("status") == 200)
        {
            int numberOfLoadedSaves = loadResponse.getJSONArray("loadedData").length();
            JSONObject save;
            for (int i =0; i<numberOfLoadedSaves; i++)
            {
                save =  loadResponse.getJSONArray("loadedData").getJSONObject(i);

                if( save.getInt("profileNumber")==1)
                {
                    save1 = save;
                    table_profile_01 = ProfileManager.createProfileTable(save, font_profile, languageManager, Gdx.graphics.getWidth()/10*2, "assets/icons/cloud.png");
                    table_profile_01.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            System.out.println("zostałem wybrany");
                            GameState.setGameState(GameState.PLAYING);
                            game.setScreen(new GameScreen(game, save1, false));
                        }
                    });

                }
                else if( save.getInt("profileNumber")==2)
                {
                    save2 = save;
                    table_profile_02 = ProfileManager.createProfileTable(save, font_profile, languageManager, Gdx.graphics.getWidth()/10*4,"assets/icons/cloud.png");
                    table_profile_02.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            GameState.setGameState(GameState.PLAYING);
                            game.setScreen(new GameScreen(game, save2, false));
                        }
                    });

                }
                else if( save.getInt("profileNumber")==3)
                {
                    save3 = save;
                    table_profile_03 = ProfileManager.createProfileTable(save, font_profile, languageManager, Gdx.graphics.getWidth()/10*6, "assets/icons/cloud.png");
                    table_profile_03.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            GameState.setGameState(GameState.PLAYING);
                            game.setScreen(new GameScreen(game, save3, false));
                        }
                    });

                }




            }//for
        }//if_downloaded


        //no saves -> make +
        if (table_profile_01.getChildren().size==0) {
            table_profile_01 = ProfileManager.createEmptyProfileTable(font, Gdx.graphics.getWidth()/10*2);
            table_profile_01.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    chosenProfile = 1;
                    newGameDialog.show(stage);
                }
            });
        }
        if (table_profile_02.getChildren().size==0) {
            table_profile_02 = ProfileManager.createEmptyProfileTable(font, Gdx.graphics.getWidth()/10*4);
            table_profile_02.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    chosenProfile = 2;
                    newGameDialog.show(stage);
                }
            });
        }
        if (table_profile_03.getChildren().size==0) {
            table_profile_03 = ProfileManager.createEmptyProfileTable(font, Gdx.graphics.getWidth()/10*6);
            table_profile_03.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    chosenProfile = 3;
                    newGameDialog.show(stage);
                }
            });
        }


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
                    saveData.put("profileNumber", 3);
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


                if (keycode == Input.Keys.D) {
                    JSONObject saveData = new JSONObject();
                    saveData.put("login", game.getLogin());
                    saveData.put("profileNumber", 1);
                    saveData.put("seed", 123);
                    saveData.put("difficulty", "normal");
                    saveData.put("finishedMaps", 123);
                    saveData.put("wave", 10);
                    saveData.put("gold", 12233);
                    saveData.put("diamonds", 1111);

                    JSONObject saveResponse = connectionManager.requestSend(saveData, "http://localhost:9000/api/uploadSave");
                    System.out.println(saveResponse);

                    return true;
                }

                if (keycode == Input.Keys.F) {
                    JSONObject deleteData = new JSONObject();
                    deleteData.put("login", game.getLogin());
                    deleteData.put("profileNumber", 1);


                    JSONObject deleteResponse = connectionManager.requestSend(deleteData, "http://localhost:9000/api/deleteSave");
                    System.out.println(deleteResponse);

                    return true;
                }

                return super.keyDown(event, keycode);
            }
        });




        //to calibrate
        table_previous.setBounds((Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/10*9 - Gdx.graphics.getHeight()/10), Gdx.graphics.getWidth()/10*2,Gdx.graphics.getHeight()/10, Gdx.graphics.getWidth()/10*2);

        table_previous.add(bOtherScreen);
        table_previous.debug();
        stage.addActor(table_previous);
        bOtherScreen.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ProfileLocalScreen(game));
            }
        });





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
                    System.out.println("Stworzono gre na profilu " + chosenProfile + "o poziomie trudnosci " + chosenDifficulty);
                    GameState.setGameState(GameState.PLAYING);
                    ///in cloud put profileNumber
                    game.setScreen(new GameScreen(game,ProfileManager.createEmptySave(chosenDifficulty, chosenProfile), false));
                }
            }
        });









        table_default.setBounds(Gdx.graphics.getWidth()/10*3, Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/10*4,50);

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

    private void initProfileCloudUI() {
        stage = new Stage();
        background = new Texture("background.png");
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Silkscreen.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 13;

        parameter.color = Color.WHITE;
        parameter.characters = "ąćęłńóśżźabcdefghijklmnopqrstuvwxyzĄĆĘÓŁŃŚŻŹABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        font = new BitmapFont();
        font = generator.generateFont(parameter);
        if(Gdx.graphics.getWidth() < 1281){
            parameter.size = 9;
        }
        else if(Gdx.graphics.getWidth() < 1601){
            parameter.size = 11;
        }else{
            parameter.size = 13;
        }
        font_profile = new BitmapFont();
        font_profile = generator.generateFont(parameter);
        taButtonsDefault = new TextureAtlas("assets/buttons/buttons_default.pack");
        taEmptyTextfield = new TextureAtlas("assets/buttons/buttons_settings.pack");
        taButtonsProfile = new TextureAtlas("assets/buttons/buttons_profile.pack");

        connectionManager = new ConnectionManager();

        images_default = new Skin(taButtonsDefault);
        images_empty = new Skin(taEmptyTextfield);
        image_profiles = new Skin(taButtonsProfile);
        images_settings = new Skin(new TextureAtlas("assets/buttons/buttons_settings.pack"));
        table_default = new Table(images_default);
        table_previous = new Table(images_default);
        table_Dialog = new Table(images_settings);
        table_profile_01 = new Table();
        table_profile_02 = new Table();
        table_profile_03 = new Table();

        textButtonStyle_bBack = new TextButton.TextButtonStyle();
        textButtonStyle_bSave = new TextButton.TextButtonStyle();
        textButtonStyle_bPrevious = new TextButton.TextButtonStyle();
        textButtonStyle_bNewProfile = new TextButton.TextButtonStyle();
        textFieldStyle = new TextField.TextFieldStyle();
        backgroundMusic = game.getMusic();
        textButtonStyle_cDialogDifficultyChecked = new TextButton.TextButtonStyle();
        textButtonStyle_cDialogDifficultyUnchecked  = new TextButton.TextButtonStyle();




    }
}
