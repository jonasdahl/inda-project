package com.eggpillow.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.eggpillow.EggPillow;

public class SettingsScreen implements Screen {
	
	public static final String PREFERENCE_NAME = "EggPillow preferences";
	public static final String PREFERENCE_MUTED = "muted";
	public static final String PREFERENCE_HIGHSCORE = "highscore";
	
	private SpriteBatch batch;
	private Texture background;
	private EggPillow game;
	
	private Stage stage;
	private TextButton buttonMute, buttonTest;
	
	private Table table;
	
	private BitmapFont font;
	private String message = "Hello";
	
	Preferences prefs;
	
	public SettingsScreen(EggPillow g) {
		game = g;
	}

	@Override
	public void render(float delta) {
		Texture.setEnforcePotImages(false);
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(background, 0, 0);
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(batch, message, 25, 160);
		batch.end();
		
		Table.drawDebug(stage); // TODO remove
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
	}
	
	/**
	 * A mess of buttons and styles. TODO sort
	 */
	@Override
	public void show() {
		batch = new SpriteBatch();
		background = new Texture("img/settings_background.png");
		
		font = new BitmapFont(Gdx.files.internal("font/EggPillow.fnt"), false);
		font.setScale(5.0f);
		table = new Table();
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		prefs = Gdx.app.getPreferences(PREFERENCE_NAME);
		boolean mute = prefs.getBoolean(PREFERENCE_MUTED, false);
		message = "Highscore " + prefs.getInteger(PREFERENCE_HIGHSCORE, -1);
		
		stage = new Stage() {
			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Keys.BACK) {
					dispose();
					game.setScreen(game.menuScreen);
				}
				return false;
			}
		};
		TextButtonStyle tbstyle = new TextButtonStyle();
		tbstyle.font = font;
		buttonMute = new TextButton("Set mute = " + !mute, tbstyle);
		buttonTest = new TextButton("Back", tbstyle);
		
		buttonMute.addListener(new ChangeListener() {
		    public void changed (ChangeEvent event, Actor actor) {
		    	boolean muted = prefs.getBoolean(PREFERENCE_MUTED, false);
		    	prefs.putBoolean(PREFERENCE_MUTED, !muted);
		    	buttonMute.setText("Set mute = " + muted);
		    	prefs.flush();
		    }
		});
		
		buttonTest.addListener(new ChangeListener() {
		    public void changed (ChangeEvent event, Actor actor) {
		    	dispose();
		    	game.setScreen(game.menuScreen);
		    }
		});
		table.pad(20);
		table.add(buttonMute);
		table.add(buttonTest);
		table.debug(); // TODO remove
		stage.addActor(table);
		
		
		Gdx.input.setInputProcessor(stage);
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
	
	// TODOD make sure everything is disposed.
	@Override
	public void dispose() {
		batch.dispose();
		background.dispose();
		stage.dispose();
		font.dispose();
	}

}
