package com.eggpillow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pillow extends Sprite implements Touchable {
	private float level;
	private boolean locked;
	private float paddingX = 0;
	private float paddingY = 0;
	private float limitXLeft; // In percent of width
	private float limitXRight; // In percent of width

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
	public Pillow(float limitXLeft, float limitXRight, float yLevel) {
		super(new Texture("img/game_pillow.png"));
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

	@Override
	public void setX(float x) {
		if (x > limitXRight * Gdx.graphics.getWidth() - getWidth())
			x = limitXRight * Gdx.graphics.getWidth() - getWidth();
		if (x < limitXLeft * Gdx.graphics.getWidth())
			x = limitXLeft * Gdx.graphics.getWidth();
		super.setX(x);
	}

	@Override
	public void setY(float y) {
		if (y < getHeight()) {
			y = getHeight();
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
}
