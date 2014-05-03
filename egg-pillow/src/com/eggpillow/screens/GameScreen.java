package com.eggpillow.screens;

import inputhandler.InputHandlerGame;

import java.util.ArrayList;
import java.util.Random;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eggpillow.Cliff;
import com.eggpillow.Egg;
import com.eggpillow.EggPillow;
import com.eggpillow.Pillow;
import com.eggpillow.tween.SpriteBatchAccessor;
import com.eggpillow.tween.TableAccessor;

public class GameScreen implements Screen {
	private InputHandlerGame inputHandler;
	private Random rand;
	private SpriteBatch batch;
	private Texture background;
	private BitmapFont font;
	private EggPillow game;
	private float totalDelta;
	private int freedEggs;
	private TweenManager tweenManager;
	public static String message = "";

	private boolean gamePaused = true;
	private boolean showInstructions;
	private Texture pTexture;

	// Sprites
	private Pillow pillow;
	private Cliff cliff;
	private ArrayList<Egg> eggs;

	// Constants
	private static final float TIME_BETWEEN_EGGS = 2f;
	private final static float EGG_HEIGHT = 0.15f; // In percent of screen
													// height
	private final static float EGG_WIDTH = 0.058f; // In percent of screen width
	private final static float CLIFF_HEIGHT = 0.5f; // In percent of screen
													// width
	private final static String BACKGROUND_IMAGE = "img/game_background.png";

	/**
	 * Constructor for GameScreen
	 * 
	 * @param g
	 *            the main Game class as a reference later on
	 */
	public GameScreen(EggPillow g) {
		rand = new Random();
		tweenManager = new TweenManager();
		Tween.registerAccessor(SpriteBatch.class, new SpriteBatchAccessor());
		game = g;
	}

	@Override
	public void render(float delta) {
		// Make images scalable
		Texture.setEnforcePotImages(false);

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		cliff.draw(batch);
		pillow.draw(batch);

		if (!gamePaused) {
			pillow.update(delta);
			updateEggs(delta);
		}

		for (Egg egg : eggs) {
			egg.draw(batch);
		}

		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.setScale(2f);
		font.draw(batch, message, Gdx.graphics.getWidth() * 0.1f,
				Gdx.graphics.getHeight() * 0.9f);

		if (gamePaused) {
			if (showInstructions) {
				drawInstructions(batch);

			} else {
				drawPaus(batch);
			}
		}

		batch.end();
	}

	/**
	 * Draw the instruction screen to batch.
	 */
	private void drawInstructions(SpriteBatch batch) {
		// TODO
		batch.draw(pTexture, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight(), 0, 0, 1, 1, false, false);
	}

	/**
	 * Draw paus screen to batch.
	 */
	private void drawPaus(SpriteBatch batch) {
		// TODO
		batch.draw(pTexture, 0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight(), 1, 1, 1, 1, false, false);
	}

	/**
	 * Update all eggs and check if they are dead. Start new eggs.
	 * 
	 * @param delta
	 *            Time since last update (seconds)
	 */
	private void updateEggs(float delta) {
		int deadEggs = 0;
		for (Egg egg : eggs) {
			egg.update(delta);
			if (egg.isDead()) {
				deadEggs++;
			}
		}

		if (deadEggs >= 3) {
			// TODO Game over

			updateHighscore(freedEggs); // TODO change to succesfully saved eggs
			// dispose(); TODO Crashes if this line is uncommented
			game.setScreen(game.menuScreen); // TODO highscore screen
		}

		// TODO Do it BETTER!
		message = (3 - deadEggs) + "/3 lives left";

		// Start eggs that should start
		totalDelta += delta;
		if (totalDelta > TIME_BETWEEN_EGGS) {
			if (freedEggs < eggs.size() && eggs.get(freedEggs) != null) {
				eggs.get(freedEggs).start();
				freedEggs++;
			}
			totalDelta = 0;
		}
	}

	/**
	 * Update the highscore in preferences.
	 * 
	 * @param score
	 *            The new score.
	 * @return The new highscore.
	 */
	private int updateHighscore(int score) {
		Preferences prefs = Gdx.app
				.getPreferences(SettingsScreen.PREFERENCE_NAME);
		if (score > prefs.getInteger(SettingsScreen.PREFERENCE_HIGHSCORE, -1)) {
			prefs.putInteger(SettingsScreen.PREFERENCE_HIGHSCORE, score);
			prefs.flush();
			// TODO U GOT A HIGHSCORE
			return score;
		}
		return prefs.getInteger(SettingsScreen.PREFERENCE_HIGHSCORE);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		batch = new SpriteBatch();

		// Setup pillow
		pillow = new Pillow(.3f, .95f, -.08f);
		pillow.setSize(Gdx.graphics.getWidth() * 0.1f,
				Gdx.graphics.getHeight() * 0.1f);

		// Setup cliff
		cliff = new Cliff(CLIFF_HEIGHT);

		// Setup eggs
		eggs = new ArrayList<Egg>();
		for (int i = 0; i < 100; i++) { // Add 100 eggs
			eggs.add(new Egg(pillow, cliff, EGG_WIDTH, EGG_HEIGHT));
		}

		background = new Texture(BACKGROUND_IMAGE);
		inputHandler = new InputHandlerGame(pillow, game);
		Gdx.input.setInputProcessor(inputHandler);
		font = new BitmapFont();

		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(0f, 0f, 0f, 0.5f);
		pixmap.fill();
		pTexture = new Texture(pixmap);
		pixmap.dispose();

		Tween.set(batch, TableAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(batch, TableAccessor.ALPHA, .25f).target(1).start(tweenManager);
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

	// TODO make sure everything is disposed.
	@Override
	public void dispose() {
		batch.dispose();
		pillow.getTexture().dispose();
		background.dispose();
		pTexture.dispose();
	}

	public void pauseGame() {
		gamePaused = true;
	}

	public void unPauseGame() {
		if (showInstructions) {
			showInstructions = false;
		}
		gamePaused = false;
	}

	public boolean isPaused() {
		return gamePaused;
	}
}
