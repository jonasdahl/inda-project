package com.eggpillow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Basket extends Touchable {
	private final static float WIDTH = 0.05f;
	private final static float HEIGHT = 0.05f;
	private final static String ATLAS_REGION = "game_basket";

	public Basket(float eggWidth, float eggHeight, TextureAtlas atlas) {
		super(atlas.findRegion(ATLAS_REGION));
		softness = 1;
		setSize(Gdx.graphics.getWidth() * (WIDTH + eggWidth), Gdx.graphics.getHeight()
				* (HEIGHT + eggHeight));
		setX(Gdx.graphics.getWidth() - getWidth());
		setY(0);
	}

	@Override
	public float getTopLimit(float x) {
		return getHeight();
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
		return getWidth();
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
