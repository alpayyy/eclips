package com.ias.test.imageprocess;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


public abstract class ImageProcessor {
	  protected double contrastThreshold;

	    public ImageProcessor(double contrastThreshold) {
	        this.contrastThreshold = contrastThreshold;
	    }
	    
	    public void processImage(String path){
	    	 System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    	 
	    	 Mat src=null;
	    	 try {
	    		 src=Imgcodecs.imread(path);
	    		 if(src.empty()) {
	    			 throw new FileNotFoundException("File Not Found!!!");
	    		 }
				
			} catch (Exception e) {
				// TODO: handle exception	
				e.printStackTrace();
			}
	    	 Mat resizedOutput = new Mat();
	         Size newSize = new Size(800, 600);
	         Result result=getContrastErrors(src);
	         FileOperation.WriteText(Paths.get("").toAbsolutePath().toString()+"\\images\\ErrorCoordinates.txt", result.getErrorCoordinates());
	         Imgproc.resize(result.get_image(), resizedOutput, newSize);
	    	 HighGui.imshow("Yazı ve Arka Plan Renkleri Çok Yakın Alanlar", resizedOutput);
	         HighGui.waitKey();

	         src.release();
	        
	    	//dosyayı yükle
	    	//kaynakları al
	    	
	    	
	    	
	    	//kaynakları boşalt
	    }
	    
	    protected abstract Result getContrastErrors(Mat src);
}

