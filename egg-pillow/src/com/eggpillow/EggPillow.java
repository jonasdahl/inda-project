package com.eggpillow;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.eggpillow.screens.GameScreen;
import com.eggpillow.screens.SplashScreen;

public class EggPillow extends Game {
	public static final String NAME = "Egg Pillow";
	public static final String VERSION = "0.0.1";
	public static final float BG_R = 1.0f, BG_G = 0.7f, BG_B = 0.0f, BG_O = 1.0f;
	
	@Override
	public void create() {	
		Texture.setEnforcePotImages(false);
		Gdx.gl.glClearColor(BG_R, BG_G, BG_B, BG_O);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Starts the splash screen
		// TODO setScreen(new SplashScreen(this));
		setScreen(new GameScreen());
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
}
