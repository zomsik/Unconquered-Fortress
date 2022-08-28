package com.game.Enemy;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Flying extends Enemy{




    public Flying(float startPositionX, float startPositionY, float scale){
        //super(100, "assets/game/flying.png", "flying", startPositionX,  startPositionY, scale);
    }

    public Flying(){
        super(100, "assets/game/flying.png", "flying");
    }


}