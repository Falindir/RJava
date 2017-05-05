package com.jimmy.v4;

/**
 * Created by jimmy on 05/05/17.
 */
public class VVoxel {

    private int i;
    private int j;
    private int k;

    private double PadBVTotal;
    private double angleMean;
    private double bvEntering;
    private double bvIntercepted;
    private double ground_distance;
    private double lMeanTotal;
    private double lgTotal;
    private int nbEchos;
    private int nbSampling;
    private double sumSurfMulLength;
    private double sumSurfMulLengthMulEnt;
    private double transmittance;
    private double transmittance_tmp;

    private double pred;

    public VVoxel(int i, int j, int k, double padBVTotal, double angleMean, double bvEntering, double bvIntercepted, double ground_distance, double lMeanTotal, double lgTotal, int nbEchos, int nbSampling, double sumSurfMulLength, double sumSurfMulLengthMulEnt, double transmittance, double transmittance_tmp) {
        this.i = i;
        this.j = j;
        this.k = k;
        PadBVTotal = padBVTotal;
        this.angleMean = angleMean;
        this.bvEntering = bvEntering;
        this.bvIntercepted = bvIntercepted;
        this.ground_distance = ground_distance;
        this.lMeanTotal = lMeanTotal;
        this.lgTotal = lgTotal;
        this.nbEchos = nbEchos;
        this.nbSampling = nbSampling;
        this.sumSurfMulLength = sumSurfMulLength;
        this.sumSurfMulLengthMulEnt = sumSurfMulLengthMulEnt;
        this.transmittance = transmittance;
        this.transmittance_tmp = transmittance_tmp;
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

    public int getNewI() {
        return i / VConstant.minivox;
    }

    public int getNewJ() {
        return j / VConstant.minivox;
    }

    public int getNewK() {
        return k / VConstant.minivox;
    }

    public double getGround_distance() {
        return ground_distance;
    }

    public boolean havePositiveRoundGroundDistance() {
        return VTools.round(ground_distance) > 0;
    }

    public double getTrials() {
        return VTools.round(bvEntering/VConstant.EP);
    }

    public boolean haveZeroTrials() {
        return getTrials() == 0;
    }

    public boolean havePositiveTrials() {
        return getTrials() > 0;
    }

    public double getSuccess() {
        return VTools.round(bvEntering*transmittance/VConstant.EP);
    }

    public double gerProp() {
        return getSuccess() / getTrials();
    }

    public VVoxel setPred(double pred) {
        this.pred = pred;
        return this;
    }

    public String getIJK() {
        return i + "_" + j + "_" + k;
    }

    @Override
    public String toString() {
        return  i +
                " " + j +
                " " + k +
                " " + PadBVTotal +
                " " + angleMean +
                " " + bvEntering +
                " " + bvIntercepted +
                " " + ground_distance +
                " " + lMeanTotal +
                " " + lgTotal +
                " " + nbEchos +
                " " + nbSampling +
                " " + sumSurfMulLength +
                " " + sumSurfMulLengthMulEnt +
                " " + transmittance +
                " " + transmittance_tmp;
    }
}
