package com.eggpillow.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.eggpillow.V;

/**
 * Represents the result board.
 * @author Jonas
 * @version 2014-05-14
 * 
 * TODO (Prio low)Add support for score > 999
 */
public class ScoreBoard extends Sprite {
	private int score;
	private BitmapFont font;
	
	/**
	 * Constructs the result board.
	 * @param atlas the atlas region where the board background can be found.
	 * @param id the type of the t
	 */
	public ScoreBoard(TextureAtlas atlas) {
		super(atlas.findRegion(V.SCORE_BOARD_REGION));
		setSize(V.SCORE_BOARD_WIDTH * V.WIDTH, V.SCORE_BOARD_HEIGHT * V.HEIGHT);
		setX(V.WIDTH * ( 1 - V.SCORE_BOARD_WIDTH ) - V.SCORE_BOARD_MARGIN * V.HEIGHT);
		setY(V.HEIGHT * ( 1 - V.SCORE_BOARD_HEIGHT ) - V.SCORE_BOARD_MARGIN * V.HEIGHT);
		
		font = new BitmapFont(Gdx.files.internal("font/EggPillow.fnt"), false);
		font.setColor(1.0f, 1.0f, 0f, .7f);
		font.setScale(V.HEIGHT * V.FONT_LARGE);
	}
	
	public void increaseScore() {
		score++;
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
		
		int ones = score % 10;
		int tens = (score - ones) % 100 / 10;
		int hundreds = (score - ones - tens * 10) % 1000 / 100;
		float startTop = getHeight() * .675f;
		font.draw(batch, "" + ones, getX() + getWidth() * 0.725f, getY() + startTop);
		font.draw(batch, "" + tens, getX() + getWidth() * 0.425f, getY() + startTop);
		font.draw(batch, "" + hundreds, getX() + getWidth() * 0.125f, getY() + startTop);
	}
}
