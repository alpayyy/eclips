package com.ias.test.imageprocess.colordifference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.opencv.core.Mat;

public class ContrastUtils2 {


	private static HashSet<BGR> GetDifferentColors(Mat image) {
		List<BGR> colors=new ArrayList<BGR>();
		for (int i = 0; i < image.height(); i++) {
			for (int j = 0; j < image.width(); j++) {
				double[] pixel = image.get(i, j);
				BGR bgr = new BGR(pixel[0], pixel[1], pixel[2]);
				if(!BGR.Any(colors, bgr)) {
					colors.add(bgr);
				}
				
			}
		}
		return new HashSet<>(colors);
	}

	// Calculate Euclidan Distance
	private static double calculateColorDifference(BGR bgr1, BGR bgr2) {
		double[] color1 = { bgr1.get_BLUE(), bgr1.get_GREEN(), bgr1.get_RED() };
		double[] color2 = { bgr2.get_BLUE(), bgr2.get_GREEN(), bgr2.get_RED() };

		return Math.sqrt(Math.pow(color1[0] - color2[0], 2) + Math.pow(color1[1] - color2[1], 2)
				+ Math.pow(color1[2] - color2[2], 2));
	}

	private static double luminance(BGR bgr) {
		final double RED = 0.2126;
		final double GREEN = 0.7152;
		final double BLUE = 0.0722;
		final double GAMMA = 2.4;

		double[] bgrArray = { bgr.get_BLUE(), bgr.get_GREEN(), bgr.get_RED() };
		Arrays.stream(bgrArray).forEach(v -> {
			v /= 255;
			v = v <= 0.03928 ? v / 12.92 : Math.pow((v + 0.055) / 1.055, GAMMA);
		});

		return bgrArray[0] * BLUE + bgrArray[1] * GREEN + bgrArray[2] * RED;

	}

	public static double GetContrast(Mat image) {

		HashSet<BGR> diffBGRS = ContrastUtils2.GetDifferentColors(image);
		List<BGR> diffBGRs2=new ArrayList<>(diffBGRS);
		BGR maxDiffBGR1 = null;
		BGR maxDiffBGR2 = null;
		double diff = -1;
		for(BGR bgr:diffBGRs2) {
			for(BGR bgr2:diffBGRs2) {
				double currentDiff=ContrastUtils2.calculateColorDifference(bgr, bgr2);
				if(currentDiff>diff) {
					diff=currentDiff;
					maxDiffBGR1 = bgr;
					maxDiffBGR2 = bgr2;
				}
			}
		}
		
		
//		for (int i = 0; i < diffBGRS.size(); i++) {
//			for (int j = i; j < diffBGRS.size(); j++) {
//				BGR bgr1 = diffBGRS.f;
//				BGR bgr2 = bgrList[j];
//				double currentDiff = ContrastUtils2.calculateColorDifference(bgr1, bgr2);
//				if (currentDiff > diff) {
//					diff = currentDiff;
//					maxDiffBGR1 = bgr1;
//					maxDiffBGR2 = bgr2;
//				}
//			}
//		}
		if (maxDiffBGR1 != null && maxDiffBGR2 != null) {

			// Calculating Contrast
			double luminance1 = ContrastUtils2.luminance(maxDiffBGR1);
			double luminance2 = ContrastUtils2.luminance(maxDiffBGR2);

			double brightest = Math.max(luminance1, luminance2);
			double darkest = Math.min(luminance1, luminance2);
			return (brightest + 0.05) / (darkest + 0.05);
		}
		
		return 0;

	}
}