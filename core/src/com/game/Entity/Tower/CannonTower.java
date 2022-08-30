package com.game.Entity.Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CannonTower extends Tower{

    public CannonTower(int tileX, int tileY, float scale)
    {

        super("cannonTower", new TextureRegion(new Texture(Gdx.files.internal("assets/game/towers/cannonTower.png"))), 64, new TextureRegion(new Texture(Gdx.files.internal("assets/game/bullets/arrow64.png"))), 64, tileX, tileY, scale, 0.5f, 200, 200, 20);

    }

}