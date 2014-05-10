package com.eggpillow.screens;

import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
import com.eggpillow.tween.SpriteBatchAccessor;
import com.eggpillow.tween.TableAccessor;

/**
 * Menuscreen for EggPillow. Showed at the start and used to redirect the player to different gamemodes and settings.
 * @author Jonas
 * @version 2014-05-09
 */
public class MenuScreen implements Screen {
	private EggPillow game;
	private SpriteBatch batch;
	private Texture background;
	private Stage stage;
	private ArrayList<TextButton> buttons;
	private Table table;
	private Skin skin;
	private BitmapFont font;
	private TextureAtlas buttonAtlas;
	private TweenManager tweenManager;

	private final static String BACKGROUND_IMG = "img/menu_background.png";

	private final static String BUTTON = "ui/button.pack";

	/**
	 * Constructor for MenuScreen.
	 * 
	 * @param g
	 *            the main class for reference (for changing screens)
	 */
	public MenuScreen(EggPillow g) {
		// TODO €ndra knapparnas storlek
		game = g;
		buttons = new ArrayList<TextButton>();
		tweenManager = new TweenManager();
		batch = new SpriteBatch();
		background = new Texture(BACKGROUND_IMG);
		Tween.registerAccessor(SpriteBatch.class, new SpriteBatchAccessor());
		Tween.registerAccessor(Table.class, new TableAccessor());

		// Create a table with the menu
		table = new Table();
		table.setBounds(0, 0, V.WIDTH, V.HEIGHT); // TODO
		stage = new Stage() {
			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Keys.BACK) {
					game.exit();
				}
				return false;
			}
		};

		stage.addActor(table);

		// Font is fun!
		font = new BitmapFont(Gdx.files.internal(V.FONT), false);
		font.setScale(V.HEIGHT / V.FONT_MEDIUM);

		// Start styling buttons
		skin = new Skin();
		buttonAtlas = new TextureAtlas(Gdx.files.internal(BUTTON));
		skin.addRegions(buttonAtlas);
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button");
		textButtonStyle.down = skin.getDrawable("button");
		textButtonStyle.checked = skin.getDrawable("button");
		textButtonStyle.font = font;

		// Add play button
		TextButton buttonStart = new TextButton("Play", textButtonStyle);
		buttonStart.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				fadeAwayTo(new GameScreen(game));
			}
		});
		buttons.add(buttonStart);

		// Add settings button
		TextButton buttonSettings = new TextButton("Settings", textButtonStyle);
		buttonSettings.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				fadeAwayTo(new SettingsScreen(game));
			}
		});
		buttons.add(buttonSettings);

		// Add exit button
		TextButton buttonExit = new TextButton("Exit", textButtonStyle);
		buttonExit.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				game.exit();
			}
		});
		buttons.add(buttonExit);

		// Actually add to table
		// table.pad(10);
		// table.add(title);
		for (TextButton button : buttons) {
			table.row().pad(10);
			table.add(button).width(V.WIDTH/2).height(V.HEIGHT/6);
		}
	}

	@Override
	public void render(float delta) {
		tweenManager.update(delta);
		Texture.setEnforcePotImages(false);
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
		Tween.set(table, TableAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(batch, TableAccessor.ALPHA, .25f).target(0).setCallback(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				game.setScreen(screen);
			}
		}).start(tweenManager);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		// Make menu fade in smooth, yep, both table and the rest
		Tween.set(batch, SpriteBatchAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(batch, SpriteBatchAccessor.ALPHA, .25f).target(1).start(tweenManager);

		Gdx.input.setInputProcessor(stage);

		// Show table - in a faded way ;)
		Tween.set(table, TableAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(table, TableAccessor.ALPHA, .25f).target(1).start(tweenManager);
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
		// TODO make sure everything is disposed.
		font.dispose();
		stage.dispose();
		batch.dispose();
		background.dispose();
	}

}
