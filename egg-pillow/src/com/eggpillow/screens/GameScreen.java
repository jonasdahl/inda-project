package com.eggpillow.screens;

import java.util.ArrayList;

import inputhandler.InputHandlerGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eggpillow.Egg;
import com.eggpillow.EggPillow;
import com.eggpillow.Pillow;

public class GameScreen implements Screen {
	InputHandlerGame inputHandler;
	Pillow pillow;
	SpriteBatch batch;
	Texture background;
	BitmapFont font;
	EggPillow game;
	ArrayList<Egg> eggs;
	
	public static String message = "";
	
	public GameScreen(EggPillow g) {
		game = g;
		eggs = new ArrayList<Egg>();
		eggs.add(new Egg());
	}
	
	@Override
	public void render(float delta) {
		Texture.setEnforcePotImages(false);
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		pillow.draw(batch);
		
		//TODO For testing prints <var>message</var> on screen
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(batch, message, 25, 160);
		
		for (Egg egg : eggs) {
			egg.draw(batch);
		}
		//TODO Draw eggs here
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		batch = new SpriteBatch(); // Where we're going to paint the splash
		pillow = new Pillow();
		background = new Texture("img/game_background.png");
		inputHandler = new InputHandlerGame(pillow);
		Gdx.input.setInputProcessor(inputHandler);
		font = new BitmapFont();
		
		eggs.get(0).start();
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
