package com.game.Enemy;

import com.badlogic.gdx.math.Vector2;

public class Warrior extends Enemy{

    public Warrior(){
        super(100, "assets/game/warrior.png", "warrior", new Vector2().add(700,900), 1.5f);
    }


    public Warrior(Vector2 startPosition, float scale){
        super(100, "assets/game/warrior.png", "warrior", startPosition, scale);
    }


}
