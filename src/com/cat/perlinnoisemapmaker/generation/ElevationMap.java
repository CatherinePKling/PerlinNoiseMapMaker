package com.cat.perlinnoisemapmaker.generation;

import com.cat.perlinnoisemapmaker.noise.PerlinNoise;

public class ElevationMap extends Map {

	public ElevationMap(int width, int height, long seed) {
		super(width, height);
		
		PerlinNoise elevation = new PerlinNoise(width, height, seed);
		
		elevation.stretch();
		
		for (int i = 0; i < pixels.length; i++) {
			if (elevation.pixels[i] < 0.1) pixels[i] = 0x070960;
			else if (elevation.pixels[i] < 0.2) pixels[i] = 0x2770b5;
			else if (elevation.pixels[i] < 0.3) pixels[i] = 0x49a5c6;
			else if (elevation.pixels[i] < 0.4) pixels[i] = 0xffe763;
			else if (elevation.pixels[i] < 0.5) pixels[i] = 0xefbc21;
			else if (elevation.pixels[i] < 0.6) pixels[i] = 0x54d648;
			else if (elevation.pixels[i] < 0.7) pixels[i] = 0x3a7c34;
			else if (elevation.pixels[i] < 0.8) pixels[i] = 0x687a52;
			else if (elevation.pixels[i] < 0.9) pixels[i] = 0x7c8182;
			else pixels[i] = 0xc0c6d6;
		}
	}
}
