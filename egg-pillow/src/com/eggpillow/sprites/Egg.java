package com.eggpillow.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.eggpillow.V;
import com.eggpillow.screens.GameScreen;

public class Egg extends Touchable {
	private boolean started; // Invariant: true if egg has started moving, false
								// otherwise
	private boolean stopped; // Invariant: true if egg has reached basket, false
								// otherwise
	private boolean dead; // Invariant: true if egg has died
	private float acceleration;
	private GameScreen game;

	private final static float CRASHED_EGG_WIDTH = 0.058f * 2.55f;
	private final static float CRASHED_EGG_HEIGHT = 0.058f;

	private final static float STARTING_HEIGHT = 0.52f; // In percent of screen
														// height
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
		super(atlas.findRegion(ATLAS_REGION), ELLIPSE);
		setSize(V.WIDTH * width, V.HEIGHT * height);
		crashRegion = atlas.findRegion(ATLAS_REGION_CRASHED);

		this.game = game;
		started = false;
		stopped = false;
		dead = false;
		xSpeed = X_SPEED * V.WIDTH * 0.1f; // TODO remove 0.1
		acceleration = V.GRAVITATION * V.HEIGHT;
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
			if (intersect.t != null) {
				// Collision vertical
				float softnessY = t.getYSoftness();
				if (Math.PI <= intersect.v && intersect.v <= Math.PI * 2) {
					if (ySpeed < 0) {
						ySpeed *= -1;
					}
					ySpeed += t.getYSpeed();
					ySpeed *= 1 - softnessY;

					setY(t.getY() + t.getHeight() + ySpeed * delta);
				} else if (0 < intersect.v && intersect.v < Math.PI) {
					if (ySpeed > 0) {
						ySpeed *= -1;
					}
					ySpeed += t.getYSpeed();
					ySpeed *= 1 - softnessY;
					setY(t.getY() - getHeight() + ySpeed * delta);
				}
				// Collision horizontal
				// TODO if funmode
				if (false) {
					float softnessX = t.getXSoftness();
					if (Math.PI / 2 > intersect.v && intersect.v > Math.PI * 3 / 2) {
						if (xSpeed < 0) {
							xSpeed *= -1;
						}
						xSpeed += t.getXSpeed();
						xSpeed *= 1 - softnessX;

						setX(t.getX() + t.getWidth() + xSpeed * delta);
					} else if (Math.PI / 2 < intersect.v && intersect.v < Math.PI * 3 / 2) {
						if (xSpeed > 0) {
							xSpeed *= -1;
						}
						xSpeed += t.getXSpeed();
						xSpeed *= 1 - softnessX;
						setX(t.getX() - getWidth() + xSpeed * delta);
					}
				}
			}
		}

		// Stopped
		if (getYSpeed() == 0 && getXSpeed() == 0 && getY() == V.HEIGHT * (V.BASKET_HEIGHT + V.EGG_HEIGHT)
				&& getX() + getWidth() > V.WIDTH * 0.95f) { // TODO Change 0.95f
															// to BASKET WIDTH
			stopped = true;
			// Egg can now be removed from arrayList in gameScreen
		}
		// Dead
		if (getY() <= 0) {
			if (!dead) {
				setY(0);
				ySpeed = 0;
				xSpeed = 0;
				setRegion(crashRegion);
				setSize(CRASHED_EGG_WIDTH * V.WIDTH, CRASHED_EGG_HEIGHT * V.HEIGHT);
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

}
