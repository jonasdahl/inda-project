package com.eggpillow;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.eggpillow.screens.GameScreen;
import com.eggpillow.screens.MenuScreen;
import com.eggpillow.screens.SettingsScreen;
import com.eggpillow.screens.SplashScreen;

/*
 *   Problem: 
 *   Pillows position ändras av inputhandler mellan Pillow.update och pillow.draw
 *   Collisiondetection is fuckedup borde göras om
 *   Konstanter i olika klasser eller alla i V?
 *   
 *   DRAW: lifeindicator
 *   		fix crashedEgg to png
 *   		settingsbackground
 */

public class EggPillow extends Game {
	public static final float BG_R = 1.0f, BG_G = 0.7f, BG_B = 0.0f, BG_O = 1.0f;

	// Screens
	public Screen splashScreen;
	public GameScreen gameScreen;
	public Screen settingsScreen;
	public Screen menuScreen;

	@Override
	public void create() {
		Texture.setEnforcePotImages(false);
		Gdx.gl.glClearColor(BG_R, BG_G, BG_B, BG_O);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		V.initV();
		
		// Starts the splash screen
		splashScreen = new SplashScreen(this);
		gameScreen = new GameScreen(this);
		settingsScreen = new SettingsScreen(this);
		menuScreen = new MenuScreen(this);
		setScreen(new SplashScreen(this));

		Gdx.input.setCatchBackKey(true);

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

	/**
	 * Sets the background to standard.
	 */
	public static void setBackground() { // TODO Behöver den vara static? alla screens har game
		Gdx.gl.glClearColor(BG_R, BG_G, BG_B, BG_O);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}
