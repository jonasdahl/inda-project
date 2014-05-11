package com.eggpillow.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.eggpillow.V;

/**
 * This class is used to draw different pauseScreens to gameScreen
 * 
 * @author Johan version 2014-05-11
 */
public class PauseScreen {

	private Texture instructionsTexture;
	
	private AtlasRegion eggRegion;
	private AtlasRegion pillowRegion;

	private String pillowDesc = "Here is your Pillow \n use it to save the eggs.";
	private String eggDesc = "Here is an egg.";
	
	// TODO use to be able to draw on 2lines.
	private Label pillowLabel;
	
	BitmapFont font;

	public PauseScreen(BitmapFont font, TextureAtlas atlas) {
		this.font = font;
		instructionsTexture = createInstructionTexture();
		eggRegion = atlas.findRegion(V.EGG_REGION);
		pillowRegion = atlas.findRegion(V.PILLOW_REGION);
		
		pillowLabel = new Label(pillowDesc, new Skin());
	}

	/**
	 * Draw the instruction screen to batch.
	 * 
	 * @param batch
	 *            the batch to draw on
	 */
	public void drawInstructions(SpriteBatch batch) {
		// Darkscreen with circles
		batch.draw(instructionsTexture, 0, 0);
		// Draw egg and pillow
		batch.draw(eggRegion, V.WIDTH / 2, V.HEIGHT * 3 / 4, V.WIDTH * V.EGG_WIDTH, V.HEIGHT * V.EGG_HEIGHT);
		batch.draw(pillowRegion, V.WIDTH / 2, V.HEIGHT / 4, V.WIDTH * 0.1f, V.HEIGHT * 0.1f);

		// Instruction texts
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.setScale(V.HEIGHT / V.FONT_MEDIUM);
		font.draw(batch, pillowDesc, V.WIDTH / 2 + V.WIDTH * 0.15f, V.HEIGHT / 4);
		font.draw(batch, eggDesc, V.WIDTH / 2 + V.WIDTH * 0.15f, V.HEIGHT * 3 / 4);
	}

	/**
	 * Draw pause screen to batch.
	 * 
	 * @param batch
	 *            the SpriteBatch to draw on
	 */
	public void drawPause(SpriteBatch batch, int score) {
		//Darkscreen (Just takes the color from (0,0) pixel in pTexture)
		batch.draw(instructionsTexture, 0f, 0f, (float) V.WIDTH, (float) V.HEIGHT, 0, 0, 1, 1, false, false);

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
		// Darkscreen (Just takes the color from (0,0) pixel in pTexture)
		batch.draw(instructionsTexture, 0f, 0f, (float) V.WIDTH, (float) V.HEIGHT, 0, 0, 1, 1, false, false);
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);

		// Game over text
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
	
	/**
	 * Creates a texture with circles to show the pillow and egg.
	 */
	private Texture createInstructionTexture() {
		Pixmap pixmap = new Pixmap(V.WIDTH, V.HEIGHT, Pixmap.Format.RGBA8888);
		pixmap.setColor(0f, 0f, 0f, 0.5f);
		pixmap.fill();
		pixmap.setColor(Color.RED);
		// Circle around egg
		pixmap.drawCircle((int) (V.WIDTH / 2 + V.WIDTH * V.EGG_WIDTH / 2), (int) (V.HEIGHT / 4 - V.HEIGHT
				* V.EGG_HEIGHT / 2), (int) (V.WIDTH * V.EGG_WIDTH * 1.2f));
		// Cicle around pillow
		pixmap.drawCircle(V.WIDTH / 2 + (int) V.PILLOW_WIDTH * V.WIDTH / 2, V.HEIGHT * 3 / 4 - (int) V.PILLOW_HEIGHT * V.HEIGHT / 2,
				(int) (V.PILLOW_WIDTH * V.WIDTH / 2 * 1.6f));
		Texture tempTexture = new Texture(pixmap);
		pixmap.dispose();
		return tempTexture;
	}
	
	public void dispose() {
		instructionsTexture.dispose();
	}

}
