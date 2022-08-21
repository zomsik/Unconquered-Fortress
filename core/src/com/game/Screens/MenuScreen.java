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
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.game.Entity.Upgrades;
import com.game.Main;
import com.game.Manager.*;
import com.game.State.GameState;
import org.json.JSONObject;

import javax.swing.*;
import java.util.Objects;

public class MenuScreen implements Screen  {
    private Main game;
    private Texture background, backgroundForDialog;
    public TextButton bExit, bLogin ,bPlay, bSettings, bCredits;

    public TextButton bDialogLogin, bDialogLoginRegister, cDialogStayLogged, bDialogExit, bDialogExit2;
    public TextField tDialogLoginTextTitle, tDialogLoginTextLogin, tDialogLoginTextPassword, tDialogLoginStayLogged, tDialogLoginErrors;
    public TextField fDialogLoginLogin, fDialogLoginPassword;
    public TextButton bDialogRegister, bDialogRegisterLogin;
    public TextField tDialogRegisterTextTitle, tDialogRegisterTextLogin, tDialogRegisterTextMail, tDialogRegisterTextPassword, tDialogRegisterTextRepeatPassword, tDialogRegisterErrors;
    public TextField fDialogRegisterLogin, fDialogRegisterMail, fDialogRegisterPassword, fDialogRegisterRepeatPassword;

    public BitmapFont fontTitle, fontText;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    public TextureAtlas taButtonsAtlas, taButtonsSettings, taButtonsDefault, taDialogBack;
    public Skin images, images_settings, images_default;
    public Stage stage;
    public Table table_bExit, table_bPlay, table_bSettings, table_bLogin, table_bCredits, table_dialogLogin, table_dialogRegister;
    private TextButton.TextButtonStyle textButtonStyle_bDialogExit, textButtonStyle_tDialogStayLogged, textButtonStyle_bExit, textButtonStyle_bPlay, textButtonStyle_bSettings, textButtonStyle_bCredits, textButtonStyle_bLogin, textButtonStyle_bDialogLogin, textButtonStyle_cDialogStayLogged;
    private TextField.TextFieldStyle textFieldStyle, formTextFieldStyle, titleTextFieldStyle, errorTextFieldStyle;
    private ConnectionManager connectionManager;
    private ButtonStyleManager buttonStyleManager;
    private TextFieldStyleManager textFieldStyleManager;
    private LanguageManager languageManager;
    private FileReader fileReader;
    private Music backgroundMusic;
    private Dialog menuDialog;

    public boolean stayLogged = false;

    public boolean isDialog = false;

    private TextButton bUpgrade;
    private Table table_upgrade, table_upgradeButton;
    private Dialog upgradeDialog;
    private Skin images_upgrades;
    private TextureAtlas taUpgrades;
    private Image fork, scythe, dagger, sword, battleAxe, mace, sceptre, book, bow, crossbow, cannon, betterCannon, cannonBall;
    private Image sonar, gear;
    private Image health, betterHealth, betterBetterHealth, shield, regeneration;
    private Image gold, diamonds, betterGold, betterDiamonds, upgrade, betterUpgrade, hammer, discount10, discount20, discount30;
    private Image luck;
    private Upgrades uFork, uScythe, uDagger, uSword, uBattleAxe, uMace, uBow, uCrossBow, uCannon, uCannonBall, uBetterCannon, uSceptre, uBook, uGear, uSonar, uHealth, uBetterHealth, uBetterBetterHealth, uRegeneration, uShield, uGold, uBetterGold, uDiamonds, uBetterDiamonds, uDiscount10, uDiscount20, uDiscount30, uUpgrade, uHammer, uBetterUpgrade, uLuck;
    private UpgradeManager upgradeManager;
    private TextTooltip.TextTooltipStyle textTooltipStyle;
    private TextTooltip tooltip;
    public MenuScreen(Main game){
        this.game = game;
        initSettingsUI();
        buttonStyleManager = new ButtonStyleManager();
        textFieldStyleManager = new TextFieldStyleManager();
        fileReader = new FileReader();
        fileReader.downloadSettings();
        if(fileReader.getLanguageValue() != null){
            languageManager = new LanguageManager(fileReader.getLanguageValue());
        }else{
            languageManager = new LanguageManager("English");
        }

        fileReader.downloadUserInfo();
        buttonStyleManager.setTextButtonStyle(textButtonStyle_bLogin, images, fontTitle, "tempmainlog", "tempmainlog");
        if (fileReader.getTokenValue()!=null || game.getIsLogged())
        {
            game.setIsLogged(true);
            if (fileReader.getTokenValue()!=null)
                game.setLogin(fileReader.getLoginFromToken());

            bLogin = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bLogout"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bLogin));

        }
        else
        {
            bLogin = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bLogin"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bLogin));

        }



        buttonStyleManager.setTextButtonStyle(textButtonStyle_bExit, images, fontTitle, "tempmain", "tempmain");
        bExit = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bExit"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bExit));
        buttonStyleManager.setTextButtonStyle(textButtonStyle_bPlay, images, fontTitle, "tempmain", "tempmain");
        bPlay = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bPlay"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bPlay));
        buttonStyleManager.setTextButtonStyle(textButtonStyle_bSettings, images, fontTitle, "tempmain", "tempmain");
        bSettings = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bSettings"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bSettings));
        buttonStyleManager.setTextButtonStyle(textButtonStyle_bCredits, images, fontTitle, "tempmain", "tempmain");
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



        buttonStyleManager.setTextButtonStyle(textButtonStyle_bDialogLogin, images_default, fontText, "defaultButton", "defaultButton");
        bDialogLogin = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bLogin"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bDialogLogin));
        bDialogLoginRegister = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bRegister"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bDialogLogin));
        bDialogRegister = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bRegister"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bDialogLogin));
        bDialogRegisterLogin = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bLogin"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bDialogLogin));

        bUpgrade = new TextButton("Upgrades", buttonStyleManager.returnTextButtonStyle(textButtonStyle_bDialogLogin));
        table_upgradeButton.setBounds(0,0,100,100);
        table_upgradeButton.add(bUpgrade);
        stage.addActor(table_upgradeButton);

        bUpgrade.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isDialog = true;
                Texture bg;
                if(Gdx.graphics.getWidth() < 721){
                    bg = new Texture(new FileHandle("assets/dialog/upgrade_dialog_720.png"));
                }else{
                    bg = new Texture(new FileHandle("assets/dialog/upgrade_dialog.png"));
                }
                upgradeDialog = new Dialog("", new Window.WindowStyle(fontText, Color.WHITE, new TextureRegionDrawable(new TextureRegion(bg)))) {
                    public void result(Object obj) {
                        System.out.println("result " + obj);
                    }
                };
                Label.LabelStyle labelStyle = new Label.LabelStyle(fontText, Color.WHITE);
                upgradeManager = new UpgradeManager(images_upgrades, languageManager);
                upgradeManager.setUpgradeTable(table_upgrade);
                upgradeManager.setUpgradeDialog(upgradeDialog, table_upgrade, labelStyle);
                initUpgrades();

                stage.addActor(upgradeManager.returnUpgradeTable());
                upgradeDialog.add(upgradeManager.returnUpgradeTable());
                upgradeDialog.show(stage);
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
                upgradeManager.getFork().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        uFork.setLevel(uFork.getLevel()+1, 3, upgradeManager.getFork());
                        uFork.setDamagemultiplayer(uFork.getLevel());
                        if(uFork.getLevel() == 1){
                            uScythe.setLevel(0, 3, upgradeManager.getScythe());
                            uScythe.setDamagemultiplayer(uScythe.getLevel());
                            upgradeManager.getScythe().setDrawable(images_upgrades, "upgradeIcons_attackScythe");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information = new Label( "Upgrade: " + uFork.getUpgradeName() + "\nLevel:" + uFork.getLevel() + "/3" + "\nDamage multiplayer: " + uFork.getDamageMultiplayer(),labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -72, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getScythe().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uScythe.getLevel()>=0){
                            uScythe.setLevel(uScythe.getLevel()+1, 1, upgradeManager.getScythe());
                            uScythe.setDamagemultiplayer(uScythe.getLevel());
                        }
                        if(uScythe.getLevel() == 1) {
                            uDagger.setLevel(0, 3, upgradeManager.getDagger());
                            uDagger.setDamagemultiplayer(uScythe.getLevel());
                            upgradeManager.getDagger().setDrawable(images_upgrades, "upgradeIcons_attackDagger");
                            uSceptre.setLevel(0,3,upgradeManager.getSceptre());
                            uSceptre.setDamagemultiplayer(uSceptre.getLevel());
                            upgradeManager.getSceptre().setDrawable(images_upgrades, "upgradeIcons_attackSceptre");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        if(uScythe.getLevel()<0){
                            information = new Label("Najpierw musisz odblować poprzednie ulepszenie.",labelStyle);
                        }else{
                            information = new Label( "Upgrade: " + uScythe.getUpgradeName() + "\nLevel:" + uScythe.getLevel() + "/1" + "\nDamage multiplayer: " + uScythe.getDamageMultiplayer(),labelStyle);
                        }
                        tooltip = new TextTooltip("Działam", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -168, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);

                    }
                });
                upgradeManager.getDagger().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uDagger.getLevel()>=0){
                            uDagger.setLevel(uDagger.getLevel()+1, 3, upgradeManager.getDagger());
                            uDagger.setDamagemultiplayer(uDagger.getLevel());
                        }
                        if(uDagger.getLevel() == 1){
                            uSword.setLevel(0,3,upgradeManager.getSword());
                            uSword.setDamagemultiplayer(uSword.getLevel());
                            upgradeManager.getSword().setDrawable(images_upgrades, "upgradeIcons_attackSword");
                            uBow.setLevel(0,3,upgradeManager.getBow());
                            uBow.setDamagemultiplayer(uBow.getLevel());
                            upgradeManager.getBow().setDrawable(images_upgrades, "upgradeIcons_attackBow");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        if(uDagger.getLevel()<0){
                            information = new Label("Najpierw musisz odblować poprzednie ulepszenie.",labelStyle);
                        }else{
                            information = new Label( "Upgrade: " + uDagger.getUpgradeName() + "\nLevel:" + uDagger.getLevel() + "/3" + "\nDamage multiplayer: " + uDagger.getDamageMultiplayer(),labelStyle);
                        }
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -264, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }

                });
                upgradeManager.getSword().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uSword.getLevel()>=0){
                            uSword.setLevel(uSword.getLevel()+1, 3, upgradeManager.getSword());
                            uSword.setDamagemultiplayer(uSword.getLevel());
                        }
                        if(uSword.getLevel() == 1){
                            uBattleAxe.setLevel(0,1,upgradeManager.getBattleAxe());
                            uBattleAxe.setDamagemultiplayer(uBattleAxe.getLevel());
                            upgradeManager.getBattleAxe().setDrawable(images_upgrades, "upgradeIcons_attackBattleAxe");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        if(uSword.getLevel()<0){
                            information = new Label("Najpierw musisz odblować poprzednie ulepszenie.",labelStyle);
                        }else{
                            information = new Label( "Upgrade: " + uSword.getUpgradeName() + "\nLevel:" + uSword.getLevel() + "/3" + "\nDamage multiplayer: " + uSword.getDamageMultiplayer(),labelStyle);
                        }
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -(upgradeDialog.getWidth()/2)+256, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getBattleAxe().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uBattleAxe.getLevel()>=0){
                            uBattleAxe.setLevel(uBattleAxe.getLevel()+1, 1, upgradeManager.getBattleAxe());
                            uBattleAxe.setDamagemultiplayer(uBattleAxe.getLevel());
                        }
                        if(uBattleAxe.getLevel() == 1) {
                            uMace.setLevel(0, 3, mace);
                            uMace.setDamagemultiplayer(uMace.getLevel());
                            upgradeManager.getMace().setDrawable(images_upgrades, "upgradeIcons_attackMace");
                            uCannon.setLevel(0,3,cannon);
                            uCannon.setDamagemultiplayer(uCannon.getLevel());
                            upgradeManager.getCannon().setDrawable(images_upgrades, "upgradeIcons_attackCannon");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        if(uBattleAxe.getLevel()<0){
                            information = new Label("Najpierw musisz odblować poprzednie ulepszenie.",labelStyle);
                        }else{
                            information = new Label( "Upgrade: " + uBattleAxe.getUpgradeName() + "\nLevel:" + uBattleAxe.getLevel() + "/1" + "\nDamage multiplayer: " + uBattleAxe.getDamageMultiplayer(),labelStyle);
                        }
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -(upgradeDialog.getWidth()/2)+160, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);

                    }
                });
                upgradeManager.getMace().addListener(new ClickListener(){

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uMace.getLevel()>=0){
                            uMace.setLevel(uMace.getLevel()+1, 3, upgradeManager.getMace());
                            uMace.setDamagemultiplayer(uMace.getLevel());
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        if(uMace.getLevel()<0){
                            information = new Label("Najpierw musisz odblować poprzednie ulepszenie.",labelStyle);
                        }else{
                            information = new Label( "Upgrade: " + uMace.getUpgradeName() + "\nLevel:" + uMace.getLevel() + "/3" + "\nDamage multiplayer: " + uMace.getDamageMultiplayer(),labelStyle);
                        }
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        System.out.println(x);
                        tooltip.enter(event, -(upgradeDialog.getWidth()/2)+64, 0, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                        tooltip.exit(event, x,y , pointer, fromActor);

                    }

                });

                upgradeManager.getBow().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uBow.getLevel()>=0){
                            uBow.setLevel(uBow.getLevel()+1, 3, upgradeManager.getBow());
                            uBow.setDamagemultiplayer(uBow.getLevel());
                        }
                        if(uBow.getLevel() == 1){
                            uCrossBow.setLevel(0,3,upgradeManager.getCrossbow());
                            uCrossBow.setDamagemultiplayer(uCrossBow.getLevel());
                            upgradeManager.getCrossbow().setDrawable(images_upgrades, "upgradeIcons_attackCrossbow");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        if(uBow.getLevel()<0){
                            information = new Label("Najpierw musisz odblować poprzednie ulepszenie.",labelStyle);
                        }else{
                            information = new Label( "Upgrade: " + uBow.getUpgradeName() + "\nLevel:" + uBow.getLevel() + "/3" + "\nDamage multiplayer: " + uBow.getDamageMultiplayer(),labelStyle);
                        }
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -264, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getCrossbow().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uCrossBow.getLevel()>=0){
                            uCrossBow.setLevel(uCrossBow.getLevel()+1, 3, upgradeManager.getCrossbow());
                            uCrossBow.setDamagemultiplayer(uCrossBow.getLevel());
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        if(uCrossBow.getLevel()<0){
                            information = new Label("Najpierw musisz odblować poprzednie ulepszenie.",labelStyle);
                        }else{
                            information = new Label( "Upgrade: " + uCrossBow.getUpgradeName() + "\nLevel:" + uCrossBow.getLevel() + "/3" + "\nDamage multiplayer: " + uCrossBow.getDamageMultiplayer(),labelStyle);
                        }
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -(upgradeDialog.getWidth()/2)+256, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getCannon().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uCannon.getLevel()>=0){
                            uCannon.setLevel(uCannon.getLevel()+1, 3, upgradeManager.getCannon());
                            uCannon.setDamagemultiplayer(uCannon.getLevel());
                        }
                        if(uCannon.getLevel() == 1) {
                            uCannonBall.setLevel(0, 3, upgradeManager.getCannonBall());
                            uCannonBall.setDamagemultiplayer(uCannonBall.getLevel());
                            upgradeManager.getCannonBall().setDrawable(images_upgrades, "upgradeIcons_attackCannonBall");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        if(uCannon.getLevel()<0){
                            information = new Label("Najpierw musisz odblować poprzednie ulepszenie.",labelStyle);
                        }else{
                            information = new Label( "Upgrade: " + uCannon.getUpgradeName() + "\nLevel:" + uCannon.getLevel() + "/1" + "\nDamage multiplayer: " + uCannon.getDamageMultiplayer(),labelStyle);
                        }
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -(upgradeDialog.getWidth()/2)+160, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);

                    }
                });
                upgradeManager.getCannonBall().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uCannonBall.getLevel()>=0){
                            uCannonBall.setLevel(uCannonBall.getLevel()+1, 3, upgradeManager.getCannonBall());
                            uCannonBall.setDamagemultiplayer(uCannonBall.getLevel());
                        }
                        if(uCannonBall.getLevel() == 1) {
                            uBetterCannon.setLevel(0, 3, upgradeManager.getBetterCannon());
                            uBetterCannon.setDamagemultiplayer(uBetterCannon.getLevel());
                            upgradeManager.getBetterCannon().setDrawable(images_upgrades, "upgradeIcons_attackBetterCannon");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        if(uCannonBall.getLevel()<0){
                            information = new Label("Najpierw musisz odblować poprzednie ulepszenie.",labelStyle);
                        }else{
                            information = new Label( "Upgrade: " + uCannonBall.getUpgradeName() + "\nLevel:" + uCannonBall.getLevel() + "/3" + "\nDamage multiplayer: " + uCannonBall.getDamageMultiplayer(),labelStyle);
                        }
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        System.out.println(x);
                        tooltip.enter(event, -(upgradeDialog.getWidth()/2)+64, 0, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                        tooltip.exit(event, x,y , pointer, fromActor);

                    }
                });
                upgradeManager.getBetterCannon().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uBetterCannon.getLevel()>=0){
                            uBetterCannon.setLevel(uBetterCannon.getLevel()+1, 3, upgradeManager.getBetterCannon());
                            uBetterCannon.setDamagemultiplayer(uBetterCannon.getLevel());
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        if(uBetterCannon.getLevel()<0){
                            information = new Label("Najpierw musisz odblować poprzednie ulepszenie.",labelStyle);
                        }else{
                            information = new Label( "Upgrade: " + uBetterCannon.getUpgradeName() + "\nLevel:" + uBetterCannon.getLevel() + "/3" + "\nDamage multiplayer: " + uBetterCannon.getDamageMultiplayer(),labelStyle);
                        }
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        System.out.println(x);
                        tooltip.enter(event, -(upgradeDialog.getWidth()/2+32), 0, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                        tooltip.exit(event, x,y , pointer, fromActor);

                    }
                });
                upgradeManager.getSceptre().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uSceptre.getLevel()>=0){
                            uSceptre.setLevel(uSceptre.getLevel()+1, 3, upgradeManager.getSceptre());
                            uSceptre.setDamagemultiplayer(uSceptre.getLevel());
                        }
                        if(uSceptre.getLevel() == 1) {
                            uBook.setLevel(0, 3, upgradeManager.getBook());
                            uBook.setDamagemultiplayer(uBook.getLevel());
                            upgradeManager.getBook().setDrawable(images_upgrades, "upgradeIcons_attackBook");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        if(uSceptre.getLevel()<0){
                            information = new Label("Najpierw musisz odblować poprzednie ulepszenie.",labelStyle);
                        }else{
                            information = new Label( "Upgrade: " + uSceptre.getUpgradeName() + "\nLevel:" + uSceptre.getLevel() + "/1" + "\nDamage multiplayer: " + uSceptre.getDamageMultiplayer(),labelStyle);
                        }
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        System.out.println(upgradeDialog.getWidth());
                        tooltip.enter(event, -168, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getBook().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uBook.getLevel()>=0){
                            uBook.setLevel(uBook.getLevel()+1, 3, upgradeManager.getBook());
                            uBook.setDamagemultiplayer(uBook.getLevel());
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        if(uBook.getLevel()<0){
                            information = new Label("Najpierw musisz odblować poprzednie ulepszenie.",labelStyle);
                        }else{
                            information = new Label( "Upgrade: " + uBook.getUpgradeName() + "\nLevel:" + uBook.getLevel() + "/3" + "\nDamage multiplayer: " + uBook.getDamageMultiplayer(),labelStyle);
                        }
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -264, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });

                upgradeManager.getGear().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        uGear.setLevel(uGear.getLevel()+1, 3, upgradeManager.getGear());
                        uGear.setDamagemultiplayer(uGear.getLevel());
                        if(uGear.getLevel() == 1){
                            uSonar.setLevel(0, 3, upgradeManager.getSonar());
                            uSonar.setDamagemultiplayer(uSonar.getLevel());
                            upgradeManager.getSonar().setDrawable(images_upgrades, "upgradeIcons_attackSonar");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information = new Label( "Upgrade: " + uGear.getUpgradeName() + "\nLevel:" + uGear.getLevel() + "/3" + "\nDamage multiplayer: " + uGear.getDamageMultiplayer(),labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -72, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                upgradeManager.getSonar().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(uSonar.getLevel()>=0){
                            uSonar.setLevel(uSonar.getLevel()+1, 1, upgradeManager.getSonar());
                            uSonar.setDamagemultiplayer(uSonar.getLevel());
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information;
                        if(uSonar.getLevel()<0){
                            information = new Label("Najpierw musisz odblować poprzednie ulepszenie.",labelStyle);
                        }else{
                            information = new Label( "Upgrade: " + uSonar.getUpgradeName() + "\nLevel:" + uSonar.getLevel() + "/1" + "\nDamage multiplayer: " + uSonar.getDamageMultiplayer(),labelStyle);
                        }
                        tooltip = new TextTooltip("Działam", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -168, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);

                    }
                });
                upgradeManager.getHealth().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        uHealth.setLevel(uHealth.getLevel()+1, 3, upgradeManager.getHealth());
                        uHealth.setDamagemultiplayer(uHealth.getLevel());
                        if(uHealth.getLevel() == 1){
                            uBetterCannon.setLevel(0, 3, upgradeManager.getBetterHealth());
                            uBetterHealth.setDamagemultiplayer(uBetterHealth.getLevel());
                            upgradeManager.getBetterHealth().setDrawable(images_upgrades, "upgradeIcons_healthBetterHealth");
                        }
                    }
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        Label information = new Label( "Upgrade: " + uHealth.getUpgradeName() + "\nLevel:" + uHealth.getLevel() + "/3" + "\nDamage multiplayer: " + uHealth.getDamageMultiplayer(),labelStyle);
                        tooltip = new TextTooltip("", textTooltipStyle);
                        tooltip.setActor(information);
                        tooltip.setInstant(true);
                        tooltip.enter(event, -72, y, pointer, fromActor);
                    }
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);
                    }
                });
                /*upgradeManager.getBetterHealth().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        betterHealth.setDrawable(images_upgrades, "upgradeIcons_bought");
                        regeneration.setDrawable(images_upgrades, "upgradeIcons_healthRegen");
                        regeneration.setTouchable(Touchable.enabled);
                        betterHealth.setTouchable(Touchable.disabled);

                    }
                });
                upgradeManager.getRegeneration().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        regeneration.setDrawable(images_upgrades, "upgradeIcons_bought");
                        shield.setDrawable(images_upgrades, "upgradeIcons_healthShield");
                        shield.setTouchable(Touchable.enabled);
                        regeneration.setTouchable(Touchable.disabled);

                    }
                });

                upgradeManager.getShield().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        shield.setDrawable(images_upgrades, "upgradeIcons_bought");
                        betterBetterHealth.setDrawable(images_upgrades, "upgradeIcons_healthBetterBetterHealth");
                        betterBetterHealth.setTouchable(Touchable.enabled);
                        shield.setTouchable(Touchable.disabled);

                    }
                });
                upgradeManager.getBetterBetterHealth().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        betterBetterHealth.setDrawable(images_upgrades, "upgradeIcons_bought");
                        betterBetterHealth.setTouchable(Touchable.disabled);

                    }
                });
                upgradeManager.getGold().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        gold.setDrawable(images_upgrades, "upgradeIcons_bought");
                        betterGold.setDrawable(images_upgrades, "upgradeIcons_incomeBetterGold");
                        betterGold.setTouchable(Touchable.enabled);
                        gold.setTouchable(Touchable.disabled);

                    }
                });
                upgradeManager.getBetterGold().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        betterGold.setDrawable(images_upgrades, "upgradeIcons_bought");
                        diamonds.setDrawable(images_upgrades, "upgradeIcons_incomeDiamonds");
                        discount10.setDrawable(images_upgrades, "upgradeIcons_incomeDiscountTen");
                        discount10.setTouchable(Touchable.enabled);
                        diamonds.setTouchable(Touchable.enabled);
                        betterGold.setTouchable(Touchable.disabled);

                    }
                });
                upgradeManager.getDiamonds().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        diamonds.setDrawable(images_upgrades, "upgradeIcons_bought");
                        betterDiamonds.setDrawable(images_upgrades, "upgradeIcons_incomeBetterDiamonds");
                        betterDiamonds.setTouchable(Touchable.enabled);
                        diamonds.setTouchable(Touchable.disabled);

                    }
                });
                upgradeManager.getBetterDiamonds().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        betterDiamonds.setDrawable(images_upgrades, "upgradeIcons_bought");
                        betterDiamonds.setTouchable(Touchable.disabled);

                    }
                });
                upgradeManager.getDiscount10().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        discount10.setDrawable(images_upgrades, "upgradeIcons_bought");
                        discount20.setDrawable(images_upgrades, "upgradeIcons_incomeDiscountTwenty");
                        upgrade.setDrawable(images_upgrades, "upgradeIcons_incomeUpgrade");
                        upgrade.setTouchable(Touchable.enabled);
                        discount20.setTouchable(Touchable.enabled);
                        discount10.setTouchable(Touchable.disabled);

                    }
                });
                upgradeManager.getDiscount20().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        discount20.setDrawable(images_upgrades, "upgradeIcons_bought");
                        discount30.setDrawable(images_upgrades, "upgradeIcons_incomeDiscountThirty");
                        discount30.setTouchable(Touchable.enabled);
                        discount20.setTouchable(Touchable.disabled);

                    }
                });
                upgradeManager.getDiscount30().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        discount30.setDrawable(images_upgrades, "upgradeIcons_bought");
                        discount30.setTouchable(Touchable.disabled);

                    }
                });
                upgradeManager.getUpgrade().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        upgrade.setDrawable(images_upgrades, "upgradeIcons_bought");
                        hammer.setDrawable(images_upgrades, "upgradeIcons_incomeHammer");
                        hammer.setTouchable(Touchable.enabled);
                        upgrade.setTouchable(Touchable.disabled);

                    }
                });
                upgradeManager.getHammer().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        hammer.setDrawable(images_upgrades, "upgradeIcons_bought");
                        betterUpgrade.setDrawable(images_upgrades, "upgradeIcons_incomeBetterUpgrade");
                        luck.setDrawable(images_upgrades, "upgradeIcons_incomeLuck");
                        luck.setTouchable(Touchable.enabled);
                        betterUpgrade.setTouchable(Touchable.enabled);
                        hammer.setTouchable(Touchable.disabled);

                    }
                });
                upgradeManager.getBetterUpgrade().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        betterUpgrade.setDrawable(images_upgrades, "upgradeIcons_bought");
                        betterUpgrade.setTouchable(Touchable.disabled);

                    }
                });
                upgradeManager.getLuck().addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        luck.setDrawable(images_upgrades, "upgradeIcons_bought");
                        luck.setTouchable(Touchable.disabled);

                    }
                });*/
// ^
                //wyłapuje tylko na textfieldach, a nie na całym table_profile
            }
        });

        buttonStyleManager.setTextButtonStyle(textButtonStyle_bDialogExit, images_settings, fontText, "checkbox_on", "checkbox_on");
        bDialogExit = new TextButton(null,buttonStyleManager.returnTextButtonStyle(textButtonStyle_bDialogExit));
        bDialogExit2 = new TextButton(null,buttonStyleManager.returnTextButtonStyle(textButtonStyle_bDialogExit));

        buttonStyleManager.setTextButtonStyle(textButtonStyle_cDialogStayLogged, images_settings, fontText, "checkbox_off", "checkbox_off");
        cDialogStayLogged = new TextButton(null, buttonStyleManager.returnTextButtonStyle(textButtonStyle_cDialogStayLogged));

        textFieldStyleManager.setTextFieldStyle(textFieldStyle, images_settings, fontText, "textBar", Color.WHITE);
        textFieldStyleManager.setTextFieldStyle(formTextFieldStyle, images_settings, fontText, "textBar", Color.WHITE);
        textFieldStyleManager.setTextFieldStyle(titleTextFieldStyle, images_settings, fontTitle, "empty_background", Color.BLACK);
        textFieldStyleManager.setTextFieldStyle(errorTextFieldStyle, images_settings, fontText, "textBar", Color.RED);


        tDialogLoginTextTitle= new TextField(languageManager.getValue(languageManager.getLanguage(), "tDialogLoginTitle"), textFieldStyleManager.returnTextFieldStyle(titleTextFieldStyle));
        tDialogLoginStayLogged = new TextField(languageManager.getValue(languageManager.getLanguage(), "tDialogLoginStayLogged"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tDialogLoginTextLogin = new TextField(languageManager.getValue(languageManager.getLanguage(), "tDialogLoginTextLogin"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tDialogLoginTextPassword = new TextField(languageManager.getValue(languageManager.getLanguage(), "tDialogLoginTextPassword"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));

        fDialogLoginLogin = new TextField(null, textFieldStyleManager.returnTextFieldStyle(formTextFieldStyle));
        fDialogLoginPassword = new TextField(null, textFieldStyleManager.returnTextFieldStyle(formTextFieldStyle));

        tDialogLoginErrors = new TextField(null,textFieldStyleManager.returnTextFieldStyle(errorTextFieldStyle));


        tDialogLoginTextTitle.setDisabled(true);
        tDialogLoginTextTitle.setAlignment(Align.right);
        tDialogLoginTextTitle.setSize(150,30);
        tDialogLoginTextLogin.setDisabled(true);
        tDialogLoginTextLogin.setAlignment(Align.center);
        tDialogLoginTextPassword.setDisabled(true);
        tDialogLoginTextPassword.setAlignment(Align.center);

        fDialogLoginPassword.setPasswordMode(true);
        fDialogLoginPassword.setPasswordCharacter('*');

        tDialogLoginErrors.setDisabled(true);


        tDialogRegisterTextTitle = new TextField(languageManager.getValue(languageManager.getLanguage(), "tDialogRegisterTitle"), textFieldStyleManager.returnTextFieldStyle(titleTextFieldStyle));
        tDialogRegisterTextLogin = new TextField(languageManager.getValue(languageManager.getLanguage(), "tDialogRegisterTextLogin"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tDialogRegisterTextMail = new TextField(languageManager.getValue(languageManager.getLanguage(), "tDialogRegisterTextMail"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tDialogRegisterTextPassword = new TextField(languageManager.getValue(languageManager.getLanguage(), "tDialogRegisterTextPassword"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tDialogRegisterTextRepeatPassword = new TextField(languageManager.getValue(languageManager.getLanguage(), "tDialogRegisterTextRepeatPassword"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));

        fDialogRegisterLogin = new TextField(null, textFieldStyleManager.returnTextFieldStyle(formTextFieldStyle));
        fDialogRegisterMail = new TextField(null, textFieldStyleManager.returnTextFieldStyle(formTextFieldStyle));
        fDialogRegisterPassword = new TextField(null, textFieldStyleManager.returnTextFieldStyle(formTextFieldStyle));
        fDialogRegisterRepeatPassword  = new TextField(null, textFieldStyleManager.returnTextFieldStyle(formTextFieldStyle));

        tDialogRegisterErrors = new TextField(null,textFieldStyleManager.returnTextFieldStyle(errorTextFieldStyle));


        tDialogRegisterTextTitle.setDisabled(true);
        tDialogRegisterTextTitle.setAlignment(Align.right);
        tDialogRegisterTextTitle.setSize(150,30);
        tDialogRegisterTextLogin.setDisabled(true);
        tDialogRegisterTextLogin.setAlignment(Align.center);
        tDialogRegisterTextMail.setDisabled(true);
        tDialogRegisterTextMail.setAlignment(Align.center);
        tDialogRegisterTextPassword.setDisabled(true);
        tDialogRegisterTextPassword.setAlignment(Align.center);
        tDialogRegisterTextRepeatPassword.setDisabled(true);
        tDialogRegisterTextRepeatPassword.setAlignment(Align.center);

        fDialogRegisterPassword.setPasswordMode(true);
        fDialogRegisterPassword.setPasswordCharacter('*');
        fDialogRegisterRepeatPassword.setPasswordMode(true);
        fDialogRegisterRepeatPassword.setPasswordCharacter('*');

        tDialogRegisterErrors.setDisabled(true);


        Texture bg = new Texture(new FileHandle("assets/dialog/skin_dialog.png"));
        menuDialog = new Dialog("", new Window.WindowStyle(fontTitle, Color.WHITE, new TextureRegionDrawable(new TextureRegion(bg)))) {
            public void result(Object obj) {
                menuDialog.cancel();
            }
        };
        menuDialog.getButtonTable().debug();

        // Login Dialog
        table_dialogLogin.setWidth(350);
        table_dialogLogin.setX(200);
        table_dialogLogin.setY(300);
        table_dialogLogin.row().colspan(2);
        table_dialogLogin.add(bDialogExit).expand().align(Align.top);
        table_dialogLogin.row().colspan(2);
        table_dialogLogin.add(tDialogLoginTextTitle).expand().fillX().align(Align.top);
        table_dialogLogin.row();
        table_dialogLogin.add(tDialogLoginTextLogin);
        table_dialogLogin.add(fDialogLoginLogin).pad(10).align(Align.center);
        table_dialogLogin.row();
        table_dialogLogin.add(tDialogLoginTextPassword);
        table_dialogLogin.add(fDialogLoginPassword).pad(10).align(Align.center);
        table_dialogLogin.row();
        table_dialogLogin.add(cDialogStayLogged);
        table_dialogLogin.add(tDialogLoginStayLogged);
        table_dialogLogin.row().colspan(2);
        table_dialogLogin.add(tDialogLoginErrors);

        // Register Dialog
        table_dialogRegister.setWidth(350);
        table_dialogRegister.setX(200);
        table_dialogRegister.setY(300);
        table_dialogRegister.row().colspan(2);
        table_dialogRegister.add(bDialogExit2).expand().align(Align.top);
        table_dialogRegister.row().colspan(2);
        table_dialogRegister.add(tDialogRegisterTextTitle).expand().fillX().align(Align.top);
        table_dialogRegister.row();
        table_dialogRegister.add(tDialogRegisterTextLogin);
        table_dialogRegister.add(fDialogRegisterLogin).pad(10).align(Align.center);
        table_dialogRegister.row();
        table_dialogRegister.add(tDialogRegisterTextMail);
        table_dialogRegister.add(fDialogRegisterMail).pad(10).align(Align.center);
        table_dialogRegister.row();
        table_dialogRegister.add(tDialogRegisterTextPassword);
        table_dialogRegister.add(fDialogRegisterPassword).pad(10).align(Align.center);
        table_dialogRegister.row();
        table_dialogRegister.add(tDialogRegisterTextRepeatPassword);
        table_dialogRegister.add(fDialogRegisterRepeatPassword).pad(10).align(Align.center);
        table_dialogRegister.row().colspan(2);
        table_dialogRegister.add(tDialogRegisterErrors);

        table_dialogLogin.debug();
        table_dialogRegister.debug();




        bLogin.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

                if (game.getIsLogged()) {
                    if (fileReader.getTokenValue()!=null){
                        fDialogLoginLogin.setText(fileReader.getLoginFromToken());

                    }

                    fileReader.setUserInfo(null);
                    bLogin.setText(languageManager.getValue(languageManager.getLanguage(), "bLogin"));
                    game.setIsLogged(false);
                    return;
                }
                isDialog = true;

                tDialogLoginErrors.setText(null);
                menuDialog.removeActor(table_dialogRegister);
                menuDialog.addActor(table_dialogLogin);
                menuDialog.getButtonTable().clearChildren();
                menuDialog.button(bDialogLogin);
                menuDialog.button(bDialogLoginRegister);
                menuDialog.show(stage);

            }
        });

        cDialogStayLogged.addListener(new ClickListener(){

            @Override
            //public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
            public void clicked(InputEvent event, float x, float y){
                stayLogged = !stayLogged;
                if (!stayLogged)
                {
                    buttonStyleManager.setTextButtonStyle(textButtonStyle_cDialogStayLogged, images_settings, fontText, "checkbox_off", "checkbox_off");
                    cDialogStayLogged = new TextButton(languageManager.getValue(languageManager.getLanguage(), "tDialogLoginStayLogged"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_cDialogStayLogged));
                }
                else
                {
                    buttonStyleManager.setTextButtonStyle(textButtonStyle_cDialogStayLogged, images_settings, fontText, "checkbox_on", "checkbox_on");
                    cDialogStayLogged = new TextButton(languageManager.getValue(languageManager.getLanguage(), "tDialogLoginStayLogged"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_cDialogStayLogged));
                }



            }
        });




        bDialogLogin.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {

                if (fDialogLoginLogin.getText().isEmpty() || fDialogLoginPassword.getText().isEmpty())
                {
                    tDialogLoginErrors.setText(languageManager.getValue(languageManager.getLanguage(), "ErrorEmptyFields"));
                    return;
                }
                if (fDialogLoginLogin.getText().length() < 5 || fDialogLoginLogin.getText().length() > 20 )
                {
                    tDialogLoginErrors.setText(languageManager.getValue(languageManager.getLanguage(), "ErrorLengthOfLogin"));
                    return;
                }
                if (fDialogLoginPassword.getText().length() < 5 || fDialogLoginPassword.getText().length() > 20 )
                {
                    tDialogLoginErrors.setText(languageManager.getValue(languageManager.getLanguage(), "ErrorLengthOfPassword"));
                    return;
                }

                JSONObject loginData = new JSONObject();

                loginData.put("login",fDialogLoginLogin.getText());
                loginData.put("password", fDialogLoginPassword.getText());

                JSONObject response = connectionManager.requestSend(loginData, "api/login");

                if (response.getInt("status") == 200)
                {
                    if (stayLogged)
                    {
                        fileReader.setUserInfo(response.getString("token"));
                    }
                    else {
                        fDialogLoginPassword.setText(null);
                    }

                    tDialogLoginErrors.setText(null);
                    bLogin.setText(languageManager.getValue(languageManager.getLanguage(), "bLogout"));
                    game.setLogin(fDialogLoginLogin.getText());
                    game.setIsLogged(true);
                    isDialog = false;
                    menuDialog.hide();
                }
                else
                {
                    tDialogLoginErrors.setText(languageManager.getValue(languageManager.getLanguage(), response.getString("message")));
                }


            }
        });

        bDialogExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                isDialog = false;
                menuDialog.hide();
            }
        });

        bDialogExit2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                isDialog = false;
                menuDialog.hide();
            }
        });

        bDialogLoginRegister.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                tDialogLoginErrors.setText(null);
                menuDialog.removeActor(table_dialogLogin);
                menuDialog.addActor(table_dialogRegister);
                menuDialog.getButtonTable().clearChildren();
                menuDialog.button(bDialogRegister);
                menuDialog.button(bDialogRegisterLogin);
            }
        });



        bDialogRegister.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if (fDialogRegisterLogin.getText().isEmpty() || fDialogRegisterMail.getText().isEmpty() || fDialogRegisterPassword.getText().isEmpty() || fDialogRegisterRepeatPassword.getText().isEmpty())
                {
                    tDialogRegisterErrors.setText(languageManager.getValue(languageManager.getLanguage(), "ErrorEmptyFields"));
                    return;
                }
                if (fDialogRegisterLogin.getText().length() < 5 || fDialogRegisterLogin.getText().length() > 20 )
                {
                    tDialogRegisterErrors.setText(languageManager.getValue(languageManager.getLanguage(), "ErrorLengthOfLogin"));
                    return;
                }
                if (fDialogRegisterPassword.getText().length() < 5 || fDialogRegisterPassword.getText().length() > 20 )
                {
                    tDialogRegisterErrors.setText(languageManager.getValue(languageManager.getLanguage(), "ErrorLengthOfPassword"));
                    return;
                }
                if (!Objects.equals(fDialogRegisterPassword.getText(), fDialogRegisterRepeatPassword.getText()))
                {
                    System.out.println(fDialogRegisterPassword.getText());
                    System.out.println(fDialogRegisterRepeatPassword.getText());

                    tDialogRegisterErrors.setText(languageManager.getValue(languageManager.getLanguage(), "ErrorRepeatPasswordDifferent"));
                    return;
                }

                //TODO mail checker



                JSONObject RegisterData = new JSONObject();

                RegisterData.put("login", fDialogRegisterLogin.getText());
                RegisterData.put("mail", fDialogRegisterMail.getText());
                RegisterData.put("password",fDialogRegisterPassword.getText());


                JSONObject response = connectionManager.requestSend(RegisterData, "api/register");

                if (response.getInt("status") == 200)
                {
                    fDialogRegisterLogin.setText(null);
                    fDialogRegisterMail.setText(null);
                    fDialogRegisterPassword.setText(null);
                    fDialogRegisterRepeatPassword.setText(null);
                    tDialogRegisterErrors.setText(null);
                    isDialog = false;
                    menuDialog.hide();

                }
                else
                {
                    tDialogRegisterErrors.setText(languageManager.getValue(languageManager.getLanguage(), response.getString("message")));
                }


            }
        });



        bDialogRegisterLogin.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                tDialogRegisterErrors.setText(null);
                menuDialog.removeActor(table_dialogRegister);
                menuDialog.addActor(table_dialogLogin);
                menuDialog.getButtonTable().clearChildren();
                menuDialog.button(bDialogLogin);
                menuDialog.button(bDialogLoginRegister);
            }
        });
        bPlay.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameState.setPreviousGameState(GameState.MENU);
                game.setScreen(new ProfileLocalScreen(game));
                dispose();
            }
        });

        bSettings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameState.setPreviousGameState(GameState.MENU);
                game.setScreen(new SettingsScreen(game));
                dispose();
            }
        });


        bCredits.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                GameState.setPreviousGameState(GameState.MENU);
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

        //table_bExit.debug();
        //table_bPlay.debug();
        //table_bLogin.debug();
        //table_bSettings.debug();
        //table_bCredits.debug();

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
            if(isDialog){
                game.batch.draw(backgroundForDialog, 0,0);
                bPlay.setVisible(false);
                bLogin.setVisible(false);
                bSettings.setVisible(false);
                bCredits.setVisible(false);
                bExit.setVisible(false);
            }else{
               game.batch.draw(background, 0,0);
                bPlay.setVisible(true);
                bLogin.setVisible(true);
                bSettings.setVisible(true);
                bCredits.setVisible(true);
                bExit.setVisible(true);
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

    private void initSettingsUI(){
        background = new Texture("background_menu.png");
        backgroundForDialog = new Texture("tempBackground.png");
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Silkscreen.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        stage = new Stage();
        fontTitle = new BitmapFont();
        fontText = new BitmapFont();

        parameter.size = 32;
        parameter.color = Color.BLACK;
        parameter.characters = "ąćęłńóśżźabcdefghijklmnopqrstuvwxyzĄĆĘÓŁŃŚŻŹABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";

        fontTitle = generator.generateFont(parameter);

        parameter.size = 15;
        parameter.color = Color.WHITE;
        fontText = generator.generateFont(parameter);

        connectionManager = new ConnectionManager();
        taButtonsAtlas = new TextureAtlas("assets/buttons/buttons_menu.pack");
        taButtonsDefault = new TextureAtlas("assets/buttons/buttons_default.pack");
        taButtonsSettings = new TextureAtlas("assets/buttons/buttons_settings.pack");
        taDialogBack = new TextureAtlas("assets/dialog/skin_dialog.pack");
        images = new Skin(taButtonsAtlas);
        images_settings = new Skin(taButtonsSettings);
        images_default = new Skin(taButtonsDefault);
        table_bExit = new Table(images);
        table_bPlay = new Table(images);
        table_bLogin = new Table(images);
        table_bCredits = new Table(images);
        table_bSettings = new Table(images);
        table_dialogLogin = new Table(images_settings);
        table_dialogRegister = new Table(images_settings);
        textFieldStyle = new TextField.TextFieldStyle();
        formTextFieldStyle = new TextField.TextFieldStyle();
        errorTextFieldStyle = new TextField.TextFieldStyle();
        titleTextFieldStyle = new TextField.TextFieldStyle();
        textButtonStyle_bExit = new TextButton.TextButtonStyle();
        textButtonStyle_bPlay = new TextButton.TextButtonStyle();
        textButtonStyle_bSettings = new TextButton.TextButtonStyle();
        textButtonStyle_bCredits = new TextButton.TextButtonStyle();
        textButtonStyle_bLogin = new TextButton.TextButtonStyle();
        textButtonStyle_bDialogLogin = new TextButton.TextButtonStyle();
        textButtonStyle_cDialogStayLogged = new TextButton.TextButtonStyle();
        textButtonStyle_tDialogStayLogged = new TextButton.TextButtonStyle();
        textButtonStyle_bDialogExit =  new TextButton.TextButtonStyle();
        backgroundMusic = game.getMusic();

        table_upgrade = new Table(images);
        table_upgradeButton = new Table(images);
        taUpgrades = new TextureAtlas("assets/icons/upgrade_icons.pack");//<- to delete
        images_upgrades = new Skin(taUpgrades);

        Drawable tooltipBackground = new TextureRegionDrawable(new TextureRegion(new Texture(new FileHandle("assets/dialog/settings_dialog.png"))));
        textTooltipStyle = new TextTooltip.TextTooltipStyle();
        textTooltipStyle.label = new Label.LabelStyle(fontText, Color.WHITE);
        textTooltipStyle.background = tooltipBackground;
        textTooltipStyle.wrapWidth = 400; //nie może być 100.0f

    }

    private void initUpgrades(){
        uFork = upgradeManager.getuFork();
        uScythe = upgradeManager.getuScythe();
        uDagger = upgradeManager.getuDagger();
        uSword = upgradeManager.getuSword();
        uBattleAxe = upgradeManager.getuBattleAxe();
        uMace = upgradeManager.getuMace();
        uBow = upgradeManager.getuBow();
        uCrossBow = upgradeManager.getuCrossBow();
        uCannon = upgradeManager.getuCannon();
        uBetterCannon = upgradeManager.getuBetterCannon();
        uCannonBall = upgradeManager.getuCannonBall();
        uSceptre = upgradeManager.getuScythe();
        uBook = upgradeManager.getuBook();
        uGear = upgradeManager.getuGear();
        uSonar = upgradeManager.getuSonar();
        uHealth = upgradeManager.getuHealth();
        uBetterHealth = upgradeManager.getuBetterHealth();
        uBetterBetterHealth = upgradeManager.getuBetterBetterHealth();
        uRegeneration = upgradeManager.getuRegeneration();
        uShield = upgradeManager.getuShield();
        uGold = upgradeManager.getuGold();
        uBetterGold = upgradeManager.getuBetterGold();
        uDiamonds = upgradeManager.getuDiamonds();
        uBetterDiamonds = upgradeManager.getuBetterDiamonds();
        uDiscount10 = upgradeManager.getuDiscount10();
        uDiscount20 = upgradeManager.getuDiscount20();
        uDiscount30 = upgradeManager.getuDiscount30();
        uUpgrade = upgradeManager.getuUpgrade();
        uHammer = upgradeManager.getuHammer();
        uBetterUpgrade = upgradeManager.getuBetterUpgrade();
        uLuck = upgradeManager.getuLuck();
    }
}
