package com.eggpillow.screens;

import inputhandler.InputHandlerGame;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eggpillow.Cliff;
import com.eggpillow.Egg;
import com.eggpillow.EggPillow;
import com.eggpillow.Pillow;

public class GameScreen implements Screen {
	private InputHandlerGame inputHandler;
	private Random rand;
	private SpriteBatch batch;
	private Texture background;
	private BitmapFont font;
	private EggPillow game;
	private float totalDelta;
	private int freedEggs;
	public static String message = "";
	
	// Sprites
	private Pillow pillow;
	private Cliff cliff;
	private ArrayList<Egg> eggs;
	
	// Constants
	private static final float TIME_BETWEEN_EGGS = 2f;
	private final static float EGG_HEIGHT = 0.15f; // In percent of screen height
	private final static float EGG_WIDTH = 0.058f; // In percent of screen width
	private final static float CLIFF_HEIGHT = 0.5f; // In percent of screen width
	private final static String BACKGROUND_IMAGE = "img/game_background.png";
	
	/**
	 * Constructor for GameScreen
	 * @param g the main Game class as a reference later on
	 */
	public GameScreen(EggPillow g) {
		rand = new Random();
		game = g;
	}
	
	@Override
	public void render(float delta) {
		// Make images scalable
		Texture.setEnforcePotImages(false);
		
		batch.begin();
		
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		pillow.draw(batch, delta);
		cliff.draw(batch);
		int deadEggs = 0;
		for (Egg egg : eggs) {
			egg.draw(batch);
			if (egg.isDead()) {
				deadEggs++;
			}
		}
		
		if (deadEggs >= 3) {
			// TODO Game over
			game.setScreen(game.menuScreen);
		}
		
		// TODO Do it BETTER!
		//message = ( 3 - deadEggs ) + "/3 lives left";
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.setScale(2f);
		font.draw(batch, message, Gdx.graphics.getWidth() * 0.1f, Gdx.graphics.getHeight() * 0.9f);
		
		// Start eggs that should start
		totalDelta += delta;
		if (totalDelta > TIME_BETWEEN_EGGS) {
			if (freedEggs < eggs.size() && eggs.get(freedEggs) != null) {
				eggs.get(freedEggs).start();
				freedEggs++;
			}
			totalDelta = 0;
		}
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		
		// Setup pillow
		pillow = new Pillow(.3f, .95f, -.08f);
		pillow.setSize(Gdx.graphics.getWidth() * 0.1f, Gdx.graphics.getHeight() * 0.1f);
		
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
		pillow.getTexture().dispose();
		background.dispose();
	}
}
