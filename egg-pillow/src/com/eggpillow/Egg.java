package com.eggpillow;

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
		setSize(V.WIDTH * width, V.HEIGHT * height);
		// TODO crashRegion = atlas.findRegion(ATLAS_REGION_CRASHED);
		// TODO har alla ägg en arrayList med pillow/cliff och ett texture?

		this.game = game;
		started = false;
		stopped = false;
		dead = false;
		xSpeed = X_SPEED * V.WIDTH;
		acceleration = ACCELERATION * V.HEIGHT;
		setY(V.HEIGHT * STARTING_HEIGHT);

		// Start outside screen and slide in
		setX(-getWidth());
	}

	/**
	 * Updates the position of this egg.
	 */
	public void updatePosition(float delta) {
		if (!hasStarted() || hasStopped() || isDead()) {
			return;
		}

		float screenWidth = V.WIDTH;

		ySpeed -= acceleration;
		setY(getY() + ySpeed * delta);
		setX(getX() + xSpeed * delta);

		// Bounce on pillow if in range
		for (Touchable t : game.getTouchables()) {
			ReturnClass intersect = intersects(t);
			float softnessY = t.getYSoftness();
			if (intersect.yDir == BOTTOM) {

				if (ySpeed < 0) {
					ySpeed *= -1;
				}
				ySpeed += t.getYSpeed();
				ySpeed *= 1 - softnessY;

				setY(t.getY() + t.getHeight() + ySpeed * delta);
			
			} else if (intersect.yDir == TOP) { 
				if (ySpeed > 0) {
					ySpeed *= -1;
				}
				ySpeed += t.getYSpeed();
				ySpeed *= 1 - softnessY;
				setY(t.getY() - getHeight() + ySpeed * delta);
			}
			// TODO make fun/special-mode only
			// TODO implement correctly
			// float softnessX = t.softnessX;
			// if (intersect.xDir == LEFT) {
			// if (xSpeed < 0) {
			// xSpeed *= -1;
			// }
			// xSpeed += t.getXSpeed();
			// setX(t.getX() + t.getWidth() + xSpeed * delta);
			// }
			// if (intersect.xDir == RIGHT) {
			// if (xSpeed > 0) {
			// xSpeed *= -1;
			// }
			// xSpeed += t.getXSpeed();
			// //xSpeed *= 1 - softnessX;
			// setX(t.getX() - getWidth() + xSpeed * delta);
			// }
		}
		
		// Stopped
		if (getYSpeed() == 0 && getXSpeed() == 0 && getY() == V.HEIGHT * (V.BASKET_HEIGHT + V.EGG_HEIGHT) && getX() + getWidth() > V.WIDTH * 0.95f) { //TODO Change 0.95f to BASKET WIDTH
			stopped = true;
			// Egg can now be removed from arrayList in gameScreen
		}
		//Dead
		if (getY() <= 0) {
			if (!dead) {
				setY(0);
				ySpeed = 0;
				xSpeed = 0;
				// TODO setRegion(crashRegion);
				dead = true;
			}
		}

		// Reached right side!
		if (getX() + getWidth() >= screenWidth) {
			setX(screenWidth - getWidth());
			xSpeed = 0; // But still go down
		}
		if (getX() < 0) {
			setX(0);
			xSpeed *= -1;
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
		return getWidth() - getRightLimit(y);
	}

	@Override
	public float getRightLimit(float y) {
		float width = getWidth();
		float height = getHeight();
		return (float) (Math.sqrt(1 - (y - height / 2) * (y - height/2) / (height * height / 4)) * width / 2 + width / 2 );
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
	// // TODO Improve - kollar bara rakt uppifrŒn mot rakt nerifrŒn just nu
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
