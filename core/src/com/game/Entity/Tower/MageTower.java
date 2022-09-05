package com.game.Entity.Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.Entity.Base;

public class MageTower extends Tower{

    public MageTower(Base base, int tileX, int tileY, float scale)
    {
        super(base, "mageTower", "assets/game/towers/mageTower.png", null, 64, new TextureRegion(new Texture(Gdx.files.internal("assets/game/bullets/mageBullet64.png"))), 64, tileX, tileY, scale, 0.5f, 150, 200, 20);

    }

}
