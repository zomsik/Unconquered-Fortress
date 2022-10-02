package com.game.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.game.Entity.Base;

public class TipsManager {
    private LanguageManager languageManager;
    private BitmapFont font;
    private Base base;
    private float scale;
    private TextureAtlas taTips, taTextTips;
    private Skin images_tips, text_tips;

    private TextFieldStyleManager textFieldStyleManager;
    private TextField tDifficulty, tDifficultyDescription, tCurrency, tGold, tDiamonds, tShop, tShopDescription, tObstacles, tObstaclesDescription;
    private TextField.TextFieldStyle textFieldStyle;

    private ButtonStyleManager buttonStyleManager;
    private TextButton bMechanics, bEnemies, bTowers, bUpgrades, bBack;
    private TextButton.TextButtonStyle textButtonStyleTop, textButtonStyleBack;

    private Table table_mechanics, table_enemies, table_upgrades, table_towers, table_buttons;

    private Dialog tipsDialog;
    private Pixmap scaledpm;

    public TipsManager(LanguageManager languageManager, BitmapFont font, Base base, float scale){
        this.languageManager = languageManager;
        this.font = font;
        this.base = base;
        this.scale = scale;

        textFieldStyleManager = new TextFieldStyleManager();
        buttonStyleManager = new ButtonStyleManager();
        initSettingUI();


        createTipsButtonTable();
        initListeners();
        createTipsMechanicsTable();
        createTipsEnemiesTable();
        createTipsTowersTable();
        createTipsUpgradesTable();
    }
    public void createTipsDialog(){
        Pixmap pm = new Pixmap(new FileHandle("assets/tempBackground.png"));
        scaledpm = new Pixmap((int) (1920*scale/1.5f), (int) (1080*scale/1.5f), pm.getFormat());
        scaledpm.drawPixmap(pm,
                0, 0, pm.getWidth(), pm.getHeight(),
                0, 0, scaledpm.getWidth(), scaledpm.getHeight()
        );
        Texture dialogBg = new Texture(scaledpm);
        this.tipsDialog = new Dialog("", new Window.WindowStyle(font, Color.WHITE, new TextureRegionDrawable(new TextureRegion(dialogBg))));
        tipsDialog.setBounds(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        tipsDialog.row();
        Table bTable = returnTipsButtonsTable();
        tipsDialog.add(bTable).align(Align.center).height(Gdx.graphics.getHeight()/10);//.padBottom(Gdx.graphics.getHeight()-bTable.getHeight())
        tipsDialog.row();
        Table mTable = returnTipsMechanicsTable();
        mTable.debug();
        tipsDialog.add(mTable).width(Gdx.graphics.getWidth()).colspan(2).height(Gdx.graphics.getHeight()/10*9);
        tipsDialog.row();
    }
    public Dialog returnTipsDialog(){
        return  tipsDialog;
    };
    public void createTipsButtonTable(){
        this.table_buttons = new Table();
        buttonStyleManager.setTextButtonStyle(textButtonStyleTop, images_tips, font, "defaultButton", "defaultButton");
        buttonStyleManager.setTextButtonStyle(textButtonStyleBack, images_tips, font, "defaultButton", "defaultButton");
        bMechanics = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bMechanics"), buttonStyleManager.returnTextButtonStyle(textButtonStyleTop));
        bEnemies = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bEnemies"), buttonStyleManager.returnTextButtonStyle(textButtonStyleTop));
        bTowers = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bTowers"), buttonStyleManager.returnTextButtonStyle(textButtonStyleTop));
        bUpgrades = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bUpgrades"), buttonStyleManager.returnTextButtonStyle(textButtonStyleTop));
        bBack = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bBack"), buttonStyleManager.returnTextButtonStyle(textButtonStyleBack));

        table_buttons.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/10);

        table_buttons.add(bMechanics).height(Gdx.graphics.getHeight()/10);
        table_buttons.add(bEnemies).height(Gdx.graphics.getHeight()/10);
        table_buttons.add(bTowers).height(Gdx.graphics.getHeight()/10);
        table_buttons.add(bUpgrades).height(Gdx.graphics.getHeight()/10);
        table_buttons.add(bBack).height(Gdx.graphics.getHeight()/10);
        //table_buttons.debug();
    }
    public void createTipsMechanicsTable(){
        this.table_mechanics = new Table();
        //table_mechanics.debug();
        table_mechanics.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/10*8);
        textFieldStyleManager.setTextFieldStyle(textFieldStyle, text_tips, font, "empty_text", Color.WHITE);
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
        table_mechanics.add(tDifficulty).width(Gdx.graphics.getWidth()).colspan(2);
        table_mechanics.row();
        table_mechanics.add(tDifficultyDescription).width(Gdx.graphics.getWidth()).colspan(2);
        table_mechanics.row();
        //table_mechanics.add(); new Image rozdzielacz
        table_mechanics.row();
        table_mechanics.add(tCurrency).width(Gdx.graphics.getWidth()).colspan(2);
        table_mechanics.row();
        table_mechanics.add(tGold).width(Gdx.graphics.getWidth()).colspan(2);
        table_mechanics.row();
        table_mechanics.add(tDiamonds).width(Gdx.graphics.getWidth()).colspan(2);
        table_mechanics.row();
        //table_mechanics.add(); new Image rozdzielacz
        table_mechanics.row();
        table_mechanics.add(tShop).width(Gdx.graphics.getWidth()/2);
        table_mechanics.add(tShopDescription).width(Gdx.graphics.getWidth()/2);
        table_mechanics.row();
        //table_mechanics.add(); new Image rozdzielacz
        table_mechanics.row();
        table_mechanics.add(tObstacles).width(Gdx.graphics.getWidth()/2);
        table_mechanics.add(tObstaclesDescription).width(Gdx.graphics.getWidth()/2);
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
        taTips = new TextureAtlas("assets/buttons/buttons_default.pack");
        images_tips = new Skin(taTips);
        taTextTips = new TextureAtlas("assets/buttons/text_credits.pack");
        text_tips = new Skin(taTextTips);
        textFieldStyle = new TextField.TextFieldStyle();
        textButtonStyleTop = new TextButton.TextButtonStyle();
        textButtonStyleBack = new TextButton.TextButtonStyle();
    }

    public void initListeners(){
        //TODO wersja robocza, może tworzyć dialog w tej klasie i usuwać dodawać tablice tutaj do dialogu
        bMechanics.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tipsDialog.clear();
                tipsDialog.row();
                Table bTable = returnTipsButtonsTable();
                tipsDialog.add(bTable).align(Align.center).height(Gdx.graphics.getHeight()/10);//.padBottom(Gdx.graphics.getHeight()-bTable.getHeight())
                tipsDialog.row();
                Table mTable = returnTipsMechanicsTable();
                System.out.println("TETS:" + bTable.getHeight() + " I " + mTable.getHeight());
                mTable.debug();
                tipsDialog.add(mTable).width(Gdx.graphics.getWidth()).colspan(2).height(Gdx.graphics.getHeight()/10*9);
                tipsDialog.row();
            }
        });
        bEnemies.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tipsDialog.clear();
                tipsDialog.add(returnTipsButtonsTable());
                tipsDialog.row();
                tipsDialog.add(returnTipsEnemiesTable());
            }
        });
        bTowers.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tipsDialog.clear();
                tipsDialog.add(returnTipsButtonsTable());
                tipsDialog.row();
                tipsDialog.add(returnTipsTowersTable());
            }
        });
        bUpgrades.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tipsDialog.clear();
                tipsDialog.add(returnTipsButtonsTable());
                tipsDialog.row();
                tipsDialog.add(returnTipsUpgradesTable());
            }
        });

        bBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                scaledpm.dispose();
                tipsDialog.clear();
                base.setState(Base.State.Resumed);
            }
        });
    }
}
