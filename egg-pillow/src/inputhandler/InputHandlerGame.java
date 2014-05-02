package inputhandler;

import com.badlogic.gdx.InputProcessor;
import com.eggpillow.Pillow;

public class InputHandlerGame implements InputProcessor {
	
	private int pillowX = -1;
	private int pillowY = -1;
	private Pillow pillow;
	
	public InputHandlerGame(Pillow p) {
		pillow = p;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		pillow.setX(screenX);
		pillow.setY(screenY);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		pillow.setX(screenX);
		pillow.setY(screenY);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		pillow.setX(screenX);
		pillow.setY(screenY);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
