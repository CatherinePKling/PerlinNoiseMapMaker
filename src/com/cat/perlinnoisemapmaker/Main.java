package com.cat.perlinnoisemapmaker;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import javax.swing.JFrame;

import com.cat.perlinnoisemapmaker.generation.BiomeMap;
import com.cat.perlinnoisemapmaker.generation.LightningMap;
import com.cat.perlinnoisemapmaker.generation.Map;
import com.cat.perlinnoisemapmaker.graphics.Screen;
import com.cat.perlinnoisemapmaker.noise.Noise;

public class Main extends Canvas implements Runnable {
	private static int scale = 1;
	private static int width = 1300 / scale;
	private static int height = 700 / scale;
	private static String title = "Perlin Noise Map Maker";
	
	private boolean running = false;
	
	private Thread thread;
	private JFrame frame;
	private Screen screen;
	
	private Noise whiteNoise;
	private Noise basicNoise;
	private Map voronoiMap;
	private Noise perlinNoise;
	private Map lightningMap;
	private Map map;
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	private Random rand = new Random();
	
	double centerMultiplier = 0.5;
	
	public Main() {
		setPreferredSize(new Dimension(width * scale, height * scale));
		
		frame = new JFrame();
		screen = new Screen(width, height);

//		whiteNoise = new WhiteNoise(width, height, rand.nextLong());
		
		//map = new BiomeMap(width, height, centerMultiplier, rand.nextLong());
		
//		for (int y = 0; y < height; y++) {
//			for (int x = 0; x < width; x++) {
//				whiteNoise.pixels[x + y * width] = whiteNoise.distFromEdges(x, y);
//				//whiteNoise.pixels[x + y * width] = Interpolation.bilerp(x / (double) width, y / (double) height, 1.0, 0.5, 0.0, 1.0);
//			}
//		}
		//whiteNoise.stretch();

		//perlinNoise = new PerlinNoise(width, height, rand.nextLong());
		//perlinNoise.center(centerMultiplier);
		//perlinNoise.stretch();
		map = new BiomeMap(width, height, centerMultiplier, rand.nextLong());
		//voronoiMap = new VoronoiMap(width, height, 100, rand.nextLong());
		//lightningMap = new LightningMap(width, height, rand.nextLong());
		
	}
	
	public static void main(String[] args) {		
		Main main = new Main();
		main.frame.setResizable(false);
		main.frame.setTitle(title);
		main.frame.add(main);
		main.frame.pack();
		main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.frame.setLocationRelativeTo(null);
		main.frame.setVisible(true);
		
		main.start();
	}

	public void run() {
		
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60;
		double delta = 0;
		int frames = 0, updates = 0;
		requestFocus();
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;
				updates++;
			}
			render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer +=1000;
				frame.setTitle(title + "  |  " + updates + " ups, "+ frames + " fps");
				frames = 0; 
				updates = 0;
			}
		}
		stop();
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		
		//perlinNoise.render(screen, 0, 0);

//		whiteNoise.render(screen, 0, 0);
		
		map.render(screen, 0, 0);
		
		//Line2D line = new Line2D(new Vector2D(40, 50), new Vector2D(300, 150));
		//line.render(screen, 0xFFFFFF);
		
		//lightningMap.render(screen, 0, 0);
		//screen.smooth();
		
		//voronoiMap.render(screen, 0, 0);
		
		//basicNoise.render(screen, 0, 0);
		
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	
	int count = 0;
	
	private void update() {
		count %= 2;
		if (count == 1) {
			//map = new BiomeMap(width, height, centerMultiplier, rand.nextLong());
			//map = new BiomeMap(width, height, rand.nextLong());
			//perlinNoise = new PerlinNoise(width, height, rand.nextLong());
			//perlinNoise.center(centerMultiplier);
			//perlinNoise.stretch();
			//voronoiMap = new VoronoiMap(width, height, 100, rand.nextLong());
			//lightningMap = new LightningMap(width, height, rand.nextLong());
		}
		count++;
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
