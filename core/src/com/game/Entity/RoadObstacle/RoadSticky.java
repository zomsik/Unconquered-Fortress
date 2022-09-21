package com.game.Entity.RoadObstacle;

import com.game.Entity.Base;
import com.game.Screens.GameScreen;

public class RoadSticky extends RoadObstacle {

    public RoadSticky(Base base, int tileX, int tileY, float scale, GameScreen gameScreen)
    {
        super("roadSticky", base,"assets/game/towers/road_sticky.png", tileX, tileY, scale, gameScreen);

    }

}
