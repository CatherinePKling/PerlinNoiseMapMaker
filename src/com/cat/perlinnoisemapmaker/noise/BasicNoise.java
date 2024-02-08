package com.cat.perlinnoisemapmaker.noise;

import com.cat.perlinnoisemapmaker.util.Interpolation;

public class BasicNoise extends Noise {

	public BasicNoise(int width, int height, Noise whiteNoise) {
		super(width, height);
		double xRatio = (whiteNoise.width - 1) / (double) width, yRatio = (whiteNoise.height - 1) / (double) height;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				double xa = x * xRatio;
				double ya = y * yRatio;
				
				int x1 = (int) Math.floor(xa);
				int y1 = (int) Math.floor(ya);
				
				int x2 = x1 + 1;
				int y2 = y1 + 1;
				
				pixels[x + y * width] = Interpolation.bilerp(xa - (double) x1, ya - (double) y1, 
						whiteNoise.getPixel(x1, y1), 
						whiteNoise.getPixel(x2, y1), 
						whiteNoise.getPixel(x1, y2),
						whiteNoise.getPixel(x2, y2));
			}
		}
	}
}
