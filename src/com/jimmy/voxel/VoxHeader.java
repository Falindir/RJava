package com.jimmy.voxel;

import java.util.List;
import java.util.Vector;

public class VoxHeader {

    private Point3D mincorner;
    private Point3D maxcorner;
    private Point3D split;
    private List<String> columnNames;
    private String extra;

    public VoxHeader(Point3D mincorner, Point3D maxcorner, Point3D split, List<String> columnNames, String extra) {
        this.mincorner = mincorner;
        this.maxcorner = maxcorner;
        this.split = split;
        this.columnNames = columnNames;
        this.extra = extra;
    }

    public Point3D getMincorner() {
        return mincorner;
    }

    public Point3D getMaxcorner() {
        return maxcorner;
    }

    public Point3D getSplit() {
        return split;
    }

    public String getExtra() {
        return extra;
    }

    public int getNumberColumn() {
        return columnNames.size();
    }

    public int getIndexColumn(String name) {
        return columnNames.indexOf(name);
    }

    @Override
    public String toString() {
        return "VoxHeader{" +
                "mincorner=" + mincorner +
                ", maxcorner=" + maxcorner +
                ", split=" + split +
                ", columnNames=" + columnNames +
                ", extra='" + extra + '\'' +
                '}';
    }
}
