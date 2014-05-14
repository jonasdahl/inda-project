package com.eggpillow.sprites;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.eggpillow.V;

/**
 * Indicates number of lifes.
 * 
 * @author Jonas
 * @version 2014-05-14
 * 
 * TODO Kolla igenom, fick skynda mig härifrån så jag har inte tid att kolla att den är klar
 */
public class LifeIndicator extends Sprite {
	private int lives;
	private int totalLives;
	private int rows;
	private int cols;
	private ArrayList<Sprite> hearts;
	
	/**
	 * Constructs the result board.
	 * @param atlas the atlas region where the board background can be found.
	 * @param id the type of the t
	 */
	public LifeIndicator(TextureAtlas atlas, int lives) {
		this.lives = lives;
		totalLives = lives;

		setSize(V.LIFE_INDICATOR_WIDTH * V.WIDTH, V.LIFE_INDICATOR_HEIGHT * V.HEIGHT);
		setX(V.WIDTH * ( 1 - V.LIFE_INDICATOR_WIDTH ) - V.SCORE_BOARD_MARGIN * V.HEIGHT);
		setY(V.HEIGHT * ( 1 - V.SCORE_BOARD_HEIGHT - V.LIFE_INDICATOR_HEIGHT ) - V.LIFE_INDICATOR_MARGIN * V.HEIGHT);
		
		// Calculate number of rows and cols
		rows = 1;
		cols = 3;
		while (rows * cols < lives) {
			rows *= 2;
			cols *= 2;
		}
		if (lives % rows != 0) 
			cols = (lives + 1) / rows;
		System.out.println("Rows: " + rows + " cols: " + cols);
		
		hearts = new ArrayList<Sprite>(lives);
		float heartWidth = getWidth() / cols;
		float heartHeight = getHeight() / rows;
		float x = getX();
		float y = getY() + getHeight() - heartWidth;
		
		for (int i = 0, col = 0, row = 0; i < lives; i++) {
			if (col >= cols) {
				col = 0;
				row++;
			}
			Sprite heart = new Sprite(new Texture("nya/heart.png"));
			heart.setX(x + getWidth() * col / cols);
			heart.setY(y - getHeight() * row / rows);
			heart.setSize(heartWidth, heartHeight);
			hearts.add(heart); // TODO Change texture for atlasregion TODO Do it with V.java
			col++;
		}
	}
	
	public void decreaseLives() {
		if (totalLives > 0 && lives > 0)
			hearts.get(totalLives - lives).setTexture(new Texture("nya/heart_dark.png")); // TODO Change texture for atlas region TODO Do it with V.java
		lives--;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		//super.draw(batch);
		
		for (Sprite heart : hearts) {
			heart.draw(batch);
		}
	}
}
