package com.ias.test.imageprocess;
import com.ias.test.imageprocess.azat.Solution2;
import com.ias.test.imageprocess.morphology.contrast.ContrastUtilsImpl;
import java.util.Scanner;
import morphology.Morphology;

public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the path to the image file:");
        String imagePath = scanner.nextLine();

        System.out.println("Please select the solution (1:Merve or 2):");
        int choice = scanner.nextInt();
        double contrastThreshold = 1.5;
        ImageProcessor processor;

        if (choice == 1) {
            processor = new Morphology(contrastThreshold, new ContrastUtilsImpl());
        } else if (choice == 2) {
            processor = new Solution2(contrastThreshold);
        } else {
            System.out.println("Invalid selection!");
            return;
        }


        processor.process(imagePath);

        scanner.close();
    }
}
