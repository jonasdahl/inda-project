package com.eggpillow.screens;

import inputhandler.InputHandlerGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
	
	InputHandlerGame inputHandler;
	
	public GameScreen() {
		inputHandler = new InputHandlerGame();
		Gdx.input.setInputProcessor(inputHandler);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		// Render eggs and pillow
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
