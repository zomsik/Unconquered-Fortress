package com.game.State;

public enum GameState {
    PLAYING,
    MENU,
    SETTINGS,
    CREDITS,
    LOGIN,
    REGISTER;
    public static GameState gameState = MENU;
    public static GameState previousGameState = MENU;
    public static void SetGameState(GameState state){
        gameState = state;
    }
    public static void SetPreviousGameState(GameState state){previousGameState = state;}
}
