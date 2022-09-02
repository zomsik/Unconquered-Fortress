package com.game.Entity.Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MeleeTower extends Tower{


    public MeleeTower(int tileX, int tileY, float scale)
    {
        super("meleeTower", "assets/game/towers/meleeTower.png", "assets/game/towers/meleeTowerBase.png",  112, new TextureRegion(new Texture(Gdx.files.internal("assets/game/bullets/meleeAttack64.png"))), 64+32, tileX, tileY, scale, 0.75f, 500, 80, 20);

    }

}
