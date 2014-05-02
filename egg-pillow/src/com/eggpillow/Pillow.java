package com.eggpillow;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Pillow extends Sprite{
	
	public Pillow() {
		super(new Texture("img/pillow.png"));
	}
	
	@Override
	public void setX(float x) {
		// TODO Check if img out of bounds and change x
		super.setX(x);
	}
	
	@Override
	public void setY(float y) {
		// TODO Check if img out of bounds and change y
		super.setY(y);
	}
	
}
