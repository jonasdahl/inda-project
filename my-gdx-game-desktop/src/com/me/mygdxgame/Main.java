package com.me.mygdxgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "€ggspelet";
		cfg.useGL20 = false;
		cfg.width = 384; // S4: 1920*1280
		cfg.height = 256;
		
		new LwjglApplication(new MyGdxGame(), cfg);
	}
}
