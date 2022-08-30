package com.game.Entity.Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MeleeTower extends Tower{


    public MeleeTower(int tileX, int tileY, float scale)
    {

        super("meleeTower", new TextureRegion(new Texture(Gdx.files.internal("assets/game/towers/meleeTower.png"))), 64, new TextureRegion(new Texture(Gdx.files.internal("assets/game/bullets/arrow64.png"))), 64, tileX, tileY, scale, 0.5f, 500, 200, 20);


    }

}
