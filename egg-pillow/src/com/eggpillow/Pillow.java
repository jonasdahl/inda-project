package com.eggpillow;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Pillow extends Sprite{
	
	public Pillow() {
		super(new Texture("img/pillow.png"));
	}
	
	/**
	 * Update the position of the pillow.
	 */
	public void update() {
		int newX = handler.getX();
		int newY = handler.getY();
				
		setX(newX);
		setY(newY);
	}

}
