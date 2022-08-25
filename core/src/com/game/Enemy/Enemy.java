package com.game.Enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;


enum AnimationDirection{
    DOWN,
    LEFT,
    RIGHT,
    UP
}

public class Enemy extends Sprite {
    private int health;
    private int speed;
    private String enemyType;
    private int lastDir;
    private Image img;
    public Animation<Drawable>[] animation;

    public Animation<Drawable> animationLeft;
    public Animation<Drawable> animationRight;
    public Animation<Drawable> animationUp;
    public Animation<Drawable> animationDown;


    private Skin enemySkin = new Skin(new TextureAtlas("assets/icons/map_sprites.pack"));


    public Enemy(){


  //      Array<Drawable> drawables = new Array<Drawable>();
/*
        for ( int i = 0; i < 4; ++i ){
            //drawables.add( enemySkin.getDrawable( TestScreen.BAR + Integer.toString( i ) ) );
            drawables.add(enemySkin.getDrawable( "grass"));
            drawables.add(enemySkin.getDrawable("water"));
        }
*/
        //animation = new Animation<Drawable>( 1f, drawables );
        //bar = new Image( animation.getKeyFrame( 0f ) );
        //addActor( bar );
        //addActor( new Image( skin.getDrawable( TestScreen.METER ) ) );
        //setTouchable( Touchable.disabled );



    }

    public Enemy(int health, Skin skin, String name){
        //construct with parameters from extended class
        //gets skin, name to animation

        //foreach (Suit suit in (Suit[]) Enum.GetValues(typeof(Suit)))
        //{
        //}
        //AnimationDirection
        //for (String direction: AnimationDirection.values()))
        //{

        //}

        //TODO finish later


        for (int j=0; j<4; j++) {
            Array<Drawable> drawables = new Array<Drawable>();

            for (int i = 1; i < 5; i++) {
                //drawables.add( enemySkin.getDrawable( TestScreen.BAR + Integer.toString( i ) ) );
                drawables.add(skin.getDrawable(name + AnimationDirection.DOWN + i));
            }

            animation[j] = new Animation<Drawable>(1f, drawables);
        }
    }




    public void draw(){

    }

}
