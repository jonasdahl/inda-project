package com.eggpillow.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.eggpillow.Stats;
import com.eggpillow.V;

/**
 * Powerup used to slow down the speed of the game.
 * @author Johan
 * @version 2014-05-10
 */
public class PowerFreeze extends PowerUp {
	
	/**
	 * Creates a new power freeze.
	 * @param atlas the atlas where image can be found
	 * @param startX the start x position
	 */
	public PowerFreeze(TextureAtlas atlas, float startX, Stats stats) {
		super(atlas, V.POWERUP_FREEZE_REGION, startX, stats);
	}

	/**
	 * Change the gameSpeed to 1/2 for 5 seconds
	 */
	@Override
	public void action() {
		gameStats.changeGameSpeed(0.5f, 5);	
	}

}
