package com.game.Manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.game.Entity.Bullet;
import com.game.Entity.Enemy.Enemy;
import com.game.Entity.RoadObstacle.RoadObstacle;
import com.game.Entity.Tower.Tower;
import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Iterator;

public class RoadObstaclesManager {
    private ArrayList<Enemy> enemies;
    private ArrayList<RoadObstacle> roadObstacles;

    public RoadObstaclesManager(ArrayList<Enemy> enemies, ArrayList<RoadObstacle> roadObstacles){
        this.enemies = enemies;
        this.roadObstacles = roadObstacles;

    }

    public JSONArray getRoadObstacles() {
        JSONArray j = new JSONArray();

        for (RoadObstacle roadObstacle: roadObstacles)
        {
            JSONObject t = new JSONObject();
            t.put("name", roadObstacle.getName());
            t.put("x", roadObstacle.getTileX());
            t.put("y", roadObstacle.getTileY());
            t.put("usesLeft", roadObstacle.getUsesLeft());
            j.put(t);
        }

        return j;
    }


    public void buyObstacle(RoadObstacle r) {
        roadObstacles.add(r);
    }

    public void update(float deltaTime) {

        Iterator<RoadObstacle> rIterator = roadObstacles.iterator();
        while (rIterator.hasNext()) {
            RoadObstacle r = rIterator.next();

            r.update(deltaTime, enemies);

            if (r.getUsesLeft() <= 0)
            {
                rIterator.remove();
            }

        }

    }

    public void render(SpriteBatch spritebatch) {

        for (RoadObstacle r : roadObstacles) {
            r.render(spritebatch);
        }
    }


}