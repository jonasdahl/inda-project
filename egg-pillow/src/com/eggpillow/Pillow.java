package com.eggpillow;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * A nice soft pillow. Eggs may bounce away if they come to close. The Pillow
 * will probably be green.
 * 
 * Update the pillow with update(delta) and draw(batch) every frame.
 */
public class Pillow extends Touchable {
	private float level;
	private boolean locked;
	private float paddingX;
	private float paddingY;
	private ArrayList<Touchable> touchables;

	private final static float WIDTH = .1f; // In percent of screen width
	private final static float HEIGHT = .1f; // In percent of screen height

	private float[] oldX, oldY;
	private int nextOld = 0;

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
	 *            the width (in percent of screen width)
	 * @param height
	 *            the height (in percent of screen height)
	 */
	public Pillow(ArrayList<Touchable> thouchables, float yLevel, TextureAtlas atlas) {
		super(atlas.findRegion(ATLAS_REGION));
		setSize(Gdx.graphics.getWidth() * WIDTH, Gdx.graphics.getHeight() * HEIGHT);
		if (yLevel < 0) {
			locked = false;
		} else {
			locked = true;
			level = yLevel;
		}
		this.touchables = thouchables;
		this.paddingX = Gdx.graphics.getWidth() / 10;
		this.paddingY = Gdx.graphics.getHeight() / 10;
		setX(0);
		setY(-1 * level);
		oldX = new float[3];
		oldY = new float[3];
	}

	/**
	 * Update the pillows properties. speed
	 * 
	 * @param delta
	 *            Time since last update (seconds)
	 */
	public void update(float delta) {
		updateSpeed(delta);
	}

	/**
	 * Update the speed of the pillow.
	 * 
	 * @param delta
	 *            Time since last update seconds
	 */
	private void updateSpeed(float delta) {
		// TODO delay or fluctuate
		xSpeed = (getX() - medel(oldX)) * delta * Gdx.graphics.getWidth();
		ySpeed = (getY() - medel(oldY)) * delta * Gdx.graphics.getHeight();
		//xSpeed = (getX() - oldX[ nextOld]) * delta * Gdx.graphics.getWidth();
		//ySpeed = (getY() - oldY[nextOld]) * delta * Gdx.graphics.getHeight();

		oldX[nextOld] = getX();
		oldY[nextOld] = getY();
		nextOld++;
		if (nextOld == 3) {
			nextOld = 0;
		}
		for (Touchable touch : touchables) {
			if (touch != this) {
				if (intersects(touch).t != null) {
					System.out.println("INTERSECT");
				}
			}
		}
	}

	private float medel(float[] v) {
		float x = 0;
		for (float f : v) {
			x += f;
		}
		return x / v.length;
	}

	/**
	 * Set the x position. This will put the pillow on the screen if x is out of
	 * bounds.
	 */
	@Override
	public void setX(float x) {
		if (x > Gdx.graphics.getWidth() - getWidth())
			x = Gdx.graphics.getWidth() - getWidth();
		if (x < 0)
			x = 0;
		super.setX(x);
	}

	/**
	 * Set the y position. This will put the pillow on a accepted position if y
	 * is illegal.
	 */
	@Override
	public void setY(float y) {
		if (y < 0) {
			y = 0;
		} else if (y > Gdx.graphics.getHeight() - getHeight()) {
			y = Gdx.graphics.getHeight() - getHeight();
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
		if (testX > getX() - paddingX && testX < getWidth() + getX() + paddingX
				&& testY < Gdx.graphics.getHeight() - getY() + paddingY
				&& testY > Gdx.graphics.getHeight() - getY() - getHeight() - paddingY) {
			return true;
		}
		return false;
	}

	@Override
	public float getTopLimit(float x) {
		return this.getHeight() / 2;
	}

	@Override
	public float getBottomLimit(float x) {
		return 0;
	}

	@Override
	public float getLeftLimit(float y) {
		return 0;
	}

	@Override
	public float getRightLimit(float y) {
		return this.getWidth();
	}
}
