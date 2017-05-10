package com.jimmy.amapvox;

public class AMAPPlot3D {

    private String header;

    public int sizeX;
    public int sizeY;
    public int sizeZ;

    public AMAPPlot3D(String header, int sizeX, int sizeY, int sizeZ) {
        this.header = header;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
    }

    public AMAPPlot3D(String header, String sx, String sy, String sz) {
        this(header, Integer.parseInt(sx), Integer.parseInt(sy), Integer.parseInt(sz));
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

    public int getSizeMiniX() {
        return sizeX / AMAPConstant.minivox;
    }

    public int getSizeMiniY() {
        return sizeY / AMAPConstant.minivox;
    }

    public int getSizeMiniZ() {
        return sizeZ / AMAPConstant.minivox;
    }

    public String toString() {
        return this.header + ":" + " " + this.sizeX + " " + this.sizeY + " " + this.sizeZ;
    }

    
}
