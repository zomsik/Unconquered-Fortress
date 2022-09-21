package com.game.Entity.RoadObstacle;

import com.game.Entity.Base;
import com.game.Screens.GameScreen;

public class RoadNeedles extends RoadObstacle {

    public RoadNeedles(Base base, int tileX, int tileY, float scale, GameScreen gameScreen)
    {
        super("roadNeedles", base,"assets/game/towers/road_needles.png", tileX, tileY, scale, gameScreen);

    }

}
