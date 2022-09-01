package com.game.Entity.Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BowTower extends Tower{

    public BowTower(int tileX, int tileY, float scale)
    {
        super("crossbowTower", "assets/game/towers/crossbowTower.png", null, 64, new TextureRegion(new Texture(Gdx.files.internal("assets/game/bullets/crossbowArrow64.png"))), 64, tileX, tileY, scale, 0.5f, 600, 200, 20);

    }

}
