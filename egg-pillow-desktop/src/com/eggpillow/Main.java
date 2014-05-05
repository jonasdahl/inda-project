package com.eggpillow;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = EggPillow.TITLE + "v" + EggPillow.VERSION;
		cfg.useGL20 = false;
		cfg.width = 768;
		cfg.height = 512;
		
		new LwjglApplication(new EggPillow(), cfg);
	}
}
