package com.game.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
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
import org.json.JSONObject;

import java.util.ArrayList;

public class StatsTableManager {
    private Base base;
    private float scale;

    private Table statsTable, operationTable, upgradeTable, multipliersTable, obstacleTable;
    private TextButton buttonUp, buttonDown;
    private TextButton.TextButtonStyle textButtonStyle_buttonUp, textButtonStyle_buttonDown;
    private ButtonStyleManager buttonStyleManager;
    private TextFieldStyleManager textFieldStyleManager;
    private TextField hpTextField, hpTextValue, goldTextField, goldTextValue, diamondTextField, diamondTextValue, waveTextField, waveTextValue,enemiesTextField, enemiesTextValue, difficultyTextField, difficultyTextValue;
    private TextField operationPriceTextField, operationPriceTextValue, operationTitleTextField, operationTitleTextValue, operationDmgTextField, operationDmgTextValue, operationRangeTextField, operationRangeTextValue, operationReloadTextField, operationReloadTextValue, operationSplashTextField, operationSplashTextValue;
    private TextField upgradePriceTextField, upgradePriceTextValue,upgradeTitleTextField, upgradeTitleTextValue, upgradeLvlTextField, upgradeLvlTextValue, upgradeDmgTextField, upgradeDmgTextValue, upgradeRangeTextField, upgradeRangeTextValue, upgradeReloadTextField, upgradeReloadTextValue, upgradeSplashTextField, upgradeSplashTextValue;
    private TextField multipliersTableTitle, multipliersTableTextField0, multipliersTableTextFieldValue0, multipliersTableTextField1, multipliersTableTextFieldValue1, multipliersTableTextField2, multipliersTableTextFieldValue2, multipliersTableTextField3, multipliersTableTextFieldValue3, multipliersTableTextField4, multipliersTableTextFieldValue4;
    private TextField obstacleUses, obstacleUsesValue;
    private TextField.TextFieldStyle statsTextFieldStyle, rightStatsTextFieldStyle, leftStatsTextFieldStyle;
    private Skin images, images_stats, images_buttons;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font;

    private int infoToDisplay;
    private String infoToDisplayName;
    private JSONObject infoToDisplayObjectNow,infoToDisplayObjectUpgraded;

    private Image[] middleStatsCoverArr;



    private int multipliersPage;

    public StatsTableManager(Base base, float scale, LanguageManager languageManager){
        this.base = base;
        this.scale = scale;

        this.infoToDisplay = 0;
        this.infoToDisplayObjectNow = null;
        this.infoToDisplayObjectUpgraded = null;

        statsTable = new Table();
        operationTable = new Table();
        upgradeTable = new Table();
        multipliersTable = new Table();
        obstacleTable = new Table();

        buttonStyleManager = new ButtonStyleManager();
        textButtonStyle_buttonUp = new TextButton.TextButtonStyle();
        textButtonStyle_buttonDown = new TextButton.TextButtonStyle();

        generator = new FreeTypeFontGenerator(Gdx.files.internal("Silkscreen.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (10*scale);
        parameter.color = Color.WHITE;
        parameter.characters = "ąćęłńóśżźabcdefghijklmnopqrstuvwxyzĄĆĘÓŁŃŚŻŹABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        font = new BitmapFont();
        font = generator.generateFont(parameter);

        images = new Skin(new TextureAtlas("assets/buttons/buttons_settings.pack"));
        images_stats = new Skin(new TextureAtlas("assets/buttons/statsCover.pack"));
        images_buttons = new Skin(new TextureAtlas("assets/icons/icons_profile.pack"));

        textFieldStyleManager = new TextFieldStyleManager();
        statsTextFieldStyle = new TextField.TextFieldStyle();
        leftStatsTextFieldStyle = new TextField.TextFieldStyle();
        rightStatsTextFieldStyle = new TextField.TextFieldStyle();


        textFieldStyleManager.setTextFieldStyle(statsTextFieldStyle, images, font, "textBar", Color.WHITE);
        textFieldStyleManager.setTextFieldStyle(rightStatsTextFieldStyle, images_stats, font, "rightStatsCover", Color.WHITE);
        textFieldStyleManager.setTextFieldStyle(leftStatsTextFieldStyle, images_stats, font, "leftStatsCover", Color.WHITE);


        //Stats Table

        hpTextField = new TextField(languageManager.getValue(languageManager.getLanguage(), "hp_field"), textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        hpTextValue = new TextField(String.valueOf(base.getHealth()), textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        hpTextField.setAlignment(Align.center);
        hpTextValue.setAlignment(Align.center);

        goldTextField = new TextField(languageManager.getValue(languageManager.getLanguage(), "gold_field"), textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        goldTextValue = new TextField(String.valueOf(base.getMoney()), textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        goldTextField.setAlignment(Align.center);
        goldTextValue.setAlignment(Align.center);

        waveTextField = new TextField(languageManager.getValue(languageManager.getLanguage(), "wave_field"), textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        waveTextValue = new TextField("0", textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        waveTextField.setAlignment(Align.center);
        waveTextValue.setAlignment(Align.center);

        enemiesTextField = new TextField(languageManager.getValue(languageManager.getLanguage(), "enemy_field"), textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        enemiesTextValue = new TextField("0", textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        enemiesTextField.setAlignment(Align.center);
        enemiesTextValue.setAlignment(Align.center);

        diamondTextField = new TextField(languageManager.getValue(languageManager.getLanguage(), "diamonds_field"), textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        diamondTextValue = new TextField(String.valueOf(base.getDiamonds()), textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        diamondTextField.setAlignment(Align.center);
        diamondTextValue.setAlignment(Align.center);

        difficultyTextField = new TextField(languageManager.getValue(languageManager.getLanguage(), "short_difficulty_field"), textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        difficultyTextValue = new TextField(languageManager.getValue(languageManager.getLanguage(), base.getDifficulty()), textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        difficultyTextField.setAlignment(Align.center);
        difficultyTextValue.setAlignment(Align.center);

        Texture table_statsBackground = new Texture(new FileHandle("assets/statsBackground.png"));

        statsTable.setBounds(Gdx.graphics.getWidth()-224*scale,(Gdx.graphics.getHeight()-Gdx.graphics.getWidth()/30*16)/2+48*scale+32*scale+350*scale,224*scale,204*scale);
        statsTable.setBackground(new TextureRegionDrawable(new TextureRegion(table_statsBackground)));
        statsTable.row().padBottom(4*scale);
        statsTable.add(hpTextField).width((200*scale)/2-6*scale);
        statsTable.add(new Image(images_stats, "middleStatsCover"));
        statsTable.add(hpTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        statsTable.row().padBottom(4*scale);
        statsTable.add(waveTextField).width((200*scale)/2-6*scale);
        statsTable.add(new Image(images_stats, "middleStatsCover"));
        statsTable.add(waveTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        statsTable.row().padBottom(4*scale);
        statsTable.add(enemiesTextField).width((200*scale)/2-6*scale);
        statsTable.add(new Image(images_stats, "middleStatsCover"));
        statsTable.add(enemiesTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        statsTable.row().padBottom(4*scale);;
        statsTable.add(goldTextField).width((200*scale)/2-6*scale);
        statsTable.add(new Image(images_stats, "middleStatsCover"));
        statsTable.add(goldTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        statsTable.row().padBottom(4*scale);;
        statsTable.add(diamondTextField).width((200*scale)/2-6*scale);
        statsTable.add(new Image(images_stats, "middleStatsCover"));
        statsTable.add(diamondTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        statsTable.row().padBottom(4*scale);;
        statsTable.add(difficultyTextField).width((200*scale)/2-6*scale);
        statsTable.add(new Image(images_stats, "middleStatsCover"));
        statsTable.add(difficultyTextValue).width((200*scale)/2-6*scale).padRight(2*scale);

        //Operation Table

        operationTitleTextField = new TextField("Upgrade: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        operationTitleTextValue = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        operationPriceTextField = new TextField("Price: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        operationPriceTextValue = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        operationDmgTextField = new TextField("Damage: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        operationDmgTextValue = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        operationRangeTextField = new TextField("Range: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        operationRangeTextValue = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        operationReloadTextField = new TextField("Reload: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        operationReloadTextValue = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        operationSplashTextField = new TextField("Splash: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        operationSplashTextValue = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));

        operationTitleTextField.setAlignment(Align.center);
        operationTitleTextValue.setAlignment(Align.center);
        operationPriceTextField.setAlignment(Align.center);
        operationPriceTextValue.setAlignment(Align.center);
        operationDmgTextField.setAlignment(Align.center);
        operationDmgTextValue.setAlignment(Align.center);
        operationRangeTextField.setAlignment(Align.center);
        operationRangeTextValue.setAlignment(Align.center);
        operationReloadTextField.setAlignment(Align.center);
        operationReloadTextValue.setAlignment(Align.center);
        operationSplashTextField.setAlignment(Align.center);
        operationSplashTextValue.setAlignment(Align.center);

        operationTable.setBounds(Gdx.graphics.getWidth()-224*scale,(Gdx.graphics.getHeight()-Gdx.graphics.getWidth()/30*16)/2+48*scale+32*scale+350*scale,224*scale,204*scale);
        operationTable.setBackground(new TextureRegionDrawable(new TextureRegion(table_statsBackground)));
        operationTable.row().padBottom(4*scale);
        operationTable.add(operationTitleTextField).width((200*scale)/2-6*scale);
        operationTable.add(new Image(images_stats, "middleStatsCover"));
        operationTable.add(operationTitleTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        operationTable.row().padBottom(4*scale);
        operationTable.add(operationPriceTextField).width((200*scale)/2-6*scale);
        operationTable.add(new Image(images_stats, "middleStatsCover"));
        operationTable.add(operationPriceTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        operationTable.row().padBottom(4*scale);
        operationTable.add(operationDmgTextField).width((200*scale)/2-6*scale);
        operationTable.add(new Image(images_stats, "middleStatsCover"));
        operationTable.add(operationDmgTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        operationTable.row().padBottom(4*scale);
        operationTable.add(operationRangeTextField).width((200*scale)/2-6*scale);
        operationTable.add(new Image(images_stats, "middleStatsCover"));
        operationTable.add(operationRangeTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        operationTable.row().padBottom(4*scale);
        operationTable.add(operationReloadTextField).width((200*scale)/2-6*scale);
        operationTable.add(new Image(images_stats, "middleStatsCover"));
        operationTable.add(operationReloadTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        operationTable.row().padBottom(4*scale);
        operationTable.add(operationSplashTextField).width((200*scale)/2-6*scale);
        operationTable.add(new Image(images_stats, "middleStatsCover"));
        operationTable.add(operationSplashTextValue).width((200*scale)/2-6*scale).padRight(2*scale);

        // Upgrade Table
        upgradePriceTextField = new TextField("Price: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        upgradePriceTextValue = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        upgradeTitleTextField = new TextField("Upgrade: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        upgradeTitleTextValue = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        upgradeLvlTextField = new TextField("Lvl: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        upgradeLvlTextValue = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        upgradeDmgTextField = new TextField("Damage: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        upgradeDmgTextValue = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        upgradeRangeTextField = new TextField("Range: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        upgradeRangeTextValue = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        upgradeReloadTextField = new TextField("Reload: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        upgradeReloadTextValue = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        upgradeSplashTextField = new TextField("Splash: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        upgradeSplashTextValue = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));

        upgradeTitleTextField.setAlignment(Align.center);
        upgradeTitleTextValue.setAlignment(Align.center);
        upgradePriceTextField.setAlignment(Align.center);
        upgradePriceTextValue.setAlignment(Align.center);
        upgradeLvlTextField.setAlignment(Align.center);
        upgradeLvlTextValue.setAlignment(Align.center);
        upgradeDmgTextField.setAlignment(Align.center);
        upgradeDmgTextValue.setAlignment(Align.center);
        upgradeRangeTextField.setAlignment(Align.center);
        upgradeRangeTextValue.setAlignment(Align.center);
        upgradeReloadTextField.setAlignment(Align.center);
        upgradeReloadTextValue.setAlignment(Align.center);
        upgradeSplashTextField.setAlignment(Align.center);
        upgradeSplashTextValue.setAlignment(Align.center);

        upgradeTable.setBounds(Gdx.graphics.getWidth()-224*scale,(Gdx.graphics.getHeight()-Gdx.graphics.getWidth()/30*16)/2+48*scale+32*scale+350*scale,224*scale,204*scale);
        upgradeTable.setBackground(new TextureRegionDrawable(new TextureRegion(table_statsBackground)));
        upgradeTable.row().padBottom(4*scale);
        upgradeTable.add(upgradeTitleTextField).width((200*scale)/2-6*scale);
        upgradeTable.add(new Image(images_stats, "middleStatsCover"));
        upgradeTable.add(upgradeTitleTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        upgradeTable.row().padBottom(4*scale);
        upgradeTable.add(upgradePriceTextField).width((200*scale)/2-6*scale);
        upgradeTable.add(new Image(images_stats, "middleStatsCover"));
        upgradeTable.add(upgradePriceTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        upgradeTable.row().padBottom(4*scale);
        upgradeTable.add(upgradeLvlTextField).width((200*scale)/2-6*scale);
        upgradeTable.add(new Image(images_stats, "middleStatsCover"));
        upgradeTable.add(upgradeLvlTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        upgradeTable.row().padBottom(4*scale);
        upgradeTable.add(upgradeDmgTextField).width((200*scale)/2-6*scale);
        upgradeTable.add(new Image(images_stats, "middleStatsCover"));
        upgradeTable.add(upgradeDmgTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        upgradeTable.row().padBottom(4*scale);
        upgradeTable.add(upgradeRangeTextField).width((200*scale)/2-6*scale);
        upgradeTable.add(new Image(images_stats, "middleStatsCover"));
        upgradeTable.add(upgradeRangeTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        upgradeTable.row().padBottom(4*scale);
        upgradeTable.add(upgradeReloadTextField).width((200*scale)/2-6*scale);
        upgradeTable.add(new Image(images_stats, "middleStatsCover"));
        upgradeTable.add(upgradeReloadTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        upgradeTable.row().padBottom(4*scale);
        upgradeTable.add(upgradeSplashTextField).width((200*scale)/2-6*scale);
        upgradeTable.add(new Image(images_stats, "middleStatsCover"));
        upgradeTable.add(upgradeSplashTextValue).width((200*scale)/2-6*scale).padRight(2*scale);


        // Multipliers Table
        multipliersTableTitle = new TextField(null, textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        multipliersTableTextField0 = new TextField(null, textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        multipliersTableTextFieldValue0 = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        multipliersTableTextField1 = new TextField(null, textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        multipliersTableTextFieldValue1 = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        multipliersTableTextField2 = new TextField(null, textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        multipliersTableTextFieldValue2 = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        multipliersTableTextField3 = new TextField(null, textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        multipliersTableTextFieldValue3 = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        multipliersTableTextField4 = new TextField(null, textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        multipliersTableTextFieldValue4 = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));


        multipliersTableTitle.setAlignment(Align.center);
        multipliersTableTextField0.setAlignment(Align.center);
        multipliersTableTextFieldValue0.setAlignment(Align.center);
        multipliersTableTextField1.setAlignment(Align.center);
        multipliersTableTextFieldValue1.setAlignment(Align.center);
        multipliersTableTextField2.setAlignment(Align.center);
        multipliersTableTextFieldValue2.setAlignment(Align.center);
        multipliersTableTextField3.setAlignment(Align.center);
        multipliersTableTextFieldValue3.setAlignment(Align.center);
        multipliersTableTextField4.setAlignment(Align.center);
        multipliersTableTextFieldValue4.setAlignment(Align.center);


        middleStatsCoverArr = new Image[5];
        for (int i=0; i<5; i++)
            middleStatsCoverArr[i] = new Image(images_stats, "middleStatsCover");

        buttonStyleManager.setTextButtonStyle(textButtonStyle_buttonUp, images_buttons, font, "cloud", "cloud");
        buttonStyleManager.setTextButtonStyle(textButtonStyle_buttonDown, images_buttons, font, "local", "local");
        buttonUp = new TextButton(null, buttonStyleManager.returnTextButtonStyle(textButtonStyle_buttonUp));

        buttonDown = new TextButton(null, buttonStyleManager.returnTextButtonStyle(textButtonStyle_buttonDown));


        buttonUp.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (multipliersPage>0)
                {
                    multipliersPage--;
                    base.setShouldUpdateInfo(true);
                }
            }
        });

        buttonDown.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (multipliersPage<4)
                {
                    multipliersPage++;
                    base.setShouldUpdateInfo(true);
                }
            }
        });


        multipliersTable.setBounds(Gdx.graphics.getWidth()-224*scale,(Gdx.graphics.getHeight()-Gdx.graphics.getWidth()/30*16)/2+48*scale+32*scale+350*scale,224*scale,204*scale);
        multipliersTable.setBackground(new TextureRegionDrawable(new TextureRegion(table_statsBackground)));

        multipliersTable.row().padBottom(4*scale);
        multipliersTable.add(multipliersTableTitle).width(multipliersTable.getWidth()).colspan(3);
        multipliersTable.row().padBottom(4*scale);
        multipliersTable.add(multipliersTableTextField0).width((200*scale)/2-6*scale);
        multipliersTable.add(middleStatsCoverArr[0]);
        multipliersTable.add(multipliersTableTextFieldValue0).width((200*scale)/2-6*scale).padRight(2*scale);
        multipliersTable.row().padBottom(4*scale);
        multipliersTable.row().padBottom(4*scale);
        multipliersTable.add(multipliersTableTextField1).width((200*scale)/2-6*scale);
        multipliersTable.add(middleStatsCoverArr[1]);
        multipliersTable.add(multipliersTableTextFieldValue1).width((200*scale)/2-6*scale).padRight(2*scale);
        multipliersTable.row().padBottom(4*scale);
        multipliersTable.add(multipliersTableTextField2).width((200*scale)/2-6*scale);
        multipliersTable.add(middleStatsCoverArr[2]);
        multipliersTable.add(multipliersTableTextFieldValue2).width((200*scale)/2-6*scale).padRight(2*scale);
        multipliersTable.row().padBottom(4*scale);
        multipliersTable.add(multipliersTableTextField3).width((200*scale)/2-6*scale);
        multipliersTable.add(middleStatsCoverArr[3]);
        multipliersTable.add(multipliersTableTextFieldValue3).width((200*scale)/2-6*scale).padRight(2*scale);
        multipliersTable.row().padBottom(4*scale);
        multipliersTable.add(multipliersTableTextField4).width((200*scale)/2-6*scale);
        multipliersTable.add(middleStatsCoverArr[4]);
        multipliersTable.add(multipliersTableTextFieldValue4).width((200*scale)/2-6*scale).padRight(2*scale);
        multipliersTable.row().padBottom(4*scale);




        // Obstacle Table


        obstacleUses = new TextField("Uses left: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        obstacleUsesValue = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));

        obstacleUses.setAlignment(Align.center);
        obstacleUsesValue.setAlignment(Align.center);

        obstacleTable.setBounds(Gdx.graphics.getWidth()-224*scale,(Gdx.graphics.getHeight()-Gdx.graphics.getWidth()/30*16)/2+48*scale+32*scale+350*scale,224*scale,204*scale);
        obstacleTable.setBackground(new TextureRegionDrawable(new TextureRegion(table_statsBackground)));
        obstacleTable.row().padBottom(4*scale);
        obstacleTable.add(obstacleUses).width((200*scale)/2-6*scale);
        obstacleTable.add(new Image(images_stats, "middleStatsCover"));
        obstacleTable.add(obstacleUsesValue).width((200*scale)/2-6*scale).padRight(2*scale);
        obstacleTable.row().padBottom(4*scale);



    }

    public int getInfoToDisplay() {
        return infoToDisplay;
    }


    public TextButton getButtonUp()
    {
        return buttonUp;
    }

    public TextButton getButtonDown()
    {
        return buttonDown;
    }

    public void setInfoToDisplay(int infoToDisplay, JSONObject towerNow, JSONObject towerUpgrade, String name) {
        this.infoToDisplay = infoToDisplay;
        this.infoToDisplayName = name;
        this.infoToDisplayObjectNow = towerNow;
        this.infoToDisplayObjectUpgraded = towerUpgrade;
    }

    public int getMultipliersPage() {
        return multipliersPage;
    }

    public void setMultipliersPage(int multipliersPage) {
        this.multipliersPage = multipliersPage;
    }

    public Table getInfoTable()
    {
        Table t = statsTable;
        switch (infoToDisplay) {
            case 0 -> t = statsTable;
            case 1 -> {
                setOperationTable(infoToDisplayName, infoToDisplayObjectNow);
                t = operationTable;
            }
            case 2 -> {
                setUpgradeTable(infoToDisplayName,infoToDisplayObjectNow, infoToDisplayObjectUpgraded);
                t = upgradeTable;
            }
            case 4 -> {
                setMultipliersTable(multipliersPage);
                t = multipliersTable;
            }
            case 5 -> {
                setObstacleTable();
                t = obstacleTable;
            }

        }
        return t;
    }

    public Table getStatsTable(){

        return statsTable;
    }

    public void setMultipliersTable(int choice){
        JSONObject multipliers = base.getMultipliers();

        switch (choice)
        {
            //languageManager.getValue(languageManager.getLanguage(), "")
            case 0 -> {
                multipliersTableTitle.setText("Upgrady");
                multipliersTableTextField0.setText("hp:");
                multipliersTableTextFieldValue0.setText(String.valueOf(base.getHealth()));

                multipliersTableTextField1.setText("dmg:");
                multipliersTableTextFieldValue1.setText(String.valueOf(multipliers.getFloat("damageMultiplier")));

            }

            case 1 -> {
                multipliersTableTitle.setText("Obrażenia");

                multipliersTableTextField0.setText("diam:");
                multipliersTableTextFieldValue0.setText(String.valueOf(base.getDiamonds()));

                multipliersTableTextField1.setText("dmg:");
                multipliersTableTextFieldValue1.setText(String.valueOf(multipliers.getFloat("damageMultiplier")));

            }

        }


    }

    public void setObstacleTable(){

        obstacleUsesValue.setText(String.valueOf(base.getUsesLeft()));
    }


    public void setOperationTable(String name, JSONObject towerNow){
        operationTitleTextValue.setText(name);
        operationPriceTextValue.setText(String.valueOf(Math.round(towerNow.getInt("cost")*base.getMultipliers().getFloat("costMultiplier"))));
        operationDmgTextValue.setText(String.valueOf((int)towerNow.getFloat("dmg")));
        operationRangeTextValue.setText(String.valueOf((int)(towerNow.getFloat("range")*base.getMultipliers().getFloat("rangeMultiplier"+name+"Tower"))));
        operationReloadTextValue.setText(String.valueOf((int)towerNow.getFloat("reload")));
        operationSplashTextValue.setText(String.valueOf((int)towerNow.getFloat("splash")));
    }

    public void setUpgradeTable(String name, JSONObject towerNow, JSONObject towerUpgraded){
        upgradeTitleTextValue.setText(name);
        if (towerUpgraded!=null)
        {
            upgradePriceTextValue.setText(String.valueOf(Math.round(towerUpgraded.getInt("cost")*base.getMultipliers().getFloat("upgradeCostMultiplier"))));
            upgradeLvlTextValue.setText(towerNow.getInt("lvl") + " -> " + towerUpgraded.getInt("lvl"));
            upgradeDmgTextValue.setText((int)towerNow.getFloat("dmg") + " -> " + (int)towerUpgraded.getFloat("dmg"));
            upgradeRangeTextValue.setText((int)(towerNow.getFloat("range")*base.getMultipliers().getFloat("rangeMultiplier"+name)) + " -> " + (int)(towerUpgraded.getFloat("range")*base.getMultipliers().getFloat("rangeMultiplier"+name)));
            upgradeReloadTextValue.setText((int)towerNow.getFloat("reload") + " -> " + (int)towerUpgraded.getFloat("reload"));
            upgradeSplashTextValue.setText((int)towerNow.getFloat("splash") + " -> " + (int)towerUpgraded.getFloat("splash"));

        }
        else
        {
            upgradePriceTextValue.setText("---");
            upgradeLvlTextValue.setText("Max");
            upgradeDmgTextValue.setText(String.valueOf(towerNow.getInt("dmg")));
            upgradeRangeTextValue.setText(String.valueOf((int)towerNow.getFloat("range")));
            upgradeReloadTextValue.setText(String.valueOf((int)towerNow.getFloat("reload")));
            upgradeSplashTextValue.setText(String.valueOf((int)towerNow.getFloat("splash")));
        }
    }


    public Table getOperationTable(){
        return operationTable;
    }

    public Table getUpgradeTable(){
        return upgradeTable;
    }


    public void update() {
        hpTextValue.setText(String.valueOf(base.getHealth()));
        goldTextValue.setText(String.valueOf(base.getMoney()));
        diamondTextValue.setText(String.valueOf(base.getDiamonds()));
        waveTextValue.setText(String.valueOf(base.getWave()));
        enemiesTextValue.setText(String.valueOf(base.getEnemiesLeft()));

        if (infoToDisplay==5)
        {
            obstacleUsesValue.setText(String.valueOf(base.getUsesLeft()));
        }

    }

}
