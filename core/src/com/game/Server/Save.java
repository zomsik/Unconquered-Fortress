package com.game.Server;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCrypt;


@Document("saves")
public class Save {

    @Id
    private String id;

    private String login;
    private int profileNumber;
    private int seed;
    private String difficulty;
    private int finishedMaps;
    private int health;
    private int maxHealth;
    private int wave;
    private int gold;
    private int diamonds;
    private String terrainModifications;
    private String buildings;
    private String roadObstacles;
    private String unlockedUpgrades;

    public Save()
    {}

    public Save(JSONObject data) {
        this.login = data.getString("login");
        this.profileNumber = data.getInt("profileNumber");
        this.seed = data.getInt("seed");
        this.difficulty = data.getString("difficulty");
        this.finishedMaps = data.getInt("finishedMaps");
        this.health = data.getInt("health");
        this.maxHealth = data.getInt("maxHealth");
        this.wave = data.getInt("wave");
        this.gold = data.getInt("gold");
        this.diamonds = data.getInt("diamonds");
        this.terrainModifications = data.getJSONArray("terrainModifications").toString();
        this.buildings = data.getJSONArray("buildings").toString();
        this.roadObstacles = data.getJSONArray("roadObstacles").toString();
        this.unlockedUpgrades = data.getJSONArray("unlockedUpgrades").toString();
    }

    public Save(String id, JSONObject data) {
        this.id = id;
        this.login = data.getString("login");
        this.profileNumber = data.getInt("profileNumber");
        this.seed = data.getInt("seed");
        this.difficulty = data.getString("difficulty");
        this.finishedMaps = data.getInt("finishedMaps");
        this.health = data.getInt("health");
        this.maxHealth = data.getInt("maxHealth");
        this.wave = data.getInt("wave");
        this.gold = data.getInt("gold");
        this.diamonds = data.getInt("diamonds");
        this.terrainModifications = data.getJSONArray("terrainModifications").toString();
        this.buildings = data.getJSONArray("buildings").toString();
        this.roadObstacles = data.getJSONArray("roadObstacles").toString();
        this.unlockedUpgrades = data.getJSONArray("unlockedUpgrades").toString();
    }

    public String getId() {
        return id;
    }

    public JSONObject getAsJsonObject() {
        JSONObject j = new JSONObject();
        j.put("login", login);
        j.put("profileNumber", profileNumber);
        j.put("seed", seed);
        j.put("difficulty", difficulty);
        j.put("finishedMaps", finishedMaps);
        j.put("health", health);
        j.put("maxHealth", maxHealth);
        j.put("wave", wave);
        j.put("gold", gold);
        j.put("diamonds", diamonds);
        j.put("terrainModifications", new JSONArray(terrainModifications));
        j.put("buildings", new JSONArray(buildings));
        j.put("roadObstacles", new JSONArray(roadObstacles));
        j.put("unlockedUpgrades", new JSONArray(unlockedUpgrades));

        return j;
    }
}
