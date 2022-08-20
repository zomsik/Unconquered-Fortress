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


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }


    public void getInfo(){
        System.out.println(this.x);
        System.out.println(this.y);
        System.out.println(this.name);
    }

}
