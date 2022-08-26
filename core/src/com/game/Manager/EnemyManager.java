package com.game.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.Enemy.Enemy;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class EnemyManager {


    private ArrayList<ArrayList<Enemy> > enemies;
    private ArrayList<Integer> spawningDelay;

    private ArrayList<Enemy> allEnemyArray = new ArrayList<Enemy>();



    public EnemyManager(){
        enemies = new ArrayList<>();
        spawningDelay = new ArrayList<>();
    }

    public void addWaveToSpawn(ArrayList<Enemy> wave) {
        enemies.add(wave);
        spawningDelay.add(60/wave.size());

    }

    //public void update() {
    //    deltaTime = Gdx.graphics.getDeltaTime();
    //    enemyIterator = enemies.iterator();
   // }

    public void update(float deltaTime) {
        for(Enemy e : allEnemyArray){
            e.update(deltaTime);
        }

    }


    public void render(SpriteBatch spritebatch){

        for (ArrayList<Enemy> EnemyWave : enemies)
            for (Enemy enemy : EnemyWave)
                enemy.render(spritebatch);
                // enemy.draw(batch);

    }


}
