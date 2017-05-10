package com.jimmy.amapvox;

/**
 * Created by jimmy on 09/05/17.
 */
public class AMAPVoxel {

    private int i;

    private int j;

    private int k;

    private double bvEntering;

    private double ground_distance;

    private double transmittance;

    private double PadBVTotal;

    public AMAPVoxel(int i, int j, int k, double bvEntering, double ground_distance, double transmittance, double padBVTotal) {
        this.i = i;
        this.j = j;
        this.k = k;
        this.bvEntering = bvEntering;
        this.ground_distance = ground_distance;
        this.transmittance = transmittance;
        PadBVTotal = padBVTotal;
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

    public double getGround_distance() {
        return ground_distance;
    }

    public double getTransmittance() {
        return transmittance;
    }

    public double getPadBVTotal() {
        return PadBVTotal;
    }

    public AMAPVoxel setTransmittance(double transmittance) {
        this.transmittance = transmittance;
        return this;
    }

    public AMAPVoxel setPadBVTotal(double padBVTotal) {
        PadBVTotal = padBVTotal;
        return this;
    }
}
