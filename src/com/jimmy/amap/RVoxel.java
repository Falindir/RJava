package com.jimmy.amap;

/**
 * Created by jimmy
 */
public class RVoxel {

    private int i;

    private int j;

    private int k;

    private double bvEntering;

    private int ground_distance;

    private double transmittance;

    private double PadBVTotal;

    public RVoxel(int i, int j, int k, double bvEntering, int ground_distance, double transmittance, double padBVTotal) {
        this.i = i;
        this.j = j;
        this.k = k;
        this.bvEntering = bvEntering;
        this.ground_distance = ground_distance;
        this.transmittance = transmittance;
        this.PadBVTotal = padBVTotal;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getK() {
        return k;
    }

    public double getBvEntering() {
        return bvEntering;
    }

    public int getGround_distance() {
        return ground_distance;
    }

    public double getTransmittance() {
        return transmittance;
    }

    public double getPadBVTotal() {
        return PadBVTotal;
    }

    public int getMiniI() {
        return (int)(Math.floor((double)i / RConstant.minivox));
    }

    public int getMiniJ() {
        return (int)(Math.floor((double)j / RConstant.minivox));
    }

    public int getMiniK() {
        return (int)(Math.floor((double)k / RConstant.minivox));
    }

    public double getTrial() {
        return RTools.round(bvEntering / RConstant.EP);
    }

    public double getSucess() {
        return RTools.round(bvEntering * transmittance / RConstant.EP);
    }

    public double getProp() {
        return getSucess() / getTrial();
    }

    public String getIJK() {
        return i + "_" + j + "_" + k;
    }

    public RVoxel setTransmittance(double transmittance) {
        this.transmittance = transmittance;
        return this;
    }

    public RVoxel setPadBVTotal(double padBVTotal) {
        PadBVTotal = padBVTotal;
        return this;
    }
}
