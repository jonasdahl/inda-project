package com.eggpillow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Egg extends Sprite {
	private boolean started;
	private boolean stopped;
	
	public Egg() {
		super(new Texture("img/game_pillow.png"));
		started = false;
		stopped = false;
	}

	@Override
	public void setX(float x) {
		super.setX(x);
	}

	@Override
	public void setY(float y) {
		super.setY(Gdx.graphics.getHeight() - y);
	}

	@Override
	public void draw(SpriteBatch batch) {
		if (hasStarted())
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
