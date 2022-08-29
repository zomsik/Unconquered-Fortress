package com.game.Entity.Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CannonTower extends Tower{

    public CannonTower(int tileX, int tileY, float scale)
    {


        super("cannonTower", new TextureRegion(new Texture(Gdx.files.internal("assets/game/towers/cannonTower.png"))), tileX, tileY, scale);


    }

}
