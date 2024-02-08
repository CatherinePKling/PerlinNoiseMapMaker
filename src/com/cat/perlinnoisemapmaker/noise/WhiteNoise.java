package com.cat.perlinnoisemapmaker.noise;

import java.util.Random;

public class WhiteNoise extends Noise {
	
	private Random rand;
	
	public WhiteNoise(int width, int height, long seed) {
		super(width, height);
		rand = new Random(seed);
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = rand.nextDouble();
	}
}
