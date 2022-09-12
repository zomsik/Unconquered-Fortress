package com.game.Entity;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import org.json.JSONArray;
import org.json.JSONObject;

public class Upgrade {
    private String upgradeName;
    private int level;
    private int maxLevel;
    private boolean isMaxLevel;
    private Image name;
    private JSONArray upgradeArray;

    /*
    private float damageMultiplayer;
    private float attackspeedmultiplayer;
    private float moreprojectilesmultiplayer;
    private float rangemultiplayer;
    private float bonushealth;
    private float healthregeneration;
    private float reductionmultiplayer;
    private float goldmultiplayer;
    private float diamondsmultiplayer;
    private float discountmultiplaer;
    private float costmultiplayer;
    private float luckmultiplayer;

     */


    public Upgrade(String upgradeName, int level, int maxLevel, Image name){
        this.upgradeName = upgradeName;
        this.level = level;
        this.maxLevel = maxLevel;
        this.name = name;


        if (level<0) {
            isMaxLevel = true;
            name.setDrawable(new Skin(new TextureAtlas("assets/icons/upgrade_icons.pack")),"upgradeIcons_lock");
        }
        else {
            this.isMaxLevel = false;
        }

        name.setTouchable(Touchable.enabled);
    }


    public Upgrade(String upgradeName, int level, int maxLevel, Image name, JSONArray upgradeArray) {
        this.upgradeName = upgradeName;
        this.level = level;
        this.maxLevel = maxLevel;
        this.name = name;
        this.upgradeArray = upgradeArray;

        if (level<0) {
            isMaxLevel = true;
            name.setDrawable(new Skin(new TextureAtlas("assets/icons/upgrade_icons.pack")),"upgradeIcons_lock");
        }
        else {
            this.isMaxLevel = false;
        }

        name.setTouchable(Touchable.enabled);
    }


    public int getLevel() {
        return level;
    }

    public void unlock(Skin images_upgrades, String unlockedIcon) {
        isMaxLevel=false;
        level = 0;
        name.setDrawable(images_upgrades, unlockedIcon);
    }


    public void levelUp() {
        if(!isMaxLevel) {
            level++;

            if (level == maxLevel) {
                isMaxLevel=true;
                name.setDrawable(new Skin(new TextureAtlas("assets/icons/upgrade_icons.pack")), "upgradeIcons_bought");
            }
        }

    }

    public String getUpgradeName() {
        return upgradeName;
    }

    public Label returnInformation(Label.LabelStyle labelStyle){

        if(this.level<0) {
            return new Label("Najpierw musisz odblować poprzednie ulepszenie.", labelStyle);
        }else if (level==maxLevel) {
            return new Label("Maksymalny poziom.", labelStyle);
        }
        else {
            return new Label("Upgrade: " + this.upgradeName + "\nLevel:" + this.level + "/" + this.maxLevel + "\nDamage multiplayer: 000",labelStyle);
        }

    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public boolean isMaxLevel() {
        return isMaxLevel;
    }

    public int getCostToUpgrade() {
        return upgradeArray.getJSONObject(level).getInt("cost");
    }

    public JSONObject getUpgrade() {
        return upgradeArray.getJSONObject(level);
    }

}
