package com.game.Entity;

public class Base {
    private int maxHealth;
    private int health;
    private int armor;
    private int money;

    public Base(){
        this.money = 0;
        this.maxHealth = 100;
        this.health = 100;
        this.armor = 100;
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

}
