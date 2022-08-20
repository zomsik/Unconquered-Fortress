package com.game.Entity;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.List;

public class Upgrades {
    private String upgradeName;
    private int level;
    private float damagemultiplayer;
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

    private Image name;
    //private List<Upgrades[]> upgradeList;
    public Upgrades(String upgradeName, int level, int maxLevel, Image name){
        this.upgradeName = upgradeName;
        this.level = level;
        setDamagemultiplayer(level);
        setLevel(level,maxLevel, name);
    }
    public void setDamagemultiplayer(int level){
        switch (level){
            case 0: {
                this.damagemultiplayer = 1.0f;
            }break;
            case 1: {
                this.damagemultiplayer = 1.05f;  //chodzi o 5% więc może być 1.05 a nie 5
            }break;
            case 2: {
                this.damagemultiplayer = 1.10f;
            }break;
            case 3: {
                this.damagemultiplayer = 1.15f;
            }break;
            default:{
                this.damagemultiplayer = 1.0f;
            }break;
        }
    }
    public float getDamageMultiplayer(){
        return damagemultiplayer;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level, int maxLevel, Image name) {
        if(level >= 0 && level < maxLevel){
            this.level = level;
            name.setTouchable(Touchable.enabled);
        }else if (level == maxLevel){
            this.level = maxLevel;
            name.setDrawable(new Skin(new TextureAtlas("assets/icons/upgrade_icons.pack")),"upgradeIcons_bought");
            name.setTouchable(Touchable.enabled);
        }else if(level < 0){
            name.setDrawable(new Skin(new TextureAtlas("assets/icons/upgrade_icons.pack")),"upgradeIcons_lock");
            name.setTouchable(Touchable.enabled);

        }

    }

    public String getUpgradeName() {
        return upgradeName;
    }
}
