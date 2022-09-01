package com.game.Entity.Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CannonTower extends Tower{

    public CannonTower(int tileX, int tileY, float scale)
    {
        super("cannonTower", "assets/game/towers/cannonTower.png", null, 64, new TextureRegion(new Texture(Gdx.files.internal("assets/game/bullets/arrow64.png"))), 64, tileX, tileY, scale, 2f, 200, 200, 20);

    }

}
