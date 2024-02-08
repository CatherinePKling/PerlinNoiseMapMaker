package com.cat.perlinnoisemapmaker.util;

import com.cat.perlinnoisemapmaker.graphics.Screen;

public class Line2D {
	
	private Vector2D start, end;
	
	public Line2D(Vector2D start, Vector2D end) {
		this.start = start;
		this.end = end;
	}
	
	public Vector2D getStartVector() {
		return start;
	}
	
	public Vector2D getEndVector() {
		return end;
	}
	
	public void setStartVector(Vector2D start) {
		this.start = start;
	}
	
	public void setEndVector(Vector2D end) {
		this.end = end;
	}
	
	public void render(Screen screen, int color) {
		screen.renderLine2D(this, color);
	}
	
	public double getDistanceNoRoot() {
		int xa = start.getX() - end.getX();
		int ya = start.getY() - end.getY();
		return xa * xa + ya * ya;
	}
}
