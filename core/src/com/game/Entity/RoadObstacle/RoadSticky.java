package com.game.Entity.RoadObstacle;

import com.game.Screens.GameScreen;

public class RoadSticky extends RoadObstacle {

    public RoadSticky(int tileX, int tileY, float scale, GameScreen gameScreen)
    {
        super("roadSticky", "assets/game/towers/road_sticky.png", tileX, tileY, scale, gameScreen);

    }

}
