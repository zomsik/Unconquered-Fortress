package com.game.Entity.Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class BowTower extends Tower{

    public BowTower(int tileX, int tileY, float scale)
    {

        super("bowTower", new TextureRegion(new Texture(Gdx.files.internal("assets/game/towers/bowTower.png"))), 64, new TextureRegion(new Texture(Gdx.files.internal("assets/game/bullets/arrow64.png"))), 64, tileX, tileY, scale, 0.5f, 300, 200, 20);


    }

}
