package com.eggpillow.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.eggpillow.V;

/**
 * This class is used to draw different pauseScreens to gameScreen
 * 
 * @author Johan version 2014-05-11
 */
public class PauseScreen {

	private AtlasRegion eggRegion;
	private AtlasRegion pillowRegion;

	private String pillowDesc = "Here is your Pillow use it to save the eggs.";
	private String eggDesc = "Here is an egg.";

	private Color pauseColor = new Color(0, 0, 0, 0.5f);

	private Label pillowLabel;
	private Label eggLabel;
	
	private Table pillowTable;
	private Table eggTable;

	private ShapeRenderer shapeRender;

	private BitmapFont font;

	public PauseScreen(BitmapFont font, TextureAtlas atlas) {
		this.font = font;
		eggRegion = atlas.findRegion(V.EGG_REGION);
		pillowRegion = atlas.findRegion(V.PILLOW_REGION);
		
		pillowTable = new Table();
		LabelStyle labelstyle = new LabelStyle(font, Color.BLACK);
		pillowLabel = new Label(pillowDesc, labelstyle);
		pillowLabel.setWrap(true);
		pillowTable.setPosition(V.WIDTH * (V.CLIFF_WIDTH + V.PILLOW_WIDTH * 1.2f), V.HEIGHT / 4);
		pillowTable.add(pillowLabel).minWidth(V.WIDTH / 2);
		pillowTable.pack();
		
		eggTable = new Table();
		eggLabel = new Label(eggDesc, labelstyle);
		eggLabel.setWrap(true);
		eggTable.setPosition(V.WIDTH * (V.CLIFF_WIDTH + V.EGG_WIDTH * 2), V.HEIGHT * 3 / 4);
		eggTable.add(eggLabel).minWidth(V.WIDTH / 2);
		eggTable.pack();
		
		shapeRender = new ShapeRenderer();
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
		shapeRender.circle(V.CLIFF_WIDTH * V.WIDTH + V.PILLOW_WIDTH * V.WIDTH / 2, V.HEIGHT / 4 + V.PILLOW_HEIGHT * V.HEIGHT / 2,
				V.WIDTH * V.PILLOW_WIDTH / 2 * 1.2f);
		// Draw egg circle
		shapeRender.circle(V.CLIFF_WIDTH * V.WIDTH + V.EGG_WIDTH * V.WIDTH / 2, V.HEIGHT * 3 / 4 + V.EGG_HEIGHT * V.HEIGHT / 2,
				V.EGG_HEIGHT * V.HEIGHT / 2 * 1.2f);
		shapeRender.end();
		// Draw egg and pillow
		batch.draw(eggRegion, V.WIDTH * V.CLIFF_WIDTH, V.HEIGHT * 3 / 4, V.WIDTH * V.EGG_WIDTH, V.HEIGHT * V.EGG_HEIGHT);
		batch.draw(pillowRegion,  V.WIDTH * V.CLIFF_WIDTH, V.HEIGHT / 4, V.WIDTH * V.PILLOW_WIDTH, V.HEIGHT * 0.1f);

		// Instruction texts
		pillowTable.draw(batch, 1);
		eggTable.draw(batch, 1);
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

		// Paus instructions text
		font.setColor(Color.BLACK);
		font.setScale(V.HEIGHT / V.FONT_BIG);
		font.draw(batch, "Touch the screen to resume your game", V.WIDTH / 2 * 0.6f, V.HEIGHT / 2);
		font.draw(batch, "Score: " + score, V.WIDTH / 2 * 0.6f, V.HEIGHT / 2 * 0.8f);
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
		
		// Game over text
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.setScale(V.HEIGHT / V.FONT_MEDIUM);
		font.draw(batch, "Game over", V.WIDTH / 2 * 0.6f, V.HEIGHT / 2);

		// Score text
		font.setScale(V.HEIGHT / V.FONT_SMALL);
		if (newHighScore) {
			font.draw(batch, "Congratulations u got a new Highscore " + score, V.WIDTH / 2 * 0.4f, V.HEIGHT / 3);
		} else {
			font.draw(batch, "Your score: " + score, V.WIDTH / 2 * 0.6f, V.HEIGHT / 3);
		}
	}

	public void dispose() {
		shapeRender.dispose();
		font.dispose();
	}

}
