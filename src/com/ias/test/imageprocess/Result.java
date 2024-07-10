package com.ias.test.imageprocess;

import org.opencv.core.Mat;

public class Result {

	private Mat _image;
	private String _errorCoordinates;
	public Mat get_image() {
		return _image;
	}
	public void set_image(Mat _image) {
		this._image = _image;
	}
	public String getErrorCoordinates() {
		return _errorCoordinates;
	}
	public void setErrorCoordinates(String errorCoordinates) {
		this._errorCoordinates = errorCoordinates;
	}
	public Result(Mat image,String errorCoordinates) {
		this._errorCoordinates=errorCoordinates;
		this._image=image;
	}
	public Result(Mat image) {
		this._image=image;
		this._errorCoordinates="";
	}
	
	
}
