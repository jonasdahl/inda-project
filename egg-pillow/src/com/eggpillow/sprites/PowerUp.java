package com.eggpillow.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.eggpillow.V;

public abstract class PowerUp extends Touchable{
	
	protected final static String ATLAS_REGION = "game_powerUp";
	
	private final static float WIDTH = 0.05f;
	private final static float HEIGHT = 0.05f;
	
	protected boolean dead = false;

	public PowerUp(TextureAtlas atlas, float startX) {
		super(atlas.findRegion(ATLAS_REGION), ELLIPSE);
		setSize(WIDTH * V.WIDTH, HEIGHT * V.HEIGHT);
		setX(startX);
		setY(V.HEIGHT);
	}
	
	public abstract void action();
	
	public void update(float delta, Pillow pillow) {
		if (intersects(pillow).t != null) {
			action();
			dead = true;
		}
		if (getY() < 0) {
			dead = true;
		}
		ySpeed -= V.HEIGHT * V.GRAVITATION;
		setY(getY() + ySpeed * delta);
	}
	
	public boolean isDead() {
		return dead;
	}

}
