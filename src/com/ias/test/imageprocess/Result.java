package com.ias.test.imageprocess;

import org.opencv.core.Mat;

import org.opencv.core.Point;

public class Result {

    private Mat _image;
    private Point[] _errorPoints;

    public Result(Mat image, Point[] points) {
        this._image = image;
        this._errorPoints = points;
    }

    public Result(Mat image) {
        this._image = image;
        this._errorPoints = new Point[0]; // Boş bir dizi oluştur
    }

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
        if (_errorPoints == null || _errorPoints.length == 0) {
            return "No error points found.";
        }
        StringBuilder tempCoordinates = new StringBuilder();
        for (int i = 0; i < _errorPoints.length; i++) {
            tempCoordinates.append((i + 1)).append("-) Contrast -> X: ").append(_errorPoints[i].x)
                    .append(" Y: ").append(_errorPoints[i].y).append("\n");
        }
        return tempCoordinates.toString();
    }
}
