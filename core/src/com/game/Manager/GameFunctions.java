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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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


        arr[0][0] = new Image(images_buildings, "sword");
        arr[0][1] = new Image(images_buildings, "bow");
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
        arr[0][1] = new Image(images_buildings, "bow");
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
        t.add(bNextWave).align(Align.center).height(32).width(128).colspan(2).padTop(4);
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


    public static ArrayList<Enemy> createTestEnemyWave(JSONObject actualGame, float scale) {
        ArrayList<Enemy> enemies = new ArrayList<>();

        enemies.add(new Warrior());
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

    public static ArrayList<Enemy> createRandomEnemyWave(JSONObject actualGame)
    {

        ArrayList<Enemy> enemies = new ArrayList<>();

        Random random = new Random(actualGame.getInt("seed"));
        int wave = actualGame.getInt("wave");
        int randomNumber = (Math.abs(actualGame.getInt("seed")))/((wave+1)*13);
        for (int i=0; i<wave*7; i++)
            random.nextInt(randomNumber);

        // 1 -normal
        // 2 - tank
        // 3 - speed
        // 4 - summoner
        System.out.print(wave + ": ");
        int enemyPoints = 50 + (wave/10 + wave)*5;

        if (wave % 10 == 5)
        {
            enemies.add(new MiniBoss());
            System.out.print("Mini Boss,");
            enemyPoints -= 20;

        }
        else if (wave % 10 == 0 && wave>=10)
        {
            enemies.add(new Boss());
            System.out.print("Boss,");
            enemyPoints -= 50;
        }

        // another option :
        // if random 1 spawn normal //else if 2 spawn other enemy
        // causes to spawn ~50% normal enemies

        while (enemyPoints>=5)
        {

            int chosenEnemy = random.nextInt(1, 6);

            if (chosenEnemy == 1 && enemyPoints >= 5)
            {
                enemies.add(new Warrior());
                System.out.print("Normal,");
                enemyPoints -= 5;
            }
            else if (chosenEnemy == 2 && wave>5 && enemyPoints >= 10)
            {
                enemies.add(new Tank());
                System.out.print("Tank,");
                enemyPoints -= 10;
            }
            else if (chosenEnemy == 3 && wave>10 && enemyPoints >= 15)
            {
                enemies.add(new Assassin());
                System.out.print("Speed,");
                enemyPoints -= 15;
            }
            else if (chosenEnemy == 4 && wave>20 && enemyPoints >= 20)
            {
                enemies.add(new MiniBoss());
                System.out.print("Mini Boss,");
                enemyPoints -= 20;
            }
            else if (chosenEnemy == 5 && wave>30 && enemyPoints >= 30 )
            {
                enemies.add(new Boss());
                System.out.print("Summoner,");
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
        float range = turretLevels.getJSONArray(chosenOperation+"Tower").getJSONObject(0).getFloat("range")*scale;
        TextureRegion towerPreview = new TextureRegion();
        float towerTextureSize=64;

        Vector2 position = new Vector2(tile.getX() * scale * 64 + Gdx.graphics.getWidth() / 20,(9 - tile.getY()) * scale * 64 + (Gdx.graphics.getHeight() - Gdx.graphics.getWidth() / 30 * 16) / 2);
        towerPreview = new TextureRegion(new Texture(Gdx.files.internal("assets/game/towers/"+chosenOperation+"Preview.png")));

        spritebatch.draw(towerPreview, position.x, position.y,towerTextureSize*scale,towerTextureSize*scale );

        spritebatch.end();

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.circle(position.x+scale*towerTextureSize/2, position.y+scale*towerTextureSize/2, range);
        shapeRenderer.end();

        spritebatch.begin();




    }





}

