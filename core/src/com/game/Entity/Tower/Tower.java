package com.game.Entity.Tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Null;
import com.game.Entity.Base;
import com.game.Entity.Bullet;
import com.game.Entity.Enemy.Enemy;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Tower extends Actor {

    private JSONArray towerLevels;

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
    private Animation<TextureRegion> towerAnimation2;

    private float timeToShoot;
    private float reloadTime;
    private float rotation;
    private int lvl;
    private ArrayList<Bullet> towerBullets;

    private float stateTime;
    private Base base;

    private boolean isMouseEntered;
    public Tower(){
        this.enemyToFollow = null;
    }


    public Tower(JSONObject towerLevelsAll, Base base, String name, String path, @Null String path2, int towerTextureSize, TextureRegion bulletTexture, int bulletTextureSize, int tileX, int tileY, float scale, float reloadTime, float bulletSpeed, float range, float bulletDamage){
        this.name = name;

        this.towerLevels = towerLevelsAll.getJSONArray(name);

        JSONObject turretFirstLevel = this.towerLevels.getJSONObject(0);

        this.base = base;
        this.isMouseEntered = false;
        this.timeToShoot = 0;
        this.reloadTime = turretFirstLevel.getFloat("reload");
        this.scale = scale;
        this.towerBullets = new ArrayList<>();

        this.tileX = tileX;
        this.tileY = tileY;

        this.stateTime = 100f;

        this.bulletTexture = bulletTexture;
        this.towerTextureSize = towerTextureSize;
        this.bulletTextureSize = bulletTextureSize;

        this.bulletSpeed = turretFirstLevel.getFloat("bulletSpeed")*scale;
        this.range = turretFirstLevel.getFloat("range")*scale;

        this.lvl = turretFirstLevel.getInt("lvl");
        this.enemyToFollow = null;


        Texture spriteMap = new Texture(Gdx.files.internal(path));
        TextureRegion[][] spritePosition = TextureRegion.split(spriteMap, 64, 64); // frame width and height get from extended class
        TextureRegion[] animationSprites = new TextureRegion[4];
        Texture spriteMap2;
        TextureRegion[][] spritePosition2 = new TextureRegion[0][];
        TextureRegion[] animationSprites2 = new TextureRegion[0];
        if(path2!=null)
        {
            spriteMap2 = new Texture(Gdx.files.internal(path2));
            spritePosition2 = TextureRegion.split(spriteMap2, 64, 64); // frame width and height get from extended class
            animationSprites2 = new TextureRegion[4];
        }



        for (int i = 0; i < animationSprites.length; i++) {
            animationSprites[i] = spritePosition[0][i];
        }
        for (int i = 0; i < animationSprites2.length; i++) {
            animationSprites2[i] = spritePosition2[0][i];
        }
        this.towerAnimation = new Animation<>(turretFirstLevel.getFloat("reload")/animationSprites.length, animationSprites);
        this.towerAnimation2 = new Animation<>(turretFirstLevel.getFloat("reload")/animationSprites.length, animationSprites2);

        this.bulletDamage = turretFirstLevel.getFloat("dmg");



        this.position = new Vector2(tileX * scale * 64 + Gdx.graphics.getWidth() / 20,(9 - tileY) * scale * 64 + (Gdx.graphics.getHeight() - Gdx.graphics.getWidth() / 30 * 16) / 2);

        this.rotation = 0;

        //this.setBounds(position.x,position.y,towerTextureSize,towerTextureSize);


        this.addListener(new ClickListener() {

            public void clicked(InputEvent event, float x, float y) {
                TowerLevelUp();

            }

            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                isMouseEntered = true;
                if (getLvl() < getTowerLevels().length())
                    getBase().setInfoToDisplay(2, getName(), getTowerLevels().getJSONObject(getLvl()-1), getTowerLevels().getJSONObject(getLvl()));
                else
                    getBase().setInfoToDisplay(2, getName(), getTowerLevels().getJSONObject(getLvl()-1), null);

            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                isMouseEntered = false;
                getBase().setInfoToDisplay(0);
            }

        });

        this.setBounds(position.x,position.y,towerTextureSize,towerTextureSize);



    }


    @Override
    public String getName() {
        return name;
    }


    public Base getBase() {
        return base;
    }


    public void TowerLevelUp() {
        System.out.println(lvl);
        if (lvl < towerLevels.length())
        {
            if (base.getMoney() >= towerLevels.getJSONObject(lvl).getInt("cost")) {
                JSONObject lvlUp = towerLevels.getJSONObject(lvl);
                base.decreaseMoney(lvlUp.getInt("cost"));
                reloadTime = lvlUp.getFloat("reload");
                bulletDamage = lvlUp.getFloat("dmg");
                bulletSpeed = lvlUp.getFloat("bulletSpeed");
                range = lvlUp.getFloat("range");
                lvl = lvlUp.getInt("lvl");
            }
        }

    }


    public JSONArray getTowerLevels() {
        return towerLevels;
    }


    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
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
                if (range>=Vector2.dst(position.x+towerTextureSize*scale/2,position.y+towerTextureSize*scale/2,e.getPosition().x+e.getEnemySize()*scale/2,e.getPosition().y+e.getEnemySize()*scale/2)) {
                    towerBullets.add(new Bullet(e, e.getEnemySize(), bulletDamage, bulletSpeed, bulletTexture, bulletTextureSize, position, scale));
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

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        //draw tower



        if (isMouseEntered)
        {

            batch.end();
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.circle(position.x+scale*towerTextureSize/2, position.y+scale*towerTextureSize/2, range);
            shapeRenderer.end();
            batch.begin();
        }

            if(Objects.equals(name, "meleeTower")){
                batch.draw(towerAnimation2.getKeyFrame(stateTime, true), position.x+32*(scale-1), position.y+32*(scale-1),towerTextureSize/2, towerTextureSize/2,towerTextureSize, towerTextureSize,scale,scale,0);
                batch.draw(towerAnimation.getKeyFrame(stateTime, false),position.x+32*(scale-1), position.y+32*(scale-1), towerTextureSize/2, towerTextureSize/2,towerTextureSize,towerTextureSize,scale,scale,rotation);

            }else{
                batch.draw(towerAnimation.getKeyFrame(stateTime, false),position.x+32*(scale-1), position.y+32*(scale-1), towerTextureSize/2, towerTextureSize/2,towerTextureSize,towerTextureSize,scale,scale,rotation);
            }





        for (Bullet b: towerBullets)
        {
            b.render(batch);
        }

    }

}
