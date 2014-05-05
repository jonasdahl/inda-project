package com.eggpillow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.eggpillow.screens.GameScreen;

public class Egg extends Touchable {
	private boolean started; // Invariant: true if egg has started moving, false
								// otherwise
	private boolean stopped; // Invariant: true if egg has reached basket, false
								// otherwise
	private boolean dead; // Invariant: true if egg has died
	private float acceleration;
	private GameScreen game;

	private final static float STARTING_HEIGHT = 0.52f; // In percent of screen
														// height
	private final static float ACCELERATION = 0.01f; // TODO balance speeds
	private final static float X_SPEED = 0.4f; // In percent of screen width
	private final static String ATLAS_REGION = "game_egg";
	private final static String ATLAS_REGION_CRASHED = "game_egg_crashed";

	private AtlasRegion crashRegion;
	
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
		// TODO crashRegion = atlas.findRegion(ATLAS_REGION_CRASHED);
		// TODO har alla ‰gg en arrayList med pillow/cliff och ett texture?

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

		ySpeed -= acceleration;
		setY(getY() + ySpeed * delta);
		setX(getX() + xSpeed * delta);

		// Bounce on pillow if in range
		for (Touchable t : game.getTouchables()) {
			ReturnClass intersect = intersects(t);
			if (intersect.yDir == BOTTOM) {
				float softness = t.getSoftness();
				if (softness > 1)
					softness = 1;
				else if (softness < 0)
					softness = 0;

				if (ySpeed < 0) {
					ySpeed *= -1;
				}
				ySpeed += t.getYSpeed();
				ySpeed *= 1 - softness;

				setY(t.getY() + t.getHeight() + ySpeed * delta);
				//TODO make fun/special-mode
				// only
			} else if (intersect.yDir == TOP) {
				if (ySpeed > 0) {
					ySpeed *= -1;
				}
				ySpeed += t.getYSpeed();
				setY(t.getY() - getHeight() + ySpeed * delta);
			}
			//TODO left-right collision
			// if (intersect.xDir == LEFT) {
			// System.out.println("LEFT");
			// if (xSpeed > 0) {
			// xSpeed *= -1;
			// }
			// xSpeed += t.getXSpeed();
			// System.out.println(xSpeed);
			// setX(t.getX() - getWidth() + xSpeed * delta);
			// }
			// if (intersect.xDir == RIGHT) {
			// System.out.println("RIGHT");
			// if (xSpeed < 0) {
			// xSpeed *= -1;
			// }
			// xSpeed += t.getXSpeed();
			// xSpeed *= 1 - t.getSoftness();
			// setX(t.getX() + xSpeed * delta);
			// }
		}

		// Dead or just stopped
		if (getY() <= 0) {
			stopped = true;
			if (getX() + getWidth() < Gdx.graphics.getWidth() * 0.95f) {
				if (!dead) {
					// TODO setRegion(crashRegion);
				}
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
	// // TODO Improve - kollar bara rakt uppifrån mot rakt nerifrån just nu
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
