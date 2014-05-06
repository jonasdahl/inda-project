package com.eggpillow.screens;

import inputhandler.InputHandlerGame;

import java.util.ArrayList;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.eggpillow.Basket;
import com.eggpillow.Cliff;
import com.eggpillow.Egg;
import com.eggpillow.EggPillow;
import com.eggpillow.Pillow;
import com.eggpillow.Touchable;
import com.eggpillow.V;
import com.eggpillow.tween.SpriteBatchAccessor;
import com.eggpillow.tween.TableAccessor;

public class GameScreen implements Screen {
	private InputHandlerGame inputHandler;
	private SpriteBatch batch;
	private Texture background;
	private BitmapFont font;
	private EggPillow game;
	private float totalDelta;
	private int freedEggs;
	private TweenManager tweenManager;
	private ArrayList<Touchable> touchables;
	public static String message = "";

	private boolean gamePaused = true;
	private boolean showInstructions = true;
	private Texture pTexture;
	private boolean gameOver = false;
	private boolean newHighscore = false;

	private TextureAtlas atlas;
	private AtlasRegion eggRegion;
	private AtlasRegion pillowRegion;

	// Sprites
	private Pillow pillow;
	private Cliff cliff;
	private ArrayList<Egg> eggs;
	private Basket basket;

	// Constants
	private static final float TIME_BETWEEN_EGGS = 2f;
	/** In percent of screen height */
	private final static float EGG_HEIGHT = 0.15f;
	/** In percent of screen width */
	private final static float EGG_WIDTH = 0.058f;
	/** In percent of screen width */
	private final static float CLIFF_HEIGHT = 0.5f;

	private final static int LIVES = 40;
	private final static String BACKGROUND_IMAGE = "img/game_background.png";

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
	}

	@Override
	public void render(float delta) {
		// Make images scalable
		Texture.setEnforcePotImages(false);

		batch.begin();
		batch.draw(background, 0, 0, V.WIDTH, V.HEIGHT);
		cliff.draw(batch);
		pillow.draw(batch);

		if (!gamePaused) {
			pillow.update(delta);
			updateEggs(delta);
		}

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
				drawInstructions(batch);
			} else if (gameOver) {
				drawGameOver(batch, freedEggs, newHighscore);
			} else {
				drawPaus(batch);

			}
		}

		batch.end();
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

		if (deadEggs >= LIVES) {
			newHighscore = updateHighscore(freedEggs); // TODO change to
														// succesfully saved
														// eggs
			gameOver = true;
			gamePaused = true;
		}

		// TODO Do it BETTER!
		message = (LIVES - deadEggs) + "/" + LIVES + "  lives left";

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

		// TODO textureAtlas or texture
		atlas = new TextureAtlas(Gdx.files.internal("gameImg/EggPillow.pack"));
		eggRegion = atlas.findRegion("game_egg");
		pillowRegion = atlas.findRegion("game_pillow");

		touchables = new ArrayList<Touchable>();
		// Setup pillow
		pillow = new Pillow(touchables, -.08f, atlas);

		// Setup cliff
		cliff = new Cliff(CLIFF_HEIGHT, atlas);
		basket = new Basket(EGG_WIDTH, EGG_HEIGHT, atlas);

		touchables.add(pillow);
		touchables.add(cliff);
		touchables.add(basket);

		// Setup eggs
		freedEggs = 0;
		eggs = new ArrayList<Egg>();
		for (int i = 0; i < 100; i++) { // Add 100 eggs // TODO bad
										// implementation ends after 100eggs.
			eggs.add(new Egg(this, EGG_WIDTH, EGG_HEIGHT, atlas));
		}

		// background = new Texture(BACKGROUND_IMAGE);
		background = new Texture(BACKGROUND_IMAGE);
		inputHandler = new InputHandlerGame(pillow, game);
		Gdx.input.setInputProcessor(inputHandler);
		font = new BitmapFont(Gdx.files.internal("font/EggPillow.fnt"), false);

		Tween.set(batch, TableAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(batch, TableAccessor.ALPHA, .25f).target(1).start(tweenManager);

		showInstructions = true;
		createInstructionTexture();
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

	public boolean gameOver() {
		return gameOver;
	}

	/**
	 * Draw the instruction screen to batch.
	 */
	private void drawInstructions(SpriteBatch batch) {
		batch.draw(pTexture, 0, 0);
		batch.draw(eggRegion, V.WIDTH / 2, V.HEIGHT * 3 / 4, V.WIDTH * EGG_WIDTH, V.HEIGHT * EGG_HEIGHT);
		batch.draw(pillowRegion, V.WIDTH / 2, V.HEIGHT / 4, V.WIDTH * 0.1f, V.HEIGHT * 0.1f);

		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.setScale(V.HEIGHT / V.FONT_BIG);
		font.draw(batch, "Here is the pillow", V.WIDTH / 2 + V.WIDTH * 0.15f, V.HEIGHT / 4);
		font.draw(batch, "Here is egg", V.WIDTH / 2 + V.WIDTH * 0.15f, V.HEIGHT * 3 / 4);
	}

	private void drawGameOver(SpriteBatch batch, int score, boolean newHS) {
		batch.draw(pTexture, 0f, 0f, (float) V.WIDTH, (float) V.HEIGHT, 0, 0, 1, 1, false, false);
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);

		font.setScale(V.HEIGHT / V.FONT_MEDIUM);
		font.draw(batch, "Game over", V.WIDTH / 2 * 0.6f, V.HEIGHT / 2);
		font.setScale(V.HEIGHT / V.FONT_SMALL);
		if (newHS) {
			font.draw(batch, "Congratulations u got a new Highscore " + score, V.WIDTH / 2 * 0.4f, V.HEIGHT / 3);
		} else {
			font.draw(batch, "Your score: " + freedEggs, V.WIDTH / 2 * 0.6f, V.HEIGHT / 3);
		}
	}

	/**
	 * Draw paus screen to batch.
	 */
	private void drawPaus(SpriteBatch batch) { // TODO change name
		font.setColor(Color.BLACK);
		font.setScale(V.HEIGHT / V.FONT_BIG);
		batch.draw(pTexture, 0f, 0f, (float) V.WIDTH, (float) V.HEIGHT, 0, 0, 1, 1, false, false);
		font.draw(batch, "Touch the screen to resume your game", V.WIDTH / 2 * 0.6f, V.HEIGHT / 2);
		font.draw(batch, "Score: " + freedEggs, V.WIDTH / 2 * 0.6f, V.HEIGHT / 2 * 0.8f);
	}

	/**
	 * Creates a texture with circles to show the pillow and egg.
	 */
	private void createInstructionTexture() {
		Pixmap pixmap = new Pixmap(V.WIDTH, V.HEIGHT, Pixmap.Format.RGBA8888);
		pixmap.setColor(0f, 0f, 0f, 0.5f);
		pixmap.fill();
		pixmap.setColor(Color.RED);
		pixmap.drawCircle((int) (V.WIDTH / 2 + V.WIDTH * EGG_WIDTH / 2), (int) (V.HEIGHT / 4 - V.HEIGHT * EGG_HEIGHT
				/ 2), (int) (V.WIDTH * EGG_WIDTH * 1.2f));
		pixmap.drawCircle(V.WIDTH / 2 + (int) pillow.getWidth() / 2, V.HEIGHT * 3 / 4 - (int) pillow.getHeight() / 2,
				(int) (pillow.getWidth() / 2 * 1.6f));
		pTexture = new Texture(pixmap);
		pixmap.dispose();
	}

	/**
	 * Gets all touchables except off eggs.
	 * 
	 * @return get all other touchables than eggs (like cliff and pillow)
	 */
	public ArrayList<Touchable> getTouchables() {
		return touchables;
	}
}
