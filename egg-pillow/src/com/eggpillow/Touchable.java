package com.eggpillow;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public abstract class Touchable extends Sprite {

	public static final int TOP = -1;
	public static final int BOTTOM = 1;
	public static final int LEFT = 1;
	public static final int RIGHT = -1;

	protected float xSpeed, ySpeed;
	
	// This be ugly
	public Touchable(AtlasRegion tex) {
		super(tex);
	}

	/**
	 * Get the top limit of this object in terms of y-pixels above the downside
	 * of this object.
	 * 
	 * @param x
	 *            the x-column that is to be investigated
	 * @return the top limit of this object y-wise for given x-column
	 */
	public abstract float getTopLimit(float x);

	/**
	 * Get the bottom limit of this object in terms of y-pixels above the
	 * downside of this object.
	 * 
	 * @param x
	 *            the x-column that is to be investigated
	 * @return the bottom limit of this object y-wise for given x-column
	 */
	public abstract float getBottomLimit(float x);

	/**
	 * Get the left limit of this object in terms of x-pixels to the right of
	 * the left side of this object.
	 * 
	 * @param y
	 *            the y-column that is to be investigated
	 * @return the left limit of this object x-wise for given y-column
	 */
	public abstract float getLeftLimit(float y);

	/**
	 * Get the right limit of this object in terms of x-pixels to the right of
	 * the left side of this object.
	 * 
	 * @param y
	 *            the y-column that is to be investigated
	 * @return the right limit of this object x-wise for given y-column
	 */
	public abstract float getRightLimit(float y);

	// Speed
	/** percent of screen width / sec */
	public float getXSpeed() {
		return xSpeed;
	}

	/** percent of screen height / sec */
	public float getYSpeed() {
		return ySpeed;
	}

	/**
	 * 
	 * @param t
	 * @return ReturnClass if intersect with t = null if no intersect.
	 */
	public ReturnClass intersects(Touchable t) {
		int xDir = 0, yDir = 0;
		float botBound = Math.max(getY(), t.getY());
		float topBound = Math.min(getY() + getHeight(), t.getY() + t.getHeight());
		float diffY = (topBound - botBound) / 10;
		for (float i = 0; i < topBound - botBound; i += diffY) {
			if (getXSpeed() >= 0) {
				if (getRightLimit(i) + getX() < t.getLeftLimit(i) + t.getX()) {
					//xDir = RIGHT;
				}
			} else {
				if (getLeftLimit(i) + getX() < t.getRightLimit(i) + t.getX()) {
					//xDir = LEFT;
				}
			}
		}
		float leftBound = Math.max(getX(), t.getX());
		float rightBound = Math.min(getX() + getWidth(), t.getX() + t.getWidth());
		float diffX = (rightBound - leftBound) / 10;
		for (float j = 0; j < rightBound - leftBound; j += diffX) {
			if (getYSpeed() > 0) {
				if (getTopLimit(j) + getY() > t.getBottomLimit(j) + t.getY()) {
					yDir = TOP;
				}
			} else {
				if (getBottomLimit(j) + getY() < t.getTopLimit(j) + t.getY()) {
					yDir = BOTTOM;
				}
			}
		}
		if (xDir != 0 || yDir != 0) {
			return new ReturnClass(t, xDir, yDir);
		}
		return new ReturnClass(null, 0, 0);
	}

	protected class ReturnClass {

		protected Touchable t;
		protected int xDir, yDir;

		protected ReturnClass(Touchable t, int x, int y) {
			this.t = t;
			xDir = x;
			yDir = y;
		}

	}
}
