package com.eggpillow;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Cliff extends Touchable {
	/** Width in percent of screen width. */
	private final static float WIDTH = .1f;
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
		softnessY = 1;
		softnessX = 1;
		setSize(WIDTH * V.WIDTH, HEIGHT * V.HEIGHT);
		setX(0);
		setY((height - HEIGHT) * V.HEIGHT);
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
		return -500; // TODO fix
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
