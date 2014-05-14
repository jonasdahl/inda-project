package com.eggpillow.sprites;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.eggpillow.V;

/**
 * Indicates number of lifes.
 * 
 * @author Jonas & Johan
 * @version 2014-05-14
 */
public class LifeIndicator extends Sprite {
	private int rows;
	private int cols;
	private ArrayList<Sprite> hearts;
	private int drawnLives;

	private AtlasRegion lightHeartRegion;
	private AtlasRegion darkHeartRegion;

	/**
	 * Constructs the result board.
	 * 
	 * @param atlas
	 *            the atlas region where the board background can be found.
	 * @param lives
	 *            Maximum amount of hearts.
	 */
	public LifeIndicator(TextureAtlas atlas, int lives) {
		drawnLives = lives;
		setSize(V.LIFE_INDICATOR_WIDTH * V.WIDTH, V.LIFE_INDICATOR_HEIGHT * V.HEIGHT);
		setX(V.WIDTH * (1 - V.LIFE_INDICATOR_WIDTH) - V.SCORE_BOARD_MARGIN * V.HEIGHT);
		setY(V.HEIGHT * (1 - V.SCORE_BOARD_HEIGHT - V.LIFE_INDICATOR_HEIGHT) - V.LIFE_INDICATOR_MARGIN * V.HEIGHT);

		lightHeartRegion = atlas.findRegion(V.HEART_SCORE_REGION);
		darkHeartRegion = atlas.findRegion(V.HEARTDARK_SCORE_REGION);

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
			Sprite heart = new Sprite(lightHeartRegion);
			heart.setX(x + getWidth() * col / cols);
			heart.setY(y - getHeight() * row / rows);
			heart.setSize(heartWidth, heartHeight);
			hearts.add(heart);
			col++;
		}
	}

	/**
	 * Removes hearts from the scoreboard and makes it match lives.
	 * 
	 * @param lives
	 *            Currentlives
	 */
	public void decreaseLives(int lives) {
		while (drawnLives > lives) {
			hearts.get(hearts.size() - drawnLives).setRegion(darkHeartRegion);
			drawnLives--;
		}
	}

	/**
	 * Adds hearts to the scoreboard and makes it match lives
	 * 
	 * @param lives
	 *            Currentlives
	 */
	public void increaseLives(int lives) {
		while (drawnLives < lives) {
			drawnLives++;
			hearts.get(hearts.size() - drawnLives).setRegion(lightHeartRegion);
		}
	}

	@Override
	public void draw(SpriteBatch batch) {
		// super.draw(batch);
		for (Sprite heart : hearts) {
			heart.draw(batch);
		}
	}
}
