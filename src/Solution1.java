import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.List;

public class Solution1 extends ImageProcessor {

    public Solution1(double contrastThreshold) {
        super(contrastThreshold);
    }

    @Override
    public Mat processImage(Mat src) {
        // Gri tonlamaya çevir
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);


        Mat equalized = new Mat();
        Imgproc.equalizeHist(gray, equalized);

     
        Mat thresholded = new Mat();
        Imgproc.adaptiveThreshold(equalized, thresholded, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 15, 10);

      
        Mat morphKernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Mat morphed = new Mat();
        Imgproc.morphologyEx(thresholded, morphed, Imgproc.MORPH_CLOSE, morphKernel);

   
        Mat edges = new Mat();
        Imgproc.Canny(morphed, edges, 50, 150);

  
        List<MatOfPoint> contours = new java.util.ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);


        Mat output = src.clone();
        for (MatOfPoint contour : contours) {
            Rect rect = Imgproc.boundingRect(contour);

         
            if (rect.width < 20 || rect.height < 20) {
                continue;
            }

         
            Scalar meanColorRect = Core.mean(new Mat(src, rect));
            double[] meanRectColor = {meanColorRect.val[2], meanColorRect.val[1], meanColorRect.val[0]};

        
            double[] meanImageColor = Core.mean(src).val;
            double contrast = ContrastUtils.calculateContrast(meanRectColor, meanImageColor);

            if (contrast <= contrastThreshold) {
                Imgproc.rectangle(output, rect, new Scalar(0, 0, 255), 2);
                System.out.println("Solution1 - Rectangle Coordinates: [Top-Left: (" + rect.x + ", " + rect.y + "), Bottom-Right: (" + (rect.x + rect.width) + ", " + (rect.y + rect.height) + ")]");
            }
        }

        gray.release();
        equalized.release();
        thresholded.release();
        morphed.release();
        edges.release();
        hierarchy.release();

        return output;
    }
}
