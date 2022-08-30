package com.game.Entity.Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.game.Entity.Bullet;
import com.game.Entity.Enemy.Enemy;

import java.util.ArrayList;
import java.util.Iterator;

public class Tower extends Actor {

    private String name;

    private float range;
    private float bulletDamage;
    private float scale;
    private Enemy enemyToFollow;
    private TextureRegion towerTexture;
    private TextureRegion bulletTexture;
    private int towerTextureSize;
    private int bulletTextureSize;

    private Vector2 position;
    private int tileX,tileY;
    private float bulletSpeed;


    private Animation<TextureRegion> towerAnimation;

    private float timeToShoot;
    private float reloadTime;
    private float rotation;
    private int lvl;
    private ArrayList<Bullet> towerBullets;

    private float stateTime;

    public Tower(){
        this.enemyToFollow = null;
    }


    public Tower(String name, String path, int towerTextureSize, TextureRegion bulletTexture, int bulletTextureSize, int tileX, int tileY, float scale, float reloadTime, float bulletSpeed, float range, float bulletDamage){
        this.name = name;

        this.timeToShoot=0;
        this.reloadTime= reloadTime;
        this.scale = scale;
        this.towerBullets = new ArrayList<>();

        this.tileX = tileX;
        this.tileY = tileY;
        //this.towerTexture = towerTexture;

        this.stateTime = 1f;

        this.bulletTexture = bulletTexture;
        this.towerTextureSize = towerTextureSize;
        this.bulletTextureSize = bulletTextureSize;

        this.bulletSpeed = bulletSpeed*scale;
        this.range = range*scale;

        this.lvl = 1;
        this.enemyToFollow = null;


        Texture spriteMap = new Texture(Gdx.files.internal(path));
        TextureRegion[][] spritePosition = TextureRegion.split(spriteMap, 64, 64); // frame width and height get from extended class
        TextureRegion[] animationSprites = new TextureRegion[4];

        for (int i = 0; i < 4; i++) {
            animationSprites[i] = spritePosition[0][i];
        }
        this.towerAnimation = new Animation<>(0.125f, animationSprites);







        this.bulletDamage = bulletDamage;



        this.position = new Vector2(tileX * scale * 64 + Gdx.graphics.getWidth() / 20,(9 - tileY) * scale * 64 + (Gdx.graphics.getHeight() - Gdx.graphics.getWidth() / 30 * 16) / 2);

        this.rotation = 0;

    }

    public int getTileX() { return tileX;}
    public int getTileY() { return tileY;}


    public void update(float deltaTime, ArrayList<Enemy> enemies) {
        //set positions, orientation
        // shoot if reloaded

        // next animation frame if animation is not ended yet
        if (!towerAnimation.isAnimationFinished(stateTime))
            stateTime += Gdx.graphics.getDeltaTime();


        Iterator<Bullet> bIterator = towerBullets.iterator();
        while (bIterator.hasNext()) {
            Bullet b = bIterator.next();

            b.update(deltaTime);
            if (b.isOnEnemy())
            {
                b.getEnemyToFollow().dealDmg(b.getBulletDamage());
                bIterator.remove();
            }

        }

        if (timeToShoot>0)
            timeToShoot-=deltaTime;


        if (timeToShoot<=0)
        {
            //if dst < range for last enemy then shoot last enemy


            // else find new enemy
            for (Enemy e: enemies)
            {
                if (range>=Vector2.dst(position.x,position.y,e.getPosition().x,e.getPosition().y)) {
                    towerBullets.add(new Bullet(e, bulletDamage, bulletSpeed, bulletTexture, bulletTextureSize, position, scale));
                    stateTime = 0f;
                    timeToShoot = reloadTime;

                    Vector2 direction = new Vector2(position.x - e.getPosition().x, position.y - e.getPosition().y);


                    double x = Math.abs(position.x - e.getPosition().x);
                    double distance = Vector2.dst(position.x,position.y,e.getPosition().x,e.getPosition().y);
                    double alfa = Math.acos(x/distance);
                    rotation = (float) Math.toDegrees(alfa);

                    if (direction.x <= 0 && direction.y <= 0)
                        rotation = 270 + rotation;
                    else if (direction.x <= 0 && direction.y > 0)
                        rotation = 270 - rotation;
                    else if (direction.x > 0 && direction.y > 0)
                        rotation = 90 + rotation;
                    else if (direction.x > 0 && direction.y <= 0)
                        rotation = 90 - rotation;


                    break;
                }
            }

        }



    }

    public void render(SpriteBatch batch) {
        //draw tower
        batch.draw(towerAnimation.getKeyFrame(stateTime, false),position.x, position.y, towerTextureSize/2, towerTextureSize/2,towerTextureSize,towerTextureSize,scale,scale,rotation);


        for (Bullet b: towerBullets)
        {
            b.render(batch);
        }

    }

}
