package com.game.Enemy;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Flying extends Enemy{

    public Flying(){
    super(100, "assets/game/flying.png", "flying", new Vector2().add(700,900));
    }


    public Flying(Vector2 startPosition){
        super(100, "assets/game/flying.png", "flying", startPosition);
    }


}