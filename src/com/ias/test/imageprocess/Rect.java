package com.ias.test.imageprocess;

public class Rect {
    private int x;
    private int y;
    private int width;
    private int height;

    public Rect(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return String.format("Rectangle Coordinates: [Top-Left: (%d, %d), Bottom-Right: (%d, %d)]",
                x, y, x + width, y + height);
    }
}
