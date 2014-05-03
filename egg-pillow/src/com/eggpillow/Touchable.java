package com.eggpillow;

public interface Touchable {
	/**
	 * Get the top limit of this object in terms of y-pixels above the downside
	 * of this object.
	 * 
	 * @param x
	 *            the x-column that is to be investigated
	 * @return the top limit of this object y-wise for given x-column
	 */
	float getTopLimit(float x);

	/**
	 * Get the bottom limit of this object in terms of y-pixels above the
	 * downside of this object.
	 * 
	 * @param x
	 *            the x-column that is to be investigated
	 * @return the bottom limit of this object y-wise for given x-column
	 */
	float getBottomLimit(float x);

	/**
	 * Get the left limit of this object in terms of x-pixels to the right of
	 * the left side of this object.
	 * 
	 * @param y
	 *            the y-column that is to be investigated
	 * @return the left limit of this object x-wise for given y-column
	 */
	float getLeftLimit(float y);
	
	/**
	 * Get the right limit of this object in terms of x-pixels to the right of
	 * the left side of this object.
	 * 
	 * @param y
	 *            the y-column that is to be investigated
	 * @return the right limit of this object x-wise for given y-column
	 */
	float getRightLimit(float y);
	
	float getWidth();
	float getHeight();
	float getX();
	float getY();
}
