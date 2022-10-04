package com.game.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.game.Entity.Base;

public class TipsManager {
    private LanguageManager languageManager;
    private BitmapFont font, bigFont;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private Base base;
    private float scale;
    private TextureAtlas taTips, taTextTips, taTips2; //<-to change with taTips later
    private Skin images_tips, text_tips, images_tips2; //<- to chane with images_tips later

    //private TextFieldStyleManager textFieldStyleManager;
    //private TextField tDifficulty, tDifficultyDescription, tCurrency, tGold, tDiamonds, tShop, tShopDescription, tObstacles, tObstaclesDescription;
    //private TextField.TextFieldStyle textFieldStyle;

    private ButtonStyleManager buttonStyleManager;
    private TextButton bMechanics, bEnemies, bTowers, bUpgrades, bBack;
    private TextButton.TextButtonStyle textButtonStyleMechanics, textButtonStyleEnemies, textButtonStyleTowers, textButtonStyleBack, textButtonStyleMechanicsChosen, textButtonStyleEnemiesChosen, textButtonStyleTowersChosen;

    private Table table_mechanics, table_enemies, table_upgrades, table_towers, table_buttons;

    private Dialog tipsDialog;
    private Pixmap scaledpm, pm;
    private Label.LabelStyle labelStyle, bigLabelStyle;

    public TipsManager(LanguageManager languageManager, BitmapFont font, Base base, float scale){
        this.languageManager = languageManager;
        this.font = font;
        this.base = base;
        this.scale = scale;

        //textFieldStyleManager = new TextFieldStyleManager();
        buttonStyleManager = new ButtonStyleManager();
        initSettingUI();


        createTipsButtonTable();
        initListeners();
        createTipsMechanicsTable();
        createTipsEnemiesTable();
        createTipsTowersTable();
        //createTipsUpgradesTable();
    }
    public void createTipsDialog(){

        pm = new Pixmap(new FileHandle("assets/tempBackground.png"));
        System.out.println("pm: " + pm.getWidth() + " : " + pm.getHeight());
        scaledpm = new Pixmap((int) (1920*scale/1.5f), (int) (1080*scale/1.5f), pm.getFormat());
        scaledpm.drawPixmap(pm,
                0, 0, pm.getWidth(), pm.getHeight(),
                0, 0, scaledpm.getWidth(), scaledpm.getHeight()
        );
        Texture dialogBg = new Texture(scaledpm);
        this.tipsDialog = new Dialog("", new Window.WindowStyle(font, Color.WHITE, new TextureRegionDrawable(new TextureRegion(dialogBg))));
        tipsDialog.setBounds(0,0,720*scale, 1280*scale);
        tipsDialog.clear();
        tipsDialog.row().colspan(2);
        Table bTable = returnTipsButtonsTable();
        tipsDialog.add(bTable).align(Align.center).height(Gdx.graphics.getHeight()/10).colspan(2);//.padBottom(Gdx.graphics.getHeight()-bTable.getHeight())
        tipsDialog.row();
        Table mTable = returnTipsMechanicsTable();
        tipsDialog.add(mTable).width(Gdx.graphics.getWidth()).height(Gdx.graphics.getHeight()/10*9).colspan(2);
        //tipsDialog.row();
    }
    public Dialog returnTipsDialog(){
        return  tipsDialog;
    };
    public void createTipsButtonTable(){
        this.table_buttons = new Table();

        bMechanics = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bMechanics"), buttonStyleManager.returnTextButtonStyle(textButtonStyleMechanics));
        bEnemies = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bEnemies"), buttonStyleManager.returnTextButtonStyle(textButtonStyleEnemies));
        bTowers = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bTowers"), buttonStyleManager.returnTextButtonStyle(textButtonStyleTowers));
        //bUpgrades = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bUpgrades"), buttonStyleManager.returnTextButtonStyle(textButtonStyleMechanis));
        bBack = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bBackTips"), buttonStyleManager.returnTextButtonStyle(textButtonStyleBack));
        bMechanics.getLabel();
        table_buttons.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/10+28*scale);
        table_buttons.add(bMechanics).height(Gdx.graphics.getHeight()/10+28*scale);
        table_buttons.add(bEnemies).height(Gdx.graphics.getHeight()/10+28*scale);
        table_buttons.add(bTowers).height(Gdx.graphics.getHeight()/10+28*scale);
        table_buttons.add(bBack).height(Gdx.graphics.getHeight()/10+28*scale);

    }
    public void createTipsMechanicsTable(){
        this.table_mechanics = new Table();
        //TODO textfield out, label in
        //table_mechanics.debug();
        table_mechanics.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/10*8);
        /*textFieldStyleManager.setTextFieldStyle(textFieldStyle, text_tips, font, "empty_text", Color.WHITE);
        //tDifficulty = new TextField(languageManager.getValue(languageManager.getLanguage(), "tDifficulty"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tDifficultyDescription = new TextField(languageManager.getValue(languageManager.getLanguage(), "tDifficultyDescription"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tCurrency = new TextField(languageManager.getValue(languageManager.getLanguage(), "tCurrency"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tGold = new TextField(languageManager.getValue(languageManager.getLanguage(), "tGoldDescription"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tDiamonds = new TextField(languageManager.getValue(languageManager.getLanguage(), "tDiamondsDescription"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tShop = new TextField(languageManager.getValue(languageManager.getLanguage(), "tShop"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tShopDescription = new TextField(languageManager.getValue(languageManager.getLanguage(), "tShopDescription"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tObstacles = new TextField(languageManager.getValue(languageManager.getLanguage(), "tObstacles"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        tObstaclesDescription = new TextField(languageManager.getValue(languageManager.getLanguage(), "tObstaclesDescription"), textFieldStyleManager.returnTextFieldStyle(textFieldStyle));
        */
        table_mechanics.row();
        table_mechanics.add(new Label(languageManager.getValue(languageManager.getLanguage(), "tDifficulty"), bigLabelStyle)).width(Gdx.graphics.getWidth());
        //table_mechanics.add(tDifficulty).width(Gdx.graphics.getWidth());
        table_mechanics.row();
        table_mechanics.add(new Label(languageManager.getValue(languageManager.getLanguage(), "tDifficultyDescription"), labelStyle)).width(Gdx.graphics.getWidth());
        table_mechanics.row();
        //table_mechanics.add(); new Image rozdzielacz
        table_mechanics.row();
        table_mechanics.add(new Label(languageManager.getValue(languageManager.getLanguage(), "tCurrency"), bigLabelStyle)).width(Gdx.graphics.getWidth());
        table_mechanics.row();
        table_mechanics.add(new Label(languageManager.getValue(languageManager.getLanguage(), "tGoldDescription"), labelStyle)).width(Gdx.graphics.getWidth());
        table_mechanics.row();
        table_mechanics.add(new Label(languageManager.getValue(languageManager.getLanguage(), "tDiamondsDescription"), labelStyle)).width(Gdx.graphics.getWidth());
        table_mechanics.row();
        //table_mechanics.add(); new Image rozdzielacz
        table_mechanics.row();
        table_mechanics.add(new Label(languageManager.getValue(languageManager.getLanguage(), "tShop"), bigLabelStyle)).width(Gdx.graphics.getWidth());
        table_mechanics.row();
        table_mechanics.add(new Label(languageManager.getValue(languageManager.getLanguage(), "tShopDescription"), labelStyle)).width(Gdx.graphics.getWidth());
        table_mechanics.row();
        //table_mechanics.add(); new Image rozdzielacz
        table_mechanics.row();
        table_mechanics.add(new Label(languageManager.getValue(languageManager.getLanguage(), "tObstacles"), bigLabelStyle)).width(Gdx.graphics.getWidth());
        table_mechanics.row();
        table_mechanics.add(new Label(languageManager.getValue(languageManager.getLanguage(), "tObstaclesDescription"), labelStyle)).width(Gdx.graphics.getWidth());;
        table_mechanics.row();
        table_mechanics.add(new Label(languageManager.getValue(languageManager.getLanguage(), "tEvent"), bigLabelStyle)).width(Gdx.graphics.getWidth());
        table_mechanics.row();
        table_mechanics.add(new Label(languageManager.getValue(languageManager.getLanguage(), "tEventDescription"), labelStyle)).width(Gdx.graphics.getWidth());;
        table_mechanics.row();

    }

    public void createTipsEnemiesTable(){
        this.table_enemies = new Table();

        table_enemies.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/10*8);
        table_enemies.columnDefaults(6);
        table_enemies.row();
        table_enemies.add(new Label("", bigLabelStyle)).height(0).colspan(1);
        table_enemies.add(new Label("", bigLabelStyle)).height(0).colspan(1);
        table_enemies.add(new Label("", bigLabelStyle)).height(0).colspan(1);
        table_enemies.add(new Label("", bigLabelStyle)).height(0).colspan(1);
        table_enemies.add(new Label("", bigLabelStyle)).height(0).colspan(1);
        table_enemies.add(new Label("", bigLabelStyle)).height(0).colspan(1);
        table_enemies.row();
        table_enemies.add(new Label(languageManager.getValue(languageManager.getLanguage(), "tEnemies"), bigLabelStyle)).width(Gdx.graphics.getWidth()).colspan(6).padLeft(8*scale);
        table_enemies.row();
        table_enemies.add(new Label(languageManager.getValue(languageManager.getLanguage(), "tEnemiesDescription"), labelStyle)).width(Gdx.graphics.getWidth()).height(Gdx.graphics.getHeight()/10*2-64*scale).colspan(6).padLeft(8*scale);
        table_enemies.row();
        table_enemies.add(new Image(images_tips2, "separator")).width(Gdx.graphics.getWidth()).height(16*scale).colspan(6).height(18*scale);
        table_enemies.row();
        table_enemies.add(new Image(images_tips2, "warrior")).width(80*scale).height(80*scale).align(Align.left).colspan(1).padLeft(16);
        table_enemies.add(new Label(languageManager.getValue(languageManager.getLanguage(), "tWarrior"), labelStyle)).height(80*scale).align(Align.left).colspan(5).expandX().padLeft(30*scale);
        table_enemies.row();
        table_enemies.add(new Image(images_tips2, "objectSeparatorLeft")).width(Gdx.graphics.getWidth()).height(16*scale).colspan(6).height(4*scale);
        table_enemies.row();
        table_enemies.add(new Label(languageManager.getValue(languageManager.getLanguage(), "tAssassin"), labelStyle)).height(80*scale).align(Align.right).colspan(5).expandX().padRight(-24*scale);
        table_enemies.add(new Image(images_tips2, "assassin")).width(80*scale).height(80*scale).align(Align.right).colspan(1).padLeft(-512*scale).padRight(16);
        table_enemies.row();
        table_enemies.add(new Image(images_tips2, "objectSeparatorRight")).width(Gdx.graphics.getWidth()).height(16*scale).colspan(6).height(4*scale);
        table_enemies.row();
        table_enemies.add(new Image(images_tips2, "incubus")).width(80*scale).height(80*scale).align(Align.left).colspan(1).padLeft(16);
        table_enemies.add(new Label(languageManager.getValue(languageManager.getLanguage(), "tIncubus"), labelStyle)).height(80*scale).align(Align.left).colspan(5).expandX().padLeft(30*scale);
        table_enemies.row();
        table_enemies.add(new Image(images_tips2, "objectSeparatorLeft")).width(Gdx.graphics.getWidth()).height(16*scale).colspan(6).height(4*scale);
        table_enemies.row();
        table_enemies.add(new Label(languageManager.getValue(languageManager.getLanguage(), "tBlob"), labelStyle)).height(80*scale).align(Align.right).colspan(5).expandX().padRight(-24*scale);
        table_enemies.add(new Image(images_tips2, "blob")).height(80*scale).align(Align.right).colspan(1).padLeft(-512*scale).padRight(16);
        table_enemies.row();
        table_enemies.add(new Image(images_tips2, "objectSeparatorRight")).width(Gdx.graphics.getWidth()).height(16*scale).colspan(6).height(4*scale);
        table_enemies.row();
        table_enemies.add(new Image(images_tips2, "summoner")).width(80*scale).height(80*scale).align(Align.left).colspan(1).padLeft(16);
        table_enemies.add(new Label(languageManager.getValue(languageManager.getLanguage(), "tSummoner"), labelStyle)).height(80*scale).align(Align.left).colspan(5).expandX().padLeft(30*scale);
        table_enemies.row();
        table_enemies.add(new Image(images_tips2, "objectSeparatorLeft")).width(Gdx.graphics.getWidth()).height(16*scale).colspan(6).height(4*scale);
        table_enemies.row();
        table_enemies.add(new Label(languageManager.getValue(languageManager.getLanguage(), "tGladiator"), labelStyle)).height(80*scale).align(Align.right).colspan(5).expandX().padRight(-24*scale);
        table_enemies.add(new Image(images_tips2, "gladiator")).width(80*scale).height(80*scale).align(Align.right).colspan(1).padLeft(-512*scale).padRight(16);
        //table_enemies.debug();
    }

    /*public void createTipsUpgradesTable(){
        this.table_upgrades = new Table();

        table_upgrades.setBounds(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/10*8);
    }*/

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

    public void setChosenButton(String name){
        switch (name){
            case "bMechanics":{
                bMechanics.setStyle(textButtonStyleMechanicsChosen);
                bEnemies.setStyle(textButtonStyleEnemies);
                bTowers.setStyle(textButtonStyleTowers);
            }break;
            case "bEnemies":{
                bMechanics.setStyle(textButtonStyleMechanics);
                bEnemies.setStyle(textButtonStyleEnemiesChosen);
                bTowers.setStyle(textButtonStyleTowers);
            }break;
            case "bTowers":{
                bMechanics.setStyle(textButtonStyleMechanics);
                bEnemies.setStyle(textButtonStyleEnemies);
                bTowers.setStyle(textButtonStyleTowersChosen);
            }break;
        }
    }

    public void initSettingUI(){
        taTips = new TextureAtlas("assets/buttons/buttons_default.pack");
        images_tips = new Skin(taTips);

        taTips2 = new TextureAtlas("assets/icons/tipsImages.pack");
        images_tips2 = new Skin(taTips2);

        taTextTips = new TextureAtlas("assets/buttons/text_credits.pack");
        text_tips = new Skin(taTextTips);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Silkscreen.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.color = Color.WHITE;
        parameter.characters = "ąćęłńóśżźabcdefghijklmnopqrstuvwxyzĄĆĘÓŁŃŚŻŹABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        bigFont = new BitmapFont();
        bigFont = generator.generateFont(parameter);
        //textFieldStyle = new TextField.TextFieldStyle();
        textButtonStyleMechanics = new TextButton.TextButtonStyle();
        textButtonStyleBack = new TextButton.TextButtonStyle();
        textButtonStyleEnemies = new TextButton.TextButtonStyle();
        textButtonStyleTowers = new TextButton.TextButtonStyle();
        textButtonStyleMechanicsChosen = new TextButton.TextButtonStyle();
        textButtonStyleEnemiesChosen = new TextButton.TextButtonStyle();
        textButtonStyleTowersChosen = new TextButton.TextButtonStyle();
        buttonStyleManager.setTextButtonStyle(textButtonStyleMechanics, images_tips2, font, "mechanics_up", "mechanics_down");
        buttonStyleManager.setTextButtonStyle(textButtonStyleEnemies, images_tips2, font, "enemies_up", "enemies_down");
        buttonStyleManager.setTextButtonStyle(textButtonStyleTowers, images_tips2, font, "towers_up", "towers_down");
        buttonStyleManager.setTextButtonStyle(textButtonStyleBack, images_tips2, font, "back_up", "back_down");
        buttonStyleManager.setTextButtonStyle(textButtonStyleMechanicsChosen, images_tips2, font, "mechanics_down", "mechanics_down");
        buttonStyleManager.setTextButtonStyle(textButtonStyleEnemiesChosen, images_tips2, font, "enemies_down", "enemies_down");
        buttonStyleManager.setTextButtonStyle(textButtonStyleTowersChosen, images_tips2, font, "towers_down", "towers_down");
        labelStyle = new Label.LabelStyle(font, Color.WHITE);
        bigLabelStyle = new Label.LabelStyle(bigFont, Color.WHITE);
    }

    public void initListeners(){
        //TODO wersja robocza, może tworzyć dialog w tej klasie i usuwać dodawać tablice tutaj do dialogu
        bMechanics.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tipsDialog.clear();
                tipsDialog.setBounds(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                tipsDialog.row().colspan(2);
                Table bTable = returnTipsButtonsTable();
                tipsDialog.add(bTable);//.padBottom(Gdx.graphics.getHeight()-bTable.getHeight())
                tipsDialog.row();
                Table mTable = returnTipsMechanicsTable();
                System.out.println("TETS:" + bTable.getHeight() + " I " + mTable.getHeight());
                tipsDialog.add(mTable).padBottom(512+128+32+16+4); //<- temp
                setChosenButton("bMechanics");
            }
        });
        bEnemies.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tipsDialog.clear();
                tipsDialog.add(returnTipsButtonsTable());
                tipsDialog.row();
                tipsDialog.add(returnTipsEnemiesTable());
                setChosenButton("bEnemies");
            }
        });
        bTowers.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tipsDialog.clear();
                tipsDialog.add(returnTipsButtonsTable());
                tipsDialog.row();
                tipsDialog.add(returnTipsTowersTable());
                setChosenButton("bTowers");
            }
        });
        /*bUpgrades.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tipsDialog.clear();
                tipsDialog.add(returnTipsButtonsTable());
                tipsDialog.row();
                tipsDialog.add(returnTipsUpgradesTable());
            }
        });*/

        bBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pm.dispose();
                scaledpm.dispose();
                tipsDialog.hide();
                tipsDialog.remove();
                base.setState(Base.State.Resumed);
            }
        });
    }

}
