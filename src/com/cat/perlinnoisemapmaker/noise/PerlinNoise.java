package com.cat.perlinnoisemapmaker.noise;

import com.cat.perlinnoisemapmaker.util.Interpolation;

public class PerlinNoise extends Noise {
	private final static int START_OCTAVE = 2;
	
	public PerlinNoise (int size, long seed) {
		super(size, size);
		
		int octaves = (int) (Math.log(width) / Math.log(2));
		
		for (int i = START_OCTAVE; i < octaves; i++) {
			Noise basicNoise = new BasicNoise(width, height, new WhiteNoise((int) Math.pow(2, i), (int) Math.pow(2, i), seed + i));
			add(basicNoise, 1 / Math.pow(2, i - (START_OCTAVE - 1)));
		}
	}
	
	public PerlinNoise (int width, int height, long seed) {
		super(width, height);
		
		int octaves;
		
		if (width > height) {
			octaves = (int) (Math.log(width) / Math.log(2));
			
			for (int x = START_OCTAVE; x < octaves; x++) {
				Noise basicNoise = new BasicNoise(width, width, new WhiteNoise((int) Math.pow(2, x), (int) Math.pow(2, x), seed + x));
				add(basicNoise, 1 / Math.pow(2, x - (START_OCTAVE - 1)));
			}
		} else {
			octaves = (int) (Math.log(height) / Math.log(2));
			
			for (int y = START_OCTAVE; y < octaves; y++) {
				Noise basicNoise = new BasicNoise(height, height, new WhiteNoise((int) Math.pow(2, y), (int) Math.pow(2, y), seed + y));
				add(basicNoise, 1 / Math.pow(2, y - (START_OCTAVE - 1)));
			}
		}
	}
}
