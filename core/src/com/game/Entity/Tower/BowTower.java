package com.game.Entity.Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class BowTower extends Tower{

    public BowTower(int tileX, int tileY, float scale)
    {


        super("bowTower", new TextureRegion(new Texture(Gdx.files.internal("assets/game/towers/bowTower.png"))), tileX, tileY, scale);


    }

}
