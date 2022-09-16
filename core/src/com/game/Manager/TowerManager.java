package com.game.Manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.game.Entity.Base;
import com.game.Entity.Enemy.Enemy;
import com.game.Entity.Tower.Tower;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class TowerManager {

    private ArrayList<Tower> towers;
    private ArrayList<Enemy> enemies;

    private float scale;

    public TowerManager(){
        towers = new ArrayList<>();


    }

    public TowerManager(ArrayList<Enemy> enemies, ArrayList<Tower> buildings){
        this.enemies = enemies;
        this.towers = buildings;

    }


    public void buyTower(Tower t) {
        towers.add(t);
    }

    public void sellTower(int x, int y) {

        Iterator<Tower> tIterator = towers.iterator();
        while (tIterator.hasNext()) {
            Tower t = tIterator.next();

            if(t.getTileX() == x && t.getTileY() == y)
            {
                //increase money for selling
                // return money for example public float sellTower
                tIterator.remove();
                return;
            }

        }

    }

    public JSONArray getTowers() {
        JSONArray j = new JSONArray();

        for (Tower tower: towers)
        {
            JSONObject t = new JSONObject();
            t.put("name", tower.getName());
            t.put("x", tower.getTileX());
            t.put("y", tower.getTileY());
            t.put("level", tower.getLvl());
            j.put(t);
        }

        return j;
    }



    public void update(float deltaTime) {

        for (Tower t : towers) {
            t.update(deltaTime, enemies);

        }


    }

    public void render(SpriteBatch spritebatch, ShapeRenderer shapeRenderer) {

        for (Tower t : towers) {
            t.render(spritebatch, shapeRenderer);

        }


    }



}
