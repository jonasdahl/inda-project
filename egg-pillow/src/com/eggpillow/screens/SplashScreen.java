package com.eggpillow.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eggpillow.EggPillow;
import com.eggpillow.tween.SpriteAccessor;

public class SplashScreen implements Screen {
	private final static float FADE_SPEED = 1.5f; 
	private final static float DELAY = 1.5f; 

	private Sprite splash;
	private SpriteBatch batch;
	private TweenManager tweenManager;
	private EggPillow game;

	public SplashScreen(EggPillow g) {
		game = g;
	}

	@Override
	public void render(float delta) {
		EggPillow.setBackground();
		tweenManager.update(delta);

		batch.begin();
		splash.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	/**
	 * Initialises splash screen and the splash animation. Starts menu screen
	 * when the animation is done.
	 */
	@Override
	public void show() {
		batch = new SpriteBatch(); // Where we're going to paint the splash
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Texture texture = new Texture("img/splash.png"); // The texture of the
															// splash
		splash = new Sprite(texture); // The splash is wrapped in a Sprite
		splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(splash, SpriteAccessor.ALPHA, FADE_SPEED).target(1)
				.start(tweenManager);
		Tween.to(splash, SpriteAccessor.ALPHA, FADE_SPEED).delay(DELAY)
				.target(0).setCallback(new TweenCallback() {
					@Override
					public void onEvent(int type, BaseTween<?> source) {
						game.setScreen(game.menuScreen);
						// TODO Chrashes somewhere here if user taps screen on
						// splashscreen // Johan kan inte reprodusera denna bug
						// :(
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

	// TODO make sure everything is disposed.
	@Override
	public void dispose() {
		batch.dispose();
		splash.getTexture().dispose();
	}
}