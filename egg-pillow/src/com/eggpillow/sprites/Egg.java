package com.eggpillow.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.eggpillow.V;
import com.eggpillow.screens.GameScreen;

/**
 * Representation of an egg.
 * @author jonas
 * @version 2014-05-09
 */
public class Egg extends Touchable {
	private boolean started; // Invariant: true if egg has started moving, false otherwise
	private boolean stopped; // Invariant: true if egg has reached basket, false otherwise
	private boolean dead; // Invariant: true if egg has died
	private float yAcceleration;
	private GameScreen game;
	private TextureAtlas atlas;

	/**
	 * Constructor for class Egg.
	 * @param pillow the pillow on which this specific egg can bounce
	 * @param height the height in percent of the screen height of this egg
	 * @param width the height in percent of the screen width of this egg
	 */
	public Egg(GameScreen gameScreen, float width, float height, TextureAtlas tAtlas) {
		super(tAtlas.findRegion(V.EGG_REGION), ELLIPSE);
		setSize(V.WIDTH * width, V.HEIGHT * height);

		game = gameScreen;
		started = false;
		stopped = false;
		dead = false;
		atlas = tAtlas;
		xSpeed = V.EGG_X_SPEED * V.WIDTH;
		yAcceleration = V.GRAVITATION * V.HEIGHT;
		setY(V.HEIGHT * V.EGG_STARTING_HEIGHT);
		setX(-getWidth()); // Start outside screen and slide in
	}

	/**
	 * Updates the position of this egg.
	 * @param delta the time since last update in seconds
	 */
	public void updatePosition(float delta) {
		if (!hasStarted() || hasStopped() || isDead()) {
			return; // We're done!
		}

		float screenWidth = V.WIDTH;
		ySpeed -= yAcceleration;
		setY(getY() + ySpeed * delta);
		setX(getX() + xSpeed * delta);

		// Bounce on touchable if in range
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
				setRegion(atlas.findRegion(V.CRASHED_EGG_REGION));
				setSize(V.CRASHED_EGG_WIDTH * V.WIDTH, V.CRASHED_EGG_HEIGHT * V.HEIGHT);
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
	 * @param delta Time since last update (seconds)
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
	 * @return true if egg is dead
	 */
	public boolean isDead() {
		return dead;
	}

	/**
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
