package com.eggpillow.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.eggpillow.EggPillow;
import com.eggpillow.V;

/**
 * This class is used to draw different pauseScreens to gameScreen
 * 
 * 
 * @author Johan version 2014-05-11
 */
public class PauseWindow {
	// Dispose
	private ShapeRenderer shapeRender;
	private BitmapFont font;
	private Stage buttonStage;
	private ImageButton resumeButton;
	private Skin skin;
	
	private EggPillow game;
	private GameScreen gameScreen;

	private AtlasRegion eggRegion;
	private AtlasRegion pillowRegion;

	private String pillowDesc = "Here is your pillow. Use it to save the eggs.";
	private String eggDesc = "Here is an example egg.";

	private Table pillowTable;
	private Table eggTable;
	private Table pauseTable;
	private Table gameOverTable;

	private Label highscoreLabel;

	private Color pauseColor = new Color(0, 0, 0, 0.5f);

	public PauseWindow(BitmapFont font, GameScreen g, EggPillow game) {
		this.gameScreen = g;
		this.font = font;
		this.game = game;
		
		TextureAtlas pauseAtlas = new TextureAtlas(V.PAUS_PACK);
		eggRegion = pauseAtlas.findRegion(V.PAUS_EGG_REGION);
		pillowRegion = pauseAtlas.findRegion(V.PAUSE_PILLOW_REGION);

		pillowTable = new Table();
		LabelStyle labelstyle = new LabelStyle(font, Color.BLACK);
		Label pillowLabel = new Label(pillowDesc, labelstyle);
		pillowLabel.setWrap(true);
		pillowTable.setPosition(V.WIDTH * (V.CLIFF_WIDTH + V.PILLOW_WIDTH * 1.2f), V.HEIGHT * 0.1f);
		pillowTable.add(pillowLabel).minWidth(V.WIDTH / 2);
		pillowTable.pack();

		eggTable = new Table();
		Label eggLabel = new Label(eggDesc, labelstyle);
		eggLabel.setWrap(true);
		eggTable.setPosition(V.WIDTH * (V.CLIFF_WIDTH + V.EGG_WIDTH * 2), V.HEIGHT * 3 / 4);
		eggTable.add(eggLabel).minWidth(V.WIDTH / 2);
		eggTable.pack();

		pauseTable = new Table();
		pauseTable.setPosition(V.WIDTH / 5, V.HEIGHT / 2);
		pauseTable.row();
		pauseTable.pack();

		gameOverTable = new Table();
		Label gameOverLabel = new Label("GAME OVER", labelstyle);
		gameOverLabel.setWrap(true);
		highscoreLabel = new Label("", labelstyle);
		highscoreLabel.setWrap(true);
		gameOverTable.setPosition(V.WIDTH / 5, V.HEIGHT / 2);
		gameOverTable.add(gameOverLabel).minWidth(V.WIDTH * 4 / 5);
		gameOverTable.row();
		gameOverTable.add(highscoreLabel).minWidth(V.WIDTH * 4 / 5);
		gameOverTable.pack();

		buttonStage = new Stage() {
			@Override
			public boolean keyDown(int keycode) {
				if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
					gameScreen.end();
				}
				return false;
			}
		};
		skin = new Skin();
		skin.addRegions(pauseAtlas);
		skin.addRegions(new TextureAtlas(V.MENU_BUTTON_PACK));

		ImageButtonStyle muteStyle = new ImageButtonStyle();
		muteStyle.up = skin.getDrawable(V.MUTE_REGION);
		muteStyle.checked = skin.getDrawable(V.MUTE_CROSSED_REGION);
		ImageButton muteButton = new ImageButton(muteStyle);
		muteButton.setChecked(Gdx.app.getPreferences(V.PREFERENCE_NAME).getBoolean(
				V.PREFERENCE_MUTED));
		muteButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				Preferences prefs = Gdx.app.getPreferences(V.PREFERENCE_NAME);
				boolean muted = prefs.getBoolean(V.PREFERENCE_MUTED, false);
				prefs.putBoolean(V.PREFERENCE_MUTED, !muted);
				prefs.flush();
			}
		});

		final ImageButtonStyle resumeStyle = new ImageButtonStyle();
		if (gameScreen.isStarted())
			resumeStyle.up = skin.getDrawable(V.RESUME_REGION);
//		else if (gameScreen.gameIsOver())
//			resumeStyle.up = skin.getDrawable(V.MAIN_MENU_REGION); TODO (img) Remove comments when added to atlas (add main_menu.png to pause atlas)
		else
			resumeStyle.up = skin.getDrawable(V.MENU_PLAY_REGION);
		resumeButton = new ImageButton(resumeStyle);
		resumeButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				gameScreen.unPauseGame();
				resumeStyle.up = skin.getDrawable(V.RESUME_REGION);
				if (gameScreen.gameIsOver()) {
					gameScreen.end();
				}
			}
		});
		//imageTable.add(muteButton).align(Align.right | Align.bottom).width(V.HEIGHT / 4).height(V.HEIGHT / 4);
		muteButton.setBounds(V.WIDTH - V.HEIGHT / 6 - V.HEIGHT / 24, V.HEIGHT / 24, V.HEIGHT / 6, V.HEIGHT / 6);
		float width = V.WIDTH / 2.5f;
		float height = V.HEIGHT / 5f;
		resumeButton.setBounds(V.WIDTH / 2 - width / 2, V.HEIGHT / 2 - height / 2, width, height);
		buttonStage.addActor(muteButton);
		buttonStage.addActor(resumeButton);

		shapeRender = new ShapeRenderer();
	}

	public void setAsInputListener() {
		Gdx.input.setInputProcessor(buttonStage);
	}

	/**
	 * Draw the instruction screen to batch.
	 * 
	 * @param batch
	 *            the batch to draw on
	 */
	public void drawInstructions(SpriteBatch batch) {
		// Draw dark screen
		shapeRender.begin(ShapeType.FilledRectangle);
		shapeRender.setColor(0, 0, 0, 0.5f);
		shapeRender.filledRect(0, 0, V.WIDTH, V.HEIGHT);
		shapeRender.end();
		shapeRender.begin(ShapeType.Circle);
		shapeRender.setColor(Color.RED);
		// Draw pillow circle
		shapeRender.circle(V.CLIFF_WIDTH * V.WIDTH + V.PILLOW_WIDTH * V.WIDTH / 2, V.HEIGHT / 10 + V.PILLOW_HEIGHT
				* V.HEIGHT / 2, V.WIDTH * V.PILLOW_WIDTH / 2 * 1.2f);
		// Draw egg circle
		shapeRender.circle(V.CLIFF_WIDTH * V.WIDTH + V.EGG_WIDTH * V.WIDTH / 2, V.HEIGHT * 3 / 4 + V.EGG_HEIGHT
				* V.HEIGHT / 2, V.EGG_HEIGHT * V.HEIGHT / 2 * 1.2f);
		shapeRender.end();
		// Draw egg and pillow
		batch.draw(eggRegion, V.WIDTH * V.CLIFF_WIDTH, V.HEIGHT * 3 / 4, V.WIDTH * V.EGG_WIDTH, V.HEIGHT * V.EGG_HEIGHT);
		batch.draw(pillowRegion, V.WIDTH * V.CLIFF_WIDTH, V.HEIGHT / 10, V.WIDTH * V.PILLOW_WIDTH, V.HEIGHT * 0.1f);

		// Instruction texts
		pillowTable.draw(batch, 1);
		eggTable.draw(batch, 1);
		batch.flush();
		buttonStage.draw();
	}

	/**
	 * Draw pause screen to batch.
	 * 
	 * @param batch
	 *            the SpriteBatch to draw on
	 */
	public void drawPause(SpriteBatch batch, int score) {
		// Darkscreen
		shapeRender.begin(ShapeType.FilledRectangle);
		shapeRender.setColor(pauseColor);
		shapeRender.filledRect(0, 0, V.WIDTH, V.HEIGHT);
		shapeRender.end();

		// Update score string
		pauseTable.draw(batch, 1);
		batch.flush();
		buttonStage.draw();
	}

	/**
	 * Draws game over screen.
	 * 
	 * @param batch
	 *            the batch to draw on
	 * @param score
	 *            the score when dead
	 * @param newHighScore
	 *            true if new highscore was set, false if not
	 */
	public void drawGameOver(SpriteBatch batch, int score, boolean newHighScore) {
		// Darkscreen
		shapeRender.begin(ShapeType.FilledRectangle);
		shapeRender.setColor(pauseColor);
		shapeRender.filledRect(0, 0, V.WIDTH, V.HEIGHT);
		shapeRender.end();
		
		//resumeButton.getStyle().up = skin.getDrawable(V.MAIN_MENU_REGION); TODO (img) Uncomment when done

		// Score text
		if (highscoreLabel.getText().length() < 1) {
			if (newHighScore) {
				highscoreLabel.setText("Congratulations you got a new Highscore " + score);
			} else {
				highscoreLabel.setText("Your score: " + score);
			}
		}
		gameOverTable.draw(batch, 1);
		batch.flush();
		buttonStage.draw();
	}

	public void dispose() {
		shapeRender.dispose();
		font.dispose();
		buttonStage.dispose();
	}

}
