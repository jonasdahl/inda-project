package com.eggpillow.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.eggpillow.EggPillow;
import com.eggpillow.V;

public class SettingsScreen implements Screen {

	private SpriteBatch batch;
	private Texture background;
	private EggPillow game;

	private Stage stage;
	private TextButton[] buttons;
	private final int INDEX_MUTE = 0;
	private final int INDEX_FUN = 1;
	private final int INDEX_RESET = 2;
	private final int INDEX_DONE = 3;
	private final int BUTTONS_LENGTH = 4;

	private Table table;

	private BitmapFont font;
	private String message = "Hello";

	Preferences prefs;

	public static final String PREFERENCE_NAME = "EggPillow preferences";
	public static final String PREFERENCE_MUTED = "muted";
	public static final String PREFERENCE_HIGHSCORE = "highscore";
	public static final String PREFERENCE_FUNMODE = "funmode";
	
	private final static String BACKGROUND_IMG = "img/settings_background.png"; // TODO make a settingsbackground or just keep it the same as menu/background

	public SettingsScreen(EggPillow g) {
		game = g;
	}

	@Override
	public void render(float delta) {
		Texture.setEnforcePotImages(false);
		EggPillow.setBackground();

		batch.begin();
		//batch.draw(background, 0, 0, V.WIDTH, V.HEIGHT);
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(batch, message, V.WIDTH / 20, V.HEIGHT / 5);
		batch.end();

		//Table.drawDebug(stage); // TODO remove

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
		background = new Texture(BACKGROUND_IMG);
		font = new BitmapFont(Gdx.files.internal(V.FONT), false);
		font.setScale(V.HEIGHT / V.FONT_MEDIUM);
		table = new Table();
		table.setBounds(0, 0, V.WIDTH, V.HEIGHT);
		
		prefs = Gdx.app.getPreferences(PREFERENCE_NAME);
		
		boolean mute = prefs.getBoolean(PREFERENCE_MUTED, false);
		message = "Highscore " + prefs.getInteger(PREFERENCE_HIGHSCORE, -1);

		stage = new Stage() {
			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
					dispose();
					game.setScreen(game.menuScreen);
				}
				return false;
			}
		};
		buttons = new TextButton[BUTTONS_LENGTH];
		TextButtonStyle tbstyle = new TextButtonStyle();
		Skin skin = new Skin();
		TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("ui/settingsButton.pack"));
        skin.addRegions(buttonAtlas);
		tbstyle.font = font;
		tbstyle.down = skin.getDrawable("buttonDown");
		tbstyle.up = skin.getDrawable("ButtonUp");
		TextButton buttonMute = new TextButton("", tbstyle);
		TextButton buttonFun = new TextButton("Fun", tbstyle );
		TextButton buttonResetHS = new TextButton("Reset highscore", tbstyle);
		TextButton buttonDone = new TextButton("Done", tbstyle);
		
		buttons[INDEX_MUTE] = buttonMute;
		buttons[INDEX_FUN] = buttonFun;
		buttons[INDEX_RESET] = buttonResetHS;
		buttons[INDEX_DONE] = buttonDone;
		
		setButtonText(INDEX_MUTE, "Sound", mute);
		setButtonText(INDEX_FUN, "Funmode", prefs.getBoolean(PREFERENCE_FUNMODE));

		buttonMute.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				boolean muted = prefs.getBoolean(PREFERENCE_MUTED, false);
				prefs.putBoolean(PREFERENCE_MUTED, !muted);
				prefs.flush();
				setButtonText(INDEX_MUTE, "Sound", !muted);
			}
		});

		buttonDone.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				dispose();
				game.setScreen(game.menuScreen);
			}
		});

		buttonFun.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				boolean modeState = prefs.getBoolean(PREFERENCE_FUNMODE, false);
				prefs.putBoolean(PREFERENCE_FUNMODE, !modeState);
				prefs.flush();
				setButtonText(INDEX_FUN, "Funmode", !modeState);
			}
		});
		
		buttonResetHS.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				prefs.putInteger(PREFERENCE_HIGHSCORE, 0);
				message = "Highscore " + 0;
			}
		});

		
		for (TextButton t : buttons) {
			table.row().pad(5).width(V.WIDTH / 2).height(V.HEIGHT/10);
			table.add(t);
		}
		table.debug(); // TODO remove
		stage.addActor(table);

		Gdx.input.setInputProcessor(stage);
	}
	
	/**
	 * Set the text on buttons[index] to text + on/off
	 */
	private void setButtonText(int index, String text, boolean on) {
		if (on) {
			buttons[index].setText(text + " : " + "on");
		} else {
			buttons[index].setText(text + " : " + "off");
		}
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
		background.dispose();
		stage.dispose();
		font.dispose();
	}

}
