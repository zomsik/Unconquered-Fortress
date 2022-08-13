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
import com.game.Manager.ButtonStyleManager;
import com.game.Manager.ConnectionManager;
import com.game.Manager.FileReader;
import com.game.Manager.LanguageManager;
import com.game.State.GameState;
import org.json.JSONObject;

import java.io.IOException;

public class MenuScreen implements Screen  {
    private Main game;
    private Texture background;
    public TextButton bExit;
    public TextButton bLogin;
    public TextButton bPlay;
    public TextButton bSettings;
    public TextButton bCredits;
    public TextButton bDialogLogin, bDialogLoginRegister;
    public TextButton cDialogStayLogged, tDialogStayLogged;
    public TextField fDialogLoginLogin, fDialogLoginPassowrd, tDialogErrors;
    public TextButton bDialogExit;
    public TextButton bDialogRegister;
    public BitmapFont font, font2;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    public TextureAtlas buttonsAtlas, buttons_settings, buttons_default, dialog_back;
    public Skin images, images_settings, images_default;
    public Stage stage;
    public Table table_bExit, table_bPlay, table_bSettings, table_bLogin, table_bCredits, table_dialogLogin;
    private TextButton.TextButtonStyle textButtonStyle_tDialogStayLogged, textButtonStyle_bExit, textButtonStyle_bPlay, textButtonStyle_bSettings, textButtonStyle_bCredits, textButtonStyle_bLogin, textButtonStyle_bDialogLogin, textButtonStyle_cDialogStayLogged;

    private ConnectionManager connectionManager;
    private ButtonStyleManager buttonStyleManager;
    private LanguageManager languageManager;
    private FileReader fileReader;
    private Music backgroundMusic;
    private Dialog loginDialog;

    public boolean stayLogged = false;
    public boolean isLogged = false;




    public MenuScreen(Main game){
        this.game = game;
        initSettingsUI();
        buttonStyleManager = new ButtonStyleManager();
        fileReader = new FileReader();
        fileReader.downloadSettings();
        if(fileReader.getLanguageValue() != null){
            languageManager = new LanguageManager(fileReader.getLanguageValue());
        }else{
            languageManager = new LanguageManager("English");
        }

        buttonStyleManager.setTextButtonStyle(textButtonStyle_bLogin, images, font, "tempmainlog", "tempmainlog");
        bLogin = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bLogin"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bLogin));
        buttonStyleManager.setTextButtonStyle(textButtonStyle_bExit, images, font, "tempmain", "tempmain");
        bExit = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bExit"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bExit));
        buttonStyleManager.setTextButtonStyle(textButtonStyle_bPlay, images, font, "tempmain", "tempmain");
        bPlay = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bPlay"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bPlay));
        buttonStyleManager.setTextButtonStyle(textButtonStyle_bSettings, images, font, "tempmain", "tempmain");
        bSettings = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bSettings"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bSettings));
        buttonStyleManager.setTextButtonStyle(textButtonStyle_bCredits, images, font, "tempmain", "tempmain");
        bCredits = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bCredits"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bCredits));



        backgroundMusic.setVolume(fileReader.getVolumeValue()/100);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
    }
    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);
        bExit.setBounds(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight() - (Gdx.graphics.getHeight()/10*8+Gdx.graphics.getHeight()/100*7+8),Gdx.graphics.getWidth()/10*2, Gdx.graphics.getHeight()/100*7+8);
        //Ustawienie pozycji przycisków, skalowane z wymiarami okna
        table_bLogin.setBounds(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight() - (Gdx.graphics.getHeight()/10*2+Gdx.graphics.getHeight()/100*7+8),Gdx.graphics.getWidth()/100*16, Gdx.graphics.getHeight()/100*7+8);
        table_bCredits.setBounds(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight() - (Gdx.graphics.getHeight()/10*7+Gdx.graphics.getHeight()/100*7+8),Gdx.graphics.getWidth()/10*2, Gdx.graphics.getHeight()/100*7+8);
        table_bExit.setBounds(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight() - (Gdx.graphics.getHeight()/10*8+Gdx.graphics.getHeight()/100*7+8),Gdx.graphics.getWidth()/10*2, Gdx.graphics.getHeight()/100*7+8);
        table_bPlay.setBounds(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight() - (Gdx.graphics.getHeight()/10*5+Gdx.graphics.getHeight()/100*7+8),Gdx.graphics.getWidth()/10*2, Gdx.graphics.getHeight()/100*7+8);
        table_bSettings.setBounds(Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight() - (Gdx.graphics.getHeight()/10*6+Gdx.graphics.getHeight()/100*7+8),Gdx.graphics.getWidth()/10*2, Gdx.graphics.getHeight()/100*7+8);

        bExit.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y){
               Gdx.app.exit();
           }
        });




        buttonStyleManager.setTextButtonStyle(textButtonStyle_bDialogLogin, images_default, font2, "defaultButton", "defaultButton");
        bDialogLogin = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bLogin"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bDialogLogin));
        bDialogLoginRegister = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bRegister"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bDialogLogin));


        buttonStyleManager.setTextButtonStyle(textButtonStyle_tDialogStayLogged, images_settings, font2, "empty_background", "empty_background");
        tDialogStayLogged = new TextButton(languageManager.getValue(languageManager.getLanguage(), "cDialogStayLogged"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_tDialogStayLogged));
        tDialogStayLogged.setTouchable(Touchable.disabled);




        buttonStyleManager.setTextButtonStyle(textButtonStyle_cDialogStayLogged, images_settings, font2, "checkbox_off", "checkbox_off");
        cDialogStayLogged = new TextButton(null, buttonStyleManager.returnTextButtonStyle(textButtonStyle_cDialogStayLogged));

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.fontColor = Color.BLACK;
        textFieldStyle.font = font;


        TextField tLoginDialogTitle = new TextField("Okno logowania", textFieldStyle);
        tLoginDialogTitle.setDisabled(true);
        tLoginDialogTitle.setAlignment(Align.right);
        tLoginDialogTitle.setSize(150,30);

        TextField.TextFieldStyle textFieldStyle2 = new TextField.TextFieldStyle();
        textFieldStyle2.fontColor = Color.WHITE;
        textFieldStyle2.font = font2;
        fDialogLoginLogin = new TextField(null, textFieldStyle2);
        fDialogLoginPassowrd = new TextField(null, textFieldStyle2);
        fDialogLoginPassowrd.setPasswordMode(true);
        fDialogLoginPassowrd.setPasswordCharacter('*');


        TextField.TextFieldStyle textFieldStyle3 = new TextField.TextFieldStyle();
        textFieldStyle3.fontColor = Color.RED;
        textFieldStyle3.font = font2;
        tDialogErrors = new TextField(null,textFieldStyle3);
        tDialogErrors.setDisabled(true);

        Texture bg = new Texture(new FileHandle("assets/dialog/skin_dialog.png"));
        loginDialog = new Dialog("", new Window.WindowStyle(font, Color.WHITE, new TextureRegionDrawable(new TextureRegion(bg)))) {
            public void result(Object obj) {
                loginDialog.cancel();
            }
        };

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);



        //loginDialog.text("Okno logowania\n",labelStyle).align(Align.top);

        labelStyle = new Label.LabelStyle(font2, Color.WHITE);


        loginDialog.getButtonTable().debug();

        table_dialogLogin.setWidth(350);
        table_dialogLogin.setX(200);
        table_dialogLogin.setY(300);
        table_dialogLogin.row().colspan(2);
        table_dialogLogin.add(tLoginDialogTitle).expand().fillX().align(Align.top);

        table_dialogLogin.row().colspan(2);;

        table_dialogLogin.add(fDialogLoginLogin).pad(10).align(Align.center);
        table_dialogLogin.row().colspan(2);
        table_dialogLogin.add(fDialogLoginPassowrd).pad(10).align(Align.center);
        table_dialogLogin.row();
        table_dialogLogin.add(cDialogStayLogged);
        table_dialogLogin.add(tDialogStayLogged);
        table_dialogLogin.row().colspan(2);
        table_dialogLogin.add(tDialogErrors);
        //table_dialogLogin.row();
        //table_dialogLogin.add(bDialogLogin);

        loginDialog.button(bDialogLogin);
        loginDialog.button(bDialogLoginRegister);
        table_dialogLogin.debug();

        loginDialog.addActor(table_dialogLogin);


        bLogin.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (isLogged) {
                    bLogin.setText(languageManager.getValue(languageManager.getLanguage(), "bLogin"));
                    isLogged = false;
                    return;
                }

                loginDialog.show(stage);

            }
        });

        cDialogStayLogged.addListener(new ClickListener(){

            @Override
            //public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            public void clicked(InputEvent event, float x, float y){
                stayLogged = !stayLogged;
                if (!stayLogged)
                {
                    buttonStyleManager.setTextButtonStyle(textButtonStyle_cDialogStayLogged, images_settings, font2, "checkbox_off", "checkbox_off");
                    cDialogStayLogged = new TextButton(languageManager.getValue(languageManager.getLanguage(), "cDialogStayLogged"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_cDialogStayLogged));
                }
                else
                {
                    buttonStyleManager.setTextButtonStyle(textButtonStyle_cDialogStayLogged, images_settings, font2, "checkbox_on", "checkbox_on");
                    cDialogStayLogged = new TextButton(languageManager.getValue(languageManager.getLanguage(), "cDialogStayLogged"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_cDialogStayLogged));
                }



            }
        });


        bDialogLogin.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

                if (fDialogLoginLogin.getText().isEmpty() || fDialogLoginPassowrd.getText().isEmpty())
                {
                    tDialogErrors.setText(languageManager.getValue(languageManager.getLanguage(), "ErrorEmptyFields"));
                    return;
                }
                if (fDialogLoginLogin.getText().length() < 5 || fDialogLoginLogin.getText().length() > 20 )
                {
                    tDialogErrors.setText(languageManager.getValue(languageManager.getLanguage(), "ErrorLengthOfLogin"));
                    return;
                }
                if (fDialogLoginPassowrd.getText().length() < 5 || fDialogLoginPassowrd.getText().length() > 20 )
                {
                    tDialogErrors.setText(languageManager.getValue(languageManager.getLanguage(), "ErrorLengthOfPassword"));
                    return;
                }

                JSONObject loginData = new JSONObject();

                loginData.put("login",fDialogLoginLogin.getText());
                loginData.put("password",fDialogLoginPassowrd.getText());
                JSONObject response = new JSONObject();
                try {
                    response = connectionManager.requestSend(loginData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (response.getInt("status") == 200)
                {
                    bLogin.setText(languageManager.getValue(languageManager.getLanguage(), "bLogout"));
                    isLogged = true;
                    loginDialog.hide();
                }
                else
                {
                    tDialogErrors.setText(languageManager.getValue(languageManager.getLanguage(), response.getString("message")));
                }


            }
        });





        bSettings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameState.SetPreviousGameState(GameState.MENU);
                game.setScreen(new SettingsScreen(game));
                dispose();
            }
        });


        bCredits.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameState.SetPreviousGameState(GameState.CREDITS);
                game.setScreen(new CreditsScreen(game));
                backgroundMusic.dispose();
                dispose();
            }
        });

        table_bExit.add(bExit).width(Gdx.graphics.getWidth()/10*2).height(Gdx.graphics.getHeight()/100*7+8);
        table_bPlay.add(bPlay).width(Gdx.graphics.getWidth()/10*2).height(Gdx.graphics.getHeight()/100*7+8);
        table_bLogin.add(bLogin).width(Gdx.graphics.getWidth()/100*16).height(Gdx.graphics.getHeight()/100*7+8);
        table_bSettings.add(bSettings).width(Gdx.graphics.getWidth()/10*2).height(Gdx.graphics.getHeight()/100*7+8);
        table_bCredits.add(bCredits).width(Gdx.graphics.getWidth()/10*2).height(Gdx.graphics.getHeight()/100*7+8);

        table_bExit.debug();
        table_bPlay.debug();
        //table_bLogin.debug();
        table_bSettings.debug();
        table_bCredits.debug();

        stage.addActor(table_bCredits);
        stage.addActor(table_bLogin);
        stage.addActor(table_bExit);
        stage.addActor(table_bLogin);
        stage.addActor(table_bSettings);
        stage.addActor(table_bPlay);

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
        background = new Texture("background_menu.png");
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Silkscreen.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        stage = new Stage();
        parameter.size = 32;
        parameter.color = Color.BLACK;
        parameter.characters = "ąćęłóśżźabcdefghijklmnopqrstuvwxyzĄĆĘÓŁŚŻŹABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        font = new BitmapFont();
        font = generator.generateFont(parameter);

        parameter.size = 15;
        parameter.color = Color.WHITE;
        font2 = new BitmapFont();
        font2 = generator.generateFont(parameter);

        connectionManager = new ConnectionManager();
        buttonsAtlas = new TextureAtlas("assets/buttons/buttons_menu.pack");
        buttons_default = new TextureAtlas("assets/buttons/buttons_default.pack");
        buttons_settings = new TextureAtlas("assets/buttons/buttons_settings.pack");
        dialog_back = new TextureAtlas("assets/dialog/skin_dialog.pack");
        images = new Skin(buttonsAtlas);
        images_settings = new Skin(buttons_settings);
        images_default = new Skin(buttons_default);
        table_bExit = new Table(images);
        table_bPlay = new Table(images);
        table_bLogin = new Table(images);
        table_bCredits = new Table(images);
        table_bSettings = new Table(images);
        table_dialogLogin = new Table(images_settings);
        textButtonStyle_bExit = new TextButton.TextButtonStyle();
        textButtonStyle_bPlay = new TextButton.TextButtonStyle();
        textButtonStyle_bSettings = new TextButton.TextButtonStyle();
        textButtonStyle_bCredits = new TextButton.TextButtonStyle();
        textButtonStyle_bLogin = new TextButton.TextButtonStyle();
        textButtonStyle_bDialogLogin = new TextButton.TextButtonStyle();
        textButtonStyle_cDialogStayLogged = new TextButton.TextButtonStyle();
        textButtonStyle_tDialogStayLogged = new TextButton.TextButtonStyle();
        backgroundMusic = game.getMusic();

    }
}
