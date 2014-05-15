package com.eggpillow.screens;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.eggpillow.EggPillow;
import com.eggpillow.Stats;
import com.eggpillow.V;
import com.eggpillow.inputhandler.InputHandlerGame;
import com.eggpillow.sprites.Basket;
import com.eggpillow.sprites.Cliff;
import com.eggpillow.sprites.Egg;
import com.eggpillow.sprites.Pillow;
import com.eggpillow.sprites.PowerFreeze;
import com.eggpillow.sprites.PowerHeart;
import com.eggpillow.sprites.PowerUp;
import com.eggpillow.sprites.Touchable;
import com.eggpillow.tween.SpriteBatchAccessor;
import com.eggpillow.tween.TableAccessor;

/**
 * Gamescreen for EggPillow.
 * 
 * @author Johan & Jonas
 * @version 2014-05-09
 */
public class GameScreen implements Screen {
	// Dispose
	private SpriteBatch batch;
	private Texture background;
	private BitmapFont font;
	private TextureAtlas atlas;
	private PauseWindow pauseScreen;

	// Gameobjects
	private InputHandlerGame inputHandler;
	private EggPillow game;
	private TweenManager tweenManager;
	public static String message = "";
	private Random random;
	
	// In game variables
	private float totalDeltaPower;
	private float randomTimePower;
	private float totalDeltaEgg;
	private boolean gamePaused = true;
	private boolean showInstructions = true;
	private boolean gameOver = false;
	private boolean newHighscore = false;
	
	// Sprites
	private ArrayList<Touchable> touchables;
	private Pillow pillow;
	private Cliff cliff;
	private Basket basket;
	private Stats stats;
	private ArrayList<PowerUp> powerups;
	private Queue<PowerUp> removePowerups;
	private ArrayList<Egg> eggs;
	private Queue<Egg> removeEggs;


	/**
	 * Constructor for GameScreen
	 * 
	 * @param g
	 *            the main Game class as a reference later on
	 */
	public GameScreen(EggPillow g) {
		tweenManager = new TweenManager();
		Tween.registerAccessor(SpriteBatch.class, new SpriteBatchAccessor());
		game = g;
		random = new Random();
	}

	@Override
	public void render(float delta) {
		batch.begin();
		batch.draw(background, 0, 0, V.WIDTH, V.HEIGHT);

		// Update gamestuff
		if (!gamePaused) {
			stats.update(delta);
			float gameSpeedDelta = delta * stats.getGameSpeed();
			inputHandler.update(delta);
			pillow.update(delta, gameSpeedDelta);
			updatePowerups(delta, gameSpeedDelta, pillow);
			updateEggs(delta, gameSpeedDelta);

			// Check for gameover
			if (stats.getLives() <= 0) {
				pauseGame();
				newHighscore = updateHighscore(stats.getScore());
				gameOver = true;
			}
		}

		// Draw all sprites
		for (PowerUp power : powerups) {
			power.draw(batch);
		}
		cliff.draw(batch);
		pillow.draw(batch);
		stats.draw(batch);
		
		for (Egg egg : eggs) {
			egg.draw(batch);
		}
		basket.draw(batch);
		
		batch.flush();
		if (gamePaused) {
			if (showInstructions) {
				pauseScreen.drawInstructions(batch);
			} else if (gameOver) {
				pauseScreen.drawGameOver(batch, stats.getScore(), newHighscore);
			} else {
				pauseScreen.drawPause(batch, stats.getScore());
			}
		}
		batch.end();
	}

	/**
	 * Update all powerUps and check if they are dead/taken. Start new powerUps.
	 * 
	 * @param delta
	 *            Time since last update (seconds)
	 * @param gameSpeedDelta
	 *            Game-time since last update (seconds)
	 */
	private void updatePowerups(float delta, float gameSpeedDelta, Pillow pillow) {
		for (PowerUp power : powerups) {
			power.update(gameSpeedDelta, pillow);
			if (power.isDead()) {
				removePowerups.add(power);
			}
		}
		for (PowerUp power : removePowerups) {
			powerups.remove(power);
		}

		// Start powerups that should start
		totalDeltaPower += gameSpeedDelta;
		if (totalDeltaPower > V.TIME_BETWEEN_POWERUPS + randomTimePower) {
			int id = random.nextInt(2);
			switch (id) {
			case 0:
				powerups.add(new PowerHeart(atlas, random.nextInt((int) (V.WIDTH
						- ((V.CLIFF_WIDTH + V.BASKET_WIDTH) * V.WIDTH) + V.CLIFF_WIDTH * V.WIDTH)), stats));
				break;
			case 1:
				powerups.add(new PowerFreeze(atlas, random.nextInt((int) (V.WIDTH
						- ((V.CLIFF_WIDTH + V.BASKET_WIDTH) * V.WIDTH) + V.CLIFF_WIDTH * V.WIDTH)), stats));
				break;
			}
			;
			totalDeltaPower = 0;
			randomTimePower = random.nextInt(7);
		}

	}

	/**
	 * Update all eggs and check if they are dead. Start new eggs.
	 * 
	 * @param delta
	 *            Time since last update (seconds)
	 * @param gameSpeedDelta
	 *            Game-time since last update (seconds)
	 */
	private void updateEggs(float delta, float gameSpeedDelta) {
		for (Egg egg : eggs) {
			egg.update(gameSpeedDelta, stats.funMode());
			if (egg.isDead() && !egg.wasDeadLastTime()) {
				stats.removeLives();
			} else if (egg.hasStopped() && !removeEggs.contains(egg)) {
				removeEggs.add(egg);
				stats.addScore();
			}
		}
		while (removeEggs.size() > 3) {
			eggs.remove(removeEggs.poll());
		}

		//message = stats.getLives() + "/" + stats.startLives() + " lives left";

		// Start eggs that should start
		totalDeltaEgg += gameSpeedDelta;
		if (totalDeltaEgg > V.TIME_BETWEEN_EGGS) {
			Egg newEgg = new Egg(this, V.EGG_WIDTH, V.EGG_HEIGHT, atlas);
			newEgg.start();
			eggs.add(newEgg);
			// }
			totalDeltaEgg = 0;
		}
	}

	/**
	 * Update the highscore in preferences.
	 * 
	 * @param score
	 *            The new score.
	 * @return The new highscore.
	 */
	private boolean updateHighscore(int score) {
		Preferences prefs = Gdx.app.getPreferences(SettingsScreen.PREFERENCE_NAME);
		if (score > prefs.getInteger(SettingsScreen.PREFERENCE_HIGHSCORE, -1)) {
			prefs.putInteger(SettingsScreen.PREFERENCE_HIGHSCORE, score);
			prefs.flush();
			return true;
		}
		return false;
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		batch = new SpriteBatch();

		atlas = new TextureAtlas(Gdx.files.internal(V.GAME_IMAGE_PACK));
		
		stats = new Stats(atlas, V.LIVES, V.GAMESPEED);

		touchables = new ArrayList<Touchable>();
		// Setup pillow
		if (stats.funMode()) {
			pillow = new Pillow(touchables, -.25f, V.CLIFF_WIDTH, atlas);
		} else {
			pillow = new Pillow(touchables, .25f, V.CLIFF_WIDTH, atlas);
		}
		inputHandler = new InputHandlerGame(game, this, pillow);

		// Setup cliff
		cliff = new Cliff(V.CLIFF_HEIGHT, atlas);
		basket = new Basket(V.EGG_WIDTH, V.EGG_HEIGHT, atlas);

		touchables.add(pillow);
		touchables.add(cliff);
		touchables.add(basket);

		// Setup powerups
		removePowerups = new LinkedList<PowerUp>();
		powerups = new ArrayList<PowerUp>();

		// Setup eggs
		removeEggs = new LinkedList<Egg>();
		eggs = new ArrayList<Egg>();

		background = new Texture(V.GAME_BACKGROUND_IMAGE);
		font = new BitmapFont(Gdx.files.internal(V.FONT), false);
		Gdx.input.setInputProcessor(inputHandler);

		Tween.set(batch, TableAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(batch, TableAccessor.ALPHA, .25f).target(1).start(tweenManager);

		showInstructions = true;
		pauseScreen = new PauseWindow(font, this);
		pauseScreen.setAsInputListener();
	}
	
	public void end() {
		game.setScreen(new MenuScreen(game));
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		batch.dispose();
		background.dispose();
		font.dispose();
		atlas.dispose();
		pauseScreen.dispose();
	}

	/**
	 * Pauses game.
	 */
	public void pauseGame() {
		gamePaused = true;
		pauseScreen.setAsInputListener();
	}

	/**
	 * Unpauses game if it is possible, ie when no instructions are actively
	 * show.
	 */
	public void unPauseGame() {
		Gdx.input.setInputProcessor(inputHandler);
		if (showInstructions) {
			showInstructions = false;
		}
		gamePaused = false;
	}

	/**
	 * Checks if game is paused.
	 * 
	 * @return true if game is paused, false if not
	 */
	public boolean isPaused() {
		return gamePaused;
	}

	/**
	 * Checks if game is over.
	 * 
	 * @return true if game is over, false if not
	 */
	public boolean gameIsOver() {
		return gameOver;
	}

	/**
	 * Gets all touchables in the screen except of eggs.
	 * 
	 * @return get all other touchables than eggs (like cliff and pillow)
	 */
	public ArrayList<Touchable> getTouchables() {
		return touchables;
	}
}
