package com.eggpillow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Cliff extends Touchable {
	/** Width in percent of screen width. */
	private final static float WIDTH = .3f;
	/** Height in percent of screen height. */
	private final static float HEIGHT = .5f;
	/** If cliff width and height is 1, then the image size is this... */
	private final static String ATLAS_REGION = "game_cliff";

	/**
	 * Constructor for Cliff.
	 * 
	 * @param height
	 *            percent of height of screen where cliff top should be
	 */
	public Cliff(float height, TextureAtlas atlas) {
		super(atlas.findRegion(ATLAS_REGION));
		setSize(WIDTH * Gdx.graphics.getWidth(), HEIGHT
				* Gdx.graphics.getHeight());
		setX(0);
		setY((height - HEIGHT) * Gdx.graphics.getHeight());
	}

	@Override
	public float getTopLimit(float x) {
		// TODO Complete method
		if (x < getWidth())
			return getHeight();
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
		return this.getWidth(); // TODO Complete method
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
