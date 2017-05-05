package com.jimmy.v4;


public class VPoint3D {

    private String header;

    private double x;
    private double y;
    private double z;

    public VPoint3D(String header, double x, double y, double z) {
        this.header = header;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public VPoint3D(String header, String sx, String sy, String sz) {
        this(header, Double.parseDouble(sx), Double.parseDouble(sy), Double.parseDouble(sz));
    }

    public VPoint3D(String[] value) {

    }

    public String toString() {
        return this.header + ":" + " " + this.x + " " + this.y + " " + this.z;
    }
}
