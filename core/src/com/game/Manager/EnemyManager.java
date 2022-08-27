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

    private ArrayList<ArrayList<Enemy> > enemies;
    private ArrayList<Integer> spawningDelay;

    private ArrayList<Enemy> allEnemyArray = new ArrayList<Enemy>();

    private Vector2 enemySpawnerPosition;

    private Vector2 gameScreen;

    public EnemyManager(){
        enemies = new ArrayList<>();
        spawningDelay = new ArrayList<>();
    }

    public EnemyManager(int enemySpawnerTileX, int enemySpawnerTileY, int tileSize, Vector2 gameScreen){
        enemies = new ArrayList<>();
        spawningDelay = new ArrayList<>();


        this.enemySpawnerTileX = enemySpawnerTileX;
        this.enemySpawnerTileY = enemySpawnerTileY;
        this.tileSize = tileSize;

        this.gameScreen = gameScreen;

        calculateEnemyCenter();

    }

    private void calculateEnemyCenter()
    {
        System.out.println("enemySpawner x: "+enemySpawnerTileX + ", y: " + enemySpawnerTileY);
        enemySpawnerPosition = new Vector2(enemySpawnerTileX * tileSize + Gdx.graphics.getWidth()/10*2 ,(9 - enemySpawnerTileY) * tileSize + Gdx.graphics.getHeight()/10*2);
        //change 500 and 200 to gdx width/x and gdx height/x

        //System.out.println(enemySpawnerPosition.x);
        //System.out.println(enemySpawnerPosition.y);

    }

    public Vector2 getEnemySpawnerPosition(){
        return enemySpawnerPosition;
    }


    public void addWaveToSpawn(ArrayList<Enemy> wave) {
        enemies.add(wave);
        spawningDelay.add(60/wave.size());

    }

    public void resize() {
        //resize all enemies
    }

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
