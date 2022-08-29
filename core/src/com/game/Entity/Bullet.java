package com.game.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.Entity.Enemy.Enemy;

public class Bullet {

    private Enemy enemyToFollow;
    private float velocity;
    private TextureRegion bulletTexture;
    private Vector2 position;
    private float scale;


    public Bullet(Enemy enemyToFollow, float velocity, TextureRegion bulletTexture, Vector2 position, float scale)
    {
        this.enemyToFollow = enemyToFollow;
        this.velocity = velocity;
        this.bulletTexture = bulletTexture;
        this.position = new Vector2(position);
        this.scale = scale;
    }


    public void update(float deltaTime) {
        position.x += velocity;
    }

    public void render(SpriteBatch batch) {
        //draw bullet
        batch.draw(bulletTexture, position.x, position.y ,scale*64, scale*64);


    }

}
