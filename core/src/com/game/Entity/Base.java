package com.game.Entity;

import org.json.JSONObject;

public class Base {
    private int maxHealth;
    private int health;
    private int armor;
    private int money;
    private int diamonds;
    private int wave;

    private String difficulty;

    public Base(JSONObject actualGame){
        this.money = 0;
        this.diamonds = 0;
        this.maxHealth = 100;
        this.health = 100;
        this.armor = 100;
        this.difficulty = actualGame.getString("difficulty");
        this.wave = 0;
    }

    public Base(int maxHealth, int health, int armor){
        this.money = 0;
        this.maxHealth = maxHealth;
        this.health = health;
        this.armor = armor;

    }

    public void damageBase(int dmg){
        this.health = this.health - dmg;
    }


    public void healBase(int heal){
        this.health = this.health + heal;
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


}
