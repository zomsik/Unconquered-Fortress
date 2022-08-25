package com.game.Manager;

import com.badlogic.gdx.Gdx;
import com.game.Enemy.Enemy;

import java.util.ArrayList;
import java.util.Iterator;

public class EnemyManager {

    private float deltaTime;
    private Iterator<ArrayList<Enemy>> enemyIterator;

    private ArrayList<ArrayList<Enemy> > enemies;
    private ArrayList<Integer> spawningDelay;

    public EnemyManager(){
        enemies = new ArrayList<>();
        spawningDelay = new ArrayList<>();
    }

    public void addWaveToSpawn(ArrayList<Enemy> wave) {
        enemies.add(wave);
        spawningDelay.add(60/wave.size());

    }

    public void update() {
        deltaTime = Gdx.graphics.getDeltaTime();
        enemyIterator = enemies.iterator();
    }

    public void draw(){

        for (ArrayList<Enemy> EnemyWave : enemies)
            for (Enemy enemy : EnemyWave)
                enemy.draw();
                // enemy.draw(batch);

    }


}
