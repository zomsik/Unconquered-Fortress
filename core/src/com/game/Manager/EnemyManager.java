package com.game.Manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.game.Enemy.Enemy;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class EnemyManager {

    private int enemySpawnerTileX, enemySpawnerTileY, tileSize;

    private ArrayList<ArrayList<Enemy>> enemies;
    private ArrayList<Float> spawningDelay;
    private ArrayList<Float> timeLeftToSpawn;

    private ArrayList<Enemy> allEnemyArray = new ArrayList<Enemy>();

    private Vector2 enemySpawnerPosition;

    private Vector2 gameScreen;
    private float scale;

    public EnemyManager() {
        enemies = new ArrayList<>();
        spawningDelay = new ArrayList<>();
    }

    public EnemyManager(int enemySpawnerTileX, int enemySpawnerTileY, int tileSize, Vector2 gameScreen, float scale) {
        enemies = new ArrayList<>();
        spawningDelay = new ArrayList<>();
        timeLeftToSpawn =  new ArrayList<>();
        this.scale = scale;

        this.enemySpawnerTileX = enemySpawnerTileX;
        this.enemySpawnerTileY = enemySpawnerTileY;
        this.tileSize = tileSize;

        this.gameScreen = gameScreen;

        calculateEnemyCenter(scale);

    }

    private void calculateEnemyCenter(float scale) {
        enemySpawnerPosition = new Vector2(enemySpawnerTileX * scale * tileSize + Gdx.graphics.getWidth() / 20, (9 - enemySpawnerTileY) * scale * tileSize + (Gdx.graphics.getHeight() - Gdx.graphics.getWidth() / 30 * 16) / 2);
    }

    public Vector2 getEnemySpawnerPosition() {
        return enemySpawnerPosition;
    }


    public void addWaveToSpawn(ArrayList<Enemy> wave) {
        enemies.add(wave);
        spawningDelay.add(60 / (float) wave.size());
        timeLeftToSpawn.add(0f);

    }

    public void resize() {
        //resize all enemies
    }

    public void spawn(Enemy e) {
        e.position.x = 900;
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

                    System.out.println(enemies.get(i).get(0).p.x);
                    System.out.println(enemies.get(i).get(0).p.y);

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

        System.out.println(allEnemyArray.size());
        for (Enemy e : allEnemyArray) {
            e.update(deltaTime);

        }


    }


    public void render(SpriteBatch spritebatch) {

        for (Enemy e : allEnemyArray) {
            e.render(spritebatch);

        }


    }
}