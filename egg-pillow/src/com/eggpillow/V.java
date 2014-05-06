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

	public V() {
		HEIGHT = Gdx.graphics.getHeight();
		WIDTH = Gdx.graphics.getWidth();
	}

}
