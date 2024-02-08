package com.cat.perlinnoisemapmaker.util;

public class Interpolation {
	
	public static double lerp(double a, double b, double alpha)
	{
	    return a + alpha * (b - a);
	}
	
	public static double unlerp(double a, double b, double val)
	{
	    return (val - a) / (b - a);
	}
	
	public static double bilerp(double xa, double ya, double a, double b, double c, double d) {
		double x1 = lerp(a, b, xa);
		double x2 = lerp(c, d, xa);
		return lerp(x1, x2, ya);
	}
}
