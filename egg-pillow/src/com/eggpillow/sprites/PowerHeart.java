package com.eggpillow.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.eggpillow.Stats;
import com.eggpillow.V;

/**
 * Represents a Heart Powerup, which should increase the lives for player.
 * @author jonas
 * @version 2014-05-09
 */
public class PowerHeart extends PowerUp {
	/**
	 * Creates a new power heart.
	 * @param atlas the atlas where image can be found
	 * @param startX the start x position
	 */
	public PowerHeart(TextureAtlas atlas, float startX, Stats stats) {
		super(atlas, V.POWERUP_HEART_REGION, startX, stats);
	}

	@Override
	public void action() {
		gameStats.addLives(V.ADD_LIVES);
		gameStats.setGameSpeed(.5f);
	}
}
