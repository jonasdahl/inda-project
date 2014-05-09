package com.eggpillow.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.eggpillow.Point;

public abstract class Touchable extends Sprite {
	protected float softnessX;
	protected float softnessY;
	protected float xSpeed;
	protected float ySpeed;

	protected final int ID;
	protected final static int SQUARE = 0;
	protected final static int ELLIPSE = 1;

	/**
	 * 
	 * @param tex
	 * @param id
	 */
	public Touchable(AtlasRegion tex, int id) {
		super(tex);
		ID = id;
	}

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
		if (ID == ELLIPSE && t.ID == SQUARE) {
			if (t.insideSquare(getCircleEdge((float) v))) {
				return new ReturnClass(t, (float) v);
			}
		} else if (ID == SQUARE && t.ID == SQUARE) {
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
				return new ReturnClass(null, (float) v);
			} else if (vX == 0 || vY == 0) {
				return new ReturnClass(t, (float) vX + vY);
			} else if (vY == 90 && vX == 360) {
				return new ReturnClass(t, (float) vY / 2);
			} else {
				return new ReturnClass(t, (float) (vX + vY) / 2);
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
		} else if (ID == SQUARE && t.ID == ELLIPSE) {
			// TODO Test
			if (insideSquare(t.getCircleEdge((float)v))) {
				throw new Error("THIS NEEDS TO BE TESTED");
				// TODO return new ReturnClass(t, (float) v);
			}
		} else {
			throw new Error("HOW DID U GET HERE? THIS IS NOT SAFE");
		}
		

		return new ReturnClass(null, (float) v);
	}

	public Point getCircleEdge(float v) {
		float x = (getWidth() / 2) * (float) Math.cos(v);
		float y = (getHeight() / 2) * (float) Math.sin(v);
		x = getCenterX() + x;
		y = getCenterY() + y;
		return new Point(x, y);
	}

	public boolean insideSquare(Point p) {
		if (p.getX() > getX() && p.getX() < getWidth() + getX() && p.getY() < getHeight() + getY() && p.getY() > getY()) {
			return true;
		}
		return false;
	}

	protected class ReturnClass {

		protected Touchable t;
		protected float v;
		protected int side;

		protected ReturnClass(Touchable t, float v) {
			this.t = t;
			this.v = v;
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

}
