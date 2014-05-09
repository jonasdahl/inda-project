package com.eggpillow;

public class Stats {
	private int startLives;
	private int lives;
	private float gameSpeed;
	
	public Stats(int lives, float gameSpeed) {
		this.startLives = lives;
		this.lives = lives;
		this.gameSpeed = gameSpeed;
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
	public void setGameSpeed(float speed) {
		gameSpeed = speed;
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
