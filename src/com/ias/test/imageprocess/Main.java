package com.ias.test.imageprocess;
import java.util.Scanner;

import com.ias.test.imageprocess.azat.Solution2;
import com.ias.test.imageprocess.ImageProcessor;
import com.ias.test.imageprocess.morphology.contrast.ContrastCalculator;
import com.ias.test.imageprocess.morphology.contrast.ContrastUtilsImpl;

import morphology.Morphology;

public class Main {



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Lütfen resim dosyasının yolunu giriniz:");
        String imagePath = scanner.nextLine();

        System.out.println("Lütfen çözüm yolunu seçiniz (1:Merve veya 2):");
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