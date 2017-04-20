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

    private double max_I = 0;
    private double max_J = 0;
    private double max_K = 0;

    private int[][] df;

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

        double vi = Math.floor(voxels_filtered[x][index_ci] / minivox);
        max_I = Math.max(max_I, vi);
        I[x] = vi;
        return this;
    }

    public VoxelsArray add_J(int x, int minivox) {
        double vj = Math.floor(voxels_filtered[x][index_cj] / minivox);
        max_J = Math.max(max_J, vj);
        J[x] = vj;
        return this;
    }

    public VoxelsArray add_K(int x, int minivox) {
        double vk = Math.floor(voxels_filtered[x][index_ck] / minivox);
        max_K = Math.max(max_K, vk);
        K[x] = vk;
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


    public int getMax_I() {
        return (int)max_I;
    }

    public int getMax_J() {
        return (int)max_J;
    }

    public int getMax_K() {
        return (int)max_K;
    }

    public int getSizeVoxelsFiltered() {
        return voxels_filtered.length;
    }

    public int getSizeVoxelsInitial() {
        return voxels_initial.length;
    }

    public int getI(int x) {
        return (int)I[x];
    }

    public int getJ(int x) {
        return (int)J[x];
    }

    public int getK(int x) {
        return (int)K[x];
    }

    public int getTrial(int x) {
        return (int)trials[x];
    }

    public double getProp(int x) {
        return prop[x];
    }

}
