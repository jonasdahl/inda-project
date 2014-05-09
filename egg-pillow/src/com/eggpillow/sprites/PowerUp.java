package com.eggpillow.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.eggpillow.Stats;
import com.eggpillow.V;

/**
 * An abstract class that defines the main properties for a powerup.
 * @author jonas
 * @version 2014-05-09
 */
public abstract class PowerUp extends Touchable {
	protected boolean dead = false;
	protected Stats gameStats;

	/**
	 * Creates a new powerup
	 * @param atlas the atlas region to use
	 * @param atlasRegion where to look in the atlas region
	 * @param startX the starting x position
	 */
	public PowerUp(TextureAtlas atlas, String atlasRegion, float startX, Stats stats) {
		super(atlas.findRegion(atlasRegion), ELLIPSE);
		setSize(V.POWERUP_WIDTH * V.WIDTH, V.POWERUP_HEIGHT * V.HEIGHT);
		setX(startX);
		setY(V.HEIGHT);
		gameStats = stats;
	}
	
	/**
	 * When the Power Up meets a pillow, this method will be called.
	 */
	public abstract void action();
	
	/**
	 * Updates the position of the powerup.
	 * @param delta the time in seconds since last update
	 * @param pillow a reference to the pillow.
	 */
	public void update(float delta, Pillow pillow) {
		if (intersects(pillow).t != null) {
			action();
			dead = true;
		}
		if (getY() < 0) {
			dead = true;
		}
		ySpeed -= V.HEIGHT * V.GRAVITATION * delta;
		setY(getY() + ySpeed * delta);
	}
	
	/**
	 * Returns true if powerup has hit the ground or is taken by pillow.
	 * @return true if powerup is dead
	 */
	public boolean isDead() {
		return dead;
	}
}
