package com.cat.perlinnoisemapmaker.graphics;

import com.cat.perlinnoisemapmaker.generation.Map;
import com.cat.perlinnoisemapmaker.noise.Noise;
import com.cat.perlinnoisemapmaker.util.Interpolation;
import com.cat.perlinnoisemapmaker.util.Line2D;
import com.cat.perlinnoisemapmaker.util.Vector2D;

public class Screen {
	private int width, height;
	public int[] pixels;
	
	public Screen (int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void renderNoise(int xa, int ya, Noise noise) {
		for (int y = 0; y < noise.getHeight(); y++) {
			int yb = y + ya;
			for (int x = 0; x < noise.getWidth(); x++) {
				int xb = x + xa;
				if (xb < 0 || yb < 0 || xb > width || yb >= height) continue;
				int lerped = (int)Interpolation.lerp(0, 255, noise.getPixel(x, y));
				int color = (lerped << 24) | (lerped << 16) | (lerped << 8) | lerped;
				//System.out.println(Integer.toHexString(color));
				pixels[xb + yb * width] = color;
			}
		}
	}

	public void renderMap(int xa, int ya, Map map) {
		for (int y = 0; y < map.getHeight(); y++) {
			int yb = y + ya;
			for (int x = 0; x < map.getWidth(); x++) {
				int xb = x + xa;
				if (xb < 0 || yb < 0 || xb > width || yb >= height) continue;
				
				pixels[xb + yb * width] = map.getPixel(x, y);
			}
		}
	}
	
	public void renderLine2D(Line2D line, int color) {
		int x = line.getStartVector().getX();
		int y = line.getStartVector().getY();
		int w = line.getEndVector().getX() - x ;
	    int h = line.getEndVector().getY() - y ;
	    int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0 ;
	    if (w<0) dx1 = -1 ; else if (w>0) dx1 = 1 ;
	    if (h<0) dy1 = -1 ; else if (h>0) dy1 = 1 ;
	    if (w<0) dx2 = -1 ; else if (w>0) dx2 = 1 ;
	    int longest = Math.abs(w) ;
	    int shortest = Math.abs(h) ;
	    if (!(longest>shortest)) {
	        longest = Math.abs(h) ;
	        shortest = Math.abs(w) ;
	        if (h<0) dy2 = -1 ; else if (h>0) dy2 = 1 ;
	        dx2 = 0 ;            
	    }
	    int numerator = longest >> 1 ;
	    for (int i=0;i<=longest;i++) {
	    	if (x > 0 && y > 0 && x < width && y < height) pixels[x + y * width] = color ;
	        numerator += shortest ;
	        if (!(numerator<longest)) {
	            numerator -= longest ;
	            x += dx1 ;
	            y += dy1 ;
	        } else {
	            x += dx2 ;
	            y += dy2 ;
	        }
	    }
	}

	public void renderVector2D(Vector2D point, int color) {
		for (int y = point.getY() - 1; y < point.getY() + 1; y++) {
			for (int x = point.getX() - 1; x < point.getX() + 1; x++) {
				if (x < 0 || x > width || y < 0 || y > width) continue;
				pixels[x + y * width] = color;
			}
		}
	}
	
	private int getPixel(int x, int y) {
		if (x < 0 || y < 0 || y >= height || x >= width) return 0;
		return pixels[x + y * width];
	}
	
	public void smooth() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int r = getPixel(x - 1, y) + getPixel(x, y - 1) +
						getPixel(x + 1, y) + getPixel(x, y + 1) + getPixel(x, y);
				pixels[x + y * width] = r / 5;
			}
		}
	}
}
