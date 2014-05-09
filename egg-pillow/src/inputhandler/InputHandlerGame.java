package inputhandler;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.eggpillow.V;
import com.eggpillow.screens.GameScreen;
import com.eggpillow.screens.MenuScreen;
import com.eggpillow.sprites.Pillow;

public class InputHandlerGame implements InputProcessor {

	private Pillow pillow;
	private boolean onPillow = false;
	GameScreen gameScreen;
	MenuScreen menuScreen;
	Game game;

	public InputHandlerGame(Pillow p, GameScreen gs, MenuScreen ms, Game g) {
		pillow = p;
		gameScreen = gs;
		menuScreen = ms;
		game = g;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.MENU) {
			gameScreen.pauseGame();
		} else if (keycode == Keys.BACK || keycode == Keys.ESCAPE) {
			if (gameScreen.isPaused()) {
				gameScreen.dispose();
				game.setScreen(menuScreen);
			}
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

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (pillow.inside(screenX, screenY, V.WIDTH / 10, V.HEIGHT / 10)) { // TODO 1 merge 2
			pillow.setX(screenX);
			pillow.setY(V.HEIGHT - screenY);
			onPillow = true;
		}
		if (gameScreen.isPaused()) {
			gameScreen.unPauseGame();
			if (gameScreen.gameIsOver()) {
				gameScreen.dispose();
				game.setScreen(menuScreen);
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		onPillow = false;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (onPillow || pillow.inside(screenX, screenY, V.WIDTH / 10, V.HEIGHT / 10)) { // TODO  2 merge 1
			pillow.setX(screenX);
			pillow.setY(V.HEIGHT - screenY);
		}
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
}
