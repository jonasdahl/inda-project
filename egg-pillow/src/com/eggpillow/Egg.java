package com.eggpillow;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Egg extends Sprite implements Touchable {
	private boolean started;   // Invariant: true if egg has started moving, false otherwise
	private boolean stopped;   // Invariant: true if egg has reached basket, false otherwise
	private boolean dead;   // Invariant: true if egg has died
	private float xSpeed; // Unit: procent of screen width per update
	private float ySpeed; // Unit: procent of screen height per update
	private ArrayList<Touchable> touchables;

	private final static float STARTING_HEIGHT = 0.5f;
	private final static float CLIFF_END = 0.25f;
	private final static float CLIFF_TILT = .15f;
	private final static float ACCELERATION = 0.1f;
	private final static float X_SPEED = 2f;   // In percent of screen width
	
	/**
	 * Constructor for class Egg.
	 * @param pillow the pillow on which this specific egg can bounce
	 * @param height the height in percent of the screen height of this egg
	 * @param width the height in percent of the screen width of this egg
	 */
	public Egg(Pillow pillow, Cliff cliff, float width, float height) {
		super(new Texture("gameImg/game_egg.png"));
		super.setSize(Gdx.graphics.getWidth() * width, Gdx.graphics.getHeight() * height);
		touchables = new ArrayList<Touchable>();
		touchables.add(pillow);
		touchables.add(cliff);
		
		started = false;
		stopped = false;
		dead = false;
		xSpeed = X_SPEED;
		setY(Gdx.graphics.getHeight() * STARTING_HEIGHT);
		
		// Start outside screen and slide in
		setX(-100);
	}
	
	/**
	 * Updates the position of this egg.
	 */
	public void updatePosition() {
		if (!hasStarted() || hasStopped()) {
			return;
		}
		
		float screenWidth = Gdx.graphics.getWidth();
		//float screenHeight = Gdx.graphics.getHeight();
		
		if (getX() < CLIFF_END * screenWidth) {
			setY(getY() + xSpeed * CLIFF_TILT * (CLIFF_END * screenWidth - getX()) / (CLIFF_END * screenWidth));
			setX(getX() + xSpeed);
		} else {
			ySpeed -= ACCELERATION;
			setY(getY() + ySpeed);
			setX(getX() + xSpeed);
		}
		
		// Bounce on pillow if in range
		for (Touchable t : touchables) {
			if (intersects(t) && ySpeed < 0) {
				ySpeed *= -1;
			}
		}
		
		// Dead or just stopped
		if (getY() <= 0) {
			stopped = true;
			if (getX() + getWidth() < Gdx.graphics.getWidth() * 0.95f) {
				dead = true;
			}
		}
		
		// Reached right side!
		if (getX() + getWidth() >= screenWidth) {
			xSpeed = 0; // But still go down
		}
	}
	
	/**
	 * Draw the egg to the batch if it has started.
	 */
	@Override
	public void draw(SpriteBatch batch) {
		if (hasStarted()) {
			super.draw(batch);
		} 
	}
	
	/**
	 * Draw the egg to the batch if it has started.
	 */
	@Override
	public void draw(SpriteBatch batch, float delta) {
		if (hasStarted()) {
			super.draw(batch, delta);
		}
	}
	
	/**
	 * Update the egg properties.
	 * position
	 * @param delta Time since last update (seconds)
	 */
	public void update(float delta) {
		if (hasStarted() && !hasStopped()) {
			updatePosition();
		}
	}
	
	/**
	 * Let the war begin!
	 */
	public void start() {
		started = true;
	}
	
	/**
	 * Checks if the egg is alive or dead.
	 * @return true if egg is dead
	 */
	public boolean isDead() {
		return dead;
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

	@Override
	public float getTopLimit(float x) {
		// The eggs are ellipses
		float width = getWidth();
		float height = getHeight();
		return (float) (Math.sqrt(1 - (x-width/2)*(x-width/2) / (width*width/4)) * height/2 + height/2);
	}

	@Override
	public float getBottomLimit(float x) {
		return getHeight() - getTopLimit(x);
	}

	@Override
	public float getLeftLimit(float y) {
		// x = sqrt((1 - y2/b2))*a
		return -1.0f * (float) (Math.sqrt(1 - y * y / (getHeight() * getHeight() / 4)) * getWidth() / 2);
	}

	@Override
	public float getRightLimit(float y) {
		return (float) (Math.sqrt(1 - y * y / (getHeight() * getHeight() / 4)) * getWidth() / 2);
	}
	
	/**
	 * Checks if this egg intersects with t.
	 * @param t a touchable, like a cliff or pillow, or another egg.
	 * @return true if the two objects share some pixels (if too many, it returns false)
	 */
	private boolean intersects(Touchable t) {
		// TODO Improve - kollar bara rakt uppifrån mot rakt nerifrån just nu
		int leftBound = (int) Math.max(getX(), t.getX()) + 1;
		int rightBound = (int) Math.min(getX() + getWidth(), t.getX() + t.getWidth()) - 1;
		for (int i = leftBound; i <= rightBound; i++) {
			float diff = (t.getTopLimit(i - t.getX()) + t.getY()) - (getBottomLimit(i - getX()) + getY());
			if (diff > 0 && diff < Gdx.graphics.getHeight() * 0.1f)
				return true;
		}
		return false;
	}
}
