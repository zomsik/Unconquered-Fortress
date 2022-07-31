package com.game.State;

public enum GameState {
    PLAYING,
    MENU,
    SETTINGS,
    LOGIN,
    REGISTER;
    public static GameState gameState = MENU;
    public static void SetGameState(GameState state){
        gameState = state;
    }
}
