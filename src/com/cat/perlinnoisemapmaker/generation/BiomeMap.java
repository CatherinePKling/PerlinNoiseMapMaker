package com.cat.perlinnoisemapmaker.generation;

import java.util.List;
import java.util.Random;

import com.cat.perlinnoisemapmaker.io.SpriteSheet;
import com.cat.perlinnoisemapmaker.noise.Noise;
import com.cat.perlinnoisemapmaker.noise.PerlinNoise;
import com.cat.perlinnoisemapmaker.util.Vector2D;

public class BiomeMap extends Map {
	public BiomeMap(int width, int height, long seed) {
		super(width, height);
		
		Noise elevation = new PerlinNoise(width, height, seed);
		elevation.stretch();
		
		Noise moisture = new PerlinNoise(width, height, seed + 1);
		moisture.stretch();
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = color(elevation.pixels[i], moisture.pixels[i]);
		}
	}
	
	public BiomeMap(int width, int height, double centerMultiplier, long seed) {
		super(width, height);
		
		Random rand = new Random(seed);
		
		Noise elevation = new PerlinNoise(width, height, seed);
		elevation.stretch();
		elevation.center(centerMultiplier);
		elevation.stretch();
		
		Noise moisture = new PerlinNoise(width, height, seed + 1);
		moisture.stretch();
	
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = color(elevation.pixels[i], moisture.pixels[i]);
		}
		
		for (int c = 0; c < 10; c++) {
			// Start source selection
			Vector2D sourceVector = null;
			double sourceElevation = -1.0;
			while(sourceVector == null) {
				int randX = rand.nextInt(width);
				int randY = rand.nextInt(height);
				
				sourceElevation = elevation.getPixel(randX, randY);
				if (sourceElevation >= 0.7) { // The minimum elevation rivers must start at
					sourceVector = new Vector2D(randX, randY);
				}
			}
			// End source selection
			
			carveRiver(sourceVector, elevation, rand);
		}
	}
	
	private void carveRiver(Vector2D sourceVector, Noise elevation, Random rand) {
		double sourceElevation;
		
		int count = 0;
		while (true) {
			count++;
			pixels[sourceVector.getX() + sourceVector.getY() * width] = 0x0000FF;
			sourceElevation = elevation.getPixel(sourceVector.getX(), sourceVector.getY());
			
			double currentLowestElevation = Double.MAX_VALUE;
			Vector2D currentLowestPos = new Vector2D(sourceVector.getX() - 1, sourceVector.getY() - 1);
			
			for (int y = -1; y < 2; y++) {
				for (int x = -1; x < 2; x++) {
					if (x == 0 && y == 0)
						continue;
					//System.out.println("X: " + x + " Y: " + y);
					double testElevation = elevation.pixels[(sourceVector.getX() + x) + (sourceVector.getY() + y) * elevation.getWidth()];
					
					if (testElevation == (double) 2.0)
						continue;
					
					if (count >= 600 && rand.nextInt(1000) == 0)
						carveRiver(new Vector2D(sourceVector.getX() + x, sourceVector.getY() + y), elevation, rand);
					
					if (testElevation < currentLowestElevation) {
						currentLowestElevation = testElevation;
						currentLowestPos = new Vector2D(sourceVector.getX() + x, sourceVector.getY() + y);
					}
				}
			}
			
			if (currentLowestElevation < 0.3)
				break;
			
//			if (currentLowestElevation >= sourceElevation) { // If river got into a hole
//				System.out.println("test");
//				elevation.pixels[sourceVector.getX() + sourceVector.getY() * elevation.getWidth()] = 2.0;
//			}
			
			elevation.pixels[sourceVector.getX() + sourceVector.getY() * elevation.getWidth()] = 2.0;
			
			int radius = 6;
			
//			if (currentLowestPos.getX() != sourceVector.getX()) {
//				for (int i = 1; i < radius; i++) {
//					elevation.pixels[(sourceVector.getX() - i) + sourceVector.getY() * elevation.getWidth()] = 2.0;
//					elevation.pixels[(sourceVector.getX() + i) + sourceVector.getY() * elevation.getWidth()] = 2.0;
//				}
//			}
//			
//			if (currentLowestPos.getY() != sourceVector.getY()) {
//				for (int i = 1; i < radius; i++) {
//					elevation.pixels[sourceVector.getX() + (sourceVector.getY() - i) * elevation.getWidth()] = 2.0;
//					elevation.pixels[sourceVector.getX() + (sourceVector.getY() + i) * elevation.getWidth()] = 2.0;
//				}
//			}
			
			for (int i = 1; i < radius; i++) {
				elevation.pixels[(sourceVector.getX() - i) + (sourceVector.getY() - i) * elevation.getWidth()] = 2.0;
				elevation.pixels[(sourceVector.getX() + i) + (sourceVector.getY() - i) * elevation.getWidth()] = 2.0;
				
				elevation.pixels[(sourceVector.getX() - i) + (sourceVector.getY() + i) * elevation.getWidth()] = 2.0;
				elevation.pixels[(sourceVector.getX() + i) + (sourceVector.getY() + i) * elevation.getWidth()] = 2.0;
			}
			
			sourceVector = currentLowestPos;
		}
	}
	
	private int color(double e, double m) {
		return SpriteSheet.biomes.getPixel((int) (m * 100), (int) (99 - e * 100)); // Because the y axis on pictures is at the top
	}
}
