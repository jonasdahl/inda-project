package com.eggpillow;

import com.badlogic.gdx.Gdx;

// TODO Finalize

/**
 * A class containing a vast number of constants and variables.
 * @author Johan & Jonas
 * @version 2014-05-09
 */
public class V {
	public static final float FONT_MEDIUM = 800f;
	public static final float FONT_SMALL = 900f;

	public static int HEIGHT; 
	public static int WIDTH;

	public static final String TITLE = "Egg Pillow";
	public static final String VERSION = "0.1.0";
	
	public final static String FONT = "font/EggPillow.fnt";
	public final static String GAME_BACKGROUND_IMAGE = "img/game_background.png";
	public final static String EGG_REGION = "game_egg";
	public final static String PILLOW_REGION = "game_pillow";
	public final static String BASKET_REGION = "game_basket";
	public final static String CLIFF_REGION = "game_cliff";
	public final static String CRASHED_EGG_REGION = "game_egg_crashed";
	public final static String POWERUP_REGION = "game_powerup"; 
	public final static String POWERUP_HEART_REGION = "game_heart";
	public final static String POWERUP_FREEZE_REGION = "game_freeze";
	public final static String ARROW_REGION = "game_triangle";
	
	
	public final static String GAME_IMAGE_PACK = "gameImg/EggPillow.pack";
	
	/** In percent of screen height */
	public final static float EGG_HEIGHT = 0.15f;
	/** In percent of screen width */
	public final static float EGG_WIDTH = 0.058f;
	/** In percent of screen width */
	public final static float CLIFF_HEIGHT = 0.5f;
	/** In percent of screen width */
	public final static float BASKET_WIDTH = 0.05f;
	public final static float BASKET_HEIGHT = 0.05f;
	/** Width in percent of screen width. */
	public final static float CLIFF_WIDTH = .1f;
	/** Width in percent of screen width. */
	public final static float CRASHED_EGG_WIDTH = 0.058f * 2.55f;
	/** Width in percent of screen height. */
	public final static float CRASHED_EGG_HEIGHT = 0.058f;
	/** Width in percent of screen height. */
	public final static float EGG_STARTING_HEIGHT = 0.52f;
	/** Width in percent of screen width. */
	public final static float PILLOW_WIDTH = .1f; 
	/** Width in percent of screen height. */
	public final static float PILLOW_HEIGHT = .1f; 
	/** Width in percent of screen width. */
	public final static float POWERUP_WIDTH = 0.05f;
	/** Height in percent of screen height. */
	public final static float POWERUP_HEIGHT = 0.05f;
	/** Width in percent of screen width. */
	public final static float ARROW_WIDTH = 0.05f;
	/** Height in percent of screen height. */
	public final static float ARROW_HEIGHT = 0.05f;

	/** Width in percent of screen width. */
	public final static float EGG_X_SPEED = 0.075f;
	public final static float GRAVITATION = 2f;
	public final static float GAMESPEED = 1f;
	
	public static final float TIME_BETWEEN_EGGS = 2f;
	public static final float TIME_BETWEEN_POWERUPS = 5f;
	
	public final static int LIVES = 20;
	public final static int ADD_LIVES = 3;


	/** The standard background colors of the screen. */
	public static final float BG_R = 1.0f, BG_G = 0.7f, BG_B = 0.0f, BG_O = 1.0f;
	
	public static void initV() {
		HEIGHT = Gdx.graphics.getHeight();
		WIDTH = Gdx.graphics.getWidth();
	}
}
