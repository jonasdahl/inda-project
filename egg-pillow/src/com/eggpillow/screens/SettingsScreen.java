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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.eggpillow.EggPillow;
import com.eggpillow.V;

/**
 * Settings screen for EggPillow. Contains all settings.
 * 
 * @author Johan
 * @version 2014-05-15
 */
public class SettingsScreen implements Screen {
	private EggPillow game;
	// Dispose
	private SpriteBatch batch;
	private Texture background;
	private Stage stage;
	private BitmapFont font;

	private Table table;
	/** Used for displaying the highscore*/
	private String message = "Hello";

	Preferences prefs;

	public SettingsScreen(EggPillow g) {
		game = g;
	}

	/**
	 * Draw settings background and buttons.
	 */
	@Override
	public void render(float delta) {
		EggPillow.setBackground();

		batch.begin();
		batch.draw(background, 0, 0, V.WIDTH, V.HEIGHT);
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(batch, message, V.WIDTH / 20, V.HEIGHT / 8);
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
		background = new Texture(V.SETTINGS_BACKGROUND_IMAGE);
		font = new BitmapFont(Gdx.files.internal(V.FONT), false);
		font.setScale(V.HEIGHT * V.FONT_MEDIUM);
		table = new Table();
		table.setBounds(0, 0, V.WIDTH, V.HEIGHT);

		prefs = Gdx.app.getPreferences(V.PREFERENCE_NAME);
		boolean funmode = prefs.getBoolean(V.PREFERENCE_FUNMODE, false);
		boolean mute = prefs.getBoolean(V.PREFERENCE_MUTED, false);
		message = "Highscore " + prefs.getInteger(V.PREFERENCE_HIGHSCORE, 0);

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
		stage.addActor(table);
		Skin skin = new Skin();
		TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal(V.SETTINGS_BUTTON_PACK));
		skin.addRegions(buttonAtlas);

		ImageButtonStyle mutestyle = new ImageButtonStyle();
		mutestyle.up = skin.getDrawable(V.SETTINGS_MUTE_ON_REGION);
		mutestyle.checked = skin.getDrawable(V.SETTINGS_MUTE_OFF_REGION);
		ImageButton buttonMute = new ImageButton(mutestyle);
		buttonMute.setChecked(mute);

		ImageButtonStyle backstyle = new ImageButtonStyle();
		backstyle.up = skin.getDrawable(V.SETTINGS_BACK_REGION);
		ImageButton buttonBack = new ImageButton(backstyle);

		ImageButtonStyle resetstyle = new ImageButtonStyle();
		resetstyle.up = skin.getDrawable(V.SETTINGS_RESET_REGION);
		ImageButton buttonResetHighscore = new ImageButton(resetstyle);

		ImageButtonStyle funstyle = new ImageButtonStyle();
		funstyle.up = skin.getDrawable(V.SETTINGS_FUN_ON_REGION);
		funstyle.checked = skin.getDrawable(V.SETTINGS_FUN_OFF_REGION);
		ImageButton buttonFun = new ImageButton(funstyle);
		buttonFun.setChecked(!funmode);

		buttonMute.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				boolean muted = prefs.getBoolean(V.PREFERENCE_MUTED, false);
				prefs.putBoolean(V.PREFERENCE_MUTED, !muted);
				prefs.flush();
				if (muted)
					game.playBackgroundMusic();
				else
					game.stopBackgroundMusic();
			}
		});

		buttonBack.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				dispose();
				game.setScreen(new MenuScreen(game));
			}
		});

		buttonFun.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				boolean modeState = prefs.getBoolean(V.PREFERENCE_FUNMODE, false);
				prefs.putBoolean(V.PREFERENCE_FUNMODE, !modeState);
				prefs.flush();
			}
		});

		buttonResetHighscore.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				prefs.putInteger(V.PREFERENCE_HIGHSCORE, 0);
				prefs.flush();
				message = "Highscore " + 0;
			}
		});

		table.row().pad(5).width(V.WIDTH / 1.5f).height(V.HEIGHT / 5);
		table.add(buttonMute);
		table.row().pad(5).width(V.WIDTH / 1.5f).height(V.HEIGHT / 5);
		table.add(buttonFun);
		table.row().pad(5).width(V.WIDTH / 1.5f).height(V.HEIGHT / 5);
		table.add(buttonResetHighscore);
		buttonBack.setBounds(V.HEIGHT / 24, V.HEIGHT - V.HEIGHT / 6 - V.HEIGHT / 24, V.HEIGHT / 6, V.HEIGHT / 6);
		stage.addActor(buttonBack);

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
		batch.dispose();
		background.dispose();
		stage.dispose();
		font.dispose();
	}

}
