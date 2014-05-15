package com.eggpillow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.eggpillow.screens.SettingsScreen;
import com.eggpillow.sprites.LifeIndicator;
import com.eggpillow.sprites.ScoreBoard;

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
	private int score;
	
	private boolean funMode = false;
	
	private LifeIndicator lifeindicator;
	private ScoreBoard scoreBoard;
	
	/**
	 * Stats-container 
	 * @param lives 
	 * @param gameSpeed
	 */
	public Stats(TextureAtlas atlas, int lives, float gameSpeed) {
		this.startLives = lives;
		this.lives = lives;
		this.gameSpeed = gameSpeed;
		timer = 0;
		
		lifeindicator = new LifeIndicator(atlas, lives);
		scoreBoard = new ScoreBoard(atlas);
		
		Preferences prefs = Gdx.app.getPreferences(V.PREFERENCE_NAME);
		funMode = prefs.getBoolean(V.PREFERENCE_FUNMODE);
	}
	
	public void update(float delta) {
		timer += delta;
		if (getGameSpeed() != V.GAMESPEED && timer > lifeTime) {
			changeGameSpeed(V.GAMESPEED, -1);
		}
	}
	
	public void draw(SpriteBatch batch) {
		lifeindicator.draw(batch);
		scoreBoard.draw(batch);
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
	 * @return the game speed
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
	 * Sets lives to lives - 1
	 */
	public void removeLives() {
		lives -= 1;
		lifeindicator.decreaseLives(lives);
	}
	
	/**
	 * Adds lives
	 * 
	 * @param newLives Amount of lives to add.
	 */
	public void addLives(int newLives) {
		lives += newLives;
		if (lives > startLives) {
			lives = startLives;			
		}
		lifeindicator.increaseLives(lives);
	}
	
	public void addScore() {
		score++;
		scoreBoard.increaseScore();
	}
	
	public int getScore() {
		return score;
	}
}
