package com.game.Entity.Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MageTower extends Tower{

    public MageTower(int tileX, int tileY, float scale)
    {
        super("mageTower", new TextureRegion(new Texture(Gdx.files.internal("assets/game/towers/mageTower.png"))), 64, new TextureRegion(new Texture(Gdx.files.internal("assets/game/bullets/arrow64.png"))), 64, tileX, tileY, scale, 0.5f, 100, 200, 20);

    }

}
