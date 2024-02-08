package com.cat.perlinnoisemapmaker.generation;

import java.util.Random;

import com.cat.perlinnoisemapmaker.graphics.Screen;
import com.cat.perlinnoisemapmaker.util.Vector2D;

public class VoronoiMap extends Map {
	
	Vector2D sites[];
	int siteColors[];
	
	public VoronoiMap(int width, int height, int siteCount, long seed) {
		super(width, height);
		
		Random rand = new Random(seed);
		
		sites = new Vector2D[siteCount];
		siteColors = new int[siteCount];
		
		for (int i = 0; i < sites.length; i++) {
			sites[i] = new Vector2D(rand.nextInt(width), rand.nextInt(height));
			siteColors[i] = rand.nextInt(0xffffff + 1);
		}
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int color = siteColors[0];
				int previous = Integer.MAX_VALUE;
				for (int i = 0; i < sites.length; i++) {
					int distance = sq2(sites[i].getX() - x, sites[i].getY() - y);
					if (distance < previous) {
						color = siteColors[i];
						previous = distance;
					}
				}
				pixels[x + y * width] = color;
			}
		}
	}
	
	public void render(Screen screen, int xa, int ya) {
		super.render(screen, xa, ya);
		for (int i = 0; i < sites.length; i++) {
			sites[i].render(screen, 0);
		}
	}
	
	private int sq2(int a, int b) {
		return a * a + b * b;
	}
}
