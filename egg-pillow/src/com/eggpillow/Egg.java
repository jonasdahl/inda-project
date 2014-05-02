package com.eggpillow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Egg extends Sprite {
	private boolean started;
	private boolean stopped;
	private float xSpeed = 2f; // Unit: procent of screen width per update
	private float ySpeed = 0f; // Unit: procent of screen height per update
	private Pillow pillow;

	private final static float STARTING_HEIGHT = 0.57f;
	private final static float CLIFF_END = 0.25f;
	private final static float CLIFF_TILT = .15f;
	private final static float ACCELERATION = 0.1f;
	private final static float EGG_HEIGHT = 0.15f; // In percent of screen height
	private final static float EGG_WIDTH = 0.075f; // In percent of screen width
	
	public Egg(Pillow pillow) {
		super(new Texture("img/game_egg.png"));
		super.setSize(Gdx.graphics.getWidth() * EGG_WIDTH, Gdx.graphics.getHeight() * EGG_HEIGHT);
		this.pillow = pillow;
		started = false;
		stopped = false;
		setY(Gdx.graphics.getHeight() * STARTING_HEIGHT);
	}

	@Override
	public void setX(float x) {
		super.setX(x);
	}

	@Override
	public void setY(float y) {
		super.setY(y);
	}

	@Override
	public void draw(SpriteBatch batch) {
		float widthOfScreen = Gdx.graphics.getWidth();
		if (!hasStarted()) {
			return;
		} else if (hasStopped()) {
			super.draw(batch);
			return;
		}
		
		if (getX() < CLIFF_END * widthOfScreen) {
			setY(getY() + xSpeed * CLIFF_TILT * (CLIFF_END * widthOfScreen - getX()) / (CLIFF_END * widthOfScreen));
			setX(getX() + xSpeed);
		} else {
			ySpeed -= ACCELERATION;
			setY(getY() + ySpeed);
			setX(getX() + xSpeed);
		}
		
		// Bounce on pillow if in range
		if (getY() <= pillow.getY() + pillow.getHeight() 
				&& getX() > pillow.getX() - getWidth()
				&& getX() < pillow.getX() + pillow.getWidth()
				&& ySpeed < 0) {
			ySpeed *= -1;
		}
		
		// Dead or in basket
		if (getY() <= 0) {
			stopped = true;
		}
		
		// Reached right side!
		if (getX() + getWidth() >= widthOfScreen) {
			xSpeed = 0; // But still go down
		}
		
		super.draw(batch);
	}
	
	/**
	 * Let the war begin!
	 */
	public void start() {
		started = true;
	}
	
	/**
	 * Checks if the egg is on the move.
	 * @return true if egg has started
	 */
	public boolean hasStarted() {
		return started;
	}
	
	/**
	 * Checks if the egg has reached the basket.
	 * @return true if egg has reached the basket.
	 */
	public boolean hasStopped() {
		return stopped;
	}
}
