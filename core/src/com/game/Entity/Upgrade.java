package com.game.Entity;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.game.Manager.LanguageManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Upgrade {
    private String upgradeName;
    private int level;
    private int maxLevel;
    private boolean isMaxLevel;
    private Image image;
    private JSONArray upgradeArray;
    private String unlockedIcon;

    private ArrayList<Upgrade> nextUpgrades;
    private List<Integer> levelToUnlockUpgrades;
    private LanguageManager languageManager;

    public Upgrade(String upgradeName, int level, JSONArray upgradeArray, Skin skin, String unlockedIcon, LanguageManager languageManager) {

        this.upgradeName = upgradeName;
        this.level = level;
        this.maxLevel = upgradeArray.length();
        this.image = new Image(skin, "upgradeIcons_lock");
        this.upgradeArray = upgradeArray;
        this.unlockedIcon = unlockedIcon;
        this.languageManager = languageManager;

        this.isMaxLevel = true;

        this.nextUpgrades = new ArrayList<>();
        this.levelToUnlockUpgrades = new ArrayList<>();

        if (level>=0) {
            this.isMaxLevel = false;
            this.image.setDrawable(skin, unlockedIcon);
        }

        image.setTouchable(Touchable.enabled);
    }


    public int getLevel() {
        return level;
    }

    public void unlock(Skin images_upgrades) {
        isMaxLevel=false;
        level = 0;
        image.setDrawable(images_upgrades, unlockedIcon);
    }


    public void levelUp() {
        if(!isMaxLevel) {
            level++;

            if (level == maxLevel) {
                isMaxLevel=true;
                image.setDrawable(new Skin(new TextureAtlas("assets/icons/upgrade_icons.pack")), "upgradeIcons_bought");
            }
        }
    }

    public String getUpgradeName() {
        return upgradeName;
    }

    public Label returnInformation(Label.LabelStyle labelStyle){

        if(this.level<0) {
            return new Label(languageManager.getValue(languageManager.getLanguage(), "upgrade_First_Unlock"), labelStyle);
        }else if (level==maxLevel) {
            return new Label(languageManager.getValue(languageManager.getLanguage(), "upgrade_Max_level"), labelStyle);
        }
        else {

            String information = languageManager.getValue(languageManager.getLanguage(), "upgrade_Upgrade") + languageManager.getValue(languageManager.getLanguage(), this.upgradeName) + "\n"+languageManager.getValue(languageManager.getLanguage(), "upgrade_Level") + this.level + "/" + this.maxLevel + "\n";

            // if unlocks tower
            if (upgradeArray.getJSONObject(level).has("unlocks"))
                return new Label(information+languageManager.getValue(languageManager.getLanguage(), "uInfoUnlocks")+ upgradeArray.getJSONObject(level).getString("unlocks"),labelStyle);

            if (upgradeArray.getJSONObject(level).has("maxHealth"))
                return new Label(information+languageManager.getValue(languageManager.getLanguage(), "uInfoMaxHealth") + upgradeArray.getJSONObject(level).getInt("maxHealth"), labelStyle);

            JSONObject upgradesNameArray = upgradeArray.getJSONObject(level).getJSONObject("multipliers");

            if (upgradesNameArray.length()==1)
            {

                if (upgradesNameArray.has( "healthRegeneration"))
                    return new Label(information+languageManager.getValue(languageManager.getLanguage(), "uInfoHealthRegeneration") + upgradesNameArray.getInt("healthRegeneration"), labelStyle);

                if (upgradesNameArray.has("damageReduction"))
                    return new Label(information+languageManager.getValue(languageManager.getLanguage(), "uInfoDamageReduction") + upgradesNameArray.getInt("damageReduction"), labelStyle);

                if (upgradesNameArray.has("goldMultiplier"))
                    return new Label(information+languageManager.getValue(languageManager.getLanguage(), "uInfoGoldMultiplier") + (int)(100*upgradesNameArray.getFloat("goldMultiplier")) +"%", labelStyle);

                if (upgradesNameArray.has("diamondsMultiplier"))
                    return new Label(information+languageManager.getValue(languageManager.getLanguage(), "uInfoDiamondsMultiplier") + upgradesNameArray.getInt("diamondsMultiplier"), labelStyle);

                if (upgradesNameArray.has("costMultiplier"))
                    return new Label( information+languageManager.getValue(languageManager.getLanguage(), "uInfoCostMultiplier") + (int)(-100*upgradesNameArray.getFloat("costMultiplier")) + "%", labelStyle);

                if (upgradesNameArray.has("upgradeCostMultiplier"))
                    return new Label( information+languageManager.getValue(languageManager.getLanguage(), "uInfoUpgradeCostMultiplier") + (int)(-100*upgradesNameArray.getFloat("upgradeCostMultiplier")) + "%", labelStyle);

                if (upgradesNameArray.has("cleaningCostMultiplier"))
                    return new Label( information+languageManager.getValue(languageManager.getLanguage(), "uInfoCleaningCostMultiplier") + (int)(-100*upgradesNameArray.getFloat("cleaningCostMultiplier")) + "%", labelStyle);

                if (upgradesNameArray.has("luckMultiplier"))
                    return new Label( information+languageManager.getValue(languageManager.getLanguage(), "uInfoLuckMultiplier") + upgradesNameArray.getInt("luckMultiplier") +"%", labelStyle);


            }


            //else
            return new Label(languageManager.getValue(languageManager.getLanguage(), "upgrade_Upgrade") + languageManager.getValue(languageManager.getLanguage(), this.upgradeName) + "\n"+languageManager.getValue(languageManager.getLanguage(), "upgrade_Level") + this.level + "/" + this.maxLevel + "\n"+languageManager.getValue(languageManager.getLanguage(), "upgrade_Multiplayer"),labelStyle);

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

    public void addNextUpgrade(Upgrade nextUpgrade, int levelToUnlock) {
        nextUpgrades.add(nextUpgrade);
        levelToUnlockUpgrades.add(levelToUnlock);

    }

    public Image getImage() {
        return image;
    }

    public int getLevelToUnlock() {
        return levelToUnlockUpgrades.get(0);
    }

    public Upgrade getNextUpgrade() {
        return nextUpgrades.get(0);
    }

    public void removeUnlocked() {
        nextUpgrades.remove(0);
        levelToUnlockUpgrades.remove(0);

    }

    public int getUnlocksLeft() {
        return nextUpgrades.size();
    }

}
