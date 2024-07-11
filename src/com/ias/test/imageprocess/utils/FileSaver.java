/*package com.ias.test.imageprocess.utils;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaver {
    private static final String OUTPUT_DIR = "output/";

    static {
        // OUTPUT_DIR dizinini oluştur
        File directory = new File(OUTPUT_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public static void saveRectangleCoordinates(String coordinates, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_DIR + fileName))) {
            writer.write(coordinates);
            System.out.println("Coordinates saved to " + OUTPUT_DIR + fileName);
        } catch (IOException e) {
            System.err.println("Error writing coordinates to file: " + e.getMessage());
        }
    }

    public static void saveProcessedImage(Mat image, String fileName) {
        boolean result = Imgcodecs.imwrite(OUTPUT_DIR + fileName, image);
        if (result) {
            System.out.println("Image saved to " + OUTPUT_DIR + fileName);
        } else {
            System.err.println("Error saving image to file.");
        }
    }
}*/
