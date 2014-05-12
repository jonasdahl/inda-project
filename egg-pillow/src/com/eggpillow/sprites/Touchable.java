package com.eggpillow.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.eggpillow.Point;

/**
 * Represents a sprite in the game with properties to make it able to collide with other touchables
 * @author Johan & Jonas
 * @version 2014-05-09
 */
public abstract class Touchable extends Sprite {
	protected float softnessX;
	protected float softnessY;
	protected float xSpeed;
	protected float ySpeed;

	protected final ids ID;
	protected enum ids {
		SQUARE, ELLIPSE
	}

	/**
	 * Creates a new touchable.
	 * @param aRegion the atlasRegion to use
	 * @param id SQUARE or ELLIPSE
	 */
	public Touchable(AtlasRegion aRegion, ids id) {
		super(aRegion);
		ID = id;
	}

	/** 
	 * Returns x speed in percent of screen width per second. 
	 * @return x speed in percent of screen width per second
	 */
	public float getXSpeed() {
		return xSpeed;
	}

	/** 
	 * Returns y speed in percent of screen width per second. 
	 * @return y speed in percent of screen width per second
	 */
	public float getYSpeed() {
		return ySpeed;
	}

	/**
	 * Checks if this object intersects with t.
	 * @param t
	 * @return angel. Negative angle if no intersections.
	 */
	public float intersects(Touchable t) {
		// Calculate Degree v
		float pX = t.getCenterX() - getCenterX();
		float pY = t.getCenterY() - getCenterY();
		double v = 0;

		if (pX < 0) {
			v = Math.atan(pY / -pX);
			v = Math.PI - v;
		} else if (pX >= 0) {
			v = Math.atan(pY / pX);
			if (v < 0) {
				v = Math.PI * 2 + v;
			}
		}
		if (ID == ids.ELLIPSE && t.ID == ids.SQUARE) {
			if (t.insideSquare(getCircleEdge((float) v))) {
				return (float)v;
			}
		} else if (ID == ids.SQUARE && t.ID == ids.SQUARE) {
			int vX = 0;
			int vY = 0;
			if (getX() + getWidth() > t.getX() && getX() + getWidth() < t.getX() + t.getWidth()
					&& getY() + getHeight() > t.getY() && getY() < t.getY() + t.getHeight()) {
				vX = 360;
			} else if (getX() < t.getX() + t.getWidth() && getX() > t.getX() && getY() + getHeight() > t.getY()
					&& getY() < t.getY() + t.getHeight()) {
				vX = 180;
			} else if (getY() + getHeight() > t.getY() && getY() + getHeight() < t.getY() + t.getHeight()
					&& getX() + getWidth() > t.getX() && getX() < t.getX() + t.getWidth()) {
				vY = 90;
			} else if (getY() < t.getY() + t.getHeight() && getY() > t.getY() && getX() + getWidth() > t.getX()
					&& getX() < t.getX() + t.getWidth()) {
				vY = 270;
			}
			if (vX == 0 && vY == 0) {
				return (float) v;
			} else if (vX == 0 || vY == 0) {
				return (float) (vX + vY);
			} else if (vY == 90 && vX == 360) {
				return (float) (vY / 2);
			} else {
				return (float) ((vX + vY) / 2);
			}
			/*
			 * LÅT STÅ plz 0 0 0
			 * 
			 * 360 360 0 180 180 0
			 * 
			 * 90 0 90 270 0 270
			 * 
			 * 45 360 90 0 + 90 / 2 135 180 90 (180 + 90) / 2 225 180 270 (180 +
			 * 270) /2 315 360 270 (270 + 360) / 2
			 */
		} else if (ID == ids.SQUARE && t.ID == ids.ELLIPSE) {
			if (insideSquare(t.getCircleEdge((float)v))) {
				throw new Error("THIS WILL PROBABLY WORK BUT NEEDS TO BE TESTED");
				// TODO test and return (float) v;
			}
		} else {
			throw new Error("HOW DID U GET HERE? THIS IS NOT SAFE"); // TODO FIX PLZ, but it's not safe to be here
		}
		return -1; // No collision
	}

	/**
	 * Get a point on the edge.
	 * @param v (radians) the angel from the positive x-axis
	 * @return the point on the edge if we go in the direction of v
	 */
	public Point getCircleEdge(float v) {
		float x = (getWidth() / 2) * (float) Math.cos(v);
		float y = (getHeight() / 2) * (float) Math.sin(v);
		x = getCenterX() + x;
		y = getCenterY() + y;
		return new Point(x, y);
	}

	/**
	 * Checks if the point p is inside this square.
	 * @param p the point (x, y)
	 * @return true if p is inside this square. else if not.
	 */
	public boolean insideSquare(Point p) {
		if (p.getX() > getX() && p.getX() < getWidth() + getX() && p.getY() < getHeight() + getY() && p.getY() > getY()) {
			return true;
		}
		return false;
	}

	/**
	 * Returns y center.
	 * @return y center.
	 */
	public float getCenterX() {
		return getX() + getWidth() / 2;
	}
	
	/**
	 * Returns x center.
	 * @return x center.
	 */
	public float getCenterY() {
		return getY() + getHeight() / 2;
	}
	
	/**
	 * Returns y softness.
	 * @return y softness.
	 */
	public float getYSoftness() {
		return softnessY;
	}

	/**
	 * Returns x softness.
	 * @return x softness.
	 */
	public float getXSoftness() {
		return softnessX;
	}
}
