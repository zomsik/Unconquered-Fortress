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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.game.Entity.Enemy.Enemy;
import com.game.Entity.Base;
import com.game.Entity.Tower.*;
import com.game.Main;
import com.game.Manager.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class GameScreen implements Screen {
    private Main game;
    private Stage stage, pauseStage, gameOverStage;
    private Texture background;
    private BitmapFont font;
    private TextureAtlas taButtonsSettings, taButtonsDefault, taDialogBack, taButtonsPause, taStatsCover;
    private Skin images, images_default, dialog, images_map, images_buildings, images_pause, images_stats;
    private TextButton  bSaveDialog, bExitDialog, bTest, bNextWave, bResume, bPauseMenu;

    private Table table_map, table_dialogPause, table_nextWave, table_operations, table_operationsSelected , table_buildings, table_dialogGameOver, table_enemies, table_stats, table_menuPause;
    private TextField hpTextField,hpTextValue, goldTextField, goldTextValue, GameOverTitle;
    private TextField.TextFieldStyle statsTextFieldStyle, rightStatsTextFieldStyle, leftStatsTextFieldStyle;
    private ArrayList<String> resolutions;
    private ArrayList<String> languages;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private TextButton.TextButtonStyle textButtonStyle_bLeft, textButtonStyle_bRight, textButtonStyle_bBack, textButtonStyle_bSave, textButtonStyle_bPauseMenu;
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
    private JSONObject actualGame;
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

    public LastClickedTile lastClickedMapTile, lastClickedOperationTile;

    private Base base;

    OrthographicCamera hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());



    public enum State{
        Running, Paused, Resumed, GameOver
    }

    private State state = State.Running;


    public GameScreen (Main game, JSONObject save, boolean isLocal){
        this.game = game;
        this.isLocal = isLocal;

        this.buildArr = new int[15][10];
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




        table_map = worldManager.drawWorld(mapArr, scale);

        hudCamera.position.set(hudCamera.viewportWidth / 2.0f, hudCamera.viewportHeight / 2.0f, 1.0f);
        // add inicjalization like position of the base and the enemy tile x,y
        //width of the tile as well
        //enemyManager = new EnemyManager(worldManager.getEnemySpawnerPosition()[1],worldManager.getEnemySpawnerPosition()[0], new Vector2(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()), scale, worldManager.getPath());

        base = new Base();

        textFieldStyleManager.setTextFieldStyle(statsTextFieldStyle, images, font, "textBar", Color.WHITE);
        textFieldStyleManager.setTextFieldStyle(rightStatsTextFieldStyle, images_stats, font, "rightStatsCover", Color.WHITE);
        textFieldStyleManager.setTextFieldStyle(leftStatsTextFieldStyle, images_stats, font, "leftStatsCover", Color.WHITE);

        GameOverTitle = new TextField("Lose", textFieldStyleManager.returnTextFieldStyle(statsTextFieldStyle));
        GameOverTitle.setAlignment(Align.center);

        hpTextField = new TextField("Hp: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        hpTextValue = new TextField(String.valueOf(base.getHealth()), textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        hpTextField.setAlignment(Align.center);
        hpTextValue.setAlignment(Align.center);

        goldTextField = new TextField("Money: ", textFieldStyleManager.returnTextFieldStyle(leftStatsTextFieldStyle));
        goldTextValue = new TextField(String.valueOf(base.getMoney()), textFieldStyleManager.returnTextFieldStyle(rightStatsTextFieldStyle));
        goldTextField.setAlignment(Align.center);
        goldTextValue.setAlignment(Align.center);

        Texture table_statsBackgound = new Texture(new FileHandle("assets/statsBackground.png"));

        table_stats.setBounds(Gdx.graphics.getWidth()-224*scale,(Gdx.graphics.getHeight()-Gdx.graphics.getWidth()/30*16)/2+48*scale+32*scale+350*scale,224*scale,204*scale);
        table_stats.setBackground(new TextureRegionDrawable(new TextureRegion(table_statsBackgound)));
        table_stats.add(hpTextField).width((200*scale)/2-6*scale);
        table_stats.add(new Image(images_stats, "middleStatsCover")).width(12*scale);
        table_stats.add(hpTextValue).width((200*scale)/2-6*scale).padRight(2*scale);
        table_stats.row();
        table_stats.add(goldTextField).width((200*scale)/2-6*scale);
        table_stats.add(new Image(images_stats, "middleStatsCover")).width(12*scale);
        table_stats.add(goldTextValue).width((200*scale)/2-6*scale).padRight(2*scale);

        if(Gdx.graphics.getHeight() == 900){
            table_menuPause.setBounds(Gdx.graphics.getWidth()/20*18,(Gdx.graphics.getHeight()/40*38-2), Gdx.graphics.getWidth()/50*3,Gdx.graphics.getHeight()/40*3);
        }else{
            table_menuPause.setBounds(Gdx.graphics.getWidth()/20*18,(Gdx.graphics.getHeight()/40*37), Gdx.graphics.getWidth()/50*3,Gdx.graphics.getHeight()/40*3);
        }

        table_menuPause.debug();
        enemyManager = new EnemyManager(base, scale, GameFunctions.calulatePath(worldManager.getPath(), scale));
        towerManager = new TowerManager(enemyManager.getEnemies());


        buttonStyleManager = new ButtonStyleManager();
        textFieldStyleManager = new TextFieldStyleManager();


        buttonStyleManager.setTextButtonStyle(textButtonStyle_bBack, images_default, font, "defaultButton", "defaultButton");
        bSaveDialog  = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bSave"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bBack));
        bExitDialog = new TextButton(languageManager.getValue(languageManager.getLanguage(), "bExit"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bBack));
        bTest = new TextButton("test", buttonStyleManager.returnTextButtonStyle(textButtonStyle_bBack));
        bNextWave = new TextButton(languageManager.getValue(languageManager.getLanguage(),"bNextWave"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bBack));
        bResume = new TextButton(languageManager.getValue(languageManager.getLanguage(),"bResume"), buttonStyleManager.returnTextButtonStyle(textButtonStyle_bBack));

        buttonStyleManager.setTextButtonStyle(textButtonStyle_bPauseMenu, images_pause, font, "ButtonPauseUp", "ButtonPauseDown");
        bPauseMenu = new TextButton("", buttonStyleManager.returnTextButtonStyle(textButtonStyle_bPauseMenu));

        operationsArr = GameFunctions.getOperationsArr(this);
        table_operations = GameFunctions.getOperationsTable(operationsArr, scale);
        Texture bg = new Texture(new FileHandle("assets/shopBackground.png"));
        table_operations.setBackground(new TextureRegionDrawable(new TextureRegion(bg)));
        operationsSelectedArr = GameFunctions.getOperationsSelectedArr();
        table_operationsSelected = GameFunctions.getOperationsTable(operationsSelectedArr, scale);

        buildingsArr = GameFunctions.getEmptyBuildingsArr();
        table_buildings = GameFunctions.getBuildingsTable(buildingsArr, scale);

        buildingsArr = GameFunctions.loadPlacedBuildings(this, buildingsArr, actualGame.getJSONArray("buildings"));
        table_nextWave.add(bNextWave).width(224*scale).padBottom(8*scale);
        //table_nextWave.row();
        //table_nextWave.add(bTest).width(Gdx.graphics.getWidth()/10);
        table_nextWave.setBounds(Gdx.graphics.getWidth()-224*scale,(Gdx.graphics.getHeight()-Gdx.graphics.getWidth()/30*16)/2,224*scale,48*scale);
        table_nextWave.debug();

        table_menuPause.add(bPauseMenu).height(Gdx.graphics.getHeight()/40*3).width(Gdx.graphics.getWidth()/50*3);
    }

    public void mouseClickOperation() {
        if (Objects.equals(chosenOperation,lastClickedOperationTile.getName()))
        {
            operationsSelectedArr[chosenOperationY][chosenOperationX].setDrawable(images_buildings, "empty");
            chosenOperation = null;
            chosenOperationX = -1;
            chosenOperationY = -1;
        }
        else {
            if (chosenOperation!=null)
                operationsSelectedArr[chosenOperationY][chosenOperationX].setDrawable(images_buildings, "empty");
            chosenOperation = lastClickedOperationTile.getName();
            chosenOperationX = lastClickedOperationTile.getX();
            chosenOperationY = lastClickedOperationTile.getY();
            operationsSelectedArr[chosenOperationY][chosenOperationX].setDrawable(images_buildings, "chosen");

        }
    }
    public void mouseEnterOperation() {
        System.out.println("Najechano: "+lastClickedOperationTile.getName());
    }
    public void mouseExitOperation() {
        //System.out.println("x: "+lastClickedMapTile.getX()+", y: "+ lastClickedMapTile.getY());
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

        if (Objects.equals(chosenOperation,"sell")) {
            if (buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()] == 1) {
                towerManager.sellTower(lastClickedMapTile.getX(),lastClickedMapTile.getY());
                buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()] = 0;
            }
        }

        if (Objects.equals(chosenOperation,"sword")) {
            if (Objects.equals(lastClickedMapTile.getName(), "grass") && buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]==0) {

                //warunki wybudowania swordTowera
                Tower t = new MeleeTower(lastClickedMapTile.getX(),lastClickedMapTile.getY(),scale);
                towerManager.buyTower(t);
                stage.addActor(t);
                buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]=1;


                /*
                JSONArray placedBuildings = actualGame.getJSONArray("buildings");
                placedBuildings.put(new JSONObject().put("buildingName","sword").put("x",lastClickedMapTile.getX()).put("y",lastClickedMapTile.getY()).put("level",1));
                actualGame.put("buildings", placedBuildings);


                buildingsArr = GameFunctions.addBuilding(this, buildingsArr, lastClickedMapTile.getX(), lastClickedMapTile.getY(), "sword");
                table_buildings = GameFunctions.getBuildingsTable(buildingsArr, scale);
                stage.addActor(table_buildings);*/


            }
        }


        if (Objects.equals(chosenOperation,"bow")) {
            if (Objects.equals(lastClickedMapTile.getName(), "grass") && buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]==0) {

                //warunki wybudowania bowTowera

                Tower t = new BowTower(lastClickedMapTile.getX(),lastClickedMapTile.getY(),scale);
                towerManager.buyTower(t);
                stage.addActor(t);
                buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]=1;


                //mapArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()].setTouchable(Touchable.);

                //JSONArray placedBuildings = actualGame.getJSONArray("buildings");
                //placedBuildings.put(new JSONObject().put("buildingName","bow").put("x",lastClickedMapTile.getX()).put("y",lastClickedMapTile.getY()).put("level",1));
                //actualGame.put("buildings", placedBuildings);

                //buildingsArr = GameFunctions.addBuilding(this, buildingsArr, lastClickedMapTile.getX(), lastClickedMapTile.getY(), "bow");
                //table_buildings = GameFunctions.getBuildingsTable(buildingsArr, scale);
                //stage.addActor(table_buildings);


            }
        }

        if (Objects.equals(chosenOperation,"mage")) {
            if (Objects.equals(lastClickedMapTile.getName(), "grass") && buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]==0) {

                Tower t = new MageTower(lastClickedMapTile.getX(),lastClickedMapTile.getY(),scale);
                towerManager.buyTower(t);
                stage.addActor(t);
                //towerManager.buyTower(new MageTower(lastClickedMapTile.getX(),lastClickedMapTile.getY(),scale));
                buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]=1;
                //warunki wybudowania mageTowera
                /*
                JSONArray placedBuildings = actualGame.getJSONArray("buildings");
                placedBuildings.put(new JSONObject().put("buildingName","mage").put("x",lastClickedMapTile.getX()).put("y",lastClickedMapTile.getY()).put("level",1));
                actualGame.put("buildings", placedBuildings);

                buildingsArr = GameFunctions.addBuilding(this, buildingsArr, lastClickedMapTile.getX(), lastClickedMapTile.getY(), "mage");
                table_buildings = GameFunctions.getBuildingsTable(buildingsArr, scale);
                stage.addActor(table_buildings);*/


            }
        }


        if (Objects.equals(chosenOperation,"cannon")) {
            if (Objects.equals(lastClickedMapTile.getName(), "grass") && buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]==0) {


                Tower t = new CannonTower(lastClickedMapTile.getX(),lastClickedMapTile.getY(),scale);
                towerManager.buyTower(t);
                stage.addActor(t);
                buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]=1;
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

                //jeśli masz kasę itd warunki, może dialog etc

                JSONArray terr = actualGame.getJSONArray("terrainModifications");

                terr.put(new JSONObject().put("tileName","grass").put("x",lastClickedMapTile.getX()).put("y",lastClickedMapTile.getY()));
                actualGame.put("terrainModifications", terr);

                table_map = worldManager.changeTileAndRedrawWorld(this, mapArr, lastClickedMapTile.getX(), lastClickedMapTile.getY(), "grass", scale);
                stage.addActor(table_map);
                //table_buildings.toFront();*/


            }
        }
    }
    public void mouseEnterMapTile() {
        if (Objects.equals(lastClickedMapTile.getName(), "grass") && buildArr[lastClickedMapTile.getX()][lastClickedMapTile.getY()]==0 && chosenOperation!=null)
            shouldRenderPreview = true;


    }

    public void mouseExitMapTile() {
        if (shouldRenderPreview)
            shouldRenderPreview = false;
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

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


        bNextWave.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                System.out.println();

                //enemyManager.addWaveToSpawn(GameFunctions.createRandomEnemyWave(actualGame));

                enemyManager.addWaveToSpawn(GameFunctions.createTestEnemyWave(actualGame, scale));


                //actualGame.put("wave",actualGame.getInt("wave")+1);
                //System.out.println("");
                // enemyArr = createNewWaveArray(seed,wave,difficulty)
                //lub
                // enemyArr = createNewWaveArray(enemyArr, seed,wave,difficulty) -- append
                //System.out.println(worldManager.getEnemySpawnerPosition()[0]);
                //System.out.println(worldManager.getEnemySpawnerPosition()[1]);
                //Flying f1 = new Flying();

                //Enemy e1 = new Enemy();
                //Enemy e2 = new Enemy();
                //ee.add(e1);
                //ee.add(new Flying(enemyManager.getEnemySpawnerPosition(), scale));
                //ee.add(new Flying());
                //ee.add(new Flying());
                //ee.add(new Flying());
                //ee.add(new Flying());
                //ee.add(new Flying());
                //ee.add(f1);
                //enemyManager.addWaveToSpawn(ee);

                //table_enemies.add(e1.animation);
                //stage.addActor(table_enemies);

            }
        });




        //buck
        bTest.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                //mapArr[4,4] = new Image(images_map, "mountain");
                //table_map = worldManager.changeTileAndRedrawWorld(mapArr, 0, 5, "water");
                //stage.addActor(table_map);

                System.out.println(lastClickedMapTile.getX()+"y: "+ lastClickedMapTile.getY());

                //set pamieci na luk
                buildingsArr[0][0].setRotation(45);

                base.damageBase(7);


            }
        });




        //naciska się tile

        



        bSaveDialog.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("zapisano");

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
        });

        bExitDialog.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });
        table_dialogPause.setBounds(0,0, 256, 460);
        System.out.println("table_Dialog" + table_dialogPause.getWidth() +":"+table_dialogPause.getHeight());
        table_dialogPause.add(bResume).width(table_dialogPause.getWidth()/20*16).padRight(table_dialogPause.getWidth()/10);
        table_dialogPause.row();
        table_dialogPause.add(bSaveDialog).width(table_dialogPause.getWidth()/20*16).padRight(table_dialogPause.getWidth()/10);
        table_dialogPause.row();
        table_dialogPause.add(bExitDialog).padBottom(table_dialogPause.getHeight()/2).width(table_dialogPause.getWidth()/20*16).padRight(table_dialogPause.getWidth()/10);
        table_dialogPause.row();
        pauseDialog.add(table_dialogPause);



        table_dialogGameOver.add(GameOverTitle);
        gameOverDialog.add(table_dialogGameOver);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    pauseDialog.show(pauseStage);
                    state = State.Paused;
                    return true;
                }
                return super.keyDown(event, keycode);
            }
        });

        pauseStage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    pauseDialog.hide();
                    state = State.Resumed;

                    return true;
                }
                return super.keyDown(event, keycode);
            }
        });

        bPauseMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseDialog.show(pauseStage);
                state = State.Paused;
            }
        });
        bResume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseDialog.hide();
                state = State.Resumed;
            }
        });
        stage.addActor(table_operations);
        stage.addActor(table_operationsSelected);
        stage.addActor(table_map);
        stage.addActor(table_buildings);
        stage.addActor(table_nextWave);
        stage.addActor(table_stats);
        //
        stage.addActor(table_menuPause);


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





        hpTextValue.setText(String.valueOf(base.getHealth()));
        goldTextValue.setText(String.valueOf(base.getMoney()));
                //spawnEnemies
                //enemyManager.draw();
                //updateEnemiesPosition
                if (base.getHealth() <= 0) {
                    //przegrana
                    //stop game

                    state = State.GameOver;
                }


        switch(state) {
            case Running:
                towerManager.update(delta);
                enemyManager.update(delta);
                spritebatch.begin();
                towerManager.render(spritebatch, shapeRenderer);
                enemyManager.render(spritebatch);
                font.draw(spritebatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, hudCamera.viewportHeight);

                if (shouldRenderPreview)
                    GameFunctions.renderPreviewBuilding(spritebatch, shapeRenderer, lastClickedMapTile, chosenOperation, scale);

                spritebatch.end();

                break;
            case Paused:
                spritebatch.begin();
                towerManager.render(spritebatch, shapeRenderer);
                enemyManager.render(spritebatch);
                spritebatch.end();
                pauseStage.draw();
                Gdx.input.setInputProcessor(pauseStage);
                break;
            case Resumed:
                spritebatch.begin();
                towerManager.render(spritebatch, shapeRenderer);
                enemyManager.render(spritebatch);
                spritebatch.end();

                Gdx.input.setInputProcessor(stage);
                state = State.Running;
                break;
            case GameOver:
                gameOverDialog.show(gameOverStage);
                spritebatch.begin();
                towerManager.render(spritebatch, shapeRenderer);
                enemyManager.render(spritebatch);
                spritebatch.end();
                gameOverStage.draw();
                Gdx.input.setInputProcessor(gameOverStage);
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

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {

    }
    private void initSettingsUI(){
        background = new Texture("background.png");
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
        images = new Skin(taButtonsSettings);
        images_default = new Skin(taButtonsDefault);
        dialog = new Skin(taDialogBack);
        images_pause = new Skin(taButtonsPause);
        images_stats = new Skin(taStatsCover);

        images_map = new Skin(new TextureAtlas("assets/icons/map_sprites.pack"));
        images_buildings = new Skin(new TextureAtlas("assets/icons/buildings.pack"));

        table_dialogGameOver = new Table(images_default);
        table_dialogPause = new Table();
        table_nextWave = new Table(images_default);
        table_operations = new Table(images_buildings);
        table_buildings = new Table(images_buildings);
        table_operationsSelected = new Table(images_buildings);
        table_enemies = new Table(images_map);
        table_stats = new Table(images_default);
        table_menuPause = new Table(images_default);

        textFieldStyleManager = new TextFieldStyleManager();

        textButtonStyle_bLeft = new TextButton.TextButtonStyle();
        textButtonStyle_bRight = new TextButton.TextButtonStyle();
        textButtonStyle_bBack = new TextButton.TextButtonStyle();
        textButtonStyle_bSave = new TextButton.TextButtonStyle();
        textButtonStyle_bPauseMenu = new TextButton.TextButtonStyle();
        textFieldStyle = new TextField.TextFieldStyle();
        statsTextFieldStyle = new TextField.TextFieldStyle();
        leftStatsTextFieldStyle = new TextField.TextFieldStyle();
        rightStatsTextFieldStyle = new TextField.TextFieldStyle();
        sliderStyle = new Slider.SliderStyle();

        backgroundMusic = game.getMusic();

    }
}