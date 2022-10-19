package com.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import static com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration.getDisplayMode;

import com.game.Server.SavesRepository;
import com.game.Server.User;
import com.game.Server.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

//https://kottke.org/plus/type/silkscreen/index.html
//Link to font licence
//Music: The Sleeping Prophet by Jesse Gallagher - YouTube licence

@SpringBootApplication
public class DesktopLauncher implements CommandLineRunner {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private SavesRepository savesRepository;


	public static void main(String[] args) {

		SpringApplication.run(DesktopLauncher.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setResizable(false);
		config.setTitle("Unconquered Fortress");
		if(getDisplayMode().width > 1920 && getDisplayMode().height > 1080)
		{
			config.setWindowedMode(1920,1080);
			config.setDecorated(true);
			config.setMaximized(true);
		}else{
			config.setFullscreenMode(getDisplayMode());
		}
		config.setWindowIcon("assets/icons/gameIcon.png");

		new Lwjgl3Application(new Main(usersRepository, savesRepository), config);

	}
}
