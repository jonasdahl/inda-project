package com.eggpillow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eggpillow.screens.GameScreen;

public class Pillow extends Sprite {
	
	private int paddingX = 0, paddingY = 0;
	
	public Pillow() {
		super(new Texture("img/game_pillow.png"));
		paddingX = Gdx.graphics.getWidth() / 10;
		paddingY = Gdx.graphics.getHeight() / 10;
	}

	@Override
	public void setX(float x) {
		// TODO Check if img out of bounds and change x
		super.setX(x);
	}

	@Override
	public void setY(float y) {
		// TODO Check if img out of bounds and change y
		super.setY(Gdx.graphics.getHeight() - y);
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
