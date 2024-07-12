package com.ias.test.imageprocess;



import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;

public class Result {

	private Mat _image;
	private Point[] _errorPoints;

	public Mat get_image() {
		return _image;
	}

	public void set_image(Mat _image) {
		this._image = _image;
	}

	public Point[] getErrorCoordinates() {
		return _errorPoints;
	}

	public void setErrorCoordinates(Point[] points) {
		this._errorPoints = points;
	}
	public String getErrorPointsReport() {
		String tempCoordinates = "";
		for (int i = 0; i < _errorPoints.length; i++) {
			tempCoordinates += (i+1) + "-)" + " Contrast ->" + "X: " + _errorPoints[i].x + " Y: " + _errorPoints[i].y + "\n";
		}
		return tempCoordinates;
	}
	public Result(Mat image, Point[] points) {
		this._errorPoints=points;
		this._image = image;
	}

	public Result(Mat image) {
		this._image = image;
		this._errorPoints=null;
	}

}