package com.eggpillow;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public abstract class Touchable extends Sprite {

	public static final int TOP = -100;
	public static final int BOTTOM = 100;
	public static final int LEFT = 100;
	public static final int RIGHT = -100;
	protected float softnessX = 0; // Between 0 and 1
	protected float softnessY = 0; // Between 0 and 1

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
		// Calculate Degree v
		float pX = t.getCenterX() - getCenterX();
		float pY = t.getCenterY()- getCenterY();
		
		double v;
		// To first quadrant
		if (pX < 0 && pY < 0) {
			// 4
			v =  Math.atan(pX / pY);
			v = 2 * Math.PI - v;
		} else if (pX < 0) {
			v =  Math.atan(-(pX / pY));
			// 2
			v = Math.PI - v;
		} else if (pY < 0) {
			// 3
			v = Math.atan(-(pX / pY));
			v = Math.PI + v; 
		} else {
			// 1
			v = Math.atan(pX / pY);
		}
		float rad0 = pX * pX + pY * pY;
		float rad1 = getRadiusSquare((float)v);
		float rad2 = t.getRadiusSquare((float)v);
		
		if (rad0 < rad1 + rad2) {
			// INTERSECT
			if (t.getWidth() == V.WIDTH * 0.1f) {
				System.out.println("INSIDE");
			}
				
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
	
	public float getCenterX() {
		return getX() + getWidth() / 2;
	}
	
	public float getCenterY() {
		return getY() + getHeight() / 2;
	}

	public float getYSoftness() {
		return softnessY;
	}

	public float getXSoftness() {
		return softnessX;
	}
	
	/**
	 * @param v Degrees from positive x-axis
	 * @return Length of radius^2
	 */
	public abstract float getRadiusSquare(float v);
}
