package com.eggpillow;

import com.badlogic.gdx.Gdx;

/**
 * A class containing a vast number of constants and variables.
 * 
 * @author Johan & Jonas
 * @version 2014-05-15
 */
public class V {
	public static final float FONT_MEDIUM = 1 / 800f;
	public static final float FONT_LARGE = 1 / 600f;

	/** Height of the screen */
	public static int HEIGHT;
	/** Width of the screen */
	public static int WIDTH;

	public static final String TITLE = "Egg Pillow ";
	public static final String VERSION = "1.0.0";
	public static final String PREFERENCE_NAME = "EggPillow preferences";
	public static final String PREFERENCE_MUTED = "muted";
	public static final String PREFERENCE_HIGHSCORE = "highscore";
	public static final String PREFERENCE_FUNMODE = "funmode";
	public static final String PREFERENCE_MAP = "selectedMap";

	public final static String FONT = "font/EggPillow.fnt";
	// Paths to images or texture regions
	// Backgrounds
	public final static String GAME_BACKGROUND_IMAGE = "backgrounds/game_background.png";
	public final static String MENU_BACKGROUND_IMAGE = "backgrounds/menu_background.png";
	public final static String SETTINGS_BACKGROUND_IMAGE = "backgrounds/settings_background.png";
	public final static String SPLASH_BACKGROUND = "backgrounds/splash.png";

	// Sounds
	public final static String BACKGROUND_SOUND = "audio/background.mp3";
	public final static String GAME_SOUND = "audio/game.mp3";
	public final static String BOUNCE_SOUND = "audio/bounce.mp3";

	// gameImg
	public final static String GAME_IMAGE_PACK = "gameImg/EggPillow.pack";
	public final static String EGG_REGION = "game_egg";
	public final static String PILLOW_REGION = "game_pillow";
	public final static String BASKET_REGION = "game_basket";
	public final static String CLIFF_REGION = "game_cliff";
	public final static String CRASHED_EGG_REGION = "game_egg_crashed";
	public final static String POWERUP_REGION = "game_powerup";
	public final static String POWERUP_HEART_REGION = "game_heart";
	public final static String POWERUP_FREEZE_REGION = "game_freeze";
	public final static String ARROW_REGION = "game_triangle";
	public final static String SCORE_BOARD_REGION = "result";
	public final static String HEART_SCORE_REGION = "heart";
	public final static String HEARTDARK_SCORE_REGION = "heart_dark";
	public final static String MAIN_MENU_REGION = "main_menu";

	// Pause
	public final static String PAUS_PACK = "pauseScreen/pausePack.pack";
	public final static String MUTE_REGION = "sound_unmuted_button";
	public final static String MUTE_CROSSED_REGION = "sound_muted_button";
	public final static String RESUME_REGION = "resume";
	public final static String PAUS_EGG_REGION = "game_egg";
	public final static String PAUSE_PILLOW_REGION = "game_pillow";

	// Settings
	public final static String SETTINGS_BUTTON_PACK = "settingsScreen/buttonPack.pack";
	public final static String SETTINGS_MUTE_ON_REGION = "sound_effects_on_button";
	public final static String SETTINGS_MUTE_OFF_REGION = "sound_effects_off_button";
	public final static String SETTINGS_FUN_ON_REGION = "fun_mode_on_button";
	public final static String SETTINGS_FUN_OFF_REGION = "fun_mode_off_button";
	public final static String SETTINGS_RESET_REGION = "reset_highscore_button";
	public final static String SETTINGS_BACK_REGION = "back_button";

	// Menu
	public final static String MENU_BUTTON_PACK = "menuScreen/menuPack.pack";
	public final static String MENU_PLAY_REGION = "play_button";
	public final static String MENU_SETTINGS_REGION = "settings_button";
	public final static String MENU_EXIT_REGION = "exit_button";
	public final static String MENU_MUTED_REGION = "sound_muted_button";
	public final static String MENU_UNMUTED_REGION = "sound_unmuted_button";

	// Sizes
	/** In percent of screen height */
	public final static float EGG_HEIGHT = 0.15f;
	/** In percent of screen width */
	public final static float EGG_WIDTH = 0.058f;
	/** In percent of screen height */
	public final static float CLIFF_HEIGHT = 0.5f;
	/** In percent of screen width */
	public final static float BASKET_WIDTH = 0.05f;
	/** In percent of screen height */
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
	public final static float SCORE_BOARD_WIDTH = 0.2f;
	/** Height in percent of screen height. */
	public final static float SCORE_BOARD_HEIGHT = 0.15f;
	/** Height in percent of screen height. */
	public final static float SCORE_BOARD_MARGIN = 0.05f;
	/** Width in percent of screen width. */
	public final static float LIFE_INDICATOR_WIDTH = 0.2f;
	/** Height in percent of screen height. */
	public final static float LIFE_INDICATOR_HEIGHT = 0.1f;
	/** Height in percent of screen height. */
	public final static float LIFE_INDICATOR_MARGIN = 0.1f;

	// Speeds
	/** Width in percent of screen width. */
	public final static float EGG_X_SPEED = 0.175f;
	public final static float GRAVITATION = 1.5f;
	public final static float GAMESPEED = 1f;

	// Time
	public static final float TIME_BETWEEN_EGGS = 2f;
	public static final float TIME_BETWEEN_POWERUPS = 4f;

	// Stats
	public final static int LIVES = 12;
	public final static int ADD_LIVES = 3;

	/** The standard background colors of the screen. */
	public static final float BG_R = 1.0f, BG_G = 0.7f, BG_B = 0.0f, BG_O = 1.0f;

	public static void initV() {
		HEIGHT = Gdx.graphics.getHeight();
		WIDTH = Gdx.graphics.getWidth();
	}
}
