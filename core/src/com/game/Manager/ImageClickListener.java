package com.game.Manager;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class ImageClickListener extends ClickListener {
    private int x;
    private int y;
    private String name;

    public ImageClickListener () {

    }

    public ImageClickListener(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;


    }


    public int getClickX() {
        return x;
    }

    public int getClickY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public void setLastClickedTile(){
        LastClickedTile.setX(x);
        LastClickedTile.setY(y);
        LastClickedTile.setName(name);
    }

    public void getInfo(){
        LastClickedTile.setX(x);
        LastClickedTile.setY(x);
        LastClickedTile.setName(name);
        System.out.println(this.x);
        System.out.println(this.y);
        System.out.println(this.name);
    }

}
