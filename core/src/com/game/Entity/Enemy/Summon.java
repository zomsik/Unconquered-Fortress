package com.game.Entity.Enemy;

import org.json.JSONObject;

public class Summon extends Enemy{

    public Summon(JSONObject enemy){
        super(enemy.getInt("health"), enemy.getInt("damage"), enemy.getInt("money"), enemy.getInt("diamonds"), enemy.getInt("speed"), "assets/game/enemies/warrior.png", enemy.getString("name"), 64, enemy.getBoolean("isFlying"));
    }

    public Summon(int health, int damage, int money, int diamonds, int speed, String s, String name, int i, boolean isFlying) {
        super(health, damage, money, diamonds, speed, s, name, i, isFlying);
    }
}
