package com.game.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.game.Entity.Base;

public class TipsManager {
    private LanguageManager languageManager;
    private BitmapFont font;
    private Base base;
    private float scale;

    private TextureAtlas taTips;
    private Skin images_tips;

    private TextFieldStyleManager textFieldStyleManager;
    private TextField tDifficulty, tDifficultyDescription, tCurrency, tGold, tDiamonds, tShop, tShopDescription, tObstacles, tObstaclesDescription;
    private TextField.TextFieldStyle textFieldStyle;

    private ButtonStyleManager buttonStyleManager;
    private TextButton bMechanics, bEnemies, bTowers, bUpgrades, bBack;
    private TextButton.TextButtonStyle textButtonStyleTop, textButtonStyleBack;

    private Table table_mechanics, table_enemies, table_upgrades, table_towers, table_buttons;

    public TipsManager(LanguageManager languageManager, BitmapFont font, Base base, float scale){
        this.languageManager = languageManager;
        this.font = font;
        this.base = base;
        this.scale = scale;

        textFieldStyleManager = new TextFieldStyleManager();
        buttonStyleManager = new ButtonStyleManager();
        initSettingUI();
        initListeners();
        createTipsButtonTable();
        createTipsMechanicsTable();
        createTipsEnemiesTable();
        createTipsTowersTable();
        createTipsUpgradesTable();
    }

    public void createTipsButtonTable(){
        this.table_buttons = new Table();
        buttonStyleManager.setTextButtonStyle(textButtonStyleTop, images_tips, font, "defaultButton", "defaultButton");
        buttonStyleManager.setTextButtonStyle(textButtonStyleBack, images_tips, font, "defaultButton", "defaultButton");
        bMechanics = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bMechanics"), buttonStyleManager.returnTextButtonStyle(textButtonStyleTop));
        bEnemies = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bEnemies"), buttonStyleManager.returnTextButtonStyle(textButtonStyleTop));
        bTowers = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bTowers"), buttonStyleManager.returnTextButtonStyle(textButtonStyleTop));
        bUpgrades = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bUpgrades"), buttonStyleManager.returnTextButtonStyle(textButtonStyleTop));
        bBack = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bBack"), buttonStyleManager.returnTextButtonStyle(textButtonStyleBack));

        table_buttons.setBounds(0, Gdx.graphics.getHeight()/10*8, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/10*2);

        table_buttons.add(bMechanics);
        table_buttons.add(bEnemies);
        table_buttons.add(bTowers);
        table_buttons.add(bUpgrades);
        table_buttons.add(bBack);
    }
    public void createTipsMechanicsTable(){
        this.table_mechanics = new Table();
        table_mechanics.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/10*8);
        textFieldStyleManager.setTextFieldStyle(textFieldStyle, images_tips, font, "empty_background", Color.WHITE);
        tDifficulty = new TextField(languageManager.getValue(languageManager.getLanguage(), "tDifficulty"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tDifficultyDescription = new TextField(languageManager.getValue(languageManager.getLanguage(), "tDifficultyDescription"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tCurrency = new TextField(languageManager.getValue(languageManager.getLanguage(), "tCurrency"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tGold = new TextField(languageManager.getValue(languageManager.getLanguage(), "tGoldDescription"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tDiamonds = new TextField(languageManager.getValue(languageManager.getLanguage(), "tDiamondsDescription"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tShop = new TextField(languageManager.getValue(languageManager.getLanguage(), "tShop"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tShopDescription = new TextField(languageManager.getValue(languageManager.getLanguage(), "tShopDescription"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tObstacles = new TextField(languageManager.getValue(languageManager.getLanguage(), "tObstacles"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tObstaclesDescription = new TextField(languageManager.getValue(languageManager.getLanguage(), "tObstaclesDescription"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));

        table_mechanics.row();
        table_mechanics.add(tDifficulty);
        table_mechanics.row();
        table_mechanics.add(tDifficultyDescription).width(Gdx.graphics.getWidth());
        table_mechanics.row();
        //table_mechanics.add(); new Image rozdzielacz
        table_mechanics.row();
        table_mechanics.add(tCurrency);
        table_mechanics.row();
        table_mechanics.add(tGold).width(Gdx.graphics.getWidth());
        table_mechanics.row();
        table_mechanics.add(tDiamonds).width(Gdx.graphics.getWidth());
        table_mechanics.row();
        //table_mechanics.add(); new Image rozdzielacz
        table_mechanics.row();
        table_mechanics.add(tShop);
        table_mechanics.add(tShopDescription).width(Gdx.graphics.getWidth());
        table_mechanics.row();
        //table_mechanics.add(); new Image rozdzielacz
        table_mechanics.row();
        table_mechanics.add(tObstacles);
        table_mechanics.add(tObstaclesDescription).width(Gdx.graphics.getWidth());
        table_mechanics.row();


    }

    public void createTipsEnemiesTable(){
        this.table_enemies = new Table();

        table_enemies.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/10*8);
    }

    public void createTipsUpgradesTable(){
        this.table_upgrades = new Table();

        table_upgrades.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/10*8);
    }

    public void createTipsTowersTable(){
        this.table_towers = new Table();

        table_towers.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/10*8);
    }

    public Table returnTipsButtonsTable(){
        return  table_buttons;
    }
    public Table returnTipsMechanicsTable(){
        return  table_mechanics;
    }
    public Table returnTipsEnemiesTable(){
        return  table_enemies;
    }
    public Table returnTipsTowersTable(){
        return  table_towers;
    }
    public Table returnTipsUpgradesTable(){
        return  table_upgrades;
    }

    public void initSettingUI(){
        taTips = new TextureAtlas();
        images_tips = new Skin(taTips);
        textFieldStyle = new TextField.TextFieldStyle();
        textButtonStyleTop = new TextButton.TextButtonStyle();
        textButtonStyleBack = new TextButton.TextButtonStyle();
    }

    public void initListeners(){
        //TODO wersja robocza, może tworzyć dialog w tej klasie i usuwać dodawać tablice tutaj do dialogu
        bMechanics.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(table_enemies!=null){
                    table_enemies.remove();
                }
                if(table_towers!=null){
                    table_towers.remove();
                }
                if(table_upgrades!=null){
                    table_upgrades.remove();
                }
                if(table_mechanics==null){
                    createTipsMechanicsTable();
                    returnTipsMechanicsTable();
                }
            }
        });
        bEnemies.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(table_mechanics!=null){
                    table_mechanics.remove();
                }
                if(table_towers!=null){
                    table_towers.remove();
                }
                if(table_upgrades!=null){
                    table_upgrades.remove();
                }
                if(table_enemies==null){
                    createTipsEnemiesTable();
                    returnTipsEnemiesTable();
                }
            }
        });
        bTowers.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(table_enemies!=null){
                    table_enemies.remove();
                }
                if(table_mechanics!=null){
                    table_mechanics.remove();
                }
                if(table_upgrades!=null){
                    table_upgrades.remove();
                }
                if(table_towers==null){
                    createTipsTowersTable();
                    returnTipsTowersTable();
                }
            }
        });
        bUpgrades.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(table_enemies!=null){
                    table_enemies.remove();
                }
                if(table_towers!=null){
                    table_towers.remove();
                }
                if(table_mechanics!=null){
                    table_mechanics.remove();
                }
                if(table_upgrades==null){
                    createTipsUpgradesTable();
                    returnTipsUpgradesTable();
                }
            }
        });

        bBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                base.setState(Base.State.Resumed);
            }
        });
    }
}