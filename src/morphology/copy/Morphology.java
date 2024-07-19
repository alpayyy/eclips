package morphology.copy;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import com.ias.test.imageprocess.ImageProcessor;
import com.ias.test.imageprocess.OpenCVResult;
import com.ias.test.imageprocess.Result;
import com.ias.test.imageprocess.Result;
import com.ias.test.imageprocess.morphology.contrast.ContrastCalculator;

import javax.swing.JFrame;
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
        this.contrastCalculator = contrastCalculator;
        this.frame = new JFrame("Processed Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    protected OpenCVResult getContrastErrors(Mat src, double contrastThreshold) {
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

        try (FileWriter fileWriter = new FileWriter("output/ErrorCoordinates.txt")) {
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

                    fileWriter.write(
                            String.format("Rectangle Coordinates: [Top-Left: (%d, %d), Bottom-Right: (%d, %d)]%n",
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

        String outputPath = "output/processed_image.jpg";
        Imgcodecs.imwrite(outputPath, output);

        return new OpenCVResult(output);
    }
}
