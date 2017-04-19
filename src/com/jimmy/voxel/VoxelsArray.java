package com.jimmy.voxel;

import com.jimmy.tools.Tools;

/**
 * Created by jimmy on 19/04/17.
 */
public class VoxelsArray {

    int index_ci;
    int index_cj;
    int index_ck;
    int index_bvEntering;
    int index_transmittance;

    private double[][] voxels;
    private double[][] save;
    private String[] ijk;
    private double[] I;
    private double[] J;
    private double[] K;
    private double[] trials;
    private double[] success;
    private double[] prop;



    public VoxelsArray(VoxHeader header, int sx, int sy) {

        index_ci = header.getIndexColumn("i");
        index_cj = header.getIndexColumn("j");
        index_ck = header.getIndexColumn("k");
        index_bvEntering = header.getIndexColumn("bvEntering");
        index_transmittance = header.getIndexColumn("transmittance");

        voxels = new double[sx][sy];
        save = new double[sx][sy];
        ijk = new String[sx];
        I = new double[sx];
        J = new double[sx];
        K = new double[sx];
        trials = new double[sx];
        success = new double[sx];
        prop = new double[sx];
    }

    public void add(int x, int y, double value) {
        voxels[x][y] = value;
        save[x][y] = value;
    }

    public VoxelsArray add_ijk(int x) {
        ijk[x] = voxels[x][index_ci] + "_"+ voxels[x][index_cj] + voxels[x][index_ck];
        return this;
    }

    public VoxelsArray add_I(int x, int minivox) {
        I[x] = Math.floor(voxels[x][index_ci] / minivox);
        return this;
    }

    public VoxelsArray add_J(int x, int minivox) {
        J[x] = Math.floor(voxels[x][index_cj] / minivox);
        return this;
    }

    public VoxelsArray add_K(int x, int minivox) {
        K[x] = Math.floor(voxels[x][index_ck] / minivox);
        return this;
    }

    public VoxelsArray add_trials(int x, double EP) {
        trials[x] = Tools.round(voxels[x][index_bvEntering]/EP);
        return this;
    }

    public VoxelsArray add_success(int x, double EP) {
        success[x] = Tools.round(voxels[x][index_bvEntering]*voxels[x][index_transmittance]/EP);
        return this;
    }

    public VoxelsArray add_prop(int x) {
        prop[x] = success[x]/trials[x];
        return this;
    }

}
