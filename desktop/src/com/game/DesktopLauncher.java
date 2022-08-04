package com.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;


import static com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration.getDisplayMode;


public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setResizable(false);
		config.setTitle("Tower Defense");
		if(getDisplayMode().width > 1920 && getDisplayMode().height > 1080)
		{
			config.setWindowedMode(1920,1080);
		}else{
			config.setFullscreenMode(getDisplayMode());
		}
		new Lwjgl3Application(new Main(), config);
	}
}
