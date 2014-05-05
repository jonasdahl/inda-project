package com.eggpillow.tween;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class TableAccessor implements TweenAccessor<Table> {
	// Defines the possible tween types
	public static final int ALPHA = 1;
	
	@Override
	public int getValues(Table target, int tweenType, float[] returnValues) {
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
	public void setValues(Table target, int tweenType, float[] newValues) {
		switch (tweenType) {
		case ALPHA:
			target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
			break;
		default:
			assert false;
		}
	}
}