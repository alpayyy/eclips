package com.ias.test.imageprocess;
<<<<<<< Updated upstream
import com.ias.test.imageprocess.azat.Solution2;
import com.ias.test.imageprocess.morphology.contrast.ContrastUtilsImpl;
=======
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

import com.ias.test.imageprocess.ImageProcessor;
import com.ias.test.imageprocess.azat.Solution2;
import com.ias.test.imageprocess.morphology.Solution1;

>>>>>>> Stashed changes
import java.util.Scanner;
import morphology.Morphology;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the path to the image file:");
        String imagePath = scanner.nextLine();

<<<<<<< Updated upstream
        System.out.println("Please select the solution (1:Merve or 2):");
=======
        Mat src = Imgcodecs.imread(imagePath);
        if (src.empty()) {
            System.out.println("Görsel yüklenemedi: " + imagePath);
            return;
        }

        System.out.println("Lütfen çözüm yolunu seçiniz (1 veya 2):");
>>>>>>> Stashed changes
        int choice = scanner.nextInt();
        double contrastThreshold =1.5;
        ImageProcessor processor;

        if (choice == 1) {
<<<<<<< Updated upstream
            processor = new Morphology(contrastThreshold, new ContrastUtilsImpl());
=======
            processor = new Solution1(contrastThreshold);
>>>>>>> Stashed changes
        } else if (choice == 2) {
            processor = new Solution2(contrastThreshold);
        } else {
            System.out.println("Invalid selection!");
            return;
        }

<<<<<<< Updated upstream

        processor.process(imagePath);

        scanner.close();
=======
        Mat output = processor.processImage(src);

        Mat resizedOutput = new Mat();
        Size newSize = new Size(800, 600);
        Imgproc.resize(output, resizedOutput, newSize);

        HighGui.imshow("Yazı ve Arka Plan Renkleri Çok Yakın Alanlar", resizedOutput);
        HighGui.waitKey();

        src.release();
        output.release();
        resizedOutput.release();
>>>>>>> Stashed changes
    }
}
