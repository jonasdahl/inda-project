package com.eggpillow.screens;

import inputhandler.InputHandlerMenu;

import com.badlogic.gdx.Gdx;
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
	
	private SpriteBatch batch;
	private Texture background;
	private EggPillow game;
	private InputHandlerMenu inputHandler;
	
	private Stage stage;
	private TextButton buttonMute, buttonTest;
	
	private Table table;
	
	private BitmapFont font;
	private String message = "Hello";
	
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

	@Override
	public void show() {
		batch = new SpriteBatch(); // Where we're going to paint the splash
		background = new Texture("img/settings_background.png");
		inputHandler = new InputHandlerMenu();
		Gdx.input.setInputProcessor(inputHandler);
		
		font = new BitmapFont(Gdx.files.internal("font/EggPillow.fnt"), false);
		table = new Table();
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		stage = new Stage();
		TextButtonStyle tbstyle = new TextButtonStyle();
		tbstyle.font = font;
		buttonMute = new TextButton("Mute", tbstyle);
		buttonTest= new TextButton("Test", tbstyle);
		
		buttonMute.addListener(new ChangeListener() {
		    public void changed (ChangeEvent event, Actor actor) {
		    	message = "Muted";
		    }
		});
		
		buttonTest.addListener(new ChangeListener() {
		    public void changed (ChangeEvent event, Actor actor) {
		    	game.setScreen(game.gameScreen); // TODO MENU
		    }
		});
		
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

	@Override
	public void dispose() {
		// TODO Fix
		//batch.dispose();
		//background.dispose();
		//stage.dispose();
	}

}
