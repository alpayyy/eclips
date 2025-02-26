package com.ias.test.imageprocess.morphology.contrast;

public class ContrastUtilsImpl implements ContrastCalculator {

	@Override
	public double calculateContrast(double[] rgb1, double[] rgb2) {
		double luminance1 = luminance(rgb1[0], rgb1[1], rgb1[2]);
		double luminance2 = luminance(rgb2[0], rgb2[1], rgb2[2]);
		double brightest = Math.max(luminance1, luminance2);
		double darkest = Math.min(luminance1, luminance2);
		return (brightest + 0.05) / (darkest + 0.05);
	}

	@Override
	public double luminance(double r, double g, double b) {
		final double RED = 0.2126;
		final double GREEN = 0.7152;
		final double BLUE = 0.0722;

		double[] color = { r / 255.0, g / 255.0, b / 255.0 };
		for (int i = 0; i < color.length; i++) {
			color[i] = color[i] <= 0.03928 ? color[i] / 12.92 : Math.pow((color[i] + 0.055) / 1.055, 2.4);
		}
		return color[0] * RED + color[1] * GREEN + color[2] * BLUE;
	}

	@Override
	public double[] rgbToGray(double r, double g, double b) {
		double gray = luminance(r, g, b);
		return new double[] { gray, gray, gray };
	}
}
