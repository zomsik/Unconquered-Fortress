package com.game.Manager;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.game.Screens.GameScreen;

public class GameFunctions {



    public static Image[][] getOperationsArr(GameScreen gameScreen){
        Image[][] arr = new Image[4][2];
        Skin images_buildings = new Skin(new TextureAtlas("assets/icons/buildings.pack"));


        arr[0][0] = new Image(images_buildings, "sword");
        arr[0][1] = new Image(images_buildings, "bow");
        arr[1][0] = new Image(images_buildings, "mage");
        arr[1][1] = new Image(images_buildings, "cannon");
        arr[2][0] = new Image(images_buildings, "clean");
        arr[2][1] = new Image(images_buildings, "fill");
        arr[3][0] = new Image(images_buildings, "stickyRoad");
        arr[3][1] = new Image(images_buildings, "roadNeedles");

        arr[0][0].setName("sword");
        arr[0][1].setName("bow");
        arr[1][0].setName("mage");
        arr[1][1].setName("cannon");
        arr[2][0].setName("clean");
        arr[2][1].setName("fill");
        arr[3][0].setName("stickyRoad");
        arr[3][1].setName("roadNeedles");

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

    public static Table getOperationsTable(Image[][] arr)
    {
        Table t = new Table();
        t.setBounds(100, 400, 64*2, 64*4);

        for (int i = 0; i<4; i++)
        {
            for (int j = 0; j<2; j++)
            {
                t.add(arr[i][j]);
            }

            t.row();

        }

        return t;
    }




}

