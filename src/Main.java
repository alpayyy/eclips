import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

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

        Mat src = Imgcodecs.imread(imagePath);
        if (src.empty()) {
            System.out.println("Görsel yüklenemedi: " + imagePath);
            return;
        }

  
        System.out.println("Lütfen çözüm yolunu seçiniz (1 veya 2):");
        int choice = scanner.nextInt();//integer değer okumayı sağlar
        double contrastThreshold = 30.5;
        ImageProcessor processor;

        if (choice == 1) {
            processor = new Solution1(contrastThreshold);
        } else if (choice == 2) {
            processor = new Solution2(contrastThreshold);
        } else {
            System.out.println("Geçersiz seçim!");
            return;
        }
     
        Mat output = processor.processImage(src);
      

        Mat resizedOutput = new Mat();
        Size newSize = new Size(800, 600);
        Imgproc.resize(output, resizedOutput, newSize);
       

        HighGui.imshow("Yazı ve Arka Plan Renkleri Çok Yakın Alanlar", resizedOutput);
        HighGui.waitKey();
       
        src.release();
        output.release();
        resizedOutput.release();
    }
}
