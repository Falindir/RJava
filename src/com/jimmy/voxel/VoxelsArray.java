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

    private double[][] voxels_filtered;
    private double[][] voxels_initial;
    private String[] ijk;
    private double[] I;
    private double[] J;
    private double[] K;
    private double[] trials;
    private double[] success;
    private double[] prop;



    public VoxelsArray(VoxHeader header, int sizeX, int sizeY, int sizeX_filter) {

        index_ci = header.getIndexColumn("i");
        index_cj = header.getIndexColumn("j");
        index_ck = header.getIndexColumn("k");
        index_bvEntering = header.getIndexColumn("bvEntering");
        index_transmittance = header.getIndexColumn("transmittance");

        voxels_filtered = new double[sizeX_filter][sizeY];
        voxels_initial = new double[sizeX][sizeY];
        ijk = new String[sizeX_filter];
        I = new double[sizeX_filter];
        J = new double[sizeX_filter];
        K = new double[sizeX_filter];
        trials = new double[sizeX_filter];
        success = new double[sizeX_filter];
        prop = new double[sizeX_filter];
    }

    public void add_initial(int x, int y, double value) {
        voxels_initial[x][y] = value;
    }

    public void add_filtered(int x, int y, double value) {

        voxels_filtered[x][y] = value;
    }


    public VoxelsArray add_ijk(int x) {
        ijk[x] = voxels_filtered[x][index_ci] + "_"+ voxels_filtered[x][index_cj] + voxels_filtered[x][index_ck];
        return this;
    }

    public VoxelsArray add_I(int x, int minivox) {
        I[x] = Math.floor(voxels_filtered[x][index_ci] / minivox);
        return this;
    }

    public VoxelsArray add_J(int x, int minivox) {
        J[x] = Math.floor(voxels_filtered[x][index_cj] / minivox);
        return this;
    }

    public VoxelsArray add_K(int x, int minivox) {
        K[x] = Math.floor(voxels_filtered[x][index_ck] / minivox);
        return this;
    }

    public VoxelsArray add_trials(int x, double EP) {
        trials[x] = Tools.round(voxels_filtered[x][index_bvEntering]/EP);
        return this;
    }

    public VoxelsArray add_success(int x, double EP) {
        success[x] = Tools.round(voxels_filtered[x][index_bvEntering]* voxels_filtered[x][index_transmittance]/EP);
        return this;
    }

    public VoxelsArray add_prop(int x) {
        prop[x] = success[x]/trials[x];
        return this;
    }

}
