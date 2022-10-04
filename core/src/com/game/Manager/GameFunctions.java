package com.game.Manager;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.game.Entity.Base;
import com.game.Entity.Enemy.*;
import com.game.Screens.GameScreen;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.List;

public class GameFunctions {

    public static Image[][] getEmptyBuildingsArr(){
        Image[][] arr = new Image[10][15];
        Skin images_buildings = new Skin(new TextureAtlas("assets/icons/buildings.pack"));

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 15; j++) {
                arr[i][j] = new Image(images_buildings,"empty");
                arr[i][j].setTouchable(Touchable.disabled);

            }
        }


        return arr;
    }


    public static Image[][] addBuilding(GameScreen gameScreen, Image[][] buildingsArr, int x, int y, String tileName){
        Skin buildings_skin = new Skin(new TextureAtlas("assets/icons/buildings.pack"));
        buildingsArr[y][x].setDrawable(buildings_skin, tileName);
        buildingsArr[y][x].setName(tileName);
        buildingsArr[y][x].setTouchable(Touchable.enabled);


        buildingsArr[y][x].addListener(new ImageClickListener(x,y,buildingsArr[y][x].getName()){
            public void clicked(InputEvent event, float x, float y) {
                this.setLastClickedTile(gameScreen.lastClickedMapTile);
                gameScreen.mouseClickBuildingTile();
            }

            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                this.setLastClickedTile(gameScreen.lastClickedMapTile);
                //gameScreen.mouseEnterMapTile();

            }
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                //this.setLastClickedTile();
                //gameScreen.mouseExitMapTile();
            }

        });


        return buildingsArr;
    }

    public static Image[][] sellBuilding(Image[][] buildingsArr, int x, int y){

        Skin buildings_skin = new Skin(new TextureAtlas("assets/icons/buildings.pack"));
        buildingsArr[y][x].setDrawable(buildings_skin, "empty");
        buildingsArr[y][x].setName("empty");
        buildingsArr[y][x].setTouchable(Touchable.disabled);

        buildingsArr[y][x].clearListeners();


        return buildingsArr;
    }

    public static Table getBuildingsTable(Image[][] arr, float scale) {
        Table t = new Table();
        t.setBounds(Gdx.graphics.getWidth()/20 , (Gdx.graphics.getHeight()-Gdx.graphics.getWidth()/30*16)/2 , 960 , 640);
        t.setTransform(true);
        System.out.println(Gdx.graphics.getWidth()/10*8);
        System.out.println(Gdx.graphics.getWidth()/30*16);

        for (int i = 0; i<10; i++)
        {
            for (int j = 0; j<15; j++)
            {
                t.add(arr[i][j]);


            }
            t.row();
        }

        t.setScale(scale);

        return t;
    }





    public static Image[][] getOperationsArr(GameScreen gameScreen){
        Image[][] arr = new Image[4][2];
        Skin images_buildings = new Skin(new TextureAtlas("assets/icons/buildings.pack"));


        /*
                arr[0][1] = new Image(images_buildings, "bow");
        arr[1][0] = new Image(images_buildings, "mage");
        arr[1][1] = new Image(images_buildings, "cannon");
         */

        arr[0][0] = new Image(images_buildings, "sword");
        arr[0][1] = new Image(images_buildings, "locked");
        arr[1][0] = new Image(images_buildings, "locked");
        arr[1][1] = new Image(images_buildings, "locked");
        arr[2][0] = new Image(images_buildings, "stickyRoad");
        arr[2][1] = new Image(images_buildings, "roadNeedles");
        arr[3][0] = new Image(images_buildings, "clean");
        arr[3][1] = new Image(images_buildings, "sell");

        arr[0][1].setTouchable(Touchable.disabled);
        arr[1][0].setTouchable(Touchable.disabled);
        arr[1][1].setTouchable(Touchable.disabled);

        arr[0][0].setName("melee");
        arr[0][1].setName("crossbow");
        arr[1][0].setName("mage");
        arr[1][1].setName("cannon");
        arr[2][0].setName("stickyRoad");
        arr[2][1].setName("roadNeedles");
        arr[3][0].setName("clean");
        arr[3][1].setName("sell");


        for(int i = 0; i<4; i++)
            for(int j = 0; j<2; j++) {
                arr[i][j].addListener(new ImageClickListener(j, i, arr[i][j].getName()) {
                    public void clicked(InputEvent event, float x, float y) {
                        this.setLastClickedTile(gameScreen.lastClickedOperationTile);
                        gameScreen.mouseClickOperation();
                    }

                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        this.setLastClickedTile(gameScreen.lastClickedOperationTile);
                        gameScreen.mouseEnterOperation();

                    }

                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        //this.lastClickedBuilding();
                        gameScreen.mouseExitOperation();
                    }


                });
            }


        return arr;
    }


    public static Image[][] getOperationsSelectedArr() {
        Image[][] arr = new Image[4][2];
        Skin images_buildings = new Skin(new TextureAtlas("assets/icons/buildings.pack"));


        arr[0][0] = new Image(images_buildings, "sword");
        arr[0][1] = new Image(images_buildings, "crossbow");
        arr[1][0] = new Image(images_buildings, "mage");
        arr[1][1] = new Image(images_buildings, "cannon");
        arr[2][0] = new Image(images_buildings, "stickyRoad");
        arr[2][1] = new Image(images_buildings, "roadNeedles");
        arr[3][0] = new Image(images_buildings, "clean");
        arr[3][1] = new Image(images_buildings, "sell");

        arr[0][0].setName("melee");
        arr[0][1].setName("crossbow");
        arr[1][0].setName("mage");
        arr[1][1].setName("cannon");
        arr[2][0].setName("stickyRoad");
        arr[2][1].setName("roadNeedles");
        arr[3][0].setName("clean");
        arr[3][1].setName("sell");

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                arr[i][j] = new Image(images_buildings, "empty");
                arr[i][j].setTouchable(Touchable.disabled);
            }
        }


        return arr;
    }


    public static Table getOperationsTable(Image[][] arr, float scale, TextButton bNextWave)
    {
        Table t = new Table();
        t.setBounds(Gdx.graphics.getWidth()-224*scale, (Gdx.graphics.getHeight()-Gdx.graphics.getWidth()/30*16)/2+48*scale+16*scale, 224, 350);
        t.setTransform(true);

        Skin images_buildings = new Skin(new TextureAtlas("assets/icons/buildings.pack"));
        t.row().padBottom(4);
        for (int i = 0; i<3; i++)
        {
            for (int j = 0; j<2; j++)
            {
                t.add(arr[i][j]);
            }

            t.row().padBottom(4);

        }
        t.add(new Image(images_buildings, "separator")).colspan(2).align(Align.left);
        t.row();
        t.add(arr[3][0]).padTop(4);
        t.add(arr[3][1]).padTop(4);
        t.row();
        t.add(bNextWave).align(Align.center).height(32).width(128).colspan(2).padTop(6);
        t.setScale(scale);
        return t;
    }


    public static Image[][] loadPlacedBuildings (GameScreen gameScreen, Image[][] buildingsArr, JSONArray loadedBuildingsArr)
    {
        Skin images_buildings = new Skin(new TextureAtlas("assets/icons/buildings.pack"));

        for (int i = 0; i< loadedBuildingsArr.length(); i++) {
            JSONObject j = loadedBuildingsArr.getJSONObject(i);

            buildingsArr = GameFunctions.addBuilding(gameScreen, buildingsArr, j.getInt("x"), j.getInt("y"), j.getString("buildingName"));

            /*

            buildingsArr[y][x].setDrawable(buildings_skin, tileName);
            buildingsArr[y][x].setName(tileName);
            buildingsArr[y][x].setTouchable(Touchable.enabled);


            buildingsArr[y][x].addListener(new ImageClickListener(x,y,buildingsArr[y][x].getName()){
                public void clicked(InputEvent event, float x, float y) {
                    this.setLastClickedTile(gameScreen.lastClickedMapTile);
                    gameScreen.mouseClickBuildingTile();
                }

                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    this.setLastClickedTile(gameScreen.lastClickedMapTile);
                    //gameScreen.mouseEnterMapTile();

                }
                public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    //this.setLastClickedTile();
                    //gameScreen.mouseExitMapTile();
                }

            });
            */
        }

        return buildingsArr;
    }


    public static ArrayList<Enemy> createTestEnemyWave() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        /*enemies.add(new Flying());
        enemies.add(new Summoner());
        enemies.add(new Assassin());
        enemies.add(new Warrior());
        enemies.add(new Flying());
        enemies.add(new Summoner());
        enemies.add(new Assassin());
        enemies.add(new Warrior());
        enemies.add(new Flying());
        enemies.add(new Summoner());
        enemies.add(new Assassin());*/

        return enemies;
    }

    public static ArrayList<Enemy> createRandomEnemyWave(int wave, int seed, JSONObject enemiesJSONObject)
    {

        ArrayList<Enemy> enemies = new ArrayList<>();

        Random random = new Random(seed);
        int randomNumber = (Math.abs(seed))/((wave+1)*13);
        for (int i=0; i<wave*7; i++)
            random.nextInt(randomNumber);

        System.out.print(wave + ": ");
        int enemyPoints = 50 + (wave/10 + wave)*5;


        if (wave % 10 == 5)
        {

            enemies.add(new Summoner(enemiesJSONObject.getJSONObject("summoner"), enemiesJSONObject.getJSONObject("summon")));
            enemyPoints -= 30;

        }
        else if (wave % 10 == 0 && wave>=10)
        {
            enemies.add(new Boss(enemiesJSONObject.getJSONObject("boss")));
            enemyPoints -= 50;
        }

        JSONObject spawnRate = enemiesJSONObject.getJSONObject("spawnRate");
        JSONObject spawnWave = enemiesJSONObject.getJSONObject("spawnWave");

        while (enemyPoints>=5)
        {

            int chosenEnemy = random.nextInt(0, spawnRate.getInt("warrior") + spawnRate.getInt("blob") + spawnRate.getInt("assassin") + spawnRate.getInt("flying") + spawnRate.getInt("summoner")+1);
            if (chosenEnemy <= spawnRate.getInt("warrior") && wave>=spawnWave.getInt("warrior") && enemyPoints >= 5)
            {
                enemies.add(new Warrior(enemiesJSONObject.getJSONObject("warrior")));
                enemyPoints -= 5;
            }
            else if (spawnRate.getInt("warrior") < chosenEnemy && chosenEnemy <= spawnRate.getInt("warrior")+spawnRate.getInt("blob") && wave>=spawnWave.getInt("blob") && enemyPoints >= 7)
            {
                enemies.add(new Blob(enemiesJSONObject.getJSONObject("blob")));
                enemyPoints -= 7;
            }
            else if (spawnRate.getInt("warrior")+spawnRate.getInt("blob") < chosenEnemy && chosenEnemy <= spawnRate.getInt("warrior")+spawnRate.getInt("blob")+spawnRate.getInt("assassin") && wave>=spawnWave.getInt("assassin") && enemyPoints >= 10)
            {
                enemies.add(new Assassin(enemiesJSONObject.getJSONObject("assassin")));
                enemyPoints -= 10;
            }
            else if (spawnRate.getInt("warrior")+spawnRate.getInt("blob")+spawnRate.getInt("assassin") < chosenEnemy && chosenEnemy <= spawnRate.getInt("warrior")+spawnRate.getInt("blob")+spawnRate.getInt("assassin")+spawnRate.getInt("flying") && wave>=spawnWave.getInt("flying") && enemyPoints >= 15)
            {
                enemies.add(new Flying(enemiesJSONObject.getJSONObject("flying")));
                enemyPoints -= 15;
            }
            else if (spawnRate.getInt("warrior")+spawnRate.getInt("blob")+spawnRate.getInt("assassin")+spawnRate.getInt("flying") < chosenEnemy && chosenEnemy <= spawnRate.getInt("warrior")+spawnRate.getInt("blob")+spawnRate.getInt("assassin")+spawnRate.getInt("flying")+spawnRate.getInt("summoner") && wave>=spawnWave.getInt("summoner")  && enemyPoints >= 30 )
            {
                enemies.add(new Summoner(enemiesJSONObject.getJSONObject("summoner"), enemiesJSONObject.getJSONObject("summon")));
                enemyPoints -= 30;
            }

        }

        return enemies;
    }


    public static ArrayList<Vector2> calculatePath(List<int[]> path, float scale) {

        ArrayList<Vector2> v = new ArrayList<>();

        for (int[] point : path)
        {
            System.out.println("x:"+point[1] + ", y:"+point[0]);
            v.add(new Vector2(point[1] * scale * 64 + Gdx.graphics.getWidth() / 20, (9 - point[0]) * scale * 64 + (Gdx.graphics.getHeight() - Gdx.graphics.getWidth() / 30 * 16) / 2));

        }


        Collections.reverse(v);

        return v;
    }


    public static void renderPreviewBuilding(SpriteBatch spritebatch, ShapeRenderer shapeRenderer, LastClickedTile tile, JSONObject turretLevels, String chosenOperation, float scale) {

        TextureRegion preview = new TextureRegion(new Texture(Gdx.files.internal("assets/game/towers/"+chosenOperation+"Preview.png")));
        float textureSize = 64;
        Vector2 position = new Vector2(tile.getX() * scale * 64 + Gdx.graphics.getWidth() / 20,(9 - tile.getY()) * scale * 64 + (Gdx.graphics.getHeight() - Gdx.graphics.getWidth() / 30 * 16) / 2);
        spritebatch.draw(preview, position.x, position.y,textureSize*scale,textureSize*scale );

        if ((!Objects.equals(chosenOperation, "roadNeedles") && !Objects.equals(chosenOperation, "stickyRoad"))) {

            float range = turretLevels.getJSONArray(chosenOperation + "Tower").getJSONObject(0).getFloat("range") * scale;

            spritebatch.end();

            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.circle(position.x+scale*textureSize/2, position.y+scale*textureSize/2, range);
            shapeRenderer.end();

            spritebatch.begin();
        }


    }





}

