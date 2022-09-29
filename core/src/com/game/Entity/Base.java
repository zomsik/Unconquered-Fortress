package com.game.Entity;

import org.json.JSONObject;

import java.util.Iterator;

public class Base {
    private int maxHealth;
    private int health;
    private int armor;
    private int money;
    private int diamonds;
    private int wave;

    private int infoToDisplay;
    private String infoToDisplayName;
    private JSONObject infoToDisplayObjectNow, infoToDisplayObjectUpgraded;

    private String difficulty;
    private float difficultyMultiplier;
    private int cleanPrice;

    private JSONObject multipliers;

    private int usesLeft;
    private int roadObstacleId;
    private boolean shouldUpdateInfo;


    public enum State{
        Running, Paused, Resumed, GameOver
    }

    private State state;


    public Base(JSONObject actualGame){
        this.infoToDisplay = 0;
        this.infoToDisplayName = "";
        this.infoToDisplayObjectNow = null;
        this.infoToDisplayObjectUpgraded = null;
        this.shouldUpdateInfo = false;

        state = State.Running;

        multipliers = new JSONObject();
        multipliers.put("damageMultiplier",1f);
        multipliers.put("damageMultipliermeleeTower",1f);
        multipliers.put("damageMultipliermageTower",1f);
        multipliers.put("damageMultipliercrossbowTower",1f);
        multipliers.put("damageMultipliercannonTower",1f);
        multipliers.put("attackSpeedMultiplier",1f);
        multipliers.put("moreProjectilesMultiplier",1f);
        multipliers.put("rangeMultiplier",1f);
        multipliers.put("healthRegeneration",0f); //done
        multipliers.put("damageReduction",0f); //done
        multipliers.put("goldMultiplier",1f); //done
        multipliers.put("diamondsMultiplier",0f); //done
        multipliers.put("costMultiplier",1f); //done
        multipliers.put("upgradeCostMultiplier",1f); //done
        multipliers.put("luckMultiplier",0f); //done

        this.money = actualGame.getInt("gold");
        this.diamonds = actualGame.getInt("diamonds");
        this.maxHealth = actualGame.getInt("maxHealth");
        this.health = actualGame.getInt("health");
        this.armor = 100;
        this.difficulty = actualGame.getString("difficulty");

        switch (this.difficulty)
        {
            case "easy" -> difficultyMultiplier = 1;
            case "normal" -> difficultyMultiplier = 2;
            case "hard" -> difficultyMultiplier = 3;
            default -> difficultyMultiplier = 1;
        }



        this.wave = actualGame.getInt("wave");
        this.cleanPrice = 200;
    }

    public Base(int maxHealth, int health, int armor){
        this.money = 0;
        this.maxHealth = maxHealth;
        this.health = health;
        this.armor = armor;

    }


    public void setPassiveUpgrade(JSONObject upgrade, boolean isLoaded) {
        if (!isLoaded)
            this.diamonds -= upgrade.getInt("cost");

        if (upgrade.has("multipliers"))
        {

            JSONObject addMultipliers = upgrade.getJSONObject("multipliers");
            Iterator<?> keys = addMultipliers.keys();

            while( keys.hasNext() ) {
                String key = (String) keys.next();
                multipliers.put(key, multipliers.getFloat(key)+addMultipliers.getFloat(key));
            }
        }

        if (upgrade.has("maxHealth") && !isLoaded)
        {

            int addingHealth = upgrade.getInt("maxHealth");
            maxHealth += addingHealth;
            health += addingHealth;

        }

    }


    public float getDifficultyMultiplier() {
        return difficultyMultiplier;
    }


    public boolean isShouldUpdateInfo() {
        return shouldUpdateInfo;
    }

    public void setShouldUpdateInfo(boolean shouldUpdateInfo) {
        this.shouldUpdateInfo = shouldUpdateInfo;
    }

    public int getUsesLeft() {
        return usesLeft;
    }

    public void setUsesLeft(int uses)
    {
        usesLeft = uses;
    }


    public int getInfoToDisplay() {
        return infoToDisplay;
    }

    public String getInfoToDisplayName() {
        return infoToDisplayName;
    }


    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setInfoToDisplay(int infoToDisplay) {
        this.infoToDisplay = infoToDisplay;
    }

    public void setInfoToDisplay(int infoToDisplay, String name, JSONObject towerNow, JSONObject towerUpgrade) {
        this.infoToDisplay = infoToDisplay;
        infoToDisplayName = name;
        infoToDisplayObjectNow = towerNow;
        infoToDisplayObjectUpgraded = towerUpgrade;
    }

    public void setInfoToDisplay(int infoToDisplay, String name) {
        this.infoToDisplay = infoToDisplay;
        infoToDisplayName = name;
    }

    public void setRoadObstacleId(int hashCode) {
        roadObstacleId = hashCode;
    }

    public int getRoadObstacleId() {
        return roadObstacleId;
    }



    public JSONObject getInfoToDisplayObjectNow() {
        return infoToDisplayObjectNow;
    }

    public JSONObject getInfoToDisplayObjectUpgraded() {
        return infoToDisplayObjectUpgraded;
    }

    public void damageBase(int dmg){
        this.health = this.health - dmg;
    }


    public void healBase(int heal){
        this.health = this.health + heal;
    }


    public JSONObject getMultipliers() {
        return multipliers;
    }


    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void increaseMoney(int money) {
        this.money += money;
    }

    public void decreaseMoney(int money) {
        this.money -= money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
    public void increaseDiamonds(int diamonds) {
        this.diamonds += diamonds;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public void setDiamonds(int diamonds) {
        this.diamonds = diamonds;
    }
    public String getDifficulty() {
        return difficulty;
    }

    public void increaseWave(int wave) {
        this.wave += wave;
    }

    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public int getCleanPrice() {
        return cleanPrice;
    }
}
