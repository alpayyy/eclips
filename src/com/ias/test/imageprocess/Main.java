package com.ias.test.imageprocess;
import org.opencv.core.Core;
import com.ias.test.imageprocess.azat.Solution2;
import com.ias.test.imageprocess.morphology.Morphology;
import com.ias.test.imageprocess.morphology.contrast.ContrastCalculator;
import com.ias.test.imageprocess.morphology.contrast.ContrastUtilsImp;

import java.util.Scanner;

public class Main {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println("OpenCV kütüphanesi yüklendi.");
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Lütfen resim dosyasının yolunu giriniz:");
        String imagePath = scanner.nextLine();

        System.out.println("Lütfen çözüm yolunu seçiniz (1:Merve veya 2):");
        int choice = scanner.nextInt();
        double contrastThreshold = 1.5;
        ImageProcessor processor;
        ContrastCalculator contrastCalculator = new ContrastUtilsImp();

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