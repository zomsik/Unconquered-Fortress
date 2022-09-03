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

    private Table statsTable,newBuildingTable, upgradeTable;

    private TextFieldStyleManager textFieldStyleManager;
    private TextField hpTextField, hpTextValue, goldTextField, goldTextValue, newBuildingTextField, newBuildingTextValue;
    private TextField.TextFieldStyle statsTextFieldStyle, rightStatsTextFieldStyle, leftStatsTextFieldStyle;
    private Skin images, images_stats;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private BitmapFont font;


    public StatsTableManager(Base base, float scale){
        this.base = base;
        this.scale = scale;

        statsTable = new Table();
        newBuildingTable = new Table();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Silkscreen.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 15;
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

        hpTextField = new TextField("Hp: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        hpTextValue = new TextField(String.valueOf(base.getHealth()), textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        hpTextField.setAlignment(Align.center);
        hpTextValue.setAlignment(Align.center);

        goldTextField = new TextField("Money: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        goldTextValue = new TextField(String.valueOf(base.getMoney()), textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        goldTextField.setAlignment(Align.center);
        goldTextValue.setAlignment(Align.center);

        Texture table_statsBackground = new Texture(new FileHandle("assets/statsBackground.png"));

        statsTable.setBounds(Gdx.graphics.getWidth()-224*scale,(Gdx.graphics.getHeight()-Gdx.graphics.getWidth()/30*16)/2+48*scale+32*scale+350*scale,224*scale,204*scale);
        statsTable.setBackground(new TextureRegionDrawable(new TextureRegion(table_statsBackground)));
        statsTable.add(hpTextField).width((200*scale)/2-6*scale);
        statsTable.add(new Image(images_stats, "middleStatsCover")).width(12*scale);
        statsTable.add(hpTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        statsTable.row();
        statsTable.add(goldTextField).width((200*scale)/2-6*scale);
        statsTable.add(new Image(images_stats, "middleStatsCover")).width(12*scale);
        statsTable.add(goldTextValue).width((200*scale)/2-6*scale).padRight(2*scale);




        //New Building Table

        newBuildingTextField = new TextField("Operacja: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        newBuildingTextValue = new TextField(null, textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        newBuildingTextField.setAlignment(Align.center);
        newBuildingTextValue.setAlignment(Align.center);

        newBuildingTable.setBounds(Gdx.graphics.getWidth()-224*scale,(Gdx.graphics.getHeight()-Gdx.graphics.getWidth()/30*16)/2+48*scale+32*scale+350*scale,224*scale,204*scale);
        newBuildingTable.setBackground(new TextureRegionDrawable(new TextureRegion(table_statsBackground)));
        newBuildingTable.add(newBuildingTextField).width((200*scale)/2-6*scale);
        newBuildingTable.add(new Image(images_stats, "middleStatsCover")).width(12*scale);
        newBuildingTable.add(newBuildingTextValue).width((200*scale)/2-6*scale).padRight(2*scale);




    }

    public Table getStatsTable(){

        return statsTable;
    }


    public void setNewBuildingTable(String name){
        newBuildingTextValue.setText(name);
    }

    public Table getNewBuildingTable(){

        return newBuildingTable;
    }

    public Table getUpgradeTable(){

        return upgradeTable;
    }


    public void update() {
        hpTextValue.setText(String.valueOf(base.getHealth()));
        goldTextValue.setText(String.valueOf(base.getMoney()));
    }

}
