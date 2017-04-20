package com.jimmy.voxel;

/**
 * Created by jimmy on 20/04/17.
 */
public class DF {

    private int I;
    private int J;
    private int K;
    private int nbI;
    private TrialsIntance trialsIntance;

    public DF(int i, int j, int k, int nbI, TrialsIntance trialsIntance) {
        I = i;
        J = j;
        K = k;
        this.nbI = nbI;
        this.trialsIntance = trialsIntance;
    }

    public int getI() {
        return I;
    }

    public int getJ() {
        return J;
    }

    public int getK() {
        return K;
    }

    public int getNbI() {
        return nbI;
    }

    public TrialsIntance getTrialsIntance() {
        return trialsIntance;
    }
}
