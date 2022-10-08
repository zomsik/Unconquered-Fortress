package com.game.State;

public enum GameState {
    PLAYING,
    MENU;
    private static GameState gameState = MENU;
    private static GameState previousGameState = MENU;
    public static void setGameState(GameState state){
        gameState = state;
    }
    public static GameState getGameState(){
        return gameState;
    }
    public static void setPreviousGameState(GameState state){previousGameState = state;}
    public static GameState getPreviousGameState(){return previousGameState;}
}
