package com.eggpillow.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.eggpillow.V;

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
		super(atlas.findRegion(ATLAS_REGION), SQUARE);
		softnessY = 1;
		softnessX = 1;
		setSize(WIDTH * V.WIDTH, HEIGHT * V.HEIGHT);
		setX(0);
		setY((height - HEIGHT) * V.HEIGHT);
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
