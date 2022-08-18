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
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.game.Main;
import com.game.Manager.*;
import com.game.State.GameState;
import org.json.JSONObject;
import org.json.JSONTokener;

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
    private TextureAtlas taButtonsDefault, taEmptyTextfield, taButtonsProfile, taDialog, taUpgrades; //<- to delete
    private Skin images_default, images_empty, image_profiles, images_settings, images_dialog, images_upgrades; //<- to delete
    private TextButton bBack, bPlay, bOtherScreen, bNewProfile01, bNewProfile02, bNewProfile03, bDialogCancel, bDialogCreate, cDialogEasyDifficulty, cDialogNormalDifficulty, cDialogHardDifficulty;
    private Table table_profile_01, table_profile_02, table_profile_03, table_default, table_next, table_Dialog, table_upgrade; //<- to delete
    private TextField tDialogEasyDifficulty, tDialogNormalDifficulty, tDialogHardDifficulty;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private TextButton.TextButtonStyle textButtonStyle_bBack,textButtonStyle_bSave, textButtonStyle_bNext, textButtonStyle_bNewProfile, textButtonStyle_cDialogDifficultyChecked, textButtonStyle_cDialogDifficultyUnchecked;
    private TextField.TextFieldStyle textFieldStyle;
    private Music backgroundMusic;
    private LanguageManager languageManager;
    private ConnectionManager connectionManager;
    private JSONObject save1, save2, save3;

    private Dialog newGameDialog, upgradeDialog;//<- to delete
    private String chosenDifficulty = null;
    private int chosenProfileToCreate;

    private boolean isDialog = false;//<- to delete or leave
    private Image attack01, attack02, attack03, attack04, attack05, health01, health02, health03, income01, income02;
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

        cDialogEasyDifficulty = new TextButton(null, buttonStyleManager.returnTextButtonStyle(textButtonStyle_cDialogDifficultyUnchecked));
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
            //table_next.debug();
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
        tDialogEasyDifficulty = new TextField(languageManager.getValue(languageManager.getLanguage(), "tEasyDifficulty"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tDialogNormalDifficulty  = new TextField(languageManager.getValue(languageManager.getLanguage(), "tNormalDifficulty"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tDialogHardDifficulty = new TextField(languageManager.getValue(languageManager.getLanguage(), "tHardDifficulty"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));




        table_Dialog.setWidth(350);
        table_Dialog.setX(200);
        table_Dialog.setY(300);
        table_Dialog.add(cDialogEasyDifficulty);
        table_Dialog.add(tDialogEasyDifficulty);
        table_Dialog.row();
        table_Dialog.add(cDialogNormalDifficulty);
        table_Dialog.add(tDialogNormalDifficulty);
        table_Dialog.row();
        table_Dialog.add(cDialogHardDifficulty);
        table_Dialog.add(tDialogHardDifficulty);
        newGameDialog.addActor(table_Dialog);

        newGameDialog.button(bDialogCancel);
        newGameDialog.button(bDialogCreate);

        cDialogEasyDifficulty.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                cDialogEasyDifficulty.setStyle(textButtonStyle_cDialogDifficultyChecked);
                cDialogNormalDifficulty.setStyle(textButtonStyle_cDialogDifficultyUnchecked);
                cDialogHardDifficulty.setStyle(textButtonStyle_cDialogDifficultyUnchecked);
                chosenDifficulty = "easy";
            }
        });

        cDialogNormalDifficulty.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                cDialogEasyDifficulty.setStyle(textButtonStyle_cDialogDifficultyUnchecked);
                cDialogNormalDifficulty.setStyle(textButtonStyle_cDialogDifficultyChecked);
                cDialogHardDifficulty.setStyle(textButtonStyle_cDialogDifficultyUnchecked);
                chosenDifficulty = "normal";
            }
        });

        cDialogHardDifficulty.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                cDialogEasyDifficulty.setStyle(textButtonStyle_cDialogDifficultyUnchecked);
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
                    GameState.setGameState(GameState.PLAYING);
                    ///in cloud put profileNumber
                    game.setScreen(new GameScreen(game,ProfileManager.createEmptySave(chosenDifficulty)));
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
            JSONObject save = fileReader.downloadSaveAsJSONObject("save/save01l.json");
            table_profile_01 = ProfileManager.createProfileTable(save, font, languageManager, Gdx.graphics.getWidth()/10*2, "assets/icons/local.png");
            table_profile_01.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    isDialog = true;
                    System.out.println("zostałem wybrany");
                    Texture bg = new Texture(new FileHandle("assets/dialog/skin_dialog.png"));                  // V
                    System.out.println(bg.getHeight());
                    System.out.println(bg.getWidth());
                    upgradeDialog = new Dialog("", new Window.WindowStyle(font, Color.WHITE, new TextureRegionDrawable(new TextureRegion(bg)))) {
                        public void result(Object obj) {
                            System.out.println("result " + obj);
                        }
                    };
                    Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
                    upgradeDialog.setBounds(0,0,200,200);
                    System.out.println(upgradeDialog.getWidth());
                    System.out.println(upgradeDialog.getHeight());
                    upgradeDialog.text(languageManager.getValue(languageManager.getLanguage(), "upgrade_dialog_field_text"), labelStyle);
                    table_upgrade.setBounds(0,0,upgradeDialog.getWidth(), upgradeDialog.getHeight()/10*9);
                    //table_upgrade.debug();
                    upgradeDialog.row();
                    //upgradeDialog.padBottom(500);
                    attack01 = new Image(images_upgrades, "upgradeIcons_attackSword");
                    Image connecta01toa02 =  new Image(images_upgrades, "upgradeIcons_connect");
                    attack02 = new Image(images_upgrades, "upgradeIcons_lock");//"upgradeIcons_attackBattleAxe"
                    Image connecta02toa03 =  new Image(images_upgrades, "upgradeIcons_connect");
                    attack03 = new Image(images_upgrades, "upgradeIcons_lock");//"upgradeIcons_attackMace"
                    Image connecta03toa04 =  new Image(images_upgrades, "upgradeIcons_connect");
                    attack04 = new Image(images_upgrades, "upgradeIcons_lock");//"upgradeIcons_attackBow"
                    Image connecta04toa05 =  new Image(images_upgrades, "upgradeIcons_connect");
                    attack05 = new Image(images_upgrades, "upgradeIcons_lock");//"upgradeIcons_attackCrossbow"

                    health01 = new Image(images_upgrades, "upgradeIcons_healthHealth");
                    Image connecth01toh02 =  new Image(images_upgrades, "upgradeIcons_connect");
                    health02 = new Image(images_upgrades, "upgradeIcons_lock");//"upgradeIcons_healthRegen"
                    Image connecth02toh03 =  new Image(images_upgrades, "upgradeIcons_connect");
                    health03 = new Image(images_upgrades, "upgradeIcons_lock");//"upgradeIcons_healthShield

                    income01 = new Image(images_upgrades, "upgradeIcons_incomeGold");
                    Image connecti01toi02 =  new Image(images_upgrades, "upgradeIcons_connect");
                    income02 = new Image(images_upgrades, "upgradeIcons_lock");//"upgradeIcons_incomeDiamonds"

                    attack01.setTouchable(Touchable.enabled);
                    attack02.setTouchable(Touchable.disabled);
                    attack03.setTouchable(Touchable.disabled);
                    attack04.setTouchable(Touchable.disabled);
                    attack05.setTouchable(Touchable.disabled);
                    health01.setTouchable(Touchable.enabled);
                    health02.setTouchable(Touchable.disabled);
                    health03.setTouchable(Touchable.disabled);
                    income01.setTouchable(Touchable.enabled);
                    income02.setTouchable(Touchable.disabled);

                    table_upgrade.add(attack01);
                    table_upgrade.add(connecta01toa02);
                    table_upgrade.add(attack02);
                    table_upgrade.add(connecta02toa03);
                    table_upgrade.add(attack03);
                    table_upgrade.add(connecta03toa04);
                    table_upgrade.add(attack04);
                    table_upgrade.add(connecta04toa05);
                    table_upgrade.add(attack05);
                    table_upgrade.row();
                    table_upgrade.add(health01);
                    table_upgrade.add(connecth01toh02);
                    table_upgrade.add(health02);
                    table_upgrade.add(connecth02toh03);
                    table_upgrade.add(health03);
                    table_upgrade.row();
                    table_upgrade.add(income01);
                    table_upgrade.add(connecti01toi02);
                    table_upgrade.add(income02);
                    stage.addActor(table_upgrade);
                    upgradeDialog.add(table_upgrade);
                    upgradeDialog.show(stage);
                    attack01.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            System.out.println("zostałem wybrany");
                            attack01.setDrawable(images_upgrades, "upgradeIcons_bought");
                            attack02.setDrawable(images_upgrades, "upgradeIcons_attackBattleAxe");
                            attack02.setTouchable(Touchable.enabled);
                            attack01.setTouchable(Touchable.disabled);

                        }
                    });
                    attack02.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            System.out.println("zostałem wybrany");
                            attack02.setDrawable(images_upgrades, "upgradeIcons_bought");
                            attack03.setDrawable(images_upgrades, "upgradeIcons_attackMace");
                            attack03.setTouchable(Touchable.enabled);
                            attack02.setTouchable(Touchable.disabled);

                        }
                    });
                    attack03.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            System.out.println("zostałem wybrany");
                            attack03.setDrawable(images_upgrades, "upgradeIcons_bought");
                            attack04.setDrawable(images_upgrades, "upgradeIcons_attackBow");
                            attack04.setTouchable(Touchable.enabled);
                            attack03.setTouchable(Touchable.disabled);

                        }
                    });
                    attack04.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            System.out.println("zostałem wybrany");
                            attack04.setDrawable(images_upgrades, "upgradeIcons_bought");
                            attack05.setDrawable(images_upgrades, "upgradeIcons_attackCrossbow");
                            attack05.setTouchable(Touchable.enabled);
                            attack04.setTouchable(Touchable.disabled);

                        }
                    });
                    attack05.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            System.out.println("zostałem wybrany");
                            attack05.setDrawable(images_upgrades, "upgradeIcons_bought");
                            attack05.setTouchable(Touchable.disabled);

                        }
                    });
                    health01.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            System.out.println("zostałem wybrany");
                            health01.setDrawable(images_upgrades, "upgradeIcons_bought");
                            health02.setDrawable(images_upgrades, "upgradeIcons_healthRegen");
                            health02.setTouchable(Touchable.enabled);
                            health01.setTouchable(Touchable.disabled);

                        }
                    });
                    health02.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            System.out.println("zostałem wybrany");
                            health02.setDrawable(images_upgrades, "upgradeIcons_bought");
                            health03.setDrawable(images_upgrades, "upgradeIcons_healthShield");
                            health03.setTouchable(Touchable.enabled);
                            health02.setTouchable(Touchable.disabled);

                        }
                    });
                    health03.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            System.out.println("zostałem wybrany");
                            health03.setDrawable(images_upgrades, "upgradeIcons_bought");
                            health03.setTouchable(Touchable.disabled);

                        }
                    });
                    income01.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            System.out.println("zostałem wybrany");
                            income01.setDrawable(images_upgrades, "upgradeIcons_bought");
                            income02.setDrawable(images_upgrades, "upgradeIcons_incomeDiamonds");
                            income02.setTouchable(Touchable.enabled);
                            income01.setTouchable(Touchable.disabled);

                        }
                    });
                    income02.addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            System.out.println("zostałem wybrany");
                            income02.setDrawable(images_upgrades, "upgradeIcons_bought");
                            income02.setTouchable(Touchable.disabled);

                        }
                    });
// ^
                    //wyłapuje tylko na textfieldach, a nie na całym table_profile
                }
            });

        }else{
            table_profile_01 = ProfileManager.createEmptyProfileTable(font, Gdx.graphics.getWidth()/10*2);
            table_profile_01.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    chosenProfileToCreate = 1;
                    newGameDialog.show(stage);
                }
            });
        }

        if(fileReader.fileExists("save/save02l.json")){
            save2 = fileReader.downloadSaveAsJSONObject("save/save02l.json");
            table_profile_02 = ProfileManager.createProfileTable(save2, font, languageManager, Gdx.graphics.getWidth()/10*4, "assets/icons/local.png");
            table_profile_02.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("zostałem wybrany");
                    //wyłapuje tylko na textfieldach, a nie na całym table_profile
                    GameState.setGameState(GameState.PLAYING);
                    game.setScreen(new GameScreen(game,save2));

                }
            });
        }else{
            table_profile_02 = ProfileManager.createEmptyProfileTable(font, Gdx.graphics.getWidth()/10*4);
            table_profile_02.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    chosenProfileToCreate = 2;
                    newGameDialog.show(stage);
                }
            });
        }

        if(fileReader.fileExists("save/save03l.json")){
            JSONObject save = fileReader.downloadSaveAsJSONObject("save/save03l.json");
            table_profile_01 = ProfileManager.createProfileTable(save, font, languageManager, Gdx.graphics.getWidth()/10*6, "assets/icons/local.png");
            table_profile_03.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("zostałem wybrany");
                    //wyłapuje tylko na textfieldach, a nie na całym table_profile
                }
            });
        }else{
            table_profile_03 = ProfileManager.createEmptyProfileTable(font, Gdx.graphics.getWidth()/10*6);
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

        //temp?
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
        if(isDialog){
            Gdx.gl.glClearColor(0,0,0,1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            table_profile_01.setVisible(false);
            table_profile_02.setVisible(false);
            table_profile_03.setVisible(false);
            table_default.setVisible(false);
            table_next.setVisible(false);
        }else{
            game.batch.draw(background, 0,0);
            table_profile_01.setVisible(true);
            table_profile_02.setVisible(true);
            table_profile_03.setVisible(true);
            table_default.setVisible(true);
            table_next.setVisible(true);
        }
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
        taDialog = new TextureAtlas("assets/dialog/skin_dialog.pack");//<- to delete
        taUpgrades = new TextureAtlas("assets/icons/upgrade_icons.pack");//<- to delete
        images_dialog = new Skin(taDialog);//<- to delete
        connectionManager = new ConnectionManager();
        images_default = new Skin(taButtonsDefault);
        images_empty = new Skin(taEmptyTextfield);
        image_profiles = new Skin(taButtonsProfile);
        images_settings = new Skin(new TextureAtlas("assets/buttons/buttons_settings.pack"));
        images_upgrades = new Skin(taUpgrades);
        table_default = new Table(images_default);
        table_next = new Table(images_default);
        table_Dialog = new Table(images_settings);
        table_profile_01 = new Table();
        table_profile_02 = new Table();
        table_profile_03 = new Table();
        table_upgrade = new Table();//<- to delete
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
