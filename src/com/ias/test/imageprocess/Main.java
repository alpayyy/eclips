package com.ias.test.imageprocess;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

import com.ias.test.imageprocess.ImageProcessor;
import com.ias.test.imageprocess.colordifference.ColorDiffContrast;
import com.ias.test.imageprocess.morphology.Solution1;

import java.util.Scanner;

public class Main {
   

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Lütfen resim dosyasının yolunu giriniz:");
        String imagePath = scanner.nextLine();

       

        System.out.println("Lütfen çözüm yolunu seçiniz (1 veya 2):");
        int choice = scanner.nextInt();
        double contrastThreshold =1.5;
        ImageProcessor processor;

        if (choice == 1) {
            processor = new Solution1(contrastThreshold);
        } else if (choice == 2) {
            processor = new ColorDiffContrast(contrastThreshold);
        } else {
            System.out.println("Geçersiz seçim!");
            return;
        }
        processor.printContrastErrors(imagePath);
        

        
        
        

        
    }
}