package com.cat.perlinnoisemapmaker.generation;

import com.cat.perlinnoisemapmaker.graphics.Screen;

public abstract class Map {
	protected int width, height;
	public int[] pixels;
	
	public Map(int width, int height) {
		this.width = width;
		this.height = height;

		pixels = new int[width * height];
	}
	
	public void update() {
		
	}
	
	public void render(Screen screen, int xa, int ya) {
		screen.renderMap(xa, ya, this);
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getPixel(int x, int y) {
		if (x < 0 || y < 0 || y >= height || x >= width) return 0x00FF00;
		return pixels[x + y * width];
	}
}
