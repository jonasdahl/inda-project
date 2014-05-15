package com.eggpillow.inputhandler;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.eggpillow.EggPillow;
import com.eggpillow.Point;
import com.eggpillow.V;
import com.eggpillow.screens.GameScreen;
import com.eggpillow.sprites.Pillow;

/**
 * Handles input on game screen.
 * 
 * @author Johan & Jonas
 * @version 2014-05-14
 */
public class InputHandlerGame implements InputProcessor {
	private Point nextPillowPosition;
	GameScreen gameScreen;
	EggPillow game;
	Pillow pillow;

	/**
	 * Creates a new input handler for a game.
	 * 
	 * @param g
	 *            a reference to the main game object
	 * @param gs
	 *            a reference to the game screen
	 */
	public InputHandlerGame(EggPillow g, GameScreen gs, Pillow p) {
		gameScreen = gs;
		game = g;
		pillow = p;
		nextPillowPosition = new Point(V.WIDTH / 2, V.HEIGHT / 2);
	}

	/**
	 * {@inheritDoc}. Pauses the game.
	 */
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.MENU) {
			gameScreen.pauseGame();
		} else if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
			gameScreen.pauseGame();
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	/**
	 * {@inheritDoc} Updates lastPosition and handles pause.
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (pillow.inside(screenX, screenY, V.PILLOW_WIDTH / 2, V.PILLOW_HEIGHT / 2)) {
			nextPillowPosition = new Point(screenX, V.HEIGHT - screenY);
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	/**
	 * {@inheritDoc} Updates lastPosition.
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		nextPillowPosition = new Point(screenX, V.HEIGHT - screenY);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	/**
	 * Returns the last known position of the mouse/finger.
	 * @return Position of the mouse/finger.
	 */
	public Point getLocation() {
		return nextPillowPosition;
	}

	/**
	 * Sends commands to Pillows and stuff.
	 * 
	 * @param delta
	 *            time since last update seconds
	 */
	public void update(float delta) {
		pillow.setLocation(nextPillowPosition);
	}
}
