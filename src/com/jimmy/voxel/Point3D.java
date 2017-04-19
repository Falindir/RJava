package com.jimmy.voxel;

public class Point3D {

    private double x;
    private double y;
    private double z;

    public Point3D() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Point3D setX(double x) {
        this.x = x;
        return this;
    }

    public Point3D setY(double y) {
        this.y = y;
        return this;
    }

    public Point3D setZ(double z) {
        this.z = z;
        return this;
    }

    @Override
    public String toString() {
        return "Point3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
