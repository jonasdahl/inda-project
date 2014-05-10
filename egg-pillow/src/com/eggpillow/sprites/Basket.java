package com.eggpillow.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.eggpillow.V;

/**
 * The basket where eggs land.
 * @author Jonas
 * @version 2014-05-09
 */
public class Basket extends Touchable {
	/**
	 * Creates a new Basket with desired egg width and height.
	 * @param eggWidth the width of egg
	 * @param eggHeight the height of egg
	 * @param atlas the atlas to get image from
	 */
	public Basket(float eggWidth, float eggHeight, TextureAtlas atlas) {
		super(atlas.findRegion(V.BASKET_REGION), ids.SQUARE);
		softnessY = 1;
		softnessX = 0;
		setSize(V.WIDTH * (V.BASKET_WIDTH + eggWidth), V.HEIGHT * (V.BASKET_HEIGHT + eggHeight));
		setX(V.WIDTH - getWidth());
		setY(0);
	}

	/**
	 * Returns the XSpeed of the basket, 0.
	 * @return 0
	 */
	@Override
	public float getXSpeed() {
		return 0;
	}
	
	/**
	 * Returns the YSpeed of the basket, 0.
	 * @return 0
	 */
	@Override
	public float getYSpeed() {
		return 0;
	}

}
