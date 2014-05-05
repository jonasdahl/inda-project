package com.eggpillow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.eggpillow.screens.GameScreen;

public class Egg extends Touchable {
	private boolean started; // Invariant: true if egg has started moving, false
								// otherwise
	private boolean stopped; // Invariant: true if egg has reached basket, false
								// otherwise
	private boolean dead; // Invariant: true if egg has died
	private float acceleration;
	private GameScreen game;

	private final static float STARTING_HEIGHT = 0.5f; // In percent of screen
														// height
	private final static float CLIFF_END = 0.25f;
	private final static float CLIFF_TILT = .15f;
	private final static float ACCELERATION = 0.01f; // TODO balance speeds
	private final static float X_SPEED = 0.2f; // In percent of screen width
	private final static String ATLAS_REGION = "game_egg";

	/**
	 * Constructor for class Egg.
	 * 
	 * @param pillow
	 *            the pillow on which this specific egg can bounce
	 * @param height
	 *            the height in percent of the screen height of this egg
	 * @param width
	 *            the height in percent of the screen width of this egg
	 */
	public Egg(GameScreen game, float width, float height, TextureAtlas atlas) {
		super(atlas.findRegion(ATLAS_REGION));
		setSize(Gdx.graphics.getWidth() * width, Gdx.graphics.getHeight() * height);

		// TODO har alla �gg en arrayList med pillow/cliff och ett texture?

		this.game = game;
		started = false;
		stopped = false;
		dead = false;
		xSpeed = X_SPEED * Gdx.graphics.getWidth();
		acceleration = ACCELERATION * Gdx.graphics.getHeight();
		setY(Gdx.graphics.getHeight() * STARTING_HEIGHT);

		// Start outside screen and slide in
		setX(-getWidth());
	}

	/**
	 * Updates the position of this egg.
	 */
	public void updatePosition(float delta) {
		if (!hasStarted() || hasStopped()) {
			return;
		}

		float screenWidth = Gdx.graphics.getWidth();

		if (getX() < CLIFF_END * screenWidth) {
			setY(getY() + xSpeed * delta /* TODD xSpeed? */* CLIFF_TILT * (CLIFF_END * screenWidth - getX())
					/ (CLIFF_END * screenWidth));
			setX(getX() + xSpeed * delta);
		} else {
			ySpeed -= acceleration;
			setY(getY() + ySpeed * delta);
			setX(getX() + xSpeed * delta);
		}

		// Bounce on pillow if in range
		for (Touchable t : game.getTouchables()) {
			if (intersects(t).yDir == BOTTOM) {
				if (ySpeed < 0) {
					System.out.println(t.getYSpeed());
					ySpeed = ySpeed * -1 + t.getYSpeed();
				} else {
					ySpeed = ySpeed + t.getYSpeed();
				}

				setY(t.getY() + t.getHeight() + ySpeed * delta);
				// yes I can hit the balls /Johan //TODO make fun/special-mode
				// only
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
	 * Update the egg properties. position
	 * 
	 * @param delta
	 *            Time since last update (seconds)
	 */
	public void update(float delta) {
		if (hasStarted() && !hasStopped()) {
			updatePosition(delta);
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
	 * 
	 * @return true if egg is dead
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * Checks if the egg is on the move.
	 * 
	 * @return true if egg has started
	 */
	public boolean hasStarted() {
		return started;
	}

	/**
	 * Checks if the egg has reached the basket.
	 * 
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
		return (float) (Math.sqrt(1 - (x - width / 2) * (x - width / 2) / (width * width / 4)) * height / 2 + height / 2);
	}

	@Override
	public float getBottomLimit(float x) {
		return getHeight() - getTopLimit(x);
	}

	@Override
	public float getLeftLimit(float y) {
		// TODO
		// x = sqrt((1 - y2/b2))*a
		return -1.0f * (float) (Math.sqrt(1 - y * y / (getHeight() * getHeight() / 4)) * getWidth() / 2);
	}

	@Override
	public float getRightLimit(float y) {
		// TODO
		return (float) (Math.sqrt(1 - y * y / (getHeight() * getHeight() / 4)) * getWidth() / 2);
	}

	// /**
	// * Checks if this egg intersects with t.
	// *
	// * @param t
	// * a touchable, like a cliff or pillow, or another egg.
	// * @return true if the two objects share some pixels (if too many, it
	// * returns false)
	// */
	// private boolean intersects(Touchable t) {
	// // TODO Improve - kollar bara rakt uppifr�n mot rakt nerifr�n just nu
	// int leftBound = (int) Math.max(getX(), t.getX()) + 1;
	// int rightBound = (int) Math.min(getX() + getWidth(),
	// t.getX() + t.getWidth()) - 1;
	// for (int i = leftBound; i <= rightBound; i += 10) {
	// float diff = (t.getTopLimit(i - t.getX()) + t.getY())
	// - (getBottomLimit(i - getX()) + getY());
	// if (diff > 0)
	// return true;
	// }
	// return false;
	// }

}
