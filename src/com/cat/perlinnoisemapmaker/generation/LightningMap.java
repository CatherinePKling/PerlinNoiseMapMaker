package com.cat.perlinnoisemapmaker.generation;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.cat.perlinnoisemapmaker.graphics.Screen;
import com.cat.perlinnoisemapmaker.util.Interpolation;
import com.cat.perlinnoisemapmaker.util.Line2D;
import com.cat.perlinnoisemapmaker.util.Vector2D;

public class LightningMap extends Map {
	private List<Line2D> lines;
	
	public LightningMap(int width, int height, long seed) {
		super(width, height);
		
		Random rand = new Random(seed);
		
		lines = new LinkedList<Line2D>();
		
		lines.add(new Line2D(new Vector2D(0, height / 2), new Vector2D(width, height / 2)));
		
		double previousOffset = 0.0;
		
		for (int i = 0; i < 50; i++) {
			Line2D longest = getLongest();
			//Vector2D end = longest.getEndVector();
			double alpha = rand.nextDouble();
			Vector2D splitVector = new Vector2D( // Generates random vector in the middle
					(int )Interpolation.lerp(
							longest.getStartVector().getX(),
							longest.getEndVector().getX(),
							alpha),
					(int) Interpolation.lerp(
							longest.getStartVector().getY(), 
							longest.getEndVector().getY(), 
							alpha));
			
			alpha = rand.nextDouble();
			double maxOffset = 50.0;
			double halfOffset = maxOffset / 2.0;
			//splitVector.setX((int) (splitVector.getX() + (alpha * maxOffset - halfOffset)));
			previousOffset = (previousOffset + alpha * maxOffset - halfOffset) / 1.2;
			splitVector.setY((int) (splitVector.getY() + (previousOffset)));
			
			Vector2D end = longest.getEndVector();
			longest.setEndVector(splitVector);
			lines.add(new Line2D(splitVector, end));
		}
	}
	
	public void render(Screen screen, int xa, int ya) {
		super.render(screen, xa, ya);
		
		for (int i = 0; i < lines.size(); i++) {
			screen.renderLine2D(lines.get(i), 0xFFFFFF);
		}
	}
	
	private Line2D getLongest() { // Returns longest line in the lines list
		Line2D longest = lines.get(0);
		double longestLength = longest.getDistanceNoRoot();
		for (int i = 1; i < lines.size(); i++) {
			double tempLength = lines.get(i).getDistanceNoRoot();
			if (tempLength > longestLength) {
				longest = lines.get(i);
				longestLength = tempLength;
			}
		}
		return longest;
	}
}
