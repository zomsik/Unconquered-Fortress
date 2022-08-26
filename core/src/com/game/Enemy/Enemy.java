package com.game.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;

public class Enemy extends Actor {
    private int health;
    private int speed;
    private String enemyType;
    private int lastDir;

    private Animation<TextureRegion> currentAnimation;
    private Animation<TextureRegion>[] animationArr;

    private Vector2 position;

    private float stateTime;


    public Enemy(){

    }

    public Enemy(int health,String path, String name, Vector2 position){

        this.position = position;

        stateTime = 0f;

        animationArr = new Animation[4];
        Texture spriteMap = new Texture(Gdx.files.internal(path));
        TextureRegion[][] spritePosition = TextureRegion.split(spriteMap, 64, 64); // frame width and height get from extended class
        TextureRegion[] animationSprites;

        for (int j=0; j<4; j++) {

            animationSprites = new TextureRegion[4];

            for (int i = 0; i < 4; i++) {
                animationSprites[i] = spritePosition[j][i];
            }

            animationArr[j] = new Animation<>(0.125f, animationSprites);

        }

        currentAnimation = animationArr[0];

    }

    public void update(float deltaTime){

    }


    public void render(SpriteBatch batch){
        stateTime += Gdx.graphics.getDeltaTime();
        batch.draw(currentAnimation.getKeyFrame(stateTime, true), position.x, position.y ,64, 64);

    }


}



