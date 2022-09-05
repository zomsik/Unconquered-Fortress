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

public class StatsTableManager {
    private Base base;
    private float scale;

    private Table statsTable, operationTable, upgradeTable;

    private TextFieldStyleManager textFieldStyleManager;
    private TextField hpTextField, hpTextValue, goldTextField, goldTextValue, diamondTextField, diamondTextValue, waveTextField, waveTextValue, difficultyTextField, difficultyTextValue;
    private TextField operationTitleTextField, operationTitleTextValue;
    private TextField upgradeTitleTextField, upgradeTitleTextValue, upgradeLvlTextField, upgradeLvlTextValue;

    private TextField.TextFieldStyle statsTextFieldStyle, rightStatsTextFieldStyle, leftStatsTextFieldStyle;
    private Skin images, images_stats;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font;

    private int infoToDisplay, infoToDisplayLvl;
    private String infoToDisplayName;

    public StatsTableManager(Base base, float scale, LanguageManager languageManager){
        this.base = base;
        this.scale = scale;

        this.infoToDisplay = 0;

        statsTable = new Table();
        operationTable = new Table();
        upgradeTable = new Table();

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
        statsTable.add(hpTextField).width((200*scale)/2-6*scale);
        statsTable.add(new Image(images_stats, "middleStatsCover")).width(12*scale);
        statsTable.add(hpTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        statsTable.row().padBottom(4*scale);
        statsTable.add(waveTextField).width((200*scale)/2-6*scale);
        statsTable.add(new Image(images_stats, "middleStatsCover")).width(12*scale);
        statsTable.add(waveTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        statsTable.row().padBottom(4*scale);;
        statsTable.add(goldTextField).width((200*scale)/2-6*scale);
        statsTable.add(new Image(images_stats, "middleStatsCover")).width(12*scale);
        statsTable.add(goldTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        statsTable.row().padBottom(4*scale);;
        statsTable.add(diamondTextField).width((200*scale)/2-6*scale);
        statsTable.add(new Image(images_stats, "middleStatsCover")).width(12*scale);
        statsTable.add(diamondTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        statsTable.row().padBottom(4*scale);;
        statsTable.add(difficultyTextField).width((200*scale)/2-6*scale);
        statsTable.add(new Image(images_stats, "middleStatsCover")).width(12*scale);
        statsTable.add(difficultyTextValue).width((200*scale)/2-6*scale).padRight(2*scale);

        //Operation Table

        operationTitleTextField = new TextField("Operacja: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        operationTitleTextValue = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        operationTitleTextField.setAlignment(Align.center);
        operationTitleTextValue.setAlignment(Align.center);

        operationTable.setBounds(Gdx.graphics.getWidth()-224*scale,(Gdx.graphics.getHeight()-Gdx.graphics.getWidth()/30*16)/2+48*scale+32*scale+350*scale,224*scale,204*scale);
        operationTable.setBackground(new TextureRegionDrawable(new TextureRegion(table_statsBackground)));
        operationTable.add(operationTitleTextField).width((200*scale)/2-6*scale);
        operationTable.add(new Image(images_stats, "middleStatsCover")).width(12*scale);
        operationTable.add(operationTitleTextValue).width((200*scale)/2-6*scale).padRight(2*scale);

        // Upgrade Table

        upgradeTitleTextField = new TextField("Upgrade: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        upgradeTitleTextValue = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        upgradeLvlTextField = new TextField("Lvl: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        upgradeLvlTextValue = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));

        upgradeTitleTextField.setAlignment(Align.center);
        upgradeTitleTextValue.setAlignment(Align.center);
        upgradeLvlTextField.setAlignment(Align.center);
        upgradeLvlTextValue.setAlignment(Align.center);

        upgradeTable.setBounds(Gdx.graphics.getWidth()-224*scale,(Gdx.graphics.getHeight()-Gdx.graphics.getWidth()/30*16)/2+48*scale+32*scale+350*scale,224*scale,204*scale);
        upgradeTable.setBackground(new TextureRegionDrawable(new TextureRegion(table_statsBackground)));
        upgradeTable.add(upgradeTitleTextField).width((200*scale)/2-6*scale);
        upgradeTable.add(new Image(images_stats, "middleStatsCover")).width(12*scale);
        upgradeTable.add(upgradeTitleTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        upgradeTable.row().padBottom(4*scale);
        upgradeTable.add(upgradeLvlTextField).width((200*scale)/2-6*scale);
        upgradeTable.add(new Image(images_stats, "middleStatsCover")).width(12*scale);
        upgradeTable.add(upgradeLvlTextValue).width((200*scale)/2-6*scale).padRight(2*scale);

    }

    public int getInfoToDisplay() {
        return infoToDisplay;
    }

    public void setInfoToDisplay(int infoToDisplay, int lvl, String name) {
        this.infoToDisplay = infoToDisplay;
        this.infoToDisplayLvl = lvl;
        this.infoToDisplayName = name;
    }


    public Table getInfoTable()
    {
        Table t = statsTable;
        switch (infoToDisplay) {
            case 0 -> t = statsTable;
            case 1 -> {
                setOperationTable(infoToDisplayName);
                t = operationTable;
            }
            case 2 -> {
                setUpgradeTable(infoToDisplayName,infoToDisplayLvl);
                t = upgradeTable;
            }

        }
        return t;
    }

    public Table getStatsTable(){

        return statsTable;
    }


    public void setOperationTable(String name){
        operationTitleTextValue.setText(name);
    }

    public void setUpgradeTable(String name, int lvl){
        upgradeTitleTextValue.setText(name);
        upgradeLvlTextValue.setText(lvl + " -> "+ (lvl+1));
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
    }

}
