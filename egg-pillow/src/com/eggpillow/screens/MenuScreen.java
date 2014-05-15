package com.eggpillow.screens;

import java.util.ArrayList;

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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.eggpillow.EggPillow;
import com.eggpillow.V;

/**
 * Menuscreen for EggPillow. Showed at the start and used to redirect the player to different gamemodes and settings.
 * 
 * @author Jonas
 * @version 2014-05-15
 */
public class MenuScreen implements Screen {
	// Dispose
	private SpriteBatch batch;
	private Texture background;
	private Stage stage;
	private BitmapFont font;
	private Skin skin;
	private TextureAtlas buttonAtlas;

	private EggPillow game;
	private ArrayList<Button> buttons;
	private Table table;

	/**
	 * Constructor for MenuScreen.
	 * 
	 * @param g
	 *            the main class for reference (for changing screens)
	 */
	public MenuScreen(EggPillow g) {
		game = g;
		buttons = new ArrayList<Button>();
		batch = new SpriteBatch();
		background = new Texture(V.MENU_BACKGROUND_IMAGE);

		// Create a table with the menu
		table = new Table();
		table.setBounds(0, 0, V.WIDTH, V.HEIGHT);
		stage = new Stage() {
			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
					game.exit();
				}
				return false;
			}
		};

		stage.addActor(table);

		// Font is fun!
		font = new BitmapFont(Gdx.files.internal(V.FONT), false);
		font.setScale(V.HEIGHT * V.FONT_MEDIUM);

		// Start styling buttons
		skin = new Skin();
		buttonAtlas = new TextureAtlas(Gdx.files.internal(V.MENU_BUTTON_PACK));
		skin.addRegions(buttonAtlas);

		// Add play button
		ImageButtonStyle playstyle = new ImageButtonStyle();
		playstyle.up = skin.getDrawable(V.MENU_PLAY_REGION);
		ImageButton buttonStart = new ImageButton(playstyle);
		buttonStart.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				fadeAwayTo(new GameScreen(game));
			}
		});
		buttons.add(buttonStart);

		// Add settings button
		ImageButtonStyle settingsstyle = new ImageButtonStyle();
		settingsstyle.up = skin.getDrawable(V.MENU_SETTINGS_REGION);
		ImageButton buttonSettings = new ImageButton(settingsstyle);
		buttonSettings.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				fadeAwayTo(new SettingsScreen(game));
			}
		});
		buttons.add(buttonSettings);

		// Add exit button
		ImageButtonStyle exitstyle = new ImageButtonStyle();
		exitstyle.up = skin.getDrawable(V.MENU_EXIT_REGION);
		ImageButton buttonExit = new ImageButton(exitstyle);
		buttonExit.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				game.exit();
			}
		});
		buttons.add(buttonExit);

		// Add mute button
		ImageButtonStyle mutestyle = new ImageButtonStyle();
		mutestyle.up = skin.getDrawable(V.MENU_UNMUTED_REGION);
		mutestyle.checked = skin.getDrawable(V.MENU_MUTED_REGION);
		ImageButton buttonMute = new ImageButton(mutestyle);
		buttonMute.setChecked(Gdx.app.getPreferences(V.PREFERENCE_NAME).getBoolean(V.PREFERENCE_MUTED, false));
		buttonMute.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				Preferences prefs = Gdx.app.getPreferences(V.PREFERENCE_NAME);
				boolean muted = prefs.getBoolean(V.PREFERENCE_MUTED, false);
				prefs.putBoolean(V.PREFERENCE_MUTED, !muted);
				prefs.flush();
				if (muted)
					game.playBackgroundMusic();
				else
					game.stopBackgroundMusic();
			}
		});

		for (Button button : buttons) {
			table.row().pad(5);
			table.add(button).width(V.WIDTH / 2.5f).height(V.HEIGHT / 5);
		}
		buttonMute.setBounds(V.WIDTH - V.HEIGHT / 6 - V.HEIGHT / 24, V.HEIGHT / 24, V.HEIGHT / 6, V.HEIGHT / 6);
		stage.addActor(buttonMute);
	}

	@Override
	public void render(float delta) {
		EggPillow.setBackground();

		batch.begin();
		batch.draw(background, 0, 0, V.WIDTH, V.HEIGHT);
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		batch.end();
		stage.act(delta);
		stage.draw();
	}

	/**
	 * Fades table and menu away.
	 */
	public void fadeAwayTo(final Screen screen) {
		game.setScreen(screen);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		game.playBackgroundMusic();
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
		skin.dispose();
		buttonAtlas.dispose();
	}

}
