package com.game.Entity.RoadObstacle;

import com.game.Screens.GameScreen;

public class RoadNeedles extends RoadObstacle {

    public RoadNeedles(int tileX, int tileY, float scale, GameScreen gameScreen)
    {
        super("roadNeedles", "assets/game/towers/cannonPreview.png", tileX, tileY, scale, gameScreen);

    }

}
