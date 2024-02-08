package com.cat.perlinnoisemapmaker.noise;

import com.cat.perlinnoisemapmaker.graphics.Screen;
import com.cat.perlinnoisemapmaker.util.Interpolation;

public abstract class Noise {
	protected int width, height;
	public double[] pixels;
	
	public Noise(int width, int height) {
		this.width = width;
		this.height = height;

		pixels = new double[width * height];
	}
	
	public void update() {
		
	}
	
	public void render(Screen screen, int x, int y) {
		screen.renderNoise(x, y, this);
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	public double getPixel(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return 1.0; // 1.0 as to not break river carving logic
		return pixels[x + y * width];
	}
	
	public void add(Noise noise, double weight) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x > noise.width || y > noise.height) continue;
				pixels[x + y * width] += noise.pixels[x + y * width] * weight;
			}
		}
	}
	
	public void stretch() { // Stretches noise so that the highest value is 1.0 and the lowest value is 0.0		
		double min = Double.MAX_VALUE;
		double max = 0.0;
		
		for (int i = 0; i < pixels.length; i++) {
			max = Double.max(pixels[i], max);
			min = Double.min(pixels[i], min);
		}
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = Interpolation.unlerp(min, max, pixels[i]);
		}
	}
	
	public void center(double multiplier) { // Lowers the areas closer to the edges NOTE: Recommended to run stretch after running this
		//double maxDist = distFromEdges(width / 2, height / 2);
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				//double dist =  Interpolation.unlerp(0, maxDist, distFromEdges(x, y));
				
//				double dist = Math.sqrt(Math.pow(x - width / 2, 2) + Math.pow(y - height / 2, 2));
				//pixels[x + y * width] = (pixels[x + y * width] + 0.1) * (0.5 * Math.pow(distFromEdges(x, y, 0), multiplier));
				pixels[x + y * width] *= distFromEdges(x, y, multiplier);
			}
		}
	}
	
	public double distFromEdges(int x, int y, double b) { // Distance is scaled 0 - 1
//		boolean belowA = y >= x * (height / (double) width);
//		boolean belowB = y >= x * -(height / (double) width) + height;
		double result;
//		if (belowA) {
//			if (belowB) {
//				result = (height - y) / (double) (height / 2);
//			} else {
//				result = (x) / (double) (width / 2);
//			}
//		} else {
//			if (belowB) {
//				result = (width - x) / (double) (width / 2);
//			} else {
//				result = (y) / (double) (height / 2);
//			}
//		}
//		
//		boolean belowVertical = y >= height / 2;
//		boolean rightHorizontal = x >= width / 2;
//		int ax = 0, ay = 0;
//		
//		if (belowVertical) {
//			ay = height - y;
//		} else {
//			ay = y;
//		}
//		
//		if (rightHorizontal) {
//			ax = width - x;
//		} else {
//			ax = x;
//		}
//		result = dist(ax, ay) / dist(width / 2, height / 2);
		
		//double b = 0.5;
		
		int widthh = width / 2;
		int widthd = -widthh * widthh;
		double xa = Math.pow((x * (1 / (double) widthd)) * (x - width), b);
		
		int heighth = height / 2;
		int heightd = -heighth * heighth;
		double ya = Math.pow((y * (1 / (double) heightd)) * (y - height), b);
		
		result = xa * ya;
		
		if (result > 0) return Math.pow(result, 1);
		else return 0.0;
		//return result;
	}
}
