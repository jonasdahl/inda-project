package com.eggpillow;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = V.TITLE + "v" + V.VERSION;
		cfg.useGL20 = false;
		cfg.width = 1152;
		cfg.height = 768;
		
		new LwjglApplication(new EggPillow(), cfg);
	}
}
