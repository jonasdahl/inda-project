package com.eggpillow.screens;

import inputhandler.InputHandlerMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.eggpillow.EggPillow;

public class menuScreen implements Screen{
	
	private EggPillow game;
	
	private SpriteBatch batch;
	private Texture background;
	private InputHandlerMenu inputHandler;
	
	private Stage stage;
	private TextButton buttonStart, buttonSettings, buttonExit;
	private Label title;
	
	private Table table;
	
	private BitmapFont font;
	private String message = "Hello";
	
	public menuScreen(EggPillow g) {
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
		background = new Texture("img/menu_background.png");
		inputHandler = new InputHandlerMenu();
		Gdx.input.setInputProcessor(inputHandler);
		
		font = new BitmapFont(Gdx.files.internal("font/EggPillow.fnt"), false);
		table = new Table();
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage();
		title = new Label(EggPillow.TITLE, new LabelStyle(font, Color.BLACK));
		
		TextButtonStyle tbstyle = new TextButtonStyle();
		tbstyle.font = font;
		buttonStart = new TextButton("Start", tbstyle);
		buttonSettings = new TextButton("Settigns", tbstyle);
		buttonExit = new TextButton("Exit", tbstyle);
		buttonStart.addListener(new ChangeListener() {
		    public void changed (ChangeEvent event, Actor actor) {
		    	game.setScreen(game.gameScreen);
		    }
		});
		
		buttonSettings.addListener(new ChangeListener() {
		    public void changed (ChangeEvent event, Actor actor) {
		    	game.setScreen(game.settingsScreen); 
		    }
		});
		buttonExit.addListener(new ChangeListener() {
		    public void changed (ChangeEvent event, Actor actor) {
		    	message = "EXIT"; // TODO Exit game
		    }
		});
		table.add(title);
		table.row();
		table.add(buttonStart);
		table.row();
		table.add(buttonSettings);
		table.row();
		table.add(buttonExit);
		stage.addActor(table);
		
		Gdx.input.setInputProcessor(stage);
		
		table.debug(); // TODO remove
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
		// TODO fix
		// font.dispose()
	}

}
