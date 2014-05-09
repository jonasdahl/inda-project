package com.eggpillow;

/**
 * Represents a point (x, y)
 * @author jonas
 * @version 2014-05-09
 */
public class Point {
	private float x;
	private float y;
	
	/**
	 * Creates a new point (x, y)
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public Point (float x, float y) {
		setX(x);
		setY(y);
	}

	/**
	 * Gets this point's x coordinate.
	 * @return the x coordinate of this Point
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets this point's x coordinate.
	 * @param x the x coordinate to be set
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * Gets this point's y coordinate.
	 * @return the y coordinate of this Point
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Sets this point's y coordinate.
	 * @param y the y coordinate to be set
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
