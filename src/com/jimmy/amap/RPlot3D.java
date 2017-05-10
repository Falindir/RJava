package com.jimmy.amap;

/**
 * Created by jimmy
 */
public class RPlot3D {

    private String header;

    private int sizeX;
    private int sizeY;
    private int sizeZ;

    public RPlot3D(String header, int sizeX, int sizeY, int sizeZ) {
        this.header = header;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
    }

    public RPlot3D(String header, String sx, String sy, String sz) {
        this(header, Integer.parseInt(sx), Integer.parseInt(sy), Integer.parseInt(sz));
    }

    public String getHeader() {
        return header;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getSizeZ() {
        return sizeZ;
    }

    public int getSize1D() {
        return getSizeX() * getSizeY() * getSizeZ();
    }
}
