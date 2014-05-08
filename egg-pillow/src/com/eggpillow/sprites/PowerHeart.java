package com.eggpillow.sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class PowerHeart extends PowerUp{
	
	protected final static String ATLAS_REGION = "game_heart";

	public PowerHeart(TextureAtlas atlas, float startX) {
		super(atlas, startX);
	}

	@Override
	public void action() {
		System.out.println("Add a heart");
	}

}
