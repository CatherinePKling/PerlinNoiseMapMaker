package com.cat.perlinnoisemapmaker;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.cat.perlinnoisemapmaker.generation.BiomeMap;
import com.cat.perlinnoisemapmaker.generation.Map;

public class MainImage {
	public static int width = 1000;
	public static int height = 1000;
	
	private static Random rand = new Random();
	
	public static void main(String args[]) {
		Map map = new BiomeMap(width, height, 0.5, rand.nextLong());
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int pixels[] = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = map.pixels[i];
		}
		
		File outImage = new File("map.jpg");
		try {
			ImageIO.write(image, "jpg", outImage);
		} catch (IOException e) {
			System.out.println("Error saving image.");
			return;
		}
		System.out.println("Map saved successfully!");
	}
}
