package com.game.Entity.RoadObstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.game.Entity.Base;
import com.game.Entity.Enemy.Enemy;
import com.game.Screens.GameScreen;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


public class RoadObstacle extends Actor {

    private String name;
    private Base base;
    private int tileX,tileY;
    private float scale;
    private TextureRegion texture;
    private int usesLeft;
    private Vector2 position;
    private int textureSize;
    private List<Integer> attackedEnemies;
    private ArrayList<Enemy> slowedEnemies;


    public RoadObstacle(String name, Base base, String path, int tileX, int tileY, float scale, GameScreen gameScreen) {
        this.name = name;
        this.base = base;


        this.usesLeft = 10;

        this.tileX = tileX;
        this.tileY = tileY;
        this.scale = scale;
        this.textureSize = 64;

        attackedEnemies = new ArrayList<>();
        slowedEnemies = new ArrayList<>();

        this.texture = new TextureRegion(new Texture(Gdx.files.internal(path)));

        this.position = new Vector2(tileX * scale * 64 + Gdx.graphics.getWidth() / 20,(9 - tileY) * scale * 64 + (Gdx.graphics.getHeight() - Gdx.graphics.getWidth() / 30 * 16) / 2);


        this.addListener(new ClickListener() {


            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                getBase().setUsesLeft(getUsesLeft());
                if(getUsesLeft()>0)
                    getBase().setInfoToDisplay(5, getName());
                else
                    getBase().setInfoToDisplay(0);


            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {

                getBase().setInfoToDisplay(0);
            }

        });

        this.setBounds(position.x,position.y,textureSize,textureSize);




    }

    public Base getBase() {
        return base;
    }

    public void setUsesLeft(int usesLeft) {
        this.usesLeft = usesLeft;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public int getUsesLeft() {
        return usesLeft;
    }


    public void update(float deltaTime, ArrayList<Enemy> enemies){

        for (Enemy e : enemies)
        {
            if (textureSize/2*scale >= Vector2.dst(position.x+textureSize*scale/2,position.y+textureSize*scale/2,e.getPosition().x+e.getEnemySize()*scale/2,e.getPosition().y+e.getEnemySize()*scale/2)) {

                if(!attackedEnemies.contains(e.hashCode())) {
                    attackedEnemies.add(e.hashCode());

                    if (Objects.equals(name, "roadNeedles"))
                    {
                        attackedEnemies.add(e.hashCode());
                        e.dealDmg(100);
                    }
                    else if (Objects.equals(name, "roadSticky"))
                    {
                        e.changeSpeed(0.5f);
                        attackedEnemies.add(e.hashCode());
                        slowedEnemies.add(e);
                    }

                    usesLeft-=1;

                    break;

                }

            }
        }

        Iterator<Enemy> eIterator = slowedEnemies.iterator();
        while (eIterator.hasNext()) {
            Enemy e = eIterator.next();

            if (textureSize/2*scale < Vector2.dst(position.x+textureSize*scale/2,position.y+textureSize*scale/2,e.getPosition().x+e.getEnemySize()*scale/2,e.getPosition().y+e.getEnemySize()*scale/2))
            {
                e.changeSpeed(2f);
                eIterator.remove();
            }

        }


    }


    public void render(SpriteBatch batch){
        batch.draw(texture, position.x+32*(scale-1), position.y+32*(scale-1));
    }



}
