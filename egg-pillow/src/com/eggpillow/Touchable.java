package com.eggpillow;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public abstract class Touchable extends Sprite {

	protected float softnessX = 0; // Between 0 and 1
	protected float softnessY = 0; // Between 0 and 1

	protected final int ID;
	protected final static int SQUARE = 0;
	protected final static int ELLIPSE = 1;

	protected float xSpeed, ySpeed;

	// This be ugly
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
				return new ReturnClass(t, (float)v);
			}
		} else if (ID == SQUARE && t.ID == SQUARE) {
			if (t.insideSquare(new Point(getX(), getY()))) {
				return new ReturnClass(t, (float)v);
			} else if (t.insideSquare(new Point(getX(), getY() + getHeight()))) {
				return new ReturnClass(t, (float)v);
			} else if (t.insideSquare(new Point(getX() + getWidth(), getY()))) {
				return new ReturnClass(t, (float)v);
			} else if (t.insideSquare(new Point(getX() + getWidth(), getY() + getHeight()))) {
				return new ReturnClass(t, (float)v);
			}
		}

		return new ReturnClass(null, (float)v);
	}

	public Point getCircleEdge(float v) {
		float x = (getWidth() / 2) * (float) Math.cos(v);
		float y = (getHeight() / 2) * (float) Math.sin(v);
		// System.out.println(x + "  " + y + "  " + v * 57 + " " + Math.sqrt(x
		// *x + y * y));
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
