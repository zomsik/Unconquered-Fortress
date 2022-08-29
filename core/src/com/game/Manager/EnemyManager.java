package com.game.Manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.game.Entity.Enemy.Enemy;
import com.game.Entity.Base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnemyManager {

    private ArrayList<ArrayList<Enemy>> enemies;
    private ArrayList<Float> spawningDelay;
    private ArrayList<Float> timeLeftToSpawn;

    private ArrayList<Enemy> allEnemyArray;

    private float scale;

    private List<Vector2> path;

    private Base base;

    public EnemyManager() {
        enemies = new ArrayList<>();
        spawningDelay = new ArrayList<>();
    }

    public EnemyManager(Base base, float scale, List<Vector2> path) {
        enemies = new ArrayList<>();
        spawningDelay = new ArrayList<>();
        timeLeftToSpawn =  new ArrayList<>();
        allEnemyArray = new ArrayList<>();

        this.path = path;
        this.scale = scale;

        this.base = base;

    }

    public void addWaveToSpawn(ArrayList<Enemy> wave) {

        for (Enemy e : wave) {
            e.initEnemy(path, scale);
        }


        enemies.add(wave);
        spawningDelay.add(60 / (float) wave.size());
        timeLeftToSpawn.add(0f);

    }

    public void resize() {
        //resize all enemies
    }

    public void spawn(Enemy e) {
        allEnemyArray.add(e);
    }

    public void update(float deltaTime) {

        if (enemies.size()>0)
        {
            ArrayList<Integer> indexesToDelete = new ArrayList<>();



            for(int i=0; i<enemies.size(); i++) {
                timeLeftToSpawn.set(i, timeLeftToSpawn.get(i) - deltaTime);


                if (timeLeftToSpawn.get(i)<=0){


                    spawn(enemies.get(i).get(0));
                    enemies.get(i).remove(0);


                    if (enemies.get(i).size()==0)
                    {
                        indexesToDelete.add(i);
                    }
                    else
                    {
                        timeLeftToSpawn.set(i,spawningDelay.get(i));
                    }
                }
            }

            if (indexesToDelete.size() > 0)
            {
                for (int index : indexesToDelete)
                {
                    timeLeftToSpawn.remove(index);
                    spawningDelay.remove(index);
                    enemies.remove(index);
                }
            }


        }

        Iterator<Enemy> eIterator = allEnemyArray.iterator();
        while (eIterator.hasNext()) {
            Enemy e = eIterator.next();
            e.update(deltaTime);

            if(e.getIsAtEnd())
            {
                base.damageBase(e.getDmg());
                eIterator.remove();
            }

        }


    }


    public void render(SpriteBatch spritebatch) {

        for (Enemy e : allEnemyArray) {
            e.render(spritebatch);

        }


    }
}