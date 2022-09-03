package com.game.Manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.game.Entity.Enemy.Enemy;
import com.game.Entity.Base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnemyManager {

    private ArrayList<ArrayList<Enemy>> enemyWavesToSpawn;
    private ArrayList<Enemy> enemies;

    private ArrayList<Float> spawningDelay;
    private ArrayList<Float> timeLeftToSpawn;


    private float scale;

    private List<Vector2> path;

    private Base base;

    public EnemyManager() {
        enemyWavesToSpawn = new ArrayList<>();
        spawningDelay = new ArrayList<>();
    }

    public EnemyManager(Base base, float scale, List<Vector2> path) {
        enemyWavesToSpawn = new ArrayList<>();
        spawningDelay = new ArrayList<>();
        timeLeftToSpawn =  new ArrayList<>();
        enemies = new ArrayList<>();

        this.path = path;
        this.scale = scale;

        this.base = base;

    }

    public ArrayList<Enemy> getEnemies(){
        return enemies;
    }

    public void addWaveToSpawn(ArrayList<Enemy> wave) {

        for (Enemy e : wave) {
            e.initEnemy(path, scale);
        }


        enemyWavesToSpawn.add(wave);
        spawningDelay.add(60 / (float) wave.size());
        timeLeftToSpawn.add(0f);

    }

    public void resize() {
        //resize all enemies
    }

    public void spawn(Enemy e) {
        enemies.add(e);
    }

    public void update(float deltaTime) {

        //spawning enemies
        if (enemyWavesToSpawn.size()>0)
        {
            ArrayList<Integer> indexesToDelete = new ArrayList<>();



            for(int i = 0; i< enemyWavesToSpawn.size(); i++) {
                timeLeftToSpawn.set(i, timeLeftToSpawn.get(i) - deltaTime);


                if (timeLeftToSpawn.get(i)<=0){


                    spawn(enemyWavesToSpawn.get(i).get(0));
                    enemyWavesToSpawn.get(i).remove(0);


                    if (enemyWavesToSpawn.get(i).size()==0)
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
                    enemyWavesToSpawn.remove(index);

                }
            }
            if(enemyWavesToSpawn.size() == 0){
                base.increaseWave(1);
            }

        }

        //updating enemies
        Iterator<Enemy> eIterator = enemies.iterator();
        while (eIterator.hasNext()) {
            Enemy e = eIterator.next();
            e.update(deltaTime);

            //if reached end
            if(e.isAtEnd())
            {
                base.damageBase(e.getDmg());
                eIterator.remove();
            }
            //if dead
            if(!e.isAlive())
            {
                base.increaseMoney(e.getMoney());
                eIterator.remove();
            }

        }


    }


    public void render(SpriteBatch spritebatch) {

        for (Enemy e : enemies) {
            e.render(spritebatch);

        }


    }
}