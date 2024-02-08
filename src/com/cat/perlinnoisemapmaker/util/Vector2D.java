package com.cat.perlinnoisemapmaker.util;

import com.cat.perlinnoisemapmaker.graphics.Screen;

public class Vector2D {
	private int x, y;
	
	public Vector2D(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void render(Screen screen, int color) {
		screen.renderVector2D(this, color);
	}
}
