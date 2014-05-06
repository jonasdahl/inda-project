package com.eggpillow;

import com.badlogic.gdx.Gdx;

public class V {

	public static final float FONT_BIG = 700f;
	public static final float FONT_MEDIUM = 800f;
	public static final float FONT_SMALL = 900f;

	public static int HEIGHT; // TODO make final/get-set
	public static int WIDTH; // TODO make final/get-set

	public static final String TITLE = "Egg Pillow";
	public static final String VERSION = "0.0.2";

	public final static String FONT = "font/EggPillow.fnt";
	
	public final static float BASKET_HEIGHT = 0.05f;
	
	public static final float TIME_BETWEEN_EGGS = 2f;
	/** In percent of screen height */
	public final static float EGG_HEIGHT = 0.15f;
	/** In percent of screen width */
	public final static float EGG_WIDTH = 0.058f;
	/** In percent of screen width */
	public final static float CLIFF_HEIGHT = 0.5f;

	public final static int LIVES = 40;

	public V() {
		HEIGHT = Gdx.graphics.getHeight();
		WIDTH = Gdx.graphics.getWidth();
	}

}
