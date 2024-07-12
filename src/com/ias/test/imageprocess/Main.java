package com.ias.test.imageprocess;

import java.util.Scanner;

import org.opencv.core.Core;
import com.ias.test.imageprocess.azat.Solution2;
import morphology.Morphology;
import com.ias.test.imageprocess.morphology.contrast.ContrastCalculator;
import com.ias.test.imageprocess.morphology.contrast.ContrastUtilsImpl;

public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the path to the image file:");
        String imagePath = scanner.nextLine();

        System.out.println("Please select the solution (1:Merve or 2):");
        int choice = scanner.nextInt();
        double contrastThreshold = 1.5;
        ImageProcessor processor;
        ContrastCalculator contrastCalculator = new ContrastUtilsImpl();

        if (choice == 1) {
            processor = new Morphology(contrastThreshold,contrastCalculator);
        } else if (choice == 2) {
            processor = new Solution2(contrastThreshold);
        } else {
            System.out.println("Geçersiz seçim!");
            return;
        }

      
        processor.process(imagePath);
    }
}