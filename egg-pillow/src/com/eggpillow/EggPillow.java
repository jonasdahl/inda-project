package com.eggpillow;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.eggpillow.screens.SplashScreen;

/*
 *   Problem: 
 *   TODO (Prio low-medium) Add predictions to intersects (use speed)
 *   TODO (Prio low) Bitmap fonts don't typical scale well, especially at small sizes. It is suggested to use a separate bitmap font for each font size.
 *   
 *   TODO Make sure everything is disposed.
 */

/**
 * The main class that initiates and starts the game.
 * 
 * @author Johan & Jonas
 * @version 2014-05-15
 */
public class EggPillow extends Game {
	FPSLogger fpslog;
	private boolean debugMode = false;
	private Music backgroundSound;
	public Preferences prefs; // TODO private

	/**
	 * Sets background and starts splash screen.
	 */
	@Override
	public void create() {
		prefs = Gdx.app.getPreferences(V.PREFERENCE_NAME);
		Texture.setEnforcePotImages(false);
		setBackground();
		V.initV(); // Initialize V's values

		// Start splash screen
		setScreen(new SplashScreen(this));

		Gdx.input.setCatchBackKey(true);

		if (debugMode) {
			fpslog = new FPSLogger();
		}

		backgroundSound = Gdx.audio.newMusic(Gdx.files.internal(V.BACKGROUND_SOUND));
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

	public void playBackgroundMusic() {
		backgroundSound.setLooping(true);
		playAudio(backgroundSound);
	}

	public void stopBackgroundMusic() {
		stopAudio(backgroundSound);
	}

	public void playAudio(Music m) {
		if (m == null || prefs.getBoolean(V.PREFERENCE_MUTED, true)) {
			return;
		}
		m.play();
	}

	public void playAudio(Sound m) {
		if (m == null || prefs.getBoolean(V.PREFERENCE_MUTED, true)) {
			return;
		}
		m.play();
	}

	public void stopAudio(Music m) {
		m.stop();
	}

	public void stopAudio(Sound m) {
		m.stop();
	}
}
