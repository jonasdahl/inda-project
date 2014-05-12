package com.eggpillow.screens;

import inputhandler.InputHandlerGame;

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
 * @author Johan & Jonas
 * @version 2014-05-09
 */
public class GameScreen implements Screen {
	private InputHandlerGame inputHandler;
	private SpriteBatch batch;
	private Texture background;
	private BitmapFont font;
	private EggPillow game;
	private TweenManager tweenManager;
	private ArrayList<Touchable> touchables;
	public static String message = "";
	private float totalDeltaPower;
	private float randomTimePower;
	private float totalDeltaEgg;
	private int freedEggs;
	private boolean gamePaused = true;
	private boolean showInstructions = true;
	private boolean gameOver = false;
	private boolean newHighscore = false;
	private Random random;
	private TextureAtlas atlas;
	// Sprites
	private Pillow pillow;
	private Cliff cliff;
	private ArrayList<Egg> eggs;
	private Basket basket;
	private ArrayList<PowerUp> powerups;
	private Stats stats;
	Queue<PowerUp> removePowerups;
	Queue<Egg> removeEggs;
	
	private PauseWindow pauseScreen;

	/**
	 * Constructor for GameScreen
	 * 
	 * @param g
	 *            the main Game class as a reference later on
	 */
	public GameScreen(EggPillow g) {
		tweenManager = new TweenManager();
		Tween.registerAccessor(SpriteBatch.class, new SpriteBatchAccessor());
		stats = new Stats(V.LIVES, V.GAMESPEED);
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
				gameOver = true;
			}
		}
		
		// Draw all sprites
		for (PowerUp power : powerups) {
			power.draw(batch);
		}
		cliff.draw(batch);
		pillow.draw(batch);

		for (Egg egg : eggs) {
			egg.draw(batch);
		}
		basket.draw(batch);

		font.setColor(1.0f, 1.0f, 0f, 1.0f);
		font.setScale(V.HEIGHT / V.FONT_MEDIUM);
		font.draw(batch, message, V.WIDTH * 0.1f, V.HEIGHT * 0.9f);
		font.draw(batch, "Score: " + freedEggs, V.WIDTH * 2.5f / 4, V.HEIGHT * 9 / 10);

		if (gamePaused) {
			if (showInstructions) {
				pauseScreen.drawInstructions(batch);
			} else if (gameOver) {
				pauseScreen.drawGameOver(batch, freedEggs, newHighscore);
			} else {
				pauseScreen.drawPause(batch, freedEggs);
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
		int deadEggs = 0;
		for (Egg egg : eggs) {
			egg.update(gameSpeedDelta, stats.funMode());
			if (egg.isDead() && !egg.wasDeadLastTime()) {
				stats.deadEgg();
			} else if (egg.hasStopped() && !removeEggs.contains(egg)) {
				removeEggs.add(egg);
				freedEggs++;
			}
		}
		while (removeEggs.size() > 3) {
			eggs.remove(removeEggs.poll());
		}

		if (deadEggs >= stats.startLives()) {
			newHighscore = updateHighscore(freedEggs); 
			gameOver = true;
			gamePaused = true;
		}

		message = stats.getLives() + "/" + stats.startLives() + " lives left";

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

		touchables = new ArrayList<Touchable>();
		// Setup pillow
		if (stats.funMode()) {
			pillow = new Pillow(touchables, -.25f, V.CLIFF_WIDTH, atlas);
		} else {			
			pillow = new Pillow(touchables, .25f, V.CLIFF_WIDTH, atlas);
		}
		inputHandler = new InputHandlerGame(game, this, pillow); 
		// TODO Menu-fix

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
		freedEggs = 0;
		eggs = new ArrayList<Egg>();

		background = new Texture(V.GAME_BACKGROUND_IMAGE);
		Gdx.input.setInputProcessor(inputHandler);
		font = new BitmapFont(Gdx.files.internal("font/EggPillow.fnt"), false);

		Tween.set(batch, TableAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(batch, TableAccessor.ALPHA, .25f).target(1).start(tweenManager);

		showInstructions = true;
		pauseScreen = new PauseWindow(font, atlas);
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
		// TODO make sure everything is disposed.
		batch.dispose();
		pillow.getTexture().dispose();
		background.dispose();
		pauseScreen.dispose();
	}

	/**
	 * Pauses game.
	 */
	public void pauseGame() {
		gamePaused = true;
	}

	/**
	 * Unpauses game if it is possible, ie when no instructions are actively
	 * show.
	 */
	public void unPauseGame() {
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
