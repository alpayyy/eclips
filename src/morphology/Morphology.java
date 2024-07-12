package morphology;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.ias.test.imageprocess.ImageProcessor;

import morphology.contrast.ContrastCalculator;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Morphology extends ImageProcessor {
    private double blockSize = 15;
    private double c = 10;
    private double cannyThresholdmin = 50;
    private double cannyThresholdmax = 150;
    private double thickness = 2;
    private ContrastCalculator contrastCalculator;
    private JFrame frame;

    public Morphology(double contrastThreshold, ContrastCalculator contrastCalculator) {
        super(contrastThreshold);
        this.contrastCalculator = contrastCalculator;
        this.frame = new JFrame("Processed Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public Mat processImage(Mat src) {
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);

        Mat equalized = new Mat();
        Imgproc.equalizeHist(gray, equalized);

        Mat thresholded = new Mat();
        Imgproc.adaptiveThreshold(equalized, thresholded, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
                Imgproc.THRESH_BINARY_INV, (int) blockSize, c);

        Mat morphKernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Mat morphed = new Mat();
        Imgproc.morphologyEx(thresholded, morphed, Imgproc.MORPH_CLOSE, morphKernel);

        Mat edges = new Mat();
        Imgproc.Canny(morphed, edges, cannyThresholdmin, cannyThresholdmax);

        List<MatOfPoint> contours = new java.util.ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        Mat output = src.clone();

        // Open the file for writing the coordinates
        try (FileWriter fileWriter = new FileWriter("contour_coordinates.txt")) {
            for (MatOfPoint contour : contours) {
                Rect rect = Imgproc.boundingRect(contour);

                if (rect.width < 20 || rect.height < 20) {
                    continue;
                }

                Scalar meanColorRect = Core.mean(new Mat(src, rect));
                double[] meanRectColor = { meanColorRect.val[2], meanColorRect.val[1], meanColorRect.val[0] };

                Scalar meanColorImage = Core.mean(src);
                double[] meanImageColor = { meanColorImage.val[2], meanColorImage.val[1], meanColorImage.val[0] };

                double contrast = contrastCalculator.calculateContrast(meanRectColor, meanImageColor);

                if (contrast <= contrastThreshold) {
                    Imgproc.rectangle(output, rect, new Scalar(0, 0, 255), (int) thickness);

                    // Write the rectangle coordinates to the file
                    fileWriter.write(String.format("Rectangle Coordinates: [Top-Left: (%d, %d), Bottom-Right: (%d, %d)]%n",
                            rect.x, rect.y, rect.x + rect.width, rect.y + rect.height));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        gray.release();
        equalized.release();
        thresholded.release();
        morphed.release();
        edges.release();
        hierarchy.release();

        Path outputDir = Paths.get("output");
        if (!Files.exists(outputDir)) {
            try {
                Files.createDirectory(outputDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String outputPath = "output/processed_image.jpg";  // The full path to the file where the processed image will be saved.
        if (Imgcodecs.imwrite(outputPath, output)) {
            System.out.println("Image saved successfully at " + outputPath);
            // Update the JFrame with the new image
            updateFrame(outputPath);
        } else {
            System.out.println("Failed to save the image.");
        }
        return output;
    }

    private void updateFrame(String imagePath) {//The updateFrame method updates the rendered image on JFrame. 
    	//This method removes the previous content and adds the new image.
       
    	Mat img = Imgcodecs.imread(imagePath);
        BufferedImage bufferedImage = matToBufferedImage(img);

        frame.getContentPane().removeAll();
        frame.setSize(bufferedImage.getWidth(), bufferedImage.getHeight());
        ImageIcon imageIcon = new ImageIcon(bufferedImage);
        JLabel jLabel = new JLabel(imageIcon);
        frame.getContentPane().add(jLabel);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }
//Mat formatındaki bir görüntüyü Java BufferedImage formatına dönüştüren bir metodu içeriyor. 
    //Bu dönüştürme, OpenCV'nin görüntü işleme yeteneklerini Java'nın grafik görüntüleme yetenekleriyle birleştirmek için gereklidir
    
    private BufferedImage matToBufferedImage(Mat mat) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage bufferedImage = new BufferedImage(mat.cols(), mat.rows(), type);
        //Mat nesnesinin genişliği (cols), yüksekliği (rows) ve belirlenen tipi (type) kullanılarak oluşturulur.

        byte[] data = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        //Mat nesnesinin genişliği (cols), yüksekliği (rows) ve belirlenen tipi (type) kullanılarak oluşturulur.

        mat.get(0, 0, data);
        return bufferedImage;
    }
}
