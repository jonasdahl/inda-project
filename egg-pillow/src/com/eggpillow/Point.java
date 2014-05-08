package com.eggpillow;

public class Point {
	
	private float x;
	private float y;
	
	public Point (float x, float y) {
		setX(x);
		setY(y);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}
