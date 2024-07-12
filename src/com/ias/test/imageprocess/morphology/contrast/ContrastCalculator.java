package com.ias.test.imageprocess.morphology.contrast;

public interface ContrastCalculator  {
	 double calculateContrast(double[] rgb1, double[] rgb2);

	    double luminance(double r, double g, double b);

	    double[] rgbToGray(double r, double g, double b);
}