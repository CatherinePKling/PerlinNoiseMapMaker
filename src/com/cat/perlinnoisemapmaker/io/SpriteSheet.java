package com.cat.perlinnoisemapmaker.io;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	public int[] pixels;
	private int width, height;
	
	public static SpriteSheet biomes = new SpriteSheet("/biomes/biome.png");
	
	public SpriteSheet(String path) {
		this.path = path;
		load();
	}
	
	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getPixel(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return 0;
		return pixels[x + y * width];
	}
}
