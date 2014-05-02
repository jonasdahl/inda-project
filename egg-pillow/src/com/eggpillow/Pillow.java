package com.eggpillow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pillow extends Sprite {
	private int PILLOW_Y = 50; // If set to -1, pillow can be moved all over the map
	private int paddingX = 0, paddingY = 0;
	private float limitXLeft; // In percent of width
	private float limitXRight; // In percent of width
	
	public Pillow(float limitXLeft, float limitXRight) {
		super(new Texture("img/game_pillow.png"));
		this.limitXLeft = limitXLeft;
		this.limitXRight = limitXRight;
		paddingX = Gdx.graphics.getWidth() / 10;
		paddingY = Gdx.graphics.getHeight() / 10;
		setX(0);
		setY(0);
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
		
		if (PILLOW_Y == -1)
			super.setY(Gdx.graphics.getHeight() - y);
		else
			super.setY(PILLOW_Y);
	}

	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}
	
	/**
	 * Checks if testX and testY is inside or very close to the pillow.
	 * @return true if testX and textY is close to the pillow
	 */
	public boolean inside(int testX, int testY) {
		if (testX > getX() - paddingX
				&& testX < getWidth() + getX() + paddingX
				&& testY < Gdx.graphics.getHeight() - getY() + paddingY
				&& testY > Gdx.graphics.getHeight() - getY() - getHeight() - paddingY) {
			return true;
		}
		return false;
	}
}
