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

/**
 * Settings screen for EggPillow. Contains all settings.
 * @author Johan
 * @version 2014-05-09
 */
public class SettingsScreen implements Screen {

	private SpriteBatch batch;
	private Texture background;
	private EggPillow game;

	private Stage stage;
	private TextButton[] buttons;
	
	// TODO Question: Enum or not?
	private final int BUTTONS_LENGTH;
//	private final int INDEX_MUTE = 0;
//	private final int INDEX_FUN = 1;
//	private final int INDEX_RESET = 2;
//	private final int INDEX_DONE = 4;
//	private final int INDEX_MAP = 3;
	private enum eb {
		INDEX_MUTE(0), INDEX_FUN(1), INDEX_RESET(2), INDEX_DONE(4), INDEX_MAP(3);
		
		private int id;
		
		private eb(int id) {
			this.id = id;
		}
	}

	private Table table;

	private BitmapFont font;
	private String message = "Hello";

	Preferences prefs;

	public static final String PREFERENCE_NAME = "EggPillow preferences";
	public static final String PREFERENCE_MUTED = "muted";
	public static final String PREFERENCE_HIGHSCORE = "highscore";
	public static final String PREFERENCE_FUNMODE = "funmode";
	public static final String PREFERENCE_MAP = "selectedMap";
	
	private final static String BACKGROUND_IMG = "img/settings_background.png"; // TODO make a settingsbackground or just keep it the same as menu/background

	public SettingsScreen(EggPillow g) {
		game = g;
		BUTTONS_LENGTH = eb.values().length;
	}

	@Override
	public void render(float delta) {
		Texture.setEnforcePotImages(false);
		EggPillow.setBackground();

		batch.begin();
		//batch.draw(background, 0, 0, V.WIDTH, V.HEIGHT); // TODO add after new Settingsbackground is drawn
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(batch, message, V.WIDTH / 20, V.HEIGHT / 5);
		batch.end();

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {

	}

	/**
	 * Create buttons and styles for settings.
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
					game.setScreen(new MenuScreen(game));
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
		TextButton buttonFun = new TextButton("", tbstyle );
		TextButton buttonResetHS = new TextButton("Reset highscore", tbstyle);
		TextButton buttonDone = new TextButton("Done", tbstyle);
		TextButton buttonMap = new TextButton("Map : " + prefs.getString(PREFERENCE_MAP, V.GAME_IMAGE_PACK) , tbstyle);
		
		buttons[eb.INDEX_MUTE.id] = buttonMute;
		buttons[eb.INDEX_FUN.id] = buttonFun;
		buttons[eb.INDEX_RESET.id] = buttonResetHS;
		buttons[eb.INDEX_DONE.id] = buttonDone;
		buttons[eb.INDEX_MAP.id] = buttonMap;
		
		setButtonText(eb.INDEX_MUTE.id, "Sound", mute);
		setButtonText(eb.INDEX_FUN.id, "Funmode", prefs.getBoolean(PREFERENCE_FUNMODE, false));

		buttonMute.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				boolean muted = prefs.getBoolean(PREFERENCE_MUTED, false);
				prefs.putBoolean(PREFERENCE_MUTED, !muted);
				prefs.flush();
				setButtonText(eb.INDEX_MUTE.id, "Sound", !muted);
			}
		});

		buttonDone.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				dispose();
				game.setScreen(new MenuScreen(game));
			}
		});

		buttonFun.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				boolean modeState = prefs.getBoolean(PREFERENCE_FUNMODE, false);
				prefs.putBoolean(PREFERENCE_FUNMODE, !modeState);
				prefs.flush();
				setButtonText(eb.INDEX_FUN.id, "Funmode", !modeState);
			}
		});
		
		buttonResetHS.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				prefs.putInteger(PREFERENCE_HIGHSCORE, 0);
				message = "Highscore " + 0;
			}
		});
		
		buttonMap.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				// TODO change map
			}
		});

		
		for (TextButton t : buttons) {
			table.row().pad(5).width(V.WIDTH / 2).height(V.HEIGHT/10);
			table.add(t);
		}
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
