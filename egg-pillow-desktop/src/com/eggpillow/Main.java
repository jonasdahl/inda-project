package com.eggpillow;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "egg-pillow";
		cfg.useGL20 = false;
		cfg.width = 384;
		cfg.height = 256;
		
		new LwjglApplication(new EggPillow(), cfg);
	}
}
