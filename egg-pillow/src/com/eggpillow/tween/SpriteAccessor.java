package com.eggpillow.tween;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.eggpillow.V;

/**
 * An accessor for Tween enginge that makes values changeable.
 * 
 * @author Jonas
 * @version 2014-05-09
 */
public class SpriteAccessor implements TweenAccessor<Sprite> {
	/**
	 * A Tween Type.
	 */
	public static final int ALPHA = 1;

	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValues) {
		switch (tweenType) {
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 1;
		default:
			assert false;
			return 0;
		}
	}

	@Override
	public void setValues(Sprite target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case ALPHA:
			target.setColor(V.BG_R, V.BG_G, V.BG_B, newValues[0]);
			break;
		default:
			assert false;
		}
	}
}
