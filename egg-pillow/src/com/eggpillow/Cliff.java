package com.eggpillow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Cliff extends Sprite implements Touchable {
	private final static float WIDTH = .3f;
	private final static float HEIGHT = .5f;
	
	/**
	 * Constructor for Cliff.
	 * @param height percent of height of screen where cliff top should be
	 */
	public Cliff(float height, TextureAtlas atlas) {
		super(atlas.findRegion("game_cliff"));
		setSize(1.5f * WIDTH * Gdx.graphics.getWidth(), 1.5f * HEIGHT * Gdx.graphics.getHeight());
		setX(0);
		setY((height - HEIGHT) * Gdx.graphics.getHeight());
	}

	@Override
	public float getTopLimit(float x) {
		if (x < getWidth() / 2)
			return this.getHeight();
		return 0;
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

	@Override
	public float getXSpeed() {
		return 0;
	}

	@Override
	public float getYSpeed() {
		return 0;
	}
}
