package com.eggpillow.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.eggpillow.V;

/**
 * The cliff where eggs jump from.
 * @author Jonas
 * @version 2014-05-09
 */
public class Cliff extends Touchable {
	/**
	 * Constructor for Cliff.
	 * @param height percent of height of screen where cliff top should be
	 */
	public Cliff(float height, TextureAtlas atlas) {
		super(atlas.findRegion(V.CLIFF_REGION), SQUARE);
		softnessY = 1; // Hard as stone
		softnessX = 1;
		setSize(V.CLIFF_WIDTH * V.WIDTH, V.CLIFF_HEIGHT * V.HEIGHT);
		setX(0);
		setY((height - V.CLIFF_HEIGHT) * V.HEIGHT);
	}

	/**
	 * Returns the XSpeed of the cliff, 0.
	 * @return 0
	 */
	@Override
	public float getXSpeed() {
		return 0;
	}
	
	/**
	 * Returns the YSpeed of the cliff, 0.
	 * @return 0
	 */
	@Override
	public float getYSpeed() {
		return 0;
	}
}
