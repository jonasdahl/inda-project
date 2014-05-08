package com.eggpillow;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Basket extends Touchable {
	private final static float WIDTH = 0.05f;
	private final static String ATLAS_REGION = "game_basket";

	public Basket(float eggWidth, float eggHeight, TextureAtlas atlas) {
		super(atlas.findRegion(ATLAS_REGION));
		softnessY = 1;
		softnessX = 0;
		setSize(V.WIDTH * (WIDTH + eggWidth), V.HEIGHT * (V.BASKET_HEIGHT + eggHeight));
		setX(V.WIDTH - getWidth());
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

	@Override
	public float getRadiusSquare(float v) {
		if (v > Math.PI * 2 || v < 0) {
			throw new IllegalArgumentException();
		}
		if (Math.PI < v && v <= 2*Math.PI) {
			v = 360 - v;
		}
		if (Math.PI / 2 < v && v <= Math.PI) {
			v = 180 - v;
		}
		float r1 = (float) ((getWidth() / 2) / Math.cos(v));
		float r2 = (float) ((getHeight() / 2) / Math.sin(v));
		float r = Math.min(r1, r2);
		return r * r;
	}

}
