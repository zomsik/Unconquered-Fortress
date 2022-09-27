package com.game.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.game.Entity.Base;
import org.json.JSONObject;

public class StatsTableManager {
    private Base base;
    private float scale;

    private Table statsTable, operationTable, upgradeTable, multipliersTable, obstacleTable;

    private TextFieldStyleManager textFieldStyleManager;
    private TextField hpTextField, hpTextValue, goldTextField, goldTextValue, diamondTextField, diamondTextValue, waveTextField, waveTextValue, difficultyTextField, difficultyTextValue;
    private TextField operationPriceTextField, operationPriceTextValue, operationTitleTextField, operationTitleTextValue, operationDmgTextField, operationDmgTextValue, operationRangeTextField, operationRangeTextValue, operationReloadTextField, operationReloadTextValue, operationSplashTextField, operationSplashTextValue;
    private TextField upgradePriceTextField, upgradePriceTextValue,upgradeTitleTextField, upgradeTitleTextValue, upgradeLvlTextField, upgradeLvlTextValue, upgradeDmgTextField, upgradeDmgTextValue, upgradeRangeTextField, upgradeRangeTextValue, upgradeReloadTextField, upgradeReloadTextValue, upgradeSplashTextField, upgradeSplashTextValue;
    private TextField damageMultiplier, damageMultiplierValue;
    private TextField obstacleUses, obstacleUsesValue;
    private TextField.TextFieldStyle statsTextFieldStyle, rightStatsTextFieldStyle, leftStatsTextFieldStyle;
    private Skin images, images_stats;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font;

    private int infoToDisplay;
    private String infoToDisplayName;
    private JSONObject infoToDisplayObjectNow,infoToDisplayObjectUpgraded;

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

        generator = new FreeTypeFontGenerator(Gdx.files.internal("Silkscreen.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (10*scale);
        parameter.color = Color.WHITE;
        parameter.characters = "ąćęłńóśżźabcdefghijklmnopqrstuvwxyzĄĆĘÓŁŃŚŻŹABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        font = new BitmapFont();
        font = generator.generateFont(parameter);

        images = new Skin(new TextureAtlas("assets/buttons/buttons_settings.pack"));
        images_stats = new Skin(new TextureAtlas("assets/buttons/statsCover.pack"));

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
        waveTextValue = new TextField("temp", textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        waveTextField.setAlignment(Align.center);
        waveTextValue.setAlignment(Align.center);

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


        damageMultiplier = new TextField("dmgMultiplier: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        damageMultiplierValue = new TextField("1.0", textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));

        damageMultiplier.setAlignment(Align.center);
        damageMultiplierValue.setAlignment(Align.center);

        multipliersTable.setBounds(Gdx.graphics.getWidth()-224*scale,(Gdx.graphics.getHeight()-Gdx.graphics.getWidth()/30*16)/2+48*scale+32*scale+350*scale,224*scale,204*scale);
        multipliersTable.setBackground(new TextureRegionDrawable(new TextureRegion(table_statsBackground)));
        multipliersTable.row().padBottom(4*scale);
        multipliersTable.add(damageMultiplier).width((200*scale)/2-6*scale);
        multipliersTable.add(new Image(images_stats, "middleStatsCover"));
        multipliersTable.add(damageMultiplierValue).width((200*scale)/2-6*scale).padRight(2*scale);
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


    public void setInfoToDisplay(int infoToDisplay, JSONObject towerNow, JSONObject towerUpgrade, String name) {
        this.infoToDisplay = infoToDisplay;
        this.infoToDisplayName = name;
        this.infoToDisplayObjectNow = towerNow;
        this.infoToDisplayObjectUpgraded = towerUpgrade;
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
                setMultipliersTable();
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

    public void setMultipliersTable(){
        JSONObject multipliers = base.getMultipliers();

        damageMultiplierValue.setText(String.valueOf(multipliers.getFloat("damageMultiplier")));;
    }

    public void setObstacleTable(){

        obstacleUsesValue.setText(String.valueOf(base.getUsesLeft()));
    }


    public void setOperationTable(String name, JSONObject towerNow){
        operationTitleTextValue.setText(name);
        operationPriceTextValue.setText(String.valueOf(Math.round(towerNow.getInt("cost")*base.getMultipliers().getFloat("costMultiplier"))));
        operationDmgTextValue.setText(String.valueOf((int)towerNow.getFloat("dmg")));
        operationRangeTextValue.setText(String.valueOf((int)towerNow.getFloat("range")));
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
            upgradeRangeTextValue.setText((int)towerNow.getFloat("range") + " -> " + (int)towerUpgraded.getFloat("range"));
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

        if (infoToDisplay==5)
        {
            obstacleUsesValue.setText(String.valueOf(base.getUsesLeft()));
        }

    }

}
