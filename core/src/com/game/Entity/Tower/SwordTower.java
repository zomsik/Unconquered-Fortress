package com.game.Entity.Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SwordTower extends Tower{


    public SwordTower(int tileX, int tileY, float scale)
    {


        super("swordTower", new TextureRegion(new Texture(Gdx.files.internal("assets/game/towers/swordTower.png"))), tileX, tileY, scale);


    }

}
