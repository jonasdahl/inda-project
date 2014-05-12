package com.eggpillow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.eggpillow.screens.SettingsScreen;

/**
 * Represents important stats which will change under the game.
 * @author Johan & Jonas
 * @version 2014-05-09
 */
public class Stats {
	private int startLives;
	private int lives;
	private float gameSpeed;
	private float timer;
	private float lifeTime;
	
	private boolean funMode = false;
	
	/**
	 * Stats-container 
	 * @param lives 
	 * @param gameSpeed
	 */
	public Stats(int lives, float gameSpeed) {
		this.startLives = lives;
		this.lives = lives;
		this.gameSpeed = gameSpeed;
		timer = 0;
		
		Preferences prefs = Gdx.app.getPreferences(SettingsScreen.PREFERENCE_NAME);
		funMode = prefs.getBoolean(SettingsScreen.PREFERENCE_FUNMODE);
	}
	
	public void update(float delta) {
		timer += delta;
		if (getGameSpeed() != V.GAMESPEED && timer > lifeTime) {
			changeGameSpeed(V.GAMESPEED, -1);
		}
	}
	
	/**
	 * @return true if funmode is on.
	 */
	public boolean funMode() {
		return funMode;
	}

	/**
	 * @return the lives
	 */
	public int startLives() {
		return startLives;
	}
	
	/**
	 * @return the lives
	 */
	public int getLives() {
		return lives;
	}
	
	/**
	 * @return the gamespeed
	 */
	public float getGameSpeed() {
		return gameSpeed;
	}
	
	/**
	 * Set the game speed
	 * @param speed new gamespeed
	 */
	public void changeGameSpeed(float speed, int time) {
		gameSpeed = V.GAMESPEED * speed;
		lifeTime = timer + time;
	}

	/**
	 * @param lives the lives to set
	 */
	public void setLives(int lives) {
		this.lives = lives;
	}

	/**
	 * Sets lives to lives - 1
	 */
	public void deadEgg() {
		lives -= 1;
	}
	
	/**
	 * Adds lives
	 */
	public void addLives(int newLives) {
		lives += newLives;
		if (lives > startLives)
			lives = startLives;
	}
}
