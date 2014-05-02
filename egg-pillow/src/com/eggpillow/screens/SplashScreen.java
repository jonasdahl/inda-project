package com.eggpillow.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eggpillow.EggPillow;
import com.eggpillow.tween.SpriteAccessor;

public class SplashScreen implements Screen {
	private final static float FADE_SPEED = 1.5f;
	private final static float DELAY = 3.0f;
	
	private Sprite splash;
	private SpriteBatch batch;
	private TweenManager tweenManager;
	private EggPillow game;
	
	
	public SplashScreen(EggPillow g) {
		game = g;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(EggPillow.BG_R, EggPillow.BG_G, EggPillow.BG_B, EggPillow.BG_O);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		tweenManager.update(delta);
		
		batch.begin();
		splash.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		batch = new SpriteBatch(); // Where we're going to paint the splash
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Texture texture = new Texture("img/splash.png"); // The texture of the splash
		splash = new Sprite(texture); // The splash is wrapped in a Sprite
		splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(splash, SpriteAccessor.ALPHA, FADE_SPEED).target(1).start(tweenManager);
		Tween.to(splash, SpriteAccessor.ALPHA, FADE_SPEED).delay(DELAY).target(0).setCallback(new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				dispose();
				// TODO Start menu screen
				//game.setScreen(game.gameScreen);	
				game.setScreen(game.settingsScreen);
			}
		}).start(tweenManager);
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
		splash.getTexture().dispose();
	}	
}