package com.ias.test.imageprocess;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

import com.ias.test.imageprocess.ImageProcessor;
import com.ias.test.imageprocess.colordifference.ColorDiffContrast;


import morphology.contrast.ContrastCalculator;
import morphology.contrast.ContrastUtilsImpl;
import morphology.Morphology;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter image path...");
        String imagePath = scanner.nextLine();

        System.out.println("Please Choose a Solution Method...");
        System.out.println("1-) Morphology");
        System.out.println("2-) Color Difference Method");
        int choice = scanner.nextInt();
        double contrastThreshold = 1.5;
        ImageProcessor processor;
        ContrastCalculator contrastCalculator = new ContrastUtilsImpl();

        if (choice == 1) {
            processor = new Morphology(contrastThreshold, contrastCalculator);
        } else if (choice == 2) {
            processor = new ColorDiffContrast();
        } else {
            System.out.println("Invalid Selection!!!");
            return;
        }

        // Drawing contrast errors and printing contrasts...
        processor.printContrastErrors(imagePath, contrastThreshold);
    }
}
