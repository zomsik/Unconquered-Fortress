package com.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.game.Entity.Enemy.Enemy;
import com.game.Entity.Base;
import com.game.Entity.RoadObstacle.RoadNeedles;
import com.game.Entity.RoadObstacle.RoadObstacle;
import com.game.Entity.RoadObstacle.RoadSticky;
import com.game.Entity.Tower.*;
import com.game.Main;
import com.game.Manager.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class GameScreen implements Screen {
    private Main game;
    private Stage stage, pauseStage, gameOverStage;
    private Texture background;
    private BitmapFont font;
    private TextureAtlas taButtonsSettings, taButtonsDefault, taDialogBack, taButtonsPause, taStatsCover, taButtonTips;
    private Skin images, images_default, dialog, images_map, images_buildings, images_pause, images_stats, images_tips;
    private TextButton  bSaveDialog, bExitDialog, bSaveAndExitDialog, bTest, bNextWave, bResume, bPauseMenu, bUpgrade, bTips;

    private Table table_info, table_map, table_dialogPause, table_nextWave, table_operations, table_operationsSelected , table_buildings, table_dialogGameOver, table_enemies, table_stats, table_menuPause, table_tips;
    private TextField hpTextField,hpTextValue, goldTextField, goldTextValue, GameOverTitle;
    private TextField.TextFieldStyle statsTextFieldStyle, rightStatsTextFieldStyle, leftStatsTextFieldStyle;
    private ArrayList<String> resolutions;
    private ArrayList<String> languages;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private TextButton.TextButtonStyle textButtonStyle_bLeft, textButtonStyle_bRight, textButtonStyle_bBack, textButtonStyle_bSave, textButtonStyle_bPauseMenu, textButtonStyle_bTips;
    private TextField.TextFieldStyle textFieldStyle;
    private Music backgroundMusic;
    private Slider volumeSlider;
    private Slider.SliderStyle sliderStyle;
    private Resolutions resolutionsClass;
    private ButtonStyleManager buttonStyleManager;
    private TextFieldStyleManager textFieldStyleManager;
    private FileReader fileReader;
    private LanguageManager languageManager;
    private Dialog pauseDialog, gameOverDialog;
    private WorldManager worldManager;
    private JSONObject actualGame, enemies;
    private boolean isLocal;
    private ConnectionManager connectionManager;
    private Image[][] mapArr, operationsArr, operationsSelectedArr, buildingsArr;
    private String chosenOperation = null;
    private int chosenOperationX, chosenOperationY;
    private int tick=0;
    private SpriteBatch spritebatch = new SpriteBatch();
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    private float scale;
    private boolean shouldRenderPreview = false;

    private int[][] buildArr;

    private ArrayList<Enemy> ee = new ArrayList<>();
    private EnemyManager enemyManager;
    private TowerManager towerManager;
    private UpgradeManager upgradeManager;
    private RoadObstaclesManager roadObstaclesManager;

    public LastClickedTile lastClickedMapTile, lastClickedOperationTile;

    private StatsTableManager statsTableManager;
    private Base base;

    OrthographicCamera hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    private Dialog eventDialog, infoDialog, upgradeDialog, tipsDialog;
    private TextButton bBackEventDialog, bBackInfoDialog;

    private TextButton bGameOverExit, bGameOverReplay;

    private TipsManager tipsManager;

    /*
    public enum State{
        Running, Paused, Resumed, GameOver
    }

    private State state = State.Running;
    */

    private JSONObject turretLevels;

    private Music cleanSound, sellSound, buySound;

    public GameScreen (Main game, JSONObject save, boolean isLocal){
        this.game = game;
        this.isLocal = isLocal;
        scale = (float) (Gdx.graphics.getWidth() / 1280.0);

        System.out.println("Skala: " + scale);
        lastClickedMapTile = new LastClickedTile();
        lastClickedOperationTile = new LastClickedTile();
        resolutionsClass = new Resolutions();
        fileReader = new FileReader();
        fileReader.downloadSettings();
        if(fileReader.getLanguageValue() != null){languageManager = new LanguageManager(fileReader.getLanguageValue());}else{languageManager = new LanguageManager("English");}

        initSettingsUI();

        actualGame = save;
        if (!isLocal)
            actualGame.put("login",game.getLogin());

        enemies = fileReader.downloadFileAsJSONObject("assets/enemies.json");
        turretLevels = fileReader.downloadFileAsJSONObject("assets/towers.json");
        operationsArr = GameFunctions.getOperationsArr(this);
        base = new Base(actualGame,operationsArr);
        tipsManager = new TipsManager(languageManager,font, base, scale);
        //reading stats etc
        if(actualGame.getString("difficulty").equals("normal")){
            worldManager.createWorld(this, actualGame.getInt("seed"), 46);
            mapArr = worldManager.createWorld(this, actualGame.getInt("seed"), 46);
        }else if(actualGame.getString("difficulty").equals("hard")){
            worldManager.createWorld(this, actualGame.getInt("seed"), 46);
            mapArr = worldManager.createWorld(this, actualGame.getInt("seed"), 36);
        }else if(actualGame.getString("difficulty").equals("easy")){
            worldManager.createWorld(this, actualGame.getInt("seed"), 46);
            mapArr = worldManager.createWorld(this, actualGame.getInt("seed"), 51);
        }

        mapArr = worldManager.loadTerrainModifications(this, mapArr, actualGame.getJSONArray("terrainModifications"));

        this.buildArr = new int[15][10];

        enemyManager = new EnemyManager(base, scale, GameFunctions.calculatePath(worldManager.getPath(), scale));
        towerManager = new TowerManager(enemyManager.getEnemies());
        base.addTowerManager(towerManager);
        roadObstaclesManager = new RoadObstaclesManager(enemyManager.getEnemies(), buildArr);

        table_map = worldManager.drawWorld(mapArr, scale);

        hudCamera.position.set(hudCamera.viewportWidth / 2.0f, hudCamera.viewportHeight / 2.0f, 1.0f);

        statsTableManager = new StatsTableManager(base,scale, languageManager);
        table_info = statsTableManager.getStatsTable();

        textFieldStyleManager.setTextFieldStyle(statsTextFieldStyle, images, font, "empty_background", Color.WHITE);
        GameOverTitle = new TextField(languageManager.getValue(languageManager.getLanguage(), "Lose"), textFieldStyleManager.returnTextFieldStyle(statsTextFieldStyle));
        GameOverTitle.setAlignment(Align.center);

        if(Gdx.graphics.getHeight() == 900){
            table_menuPause.setBounds(Gdx.graphics.getWidth()/40*37,(Gdx.graphics.getHeight()/40*38-2), Gdx.graphics.getWidth()/50*3,Gdx.graphics.getHeight()/40*3);
        }else{
            table_menuPause.setBounds(Gdx.graphics.getWidth()/40*37,(Gdx.graphics.getHeight()/40*37), Gdx.graphics.getWidth()/50*3,Gdx.graphics.getHeight()/40*3);
        }

        table_tips.setBounds(Gdx.graphics.getWidth()/80*67+8*scale,(Gdx.graphics.getHeight()-(Gdx.graphics.getWidth()/50*3-12*scale))-2, Gdx.graphics.getWidth()/50*3,Gdx.graphics.getWidth()/50*3-12*scale);

        buttonStyleManager = new ButtonStyleManager();
        textFieldStyleManager = new TextFieldStyleManager();


        buttonStyleManager.setTextButtonStyle(textButtonStyle_bBack, images_default, font, "defaultButton", "defaultButton");
        buttonStyleManager.setTextButtonStyle(textButtonStyle_bTips, images_tips,font, "button_tips", "button_tips");
        bTips = new TextButton("", buttonStyleManager.returnTextButtonStyle(textButtonStyle_bTips));
        bSaveDialog  = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bSave"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bBack));
        bExitDialog = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bExit"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bBack));
        bSaveAndExitDialog = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bSaveAndExit"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bBack));
        bNextWave = new TextButton(languageManager.getValue(languageManager.getLanguage(),"bNextWave"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bBack));
        bResume = new TextButton(languageManager.getValue(languageManager.getLanguage(),"bResume"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bBack));
        bUpgrade = new TextButton(languageManager.getValue(languageManager.getLanguage(),"bUpgrade"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bBack));
        buttonStyleManager.setTextButtonStyle(textButtonStyle_bPauseMenu, images_pause, font, "ButtonPauseUp", "ButtonPauseDown");
        bPauseMenu = new TextButton("", buttonStyleManager.returnTextButtonStyle(textButtonStyle_bPauseMenu));
        bBackEventDialog = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bExit"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bBack));
        bBackInfoDialog = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bExit"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bBack));
        table_operations = GameFunctions.getOperationsTable(operationsArr, scale, bUpgrade);
        Texture shopBackground = new Texture(new FileHandle("assets/shopBackground.png"));
        table_operations.setBackground(new TextureRegionDrawable(new TextureRegion(shopBackground)));
        operationsSelectedArr = GameFunctions.getOperationsSelectedArr();
        table_operationsSelected = GameFunctions.getOperationsTable(operationsSelectedArr, scale, bUpgrade);

        bGameOverExit = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bExit"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bBack));
        bGameOverReplay = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bReplay"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bBack));

        //buildingsArr = GameFunctions.getEmptyBuildingsArr();
        //table_buildings = GameFunctions.getBuildingsTable(buildingsArr, scale);

        //buildingsArr = GameFunctions.loadPlacedBuildings(this, buildingsArr, actualGame.getJSONArray("buildings"));
        table_nextWave.add(bNextWave).width(224*scale).padBottom(8*scale).height(48*scale);
        //table_nextWave.row();
        //table_nextWave.add(bTest).width(Gdx.graphics.getWidth()/10);
        table_nextWave.setBounds(Gdx.graphics.getWidth()-224*scale,(Gdx.graphics.getHeight()-Gdx.graphics.getWidth()/30*16)/2,224*scale,48*scale);
        table_menuPause.add(bPauseMenu).height(Gdx.graphics.getHeight()/40*3).width(Gdx.graphics.getWidth()/50*3);
        table_tips.add(bTips).width(64*scale*0.75f).height(64*scale*0.75f);


    }

    public void loadObstacles() {



        JSONArray roadObstacles = actualGame.getJSONArray("roadObstacles");
        ArrayList<RoadObstacle> roadObstaclesLoad = new ArrayList<>();
        for (int b=0; b<roadObstacles.length(); b++)
        {
            buildArr[roadObstacles.getJSONObject(b).getInt("x")][roadObstacles.getJSONObject(b).getInt("y")] = 2;

            switch(roadObstacles.getJSONObject(b).getString("name"))
            {
                case "roadSticky" -> {
                    RoadObstacle t = new RoadSticky(base, roadObstacles.getJSONObject(b).getInt("x"),roadObstacles.getJSONObject(b).getInt("y"), scale, this);
                    t.setUsesLeft(roadObstacles.getJSONObject(b).getInt("usesLeft"));
                    roadObstaclesLoad.add(t);
                }
                case "roadNeedles" -> {
                    RoadObstacle t = new RoadNeedles(base, roadObstacles.getJSONObject(b).getInt("x"),roadObstacles.getJSONObject(b).getInt("y"), scale, this);
                    t.setUsesLeft(roadObstacles.getJSONObject(b).getInt("usesLeft"));
                    roadObstaclesLoad.add(t);
                }

            }
        }
        roadObstaclesManager.initObstacles(roadObstaclesLoad);

    }

    public void loadTowers() {
        JSONArray buildings = actualGame.getJSONArray("buildings");
        for (int b=0; b<buildings.length(); b++)
        {


            switch(buildings.getJSONObject(b).getString("name"))
            {
                case "meleeTower" -> {
                    Tower t = new MeleeTower(turretLevels, base, buildings.getJSONObject(b).getInt("x"),buildings.getJSONObject(b).getInt("y"),scale, this);
                    t.setLevel(buildings.getJSONObject(b).getInt("level"));
                    stage.addActor(t);
                    towerManager.buyTower(t);

                }
                case "mageTower" -> {
                    Tower t = new MageTower(turretLevels, base, buildings.getJSONObject(b).getInt("x"),buildings.getJSONObject(b).getInt("y"),scale, this);
                    t.setLevel(buildings.getJSONObject(b).getInt("level"));
                    stage.addActor(t);
                    towerManager.buyTower(t);

                }
                case "crossbowTower" -> {
                    Tower t = new BowTower(turretLevels, base, buildings.getJSONObject(b).getInt("x"),buildings.getJSONObject(b).getInt("y"),scale, this);
                    t.setLevel(buildings.getJSONObject(b).getInt("level"));
                    stage.addActor(t);
                    towerManager.buyTower(t);

                }
                case "cannonTower" -> {
                    Tower t = new CannonTower(turretLevels, base, buildings.getJSONObject(b).getInt("x"),buildings.getJSONObject(b).getInt("y"),scale, this);
                    t.setLevel(buildings.getJSONObject(b).getInt("level"));
                    stage.addActor(t);
                    towerManager.buyTower(t);

                }
            }
            buildArr[buildings.getJSONObject(b).getInt("x")][buildings.getJSONObject(b).getInt("y")] = 1;
        }
    }


    public void mouseClickOperation() {
        if (Objects.equals(chosenOperation,lastClickedOperationTile.getName()))
        {
            operationsSelectedArr[chosenOperationY][chosenOperationX].setDrawable(images_buildings, "empty");
            chosenOperation = null;
            chosenOperationX = -1;
            chosenOperationY = -1;

            if (towerManager.getIsDisabledListeners() || roadObstaclesManager.getIsDisabledListeners()) {
                towerManager.enableListeners();
                roadObstaclesManager.enableListeners();
            }
        }
        else {
            if (chosenOperation != null)
                operationsSelectedArr[chosenOperationY][chosenOperationX].setDrawable(images_buildings, "empty");
            chosenOperation = lastClickedOperationTile.getName();
            chosenOperationX = lastClickedOperationTile.getX();
            chosenOperationY = lastClickedOperationTile.getY();
            operationsSelectedArr[chosenOperationY][chosenOperationX].setDrawable(images_buildings, "chosen");

            if (!towerManager.getIsDisabledListeners() || !roadObstaclesManager.getIsDisabledListeners())
            {
                towerManager.disableListeners();
                roadObstaclesManager.disableListeners();
            }
        }




    }

    public void mouseEnterOperation() {
        if (Objects.equals(lastClickedOperationTile.getName(), "melee") || Objects.equals(lastClickedOperationTile.getName(), "crossbow") || Objects.equals(lastClickedOperationTile.getName(), "mage")|| Objects.equals(lastClickedOperationTile.getName(), "cannon"))
            base.setInfoToDisplay(1,lastClickedOperationTile.getName(),turretLevels.getJSONArray(lastClickedOperationTile.getName()+"Tower").getJSONObject(0),null);
    }

    public void mouseExitOperation() {
        base.setInfoToDisplay(0);
    }

    public void mouseClickBuildingTile() {
        /*
        if (Objects.equals(chosenOperation,"sell"))
        {
            System.out.println(lastClickedMapTile.getName());
            System.out.println("test1");

            if (Objects.equals(lastClickedMapTile.getName(), "sword") || Objects.equals(lastClickedMapTile.getName(), "bow") || Objects.equals(lastClickedMapTile.getName(), "mage") || Objects.equals(lastClickedMapTile.getName(), "cannon"))
            {
                System.out.println("test12");


                JSONArray placedBuildings = actualGame.getJSONArray("buildings");




                for (int i = 0; i < placedBuildings.length(); i++) {
                    JSONObject searchedObject = placedBuildings.getJSONObject(i);
                    if (lastClickedMapTile.getX() == searchedObject.getInt("x") && lastClickedMapTile.getY() == searchedObject.getInt("y") && lastClickedMapTile.getName() == searchedObject.getString("buildingName"))
                    {
                        placedBuildings.remove(i);
                        break;

                    }
                }
                actualGame.put("buildings", placedBuildings);
                System.out.println("test2");


                //get from buildingsArr type and price etc
                System.out.println("sprzedaje");
                buildingsArr = GameFunctions.sellBuilding(buildingsArr, lastClickedMapTile.getX(), lastClickedMapTile.getY());
                table_buildings = GameFunctions.getBuildingsTable(buildingsArr, scale);
                stage.addActor(table_buildings);
            }


        }*/
    }



    public void mouseClickMapTile() {
        System.out.println(chosenOperation);
        if (Objects.equals(chosenOperation,"sell")) {

            if (buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()] == 1) {
                sellSound.dispose();

                sellSound.play();
                base.increaseMoney(towerManager.getSellWorth(lastClickedMapTile.getX(),lastClickedMapTile.getY()));
                towerManager.sellTower(lastClickedMapTile.getX(),lastClickedMapTile.getY());
                buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()] = 0;
            }

            if (buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()] == 2) {
                sellSound.dispose();
                sellSound.play();
                base.increaseMoney(roadObstaclesManager.getSellWorth(lastClickedMapTile.getX(),lastClickedMapTile.getY()));
                roadObstaclesManager.sellRoadObstacle(lastClickedMapTile.getX(),lastClickedMapTile.getY());
                buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()] = 0;
            }

        }

        if (Objects.equals(chosenOperation,"stickyRoad")) {
            if (Objects.equals(lastClickedMapTile.getName(), "path") && buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]==0) {
                if (base.getMoney() >= 100)
                {
                    base.decreaseMoney(100);
                    RoadObstacle r = new RoadSticky(base, lastClickedMapTile.getX(),lastClickedMapTile.getY(), scale, this);
                    roadObstaclesManager.buyObstacle(r);
                    stage.addActor(r);
                    buySound.dispose();
                    buySound.play();
                    buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]=2;

                }else{
                    showInfoDialog();
                }

            }
        }

        if (Objects.equals(chosenOperation,"roadNeedles")) {
            if (Objects.equals(lastClickedMapTile.getName(), "path") && buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]==0) {
                if (base.getMoney() >= 100)
                {
                    base.decreaseMoney(100);
                    RoadObstacle r = new RoadNeedles(base, lastClickedMapTile.getX(),lastClickedMapTile.getY(), scale, this);
                    roadObstaclesManager.buyObstacle(r);
                    stage.addActor(r);
                    buySound.dispose();
                    buySound.play();
                    buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]=2;

                }else{
                    showInfoDialog();
                }

            }
        }







        if (Objects.equals(chosenOperation,"melee")) {
            if (Objects.equals(lastClickedMapTile.getName(), "grass") && buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]==0) {
                int cost = Math.round(turretLevels.getJSONArray("meleeTower").getJSONObject(0).getInt("cost")*base.getMultipliers().getFloat("costMultiplier"));

                if (base.getMoney()>= cost)
                {
                    base.decreaseMoney(cost);
                    Tower t = new MeleeTower(turretLevels, base, lastClickedMapTile.getX(),lastClickedMapTile.getY(),scale, this);
                    towerManager.buyTower(t);
                    stage.addActor(t);
                    buySound.dispose();
                    buySound.play();
                    buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]=1;
                }else{
                    showInfoDialog();
                }

            }
        }


        if (Objects.equals(chosenOperation,"crossbow")) {

            if (Objects.equals(lastClickedMapTile.getName(), "grass") && buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]==0) {

                int cost = Math.round(turretLevels.getJSONArray("crossbowTower").getJSONObject(0).getInt("cost")*base.getMultipliers().getFloat("costMultiplier"));

                if (base.getMoney()>= cost)
                {
                    base.decreaseMoney(cost);
                    Tower t = new BowTower(turretLevels, base, lastClickedMapTile.getX(),lastClickedMapTile.getY(),scale, this);
                    towerManager.buyTower(t);
                    stage.addActor(t);
                    buySound.dispose();
                    buySound.play();
                    buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]=1;
                }else{
                    showInfoDialog();
                }
            }
        }

        if (Objects.equals(chosenOperation,"mage")) {
            if (Objects.equals(lastClickedMapTile.getName(), "grass") && buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]==0) {
                int cost = Math.round(turretLevels.getJSONArray("mageTower").getJSONObject(0).getInt("cost")*base.getMultipliers().getFloat("costMultiplier"));

                if (base.getMoney()>= cost)
                {
                    base.decreaseMoney(cost);
                    Tower t = new MageTower(turretLevels, base, lastClickedMapTile.getX(),lastClickedMapTile.getY(),scale, this);
                    towerManager.buyTower(t);
                    stage.addActor(t);
                    buySound.dispose();
                    buySound.play();
                    buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]=1;
                }else {
                    showInfoDialog();
                }


            }
        }


        if (Objects.equals(chosenOperation,"cannon")) {
            if (Objects.equals(lastClickedMapTile.getName(), "grass") && buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]==0) {

                int cost = Math.round(turretLevels.getJSONArray("cannonTower").getJSONObject(0).getInt("cost")*base.getMultipliers().getFloat("costMultiplier"));

                if (base.getMoney()>= cost)
                {

                    base.decreaseMoney(cost);
                    Tower t = new CannonTower(turretLevels, base, lastClickedMapTile.getX(),lastClickedMapTile.getY(),scale,this);
                    towerManager.buyTower(t);
                    stage.addActor(t);
                    buySound.dispose();
                    buySound.play();
                    buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]=1;
                }else{
                    showInfoDialog();
                }

                //warunki wybudowania cannonTowera

                /*JSONArray placedBuildings = actualGame.getJSONArray("buildings");
                placedBuildings.put(new JSONObject().put("buildingName","cannon").put("x",lastClickedMapTile.getX()).put("y",lastClickedMapTile.getY()).put("level",1));
                actualGame.put("buildings", placedBuildings);


                buildingsArr = GameFunctions.addBuilding(this, buildingsArr, lastClickedMapTile.getX(), lastClickedMapTile.getY(), "cannon");
                table_buildings = GameFunctions.getBuildingsTable(buildingsArr, scale);
                stage.addActor(table_buildings);*/


            }
        }


        if (Objects.equals(chosenOperation,"clean")) {
            if (Objects.equals(lastClickedMapTile.getName(), "obstacle")) {
                if(base.getMoney()>=base.getCleanPrice()){
                    //jeśli masz kasę itd warunki, może dialog etc
                    cleanSound.dispose();
                    cleanSound.play();
                    base.decreaseMoney(base.getCleanPrice());

                    JSONArray terr = actualGame.getJSONArray("terrainModifications");

                    terr.put(new JSONObject().put("tileName","grass").put("x",lastClickedMapTile.getX()).put("y",lastClickedMapTile.getY()));
                    actualGame.put("terrainModifications", terr);

                    table_map = worldManager.changeTileAndRedrawWorld(this, mapArr, lastClickedMapTile.getX(), lastClickedMapTile.getY(), "grass", scale);

                    /////////////////////////////////////////////////////////////////////////////////////////////////////////

                    stage.addActor(table_map);
                    showEventDialog();
                    //table_buildings.toFront();*/
                }else{
                    showInfoDialog();
                }



            }
        }
    }
    public void mouseEnterMapTile() {
        if (Objects.equals(lastClickedMapTile.getName(), "grass") && buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]==0 && (Objects.equals(chosenOperation,"melee") || Objects.equals(chosenOperation,"crossbow") || Objects.equals(chosenOperation,"mage") || Objects.equals(chosenOperation,"cannon")))
            shouldRenderPreview = true;

        if (Objects.equals(lastClickedMapTile.getName(), "path") && buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]==0 && (Objects.equals(chosenOperation,"roadNeedles") || Objects.equals(chosenOperation,"stickyRoad")))
            shouldRenderPreview = true;

        if (Objects.equals(chosenOperation,"sell")) {

            if (buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()] == 1) {
                System.out.println(towerManager.getSellWorth(lastClickedMapTile.getX(),lastClickedMapTile.getY()));
            }

            if (buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()] == 2) {
                System.out.println(roadObstaclesManager.getSellWorth(lastClickedMapTile.getX(),lastClickedMapTile.getY()));
            }

        }


    }

    public void showInfoDialog(){
            Texture infoDialogBackground = new Texture(new FileHandle("assets/dialog/settings_dialog.png"));
            infoDialog = new Dialog("", new Window.WindowStyle(font, Color.WHITE, new TextureRegionDrawable(new TextureRegion(infoDialogBackground)))) {
                public void result(Object obj) {
                    System.out.println("result " + obj);
                }
            };
            Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
            infoDialog.text("Brak wystarczającej ilości złota", labelStyle);
            infoDialog.button(bBackInfoDialog).padBottom(16);
            infoDialog.show(pauseStage);
            base.setState(Base.State.Paused);
    }
    public void showEventDialog(){
        Texture eventDialogBackground = new Texture(new FileHandle("assets/dialog/settings_dialog.png"));
        eventDialog = new Dialog("", new Window.WindowStyle(font, Color.WHITE, new TextureRegionDrawable(new TextureRegion(eventDialogBackground)))) {
            public void result(Object obj) {
                System.out.println("result " + obj);
            }
        };
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        Random eventRandom = new Random();
        int eventChance = eventRandom.nextInt(0,3);
        int eventPriority = eventChance;

        if (base.getMultipliers().getFloat("luckMultiplier")  >= eventRandom.nextInt(0,101))
        {
            eventChance = eventRandom.nextInt(0,3);
            if (eventPriority > eventChance)
                eventChance = eventPriority;
        }

        if(eventChance==0) {
            eventDialog.text("Dostałeś na głowę, EO", labelStyle);
            base.damageBase(5);
            eventDialog.button(bBackEventDialog).padBottom(16);
            //eventDialog.show(stage);
            eventDialog.show(pauseStage);
            base.setState(Base.State.Paused);
        }else if(eventChance==1){
            eventDialog.text("Dostałeś golda, EO", labelStyle);
            base.increaseMoney(50);
            eventDialog.button(bBackEventDialog).padBottom(16);
            //eventDialog.show(stage);
            eventDialog.show(pauseStage);
            base.setState(Base.State.Paused);
        }else if(eventChance==2){
            eventDialog.text("Znalazłeś diamonda, EO", labelStyle);
            base.increaseDiamonds(1);
            eventDialog.button(bBackEventDialog).padBottom(16);
            //eventDialog.show(stage);
            eventDialog.show(pauseStage);
            base.setState(Base.State.Paused);
        } else{

        }
    }

    public void mouseExitMapTile() {
        if (shouldRenderPreview)
            shouldRenderPreview = false;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        upgradeManager = new UpgradeManager(languageManager, font, base, fileReader.downloadFileAsJSONObject("assets/upgrades.json"), actualGame.getJSONArray("unlockedUpgrades"), scale);

        Texture bg = new Texture(new FileHandle("assets/profile_banner.png"));

        pauseDialog = new Dialog("", new Window.WindowStyle(font, Color.WHITE, new TextureRegionDrawable(new TextureRegion(bg)))) {
            public void result(Object obj) {
                pauseDialog.cancel();
            }
        };

        gameOverDialog = new Dialog("", new Window.WindowStyle(font, Color.WHITE, new TextureRegionDrawable(new TextureRegion(bg)))) {
            public void result(Object obj) {
                gameOverDialog.cancel();
            }
        };

        bUpgrade.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                base.setState(Base.State.Paused);

                Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
                upgradeDialog = new Dialog("", new Window.WindowStyle(font, Color.WHITE, new TextureRegionDrawable(new TextureRegion(new Texture(new FileHandle("assets/dialog/upgrade_dialog_720.png"))))));
                upgradeDialog.setBounds(0,0,Gdx.graphics.getWidth()/8, Gdx.graphics.getHeight()/8);
                upgradeDialog.setScale(scale);
                upgradeDialog.row();
                upgradeDialog.text(languageManager.getValue(languageManager.getLanguage(), "upgrade_dialog_field_text"), labelStyle);

                upgradeDialog.add(upgradeManager.returnUpgradeTable());
                upgradeDialog.show(pauseStage);
                upgradeDialog.setY(0);
                upgradeDialog.setX(((Gdx.graphics.getWidth())-upgradeDialog.getWidth())/scale/2);
            }


            //////////////////////////// imo nie potrzebne ///////////////////////////

           /* public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                //base.setInfoToDisplay(4);
            }

            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                //base.setInfoToDisplay(0);
            }*/


    });

        bNextWave.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                enemyManager.addWaveToSpawn(GameFunctions.createRandomEnemyWave(base.getWave(), base.getSeed(), enemies));
                //enemyManager.addWaveToSpawn(GameFunctions.createTestEnemyWave());

                base.increaseWave(1);

            }
        });

        bSaveDialog.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveGame();
            }


        });

        bSaveAndExitDialog.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveGame();
                game.setScreen(new MenuScreen(game));


            }


        });

        bExitDialog.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });
        bGameOverExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("kliklem");
                game.setScreen(new MenuScreen(game));
            }
        });
        bGameOverReplay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.setScreen(new GameScreen(game, ProfileManager.getReplaySave(actualGame,base), isLocal));
            }
        });

        bBackEventDialog.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                eventDialog.hide();
                eventDialog.remove();
                base.setState(Base.State.Resumed);
            }
        });
        bBackInfoDialog.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                infoDialog.hide();
                infoDialog.remove();
                base.setState(Base.State.Resumed);
            }
        });


        table_dialogPause.setBounds(0,0, 256, 460);
        System.out.println("table_Dialog" + table_dialogPause.getWidth() +":"+table_dialogPause.getHeight());
        table_dialogPause.add(bResume).width(table_dialogPause.getWidth()/20*16).padRight(table_dialogPause.getWidth()/10);
        table_dialogPause.row();
        table_dialogPause.add(bSaveDialog).width(table_dialogPause.getWidth()/20*16).padRight(table_dialogPause.getWidth()/10);
        table_dialogPause.row();
        table_dialogPause.add(bSaveAndExitDialog).width(table_dialogPause.getWidth()/20*16).padRight(table_dialogPause.getWidth()/10);
        table_dialogPause.row();
        table_dialogPause.add(bExitDialog).padBottom(table_dialogPause.getHeight()/2-16).width(table_dialogPause.getWidth()/20*16).padRight(table_dialogPause.getWidth()/10);
        table_dialogPause.row();
        pauseDialog.add(table_dialogPause);


        table_dialogGameOver.setBounds(0,0, 256, 460);
        table_dialogGameOver.row().padTop(32);
        table_dialogGameOver.add(GameOverTitle).width(table_dialogGameOver.getWidth()/20*16).padRight(table_dialogGameOver.getWidth()/10);
        table_dialogGameOver.row().padTop(32);
        table_dialogGameOver.add(bGameOverReplay).width(table_dialogGameOver.getWidth()/20*16).padRight(table_dialogGameOver.getWidth()/10);
        table_dialogGameOver.row().padTop(32);
        table_dialogGameOver.add(bGameOverExit).width(table_dialogGameOver.getWidth()/20*16).padRight(table_dialogGameOver.getWidth()/10);
        gameOverDialog.add(table_dialogGameOver).padBottom(180);
        gameOverDialog.show(gameOverStage);
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    pauseDialog.show(pauseStage);
                    base.setState(Base.State.Paused);
                    return true;
                }
                if(keycode == Input.Keys.F5){
                    saveGame();
                    return true;
                }
                return super.keyDown(event, keycode);
            }
        });

        pauseStage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {

                    base.setState(Base.State.Resumed);

                    return true;
                }
                return super.keyDown(event, keycode);
            }
        });

        bPauseMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseDialog.show(pauseStage);
                base.setState(Base.State.Paused);
            }
        });

        bTips.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tipsManager.createTipsDialog();
                Dialog tipsDialog = tipsManager.returnTipsDialog();
                //Dialog tipsDialog = new Dialog("", new Window.WindowStyle(font, Color.WHITE, new TextureRegionDrawable(new TextureRegion(new Texture(new FileHandle("assets/tempBackground.png"))))));
                tipsDialog.setBounds(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                tipsDialog.show(pauseStage);
                base.setState(Base.State.Paused);
            }
        });
        bResume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseDialog.hide();
                pauseDialog.remove();
                base.setState(Base.State.Resumed);
            }

        });
        stage.addActor(table_operations);
        stage.addActor(table_operationsSelected);
        stage.addActor(table_map);
        stage.addActor(table_buildings);
        stage.addActor(table_nextWave);
        //stage.addActor(table_stats);
        stage.addActor(table_info);
        //
        stage.addActor(table_menuPause);
        stage.addActor(table_tips);
        loadTowers();
        loadObstacles();
        towerManager.enableListeners();
        roadObstaclesManager.enableListeners();

    }
    public void saveGame(){
        actualGame.put("buildings",towerManager.getTowers());
        actualGame.put("gold",base.getMoney());
        actualGame.put("maxHealth",base.getMaxHealth());
        actualGame.put("health",base.getHealth());
        actualGame.put("wave",base.getWave());
        actualGame.put("roadObstacles", roadObstaclesManager.getRoadObstacles());

        if (isLocal)
        {
            fileReader.setSave(actualGame);
        }
        else
        {
            JSONObject saveResponse = connectionManager.requestSend(actualGame, "api/uploadSave");
            System.out.println(saveResponse);

        }
    }
    public void updateInfoDisplay(){
        statsTableManager.setInfoToDisplay(base.getInfoToDisplay(), base.getInfoToDisplayObjectNow(),  base.getInfoToDisplayObjectUpgraded(), base.getInfoToDisplayName());
        table_info.remove();
        table_info = statsTableManager.getInfoTable();
        if (base.getState() == Base.State.Running || base.getState() == Base.State.Resumed)
            stage.addActor(table_info);
        else if (base.getState() == Base.State.Paused)
            pauseStage.addActor(table_info);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, 0,0);
        game.batch.end();
        stage.act(delta);
        stage.draw();


        if ((statsTableManager.getInfoToDisplay()!=base.getInfoToDisplay()) || (base.getInfoToDisplay()==1 && chosenOperation!=base.getInfoToDisplayName()) || base.isShouldUpdateInfo())
        {
            if (base.isShouldUpdateInfo())
                base.setShouldUpdateInfo(false);
            updateInfoDisplay();
        }





        //spawnEnemies
        //enemyManager.draw();
        //updateEnemiesPosition
        if (base.getHealth() <= 0) {
            base.setState(Base.State.GameOver);
        }


        switch(base.getState()) {
            case Running:
                roadObstaclesManager.update(delta);
                buildArr = roadObstaclesManager.getArr();
                statsTableManager.update();
                towerManager.update(delta);
                enemyManager.update(delta);
                spritebatch.begin();
                roadObstaclesManager.render(spritebatch);
                towerManager.render(spritebatch, shapeRenderer);
                enemyManager.render(spritebatch, shapeRenderer);
                font.draw(spritebatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, hudCamera.viewportHeight);

                if (shouldRenderPreview)
                    GameFunctions.renderPreviewBuilding(spritebatch, shapeRenderer, lastClickedMapTile, turretLevels, chosenOperation, scale);


                spritebatch.end();

                break;
            case Paused:
                Gdx.input.setInputProcessor(pauseStage);
                table_info.toBack();
                statsTableManager.update();
                pauseStage.act(delta);
                spritebatch.begin();
                roadObstaclesManager.render(spritebatch);
                towerManager.render(spritebatch, shapeRenderer);
                enemyManager.render(spritebatch, shapeRenderer);
                spritebatch.end();
                pauseStage.draw();
                break;
            case Resumed:
                resume();
                spritebatch.begin();
                roadObstaclesManager.render(spritebatch);
                towerManager.render(spritebatch, shapeRenderer);
                enemyManager.render(spritebatch, shapeRenderer);
                spritebatch.end();
                Gdx.input.setInputProcessor(stage);
                base.setState(Base.State.Running);
                break;
            case GameOver:
                Gdx.input.setInputProcessor(gameOverStage);
                statsTableManager.update();

                //gameOverStage.act(delta);
                spritebatch.begin();
                roadObstaclesManager.render(spritebatch);
                towerManager.render(spritebatch, shapeRenderer);
                enemyManager.render(spritebatch, shapeRenderer);
                spritebatch.end();
                gameOverStage.draw();
                break;
        }






    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        if (pauseDialog!=null)
            if (pauseDialog.isVisible()) {
                pauseDialog.hide();
                pauseDialog.remove();
            }
        if (upgradeDialog!=null)
            if (upgradeDialog.isVisible()) {
                upgradeDialog.hide();
                upgradeDialog.remove();
                upgradeDialog.clear();
            }

        if (eventDialog!=null)
            if (eventDialog.isVisible()) {
                eventDialog.hide();
                eventDialog.remove();
            }

        if (infoDialog!=null)
            if (infoDialog.isVisible()) {
                infoDialog.hide();
                infoDialog.remove();
            }

        if (tipsManager.returnTipsDialog()!=null)
            if (tipsManager.returnTipsDialog().isVisible()) {
                tipsManager.returnTipsDialog().hide();
                tipsManager.returnTipsDialog().remove();
            }


        base.setInfoToDisplay(0);
        updateInfoDisplay();

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {

    }
    private void initSettingsUI(){
        worldManager = new WorldManager();
        background = new Texture("tempBackground.png");
        resolutions = new ArrayList<>();
        resolutions = resolutionsClass.getResolutionsArrayList();
        languages = new ArrayList<>();
        languages.add("English");
        languages.add("Polski");
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Silkscreen.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        stage = new Stage();
        pauseStage = new Stage();
        gameOverStage = new Stage();
        parameter.size = 15;
        parameter.color = Color.WHITE;
        parameter.characters = "ąćęłńóśżźabcdefghijklmnopqrstuvwxyzĄĆĘÓŁŃŚŻŹABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        font = new BitmapFont();
        font = generator.generateFont(parameter);

        connectionManager = new ConnectionManager();
        taButtonsSettings = new TextureAtlas("assets/buttons/buttons_settings.pack");
        taButtonsDefault = new TextureAtlas("assets/buttons/buttons_default.pack");
        taDialogBack = new TextureAtlas("assets/dialog/skin_dialog.pack");
        taButtonsPause = new TextureAtlas("assets/buttons/buttons_pause.pack");
        taStatsCover = new TextureAtlas("assets/buttons/statsCover.pack");
        taButtonTips = new TextureAtlas("assets/buttons/buttons_tips.pack");
        images = new Skin(taButtonsSettings);
        images_default = new Skin(taButtonsDefault);
        dialog = new Skin(taDialogBack);
        images_pause = new Skin(taButtonsPause);
        images_stats = new Skin(taStatsCover);
        images_tips = new Skin(taButtonTips);
        images_map = new Skin(new TextureAtlas("assets/icons/map_sprites.pack"));
        images_buildings = new Skin(new TextureAtlas("assets/icons/buildings.pack"));

        table_dialogGameOver = new Table();
        table_dialogPause = new Table();
        table_nextWave = new Table(images_default);
        table_operations = new Table(images_buildings);
        table_buildings = new Table(images_buildings);
        table_operationsSelected = new Table(images_buildings);
        table_enemies = new Table(images_map);
        table_stats = new Table(images_default);
        table_menuPause = new Table(images_default);
        table_info = new Table();
        table_tips = new Table();


        textFieldStyleManager = new TextFieldStyleManager();

        textButtonStyle_bLeft = new TextButton.TextButtonStyle();
        textButtonStyle_bRight = new TextButton.TextButtonStyle();
        textButtonStyle_bBack = new TextButton.TextButtonStyle();
        textButtonStyle_bSave = new TextButton.TextButtonStyle();
        textButtonStyle_bPauseMenu = new TextButton.TextButtonStyle();
        textButtonStyle_bTips = new TextButton.TextButtonStyle();
        textFieldStyle = new TextField.TextFieldStyle();
        statsTextFieldStyle = new TextField.TextFieldStyle();
        leftStatsTextFieldStyle = new TextField.TextFieldStyle();
        rightStatsTextFieldStyle = new TextField.TextFieldStyle();
        sliderStyle = new Slider.SliderStyle();

        backgroundMusic = game.getMusic();
        cleanSound = game.getCleanSound();
        cleanSound.setVolume(fileReader.getVolumeValue()/100);
        cleanSound.setLooping(false);
        sellSound = game.getSellSound();
        sellSound.setVolume(fileReader.getVolumeValue()/100);
        sellSound.setLooping(false);
        buySound = game.getBuySound();
        buySound.setVolume(fileReader.getVolumeValue()/100);
        buySound.setLooping(false);

    }

    public Music getBuySound() {
        return buySound;
    }
}