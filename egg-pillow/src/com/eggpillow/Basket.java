package com.eggpillow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Basket extends Sprite implements Touchable{
	private final static float WIDTH = 0.5f;
	private final static float HEIGHT = 0.5f;
	private final static String ATLAS_REGION = "game_egg";
	
	public Basket(TextureAtlas atlas) {
		super(atlas.findRegion(ATLAS_REGION));
		setSize(Gdx.graphics.getWidth() * WIDTH, Gdx.graphics.getHeight() * HEIGHT);
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
