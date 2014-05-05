package com.eggpillow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * A nice soft pillow. Eggs may bounce away if they come to close. The Pillow will probably be green.
 *  
 *  Update the pillow with update(delta) and draw(batch) every frame.
 */
public class Pillow extends Sprite implements Touchable {
	private float level;
	private boolean locked;
	private float paddingX;
	private float paddingY;
	private float limitXLeft; // In percent of screen width
	private float limitXRight; // In percent of sreen width
	
	private final static float WIDTH = .1f; // In percent of screen width
	private final static float HEIGHT = .1f; // In percent of screen height
	
	private float oldX, oldY, xSpeed, ySpeed;
	
	private final static String ATLAS_REGION = "game_pillow";

	/**
	 * Constructor for Pillow.
	 * 
	 * @param limitXLeft
	 *            the pillow can't be taken left of this column
	 * @param limitXRight
	 *            the pillow can't be taken right of this column
	 * @param yLevel
	 *            the level of the pillow in percent of the screen. if set to
	 *            negative value, then the pillow has no fixed level y-wise.
	 * @param width
	 * 			  the width (in percent of screen width)
	 * @param height
	 * 			  the height (in percent of screen height)
	 */
	public Pillow(float limitXLeft, float limitXRight, float yLevel, TextureAtlas atlas) {
		super(atlas.findRegion(ATLAS_REGION));
		setSize(Gdx.graphics.getWidth() * WIDTH, Gdx.graphics.getHeight() * HEIGHT);
		if (yLevel < 0) {
			locked = false;
		} else {
			locked = true;
			level = yLevel;
		}
		this.limitXLeft = limitXLeft;
		this.limitXRight = limitXRight;
		this.paddingX = Gdx.graphics.getWidth() / 10;
		this.paddingY = Gdx.graphics.getHeight() / 10;
		setX(0);
		setY(-1 * level);
	}
	
	/**
	 * Update the pillows properties.
	 * speed
	 *  @param delta Time since last update (seconds)
	 */
	public void update(float delta) {
		updateSpeed(delta);
	}
	
	/**
	 * Update the speed of the pillow.
	 * @param delta Time since last update seconds
	 */
	private void updateSpeed(float delta) {
		xSpeed = (oldX - getX()) * delta;
		ySpeed = (oldY - getY()) * delta;
		oldX = getX();
		oldY = getY();
	}
	
	/**
	 * Set the x position. This will put the pillow on the screen if x is out of bounds.
	 */
	@Override
	public void setX(float x) {
		if (x > limitXRight * Gdx.graphics.getWidth() - getWidth())
			x = limitXRight * Gdx.graphics.getWidth() - getWidth();
		if (x < limitXLeft * Gdx.graphics.getWidth())
			x = limitXLeft * Gdx.graphics.getWidth();
		super.setX(x);
	}
	
	/**
	 * Set the y position. This will put the pillow on a accepted position if y is illegal.
	 */
	@Override
	public void setY(float y) {
		if (y < 0) {
			y = 0;
		} else if (y > Gdx.graphics.getHeight()) {
			y = Gdx.graphics.getHeight();
		}

		if (!locked)
			super.setY(y);
		else
			super.setY(Gdx.graphics.getHeight() * level);
	}

	/**
	 * Checks if testX and testY is inside or very close to the pillow.
	 * 
	 * @return true if testX and textY is close to the pillow
	 */
	public boolean inside(int testX, int testY) {
		if (testX > getX() - paddingX
				&& testX < getWidth() + getX() + paddingX
				&& testY < Gdx.graphics.getHeight() - getY() + paddingY
				&& testY > Gdx.graphics.getHeight() - getY() - getHeight()
						- paddingY) {
			return true;
		}
		return false;
	}

	@Override
	public float getTopLimit(float x) {
		// TODO Fix limits of pillow
		return this.getHeight();
	}

	@Override
	public float getBottomLimit(float x) {
		// TODO Fix limits of pillow
		return 0;
	}

	@Override
	public float getLeftLimit(float y) {
		// TODO Fix limits of pillow
		return 0;
	}

	@Override
	public float getRightLimit(float y) {
		// TODO Fix limits of pillow
		return this.getWidth();
	}

	@Override
	public float getXSpeed() {
		return xSpeed * Gdx.graphics.getWidth();
	}

	@Override
	public float getYSpeed() {
		return ySpeed * Gdx.graphics.getHeight();
	}
}
