package inputhandler;

import com.badlogic.gdx.InputProcessor;
import com.eggpillow.Pillow;

public class InputHandlerGame implements InputProcessor {
	
	private Pillow pillow;
	
	public InputHandlerGame(Pillow p) {
		pillow = p;
	}
	
	@Override
	public boolean keyDown(int keycode) {
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
		pillow.setX(screenX);
		pillow.setY(screenY);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		pillow.setX(screenX);
		pillow.setY(screenY);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		pillow.setX(screenX);
		pillow.setY(screenY);
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
