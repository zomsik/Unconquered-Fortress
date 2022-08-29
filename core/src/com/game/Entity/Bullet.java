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
        double x = Math.abs(position.x-enemyToFollow.getPosition().x);
        //double c = Vector2.dst(position.x,position.y,enemyToFollow.getPosition().x,enemyToFollow.getPosition().y);
        double y = Math.abs(position.y-enemyToFollow.getPosition().y);

        double alfa = Math.atan(y/x);

        float velocityX = (float)(velocity * Math.cos(alfa));
        float velocityY = (float)(velocity * Math.sin(alfa));

        // Find direction from current position to enemy
        Vector2 direction = new Vector2  (position.x - enemyToFollow.getPosition().x, position.y - enemyToFollow.getPosition().y);

        // Move in that direction in x-axis
        if (direction.x > 0)
            position.x = position.x - velocityX * deltaTime;
        else if (direction.x < 0)
            position.x = position.x + velocityX * deltaTime;

        // Move in that direction in y-axis
        if (direction.y > 0)
            position.y = position.y - velocityY * deltaTime;
        else if (direction.y < 0)
            position.y = position.y + velocityY * deltaTime;

    }

    public void render(SpriteBatch batch) {
        //draw bullet
        batch.draw(bulletTexture, position.x, position.y ,scale*64, scale*64);


    }

}
