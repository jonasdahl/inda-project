package com.eggpillow.screens;

import inputhandler.InputHandlerGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eggpillow.Pillow;

public class GameScreen implements Screen {
	
	InputHandlerGame inputHandler;
	Pillow pillow;
	SpriteBatch batch;
	Texture background;
	
	@Override
	public void render(float delta) {
		
		batch.begin();
		batch.draw(background, 0, background.getHeight());
		pillow.draw(batch);
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
		background = new Texture("img/game_backgound.png");
		inputHandler = new InputHandlerGame(pillow);
		Gdx.input.setInputProcessor(inputHandler);
		
		
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
		
	}

}
