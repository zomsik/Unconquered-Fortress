package com.game.Entity;

public class Base {
    private int maxHealth;
    private int health;
    private int armor;

    Base(){}

    Base(int maxHealth, int health, int armor){
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
}
