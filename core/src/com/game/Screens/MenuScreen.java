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
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
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
                System.out.println("zostałem wybrany");
                Texture bg = new Texture(new FileHandle("assets/dialog/upgrade_dialog.png"));                  // V
                System.out.println(bg.getHeight());
                System.out.println(bg.getWidth());
                upgradeDialog = new Dialog("", new Window.WindowStyle(fontText, Color.WHITE, new TextureRegionDrawable(new TextureRegion(bg)))) {
                    public void result(Object obj) {
                        System.out.println("result " + obj);
                    }
                };
                Label.LabelStyle labelStyle = new Label.LabelStyle(fontText, Color.WHITE);
                upgradeDialog.setBounds(0,0,200,200);
                System.out.println(upgradeDialog.getWidth());
                System.out.println(upgradeDialog.getHeight());
                upgradeDialog.text(languageManager.getValue(languageManager.getLanguage(), "upgrade_dialog_field_text"), labelStyle);
                table_upgrade.setBounds(0,0,upgradeDialog.getWidth(), upgradeDialog.getHeight()/10*9);
                //table_upgrade.debug();
                upgradeDialog.row();
                //upgradeDialog.padBottom(500);
                UpgradeTalesManager tile = new UpgradeTalesManager();
                fork = new Image(images_upgrades, "upgradeIcons_attackFork");
                scythe = new Image(images_upgrades, "upgradeIcons_lock");
                dagger = new Image(images_upgrades, "upgradeIcons_lock");
                sword = new Image(images_upgrades, "upgradeIcons_lock");
                battleAxe = new Image(images_upgrades, "upgradeIcons_lock");
                mace = new Image(images_upgrades, "upgradeIcons_lock");

                bow = new Image(images_upgrades, "upgradeIcons_lock");
                crossbow = new Image(images_upgrades, "upgradeIcons_lock");

                cannon = new Image(images_upgrades, "upgradeIcons_lock");
                betterCannon = new Image(images_upgrades, "upgradeIcons_lock");
                cannonBall = new Image(images_upgrades, "upgradeIcons_lock");

                sceptre = new Image(images_upgrades, "upgradeIcons_lock");
                book = new Image(images_upgrades, "upgradeIcons_lock");

                gear = new Image(images_upgrades, "upgradeIcons_attackGear");
                sonar = new Image(images_upgrades, "upgradeIcons_lock");

                health = new Image(images_upgrades, "upgradeIcons_healthHealth");
                betterHealth = new Image(images_upgrades, "upgradeIcons_lock");
                betterBetterHealth = new Image(images_upgrades, "upgradeIcons_lock");
                regeneration = new Image(images_upgrades, "upgradeIcons_lock");
                shield = new Image(images_upgrades, "upgradeIcons_lock");

                gold = new Image(images_upgrades, "upgradeIcons_incomeGold");
                betterGold = new Image(images_upgrades, "upgradeIcons_lock");
                diamonds = new Image(images_upgrades, "upgradeIcons_lock");
                betterDiamonds = new Image(images_upgrades, "upgradeIcons_lock");

                discount10 = new Image(images_upgrades, "upgradeIcons_lock");
                discount20 =  new Image(images_upgrades, "upgradeIcons_lock");
                discount30 =  new Image(images_upgrades, "upgradeIcons_lock");

                upgrade = new Image(images_upgrades, "upgradeIcons_lock");
                hammer = new Image(images_upgrades, "upgradeIcons_lock");
                betterUpgrade = new Image(images_upgrades, "upgradeIcons_lock");

                luck = new Image(images_upgrades, "upgradeIcons_lock");
                fork.setTouchable(Touchable.enabled);
                scythe.setTouchable(Touchable.disabled);
                dagger.setTouchable(Touchable.disabled);
                sword.setTouchable(Touchable.disabled);
                battleAxe.setTouchable(Touchable.disabled);
                mace.setTouchable(Touchable.disabled);
                bow.setTouchable(Touchable.disabled);
                crossbow.setTouchable(Touchable.disabled);
                cannon.setTouchable(Touchable.disabled);
                cannonBall.setTouchable(Touchable.disabled);
                betterCannon.setTouchable(Touchable.disabled);
                sceptre.setTouchable(Touchable.disabled);
                book.setTouchable(Touchable.disabled);
                gear.setTouchable(Touchable.enabled);
                sonar.setTouchable(Touchable.disabled);
                health.setTouchable(Touchable.enabled);
                regeneration.setTouchable(Touchable.disabled);
                betterHealth.setTouchable(Touchable.disabled);
                betterBetterHealth.setTouchable(Touchable.disabled);
                gold.setTouchable(Touchable.enabled);
                betterGold.setTouchable(Touchable.disabled);
                diamonds.setTouchable(Touchable.disabled);
                betterDiamonds.setTouchable(Touchable.disabled);
                discount10.setTouchable(Touchable.disabled);
                discount20.setTouchable(Touchable.disabled);
                discount30.setTouchable(Touchable.disabled);
                upgrade.setTouchable(Touchable.disabled);
                hammer.setTouchable(Touchable.disabled);
                betterUpgrade.setTouchable(Touchable.disabled);
                luck.setTouchable(Touchable.disabled);
                //Attack
                table_upgrade.add(fork);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect"));
                table_upgrade.add(scythe);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect"));
                table_upgrade.add(dagger);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect"));
                table_upgrade.add(sword);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect"));
                table_upgrade.add(battleAxe);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect"));
                table_upgrade.add(mace);
                table_upgrade.row();
                //connectors to lower upgrades
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.row();
                //bow type turret
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(bow);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect"));
                table_upgrade.add(crossbow);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.row();
                //connectors to lower upgrades
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.row();
                //cannon type turret
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(cannon);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect"));
                table_upgrade.add(cannonBall);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect"));
                table_upgrade.add(betterCannon);
                table_upgrade.row().padBottom(10);
                //mage type turret
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(sceptre);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect"));
                table_upgrade.add(book);
                table_upgrade.row().padBottom(10);
                //range
                table_upgrade.add(gear);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect"));
                table_upgrade.add(sonar);
                table_upgrade.row().padBottom(10);
                //health
                table_upgrade.add(health);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect"));
                table_upgrade.add(betterHealth);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect"));
                table_upgrade.add(regeneration);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect"));
                table_upgrade.add(shield);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect"));
                table_upgrade.add(betterBetterHealth);
                table_upgrade.row();
                //income
                table_upgrade.add(gold);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect"));
                table_upgrade.add(betterGold);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect"));
                table_upgrade.add(diamonds);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect"));
                table_upgrade.add(betterDiamonds);
                table_upgrade.row();
                //connectors to lower level
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical"));
                table_upgrade.row();
                //sales
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(discount10);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect"));
                table_upgrade.add(discount20);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect"));
                table_upgrade.add(discount30);
                table_upgrade.row();
                //connectors to lower level
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical"));
                table_upgrade.row();
                //upgarde/hammer
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(upgrade);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect"));
                table_upgrade.add(hammer);
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connect"));
                table_upgrade.add(betterUpgrade);
                table_upgrade.row();
                //connectors to lower level
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_connectVertical"));
                table_upgrade.row();
                //luck
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(new Image(images_upgrades, "upgradeIcons_empty"));
                table_upgrade.add(luck).padBottom(10);;
                table_upgrade.padBottom(16);
                stage.addActor(table_upgrade);
                upgradeDialog.add(table_upgrade);
                upgradeDialog.show(stage);
                System.out.println("table width: " + table_upgrade.getWidth() + " height: " + table_upgrade.getWidth());
                fork.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        fork.setDrawable(images_upgrades, "upgradeIcons_bought");
                        scythe.setDrawable(images_upgrades, "upgradeIcons_attackScythe");
                        scythe.setTouchable(Touchable.enabled);
                        fork.setTouchable(Touchable.disabled);


                    }
                });
                fork.addListener(new ClickListener() {
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        System.out.println("Hover");

                        tooltip = new TextTooltip("Działam", textTooltipStyle);
                        tooltip.setInstant(true);
                        System.out.println(tooltip.getActor());
                        tooltip.enter(event, x, y, pointer, fromActor);

                    }});
                fork.addListener(new ClickListener() {
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.exit(event, x, y, pointer, fromActor);

                    }});

                scythe.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        scythe.setDrawable(images_upgrades, "upgradeIcons_bought");
                        dagger.setDrawable(images_upgrades, "upgradeIcons_attackDagger");
                        sceptre.setDrawable(images_upgrades, "upgradeIcons_attackSceptre");
                        dagger.setTouchable(Touchable.enabled);
                        sceptre.setTouchable(Touchable.enabled);
                        scythe.setTouchable(Touchable.disabled);

                    }
                });
                dagger.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        dagger.setDrawable(images_upgrades, "upgradeIcons_bought");
                        sword.setDrawable(images_upgrades, "upgradeIcons_attackSword");
                        bow.setDrawable(images_upgrades, "upgradeIcons_attackBow");
                        sword.setTouchable(Touchable.enabled);
                        bow.setTouchable(Touchable.enabled);
                        dagger.setTouchable(Touchable.disabled);

                    }
                });
                sword.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        sword.setDrawable(images_upgrades, "upgradeIcons_bought");
                        battleAxe.setDrawable(images_upgrades, "upgradeIcons_attackBattleAxe");
                        battleAxe.setTouchable(Touchable.enabled);
                        sword.setTouchable(Touchable.disabled);

                    }
                });
                battleAxe.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        battleAxe.setDrawable(images_upgrades, "upgradeIcons_bought");
                        mace.setDrawable(images_upgrades, "upgradeIcons_attackMace");
                        cannon.setDrawable(images_upgrades,"upgradeIcons_attackCannon");
                        mace.setTouchable(Touchable.enabled);
                        cannon.setTouchable(Touchable.enabled);
                        battleAxe.setTouchable(Touchable.disabled);

                    }
                });
                mace.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        mace.setDrawable(images_upgrades, "upgradeIcons_bought");
                        mace.setTouchable(Touchable.disabled);

                    }
                });
                bow.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        bow.setDrawable(images_upgrades, "upgradeIcons_bought");
                        crossbow.setDrawable(images_upgrades, "upgradeIcons_attackCrossbow");
                        crossbow.setTouchable(Touchable.enabled);
                        bow.setTouchable(Touchable.disabled);

                    }
                });
                crossbow.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        crossbow.setDrawable(images_upgrades, "upgradeIcons_bought");
                        crossbow.setTouchable(Touchable.disabled);

                    }
                });
                cannon.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        cannon.setDrawable(images_upgrades, "upgradeIcons_bought");
                        cannonBall.setDrawable(images_upgrades, "upgradeIcons_attackCannonBall");
                        cannonBall.setTouchable(Touchable.enabled);
                        cannon.setTouchable(Touchable.disabled);

                    }
                });
                cannonBall.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        cannonBall.setDrawable(images_upgrades, "upgradeIcons_bought");
                        betterCannon.setDrawable(images_upgrades, "upgradeIcons_attackBetterCannon");
                        betterCannon.setTouchable(Touchable.enabled);
                        cannonBall.setTouchable(Touchable.disabled);

                    }
                });
                betterCannon.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        betterCannon.setDrawable(images_upgrades, "upgradeIcons_bought");
                        betterCannon.setTouchable(Touchable.disabled);

                    }
                });
                sceptre.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        sceptre.setDrawable(images_upgrades, "upgradeIcons_bought");
                        book.setDrawable(images_upgrades, "upgradeIcons_attackBook");
                        book.setTouchable(Touchable.enabled);
                        sceptre.setTouchable(Touchable.disabled);

                    }
                });
                book.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        book.setDrawable(images_upgrades, "upgradeIcons_bought");
                        book.setTouchable(Touchable.disabled);

                    }
                });

                gear.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        gear.setDrawable(images_upgrades, "upgradeIcons_bought");
                        sonar.setDrawable(images_upgrades, "upgradeIcons_attackSonar");
                        sonar.setTouchable(Touchable.enabled);
                        gear.setTouchable(Touchable.disabled);

                    }
                });
                sonar.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        sonar.setDrawable(images_upgrades, "upgradeIcons_bought");
                        sonar.setTouchable(Touchable.disabled);

                    }
                });
                health.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        health.setDrawable(images_upgrades, "upgradeIcons_bought");
                        betterHealth.setDrawable(images_upgrades, "upgradeIcons_healthBetterHealth");
                        betterHealth.setTouchable(Touchable.enabled);
                        health.setTouchable(Touchable.disabled);

                    }
                });
                betterHealth.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        betterHealth.setDrawable(images_upgrades, "upgradeIcons_bought");
                        regeneration.setDrawable(images_upgrades, "upgradeIcons_healthRegen");
                        regeneration.setTouchable(Touchable.enabled);
                        betterHealth.setTouchable(Touchable.disabled);

                    }
                });
                regeneration.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        regeneration.setDrawable(images_upgrades, "upgradeIcons_bought");
                        shield.setDrawable(images_upgrades, "upgradeIcons_healthShield");
                        shield.setTouchable(Touchable.enabled);
                        regeneration.setTouchable(Touchable.disabled);

                    }
                });

                shield.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        shield.setDrawable(images_upgrades, "upgradeIcons_bought");
                        betterBetterHealth.setDrawable(images_upgrades, "upgradeIcons_healthBetterBetterHealth");
                        betterBetterHealth.setTouchable(Touchable.enabled);
                        shield.setTouchable(Touchable.disabled);

                    }
                });
                betterBetterHealth.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        betterBetterHealth.setDrawable(images_upgrades, "upgradeIcons_bought");
                        betterBetterHealth.setTouchable(Touchable.disabled);

                    }
                });
                gold.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        gold.setDrawable(images_upgrades, "upgradeIcons_bought");
                        betterGold.setDrawable(images_upgrades, "upgradeIcons_incomeBetterGold");
                        betterGold.setTouchable(Touchable.enabled);
                        gold.setTouchable(Touchable.disabled);

                    }
                });
                betterGold.addListener(new ClickListener(){
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
                diamonds.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        diamonds.setDrawable(images_upgrades, "upgradeIcons_bought");
                        betterDiamonds.setDrawable(images_upgrades, "upgradeIcons_incomeBetterDiamonds");
                        betterDiamonds.setTouchable(Touchable.enabled);
                        diamonds.setTouchable(Touchable.disabled);

                    }
                });
                betterDiamonds.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        betterDiamonds.setDrawable(images_upgrades, "upgradeIcons_bought");
                        betterDiamonds.setTouchable(Touchable.disabled);

                    }
                });
                discount10.addListener(new ClickListener(){
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
                discount20.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        discount20.setDrawable(images_upgrades, "upgradeIcons_bought");
                        discount30.setDrawable(images_upgrades, "upgradeIcons_incomeDiscountThirty");
                        discount30.setTouchable(Touchable.enabled);
                        discount20.setTouchable(Touchable.disabled);

                    }
                });
                discount30.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        discount30.setDrawable(images_upgrades, "upgradeIcons_bought");
                        discount30.setTouchable(Touchable.disabled);

                    }
                });
                upgrade.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        upgrade.setDrawable(images_upgrades, "upgradeIcons_bought");
                        hammer.setDrawable(images_upgrades, "upgradeIcons_incomeHammer");
                        hammer.setTouchable(Touchable.enabled);
                        upgrade.setTouchable(Touchable.disabled);

                    }
                });
                hammer.addListener(new ClickListener(){
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
                betterUpgrade.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        betterUpgrade.setDrawable(images_upgrades, "upgradeIcons_bought");
                        betterUpgrade.setTouchable(Touchable.disabled);

                    }
                });
                luck.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        System.out.println("zostałem wybrany");
                        luck.setDrawable(images_upgrades, "upgradeIcons_bought");
                        luck.setTouchable(Touchable.disabled);

                    }
                });
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
                //Gdx.gl.glClearColor(0,0,0,1);//do zamiany na inne tło
                //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);         //
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
        parameter.characters = "ąćęłóśżźabcdefghijklmnopqrstuvwxyzĄĆĘÓŁŚŻŹABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";

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
        textTooltipStyle.wrapWidth = 100.0f;


    }
}
