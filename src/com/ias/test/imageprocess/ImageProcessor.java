package com.ias.test.imageprocess;
import org.opencv.core.Mat;


public abstract class ImageProcessor {
	  protected double contrastThreshold;

	    public ImageProcessor(double contrastThreshold) {
	        this.contrastThreshold = contrastThreshold;
	    }
	    
	    public void process(){
	    	//dosyayı yükle
	    	//kaynakları al
	    	
	    	Result result = this.processImage(null);
	    	
	    	//kaynakları boşalt
	    }

	    protected abstract Mat processImage(Mat src);
}

