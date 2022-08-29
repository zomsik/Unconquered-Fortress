package com.game.Entity.Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.game.Entity.Bullet;
import com.game.Entity.Enemy.Enemy;

import java.util.ArrayList;

public class Tower extends Actor {

    private float range;
    private float dmg;
    private float scale;
    private Enemy enemyToFollow;
    private TextureRegion towerTexture;
    private Vector2 position;
    private int tileX,tileY;
    private float bulletVelocity;
    private TextureRegion bulletTexture;
    private float timeToShoot;
    private float reloadTime;

    private ArrayList<Bullet> towerBullets;


    public Tower(){
        this.enemyToFollow = null;
    }


    public Tower(String name, TextureRegion towerTexture, int tileX, int tileY, float scale){
        this.timeToShoot=0;
        this.reloadTime=3;
        this.scale = scale;
        this.towerBullets = new ArrayList<>();
        this.bulletTexture = new TextureRegion(new Texture(Gdx.files.internal("assets/game/bullets/arrow64.png")));

        this.bulletVelocity = 5*scale;
        this.range = 100*scale;

        this.dmg = 20;
        this.enemyToFollow = null;
        this.towerTexture = towerTexture;
        this.tileX = tileX;
        this.tileY = tileY;


        this.position = new Vector2(tileX * scale * 64 + Gdx.graphics.getWidth() / 20,(9 - tileY) * scale * 64 + (Gdx.graphics.getHeight() - Gdx.graphics.getWidth() / 30 * 16) / 2);



    }

    public int getTileX() { return tileX;}
    public int getTileY() { return tileY;}

    public void initTower(float scale) {
        this.scale = scale;
    }


    public void shootBullet()
    {

    }

    public void update(float deltaTime, ArrayList<Enemy> enemies) {
        //set positions, orientation
        // shoot if reloaded
        if (timeToShoot>0)
            timeToShoot-=deltaTime;



        if (timeToShoot<=0)
        {
            for (Enemy e: enemies)
            {
                if (range>=Vector2.dst(position.x,position.y,e.getPosition().x,e.getPosition().y)) {
                    towerBullets.add(new Bullet(e, bulletVelocity, bulletTexture, position, scale));
                    timeToShoot = reloadTime;
                    break;
                }
            }

        }

        for (Bullet b: towerBullets)
        {
            b.update(deltaTime);
        }

    }

    public void render(SpriteBatch batch) {
        //draw towers
        batch.draw(towerTexture, position.x, position.y ,scale*64, scale*64);



        for (Bullet b: towerBullets)
        {
            b.render(batch);
        }

    }

}
