package com.game.Manager;

public class LastClickedTile {
    private static int x = -1;
    private static int y = -1;
    private static String name = null;

    public static int getX() {
        return x;
    }

    public static void setX(int x) {
        LastClickedTile.x = x;
    }

    public static int getY() {
        return y;
    }

    public static void setY(int y) {
        LastClickedTile.y = y;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        LastClickedTile.name = name;
    }
}
