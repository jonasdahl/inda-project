package com.eggpillow;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.eggpillow.screens.SplashScreen;

/*
 *   Problem: 
 *   TODO Konstanter i olika klasser eller alla i V?
 *   TODO (Prio low-medium) Add predictions to intersects (use speed)
 *   TODO (Prio low) Bitmap fonts don't typical scale well, especially at small sizes. It is suggested to use a separate bitmap font for each font size.
 *   
 *   // TODO (Prio high) Make sure everything is disposed.
 *   
 *   TODO DRAW: lifeindicator
 *   TODO DRAW:	settingsbackground
 */

/**
 * The main class that initiates and starts the game.
 * @author Johan & Jonas
 * @version 2014-05-10
 */
public class EggPillow extends Game {
	
	FPSLogger fpslog;
	private boolean debugMode = false;
 	
	/**
	 * Sets background and starts splash screen.
	 */
	@Override
	public void create() {
		Texture.setEnforcePotImages(false); // TODO Keep everywhere or just in one place?
		setBackground();
		V.initV(); // Initialize V's values
		
		// Start splash screen
		setScreen(new SplashScreen(this));

		Gdx.input.setCatchBackKey(true);
		
		if (debugMode) {
			fpslog = new FPSLogger();			
		}
	}

	/**
	 * Sets the background to standard.
	 */
	public static void setBackground() {
		Gdx.gl.glClearColor(V.BG_R, V.BG_G, V.BG_B, V.BG_O);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	/**
	 * Dispose current screen and set the screen to newScreen
	 */
	@Override
	public void setScreen(Screen newScreen) {
		// TODO add getScreen().dispose();
		super.setScreen(newScreen);
	}

	public void exit() {
		// TODO make sure everything is okay and save files.
		Gdx.app.exit();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
		if (debugMode) {
			fpslog.log();			
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
