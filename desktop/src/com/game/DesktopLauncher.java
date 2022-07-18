package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Window;
import org.lwjgl.system.windows.RECT;

import java.awt.*;

import static com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration.getDisplayMode;
import static org.lwjgl.system.windows.User32.GetSystemMetrics;
import static org.lwjgl.system.windows.User32.SM_CYSCREEN;

public class DesktopLauncher {
	public static void main (String[] arg) {
		//Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
		//Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		//int taskBarHeight = scrnSize.height - winSize.height;

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setResizable(false);
		config.setTitle("TowerDefense");
		config.setFullscreenMode(getDisplayMode());
		config.setWindowedMode(1920,1080);
		new Lwjgl3Application(new Main(), config);
	}
}
