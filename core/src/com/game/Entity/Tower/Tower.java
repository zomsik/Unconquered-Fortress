package com.game.Entity.Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.game.Entity.Enemy.Enemy;

public class Tower  extends Actor {

    private float range;
    private float dmg;
    private float scale;
    private Enemy enemyToFollow;
    private TextureRegion towerTexture;
    private Vector2 position;
    private int tileX,tileY;

    public Tower(){
        this.enemyToFollow = null;
    }


    public Tower(String name, TextureRegion towerTexture, int tileX, int tileY, float scale){
        this.range = 50;
        this.dmg = 20;
        this.enemyToFollow = null;
        this.towerTexture = towerTexture;
        this.tileX = tileX;
        this.tileY = tileY;
        this.scale = scale;

        this.position = new Vector2(tileX * scale * 64 + Gdx.graphics.getWidth() / 20,(9 - tileY) * scale * 64 + (Gdx.graphics.getHeight() - Gdx.graphics.getWidth() / 30 * 16) / 2);



    }

    public int getTileX() { return tileX;}
    public int getTileY() { return tileY;}

    public void initTower(float scale) {
        this.scale = scale;
    }


    public void update(float deltaTime) {
        //set positions, orientation
        // shoot if reloaded
    }

    public void render(SpriteBatch batch) {
        //draw towers
        batch.draw(towerTexture, position.x, position.y ,scale*64, scale*64);


    }

}
