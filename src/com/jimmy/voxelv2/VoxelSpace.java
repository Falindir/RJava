package com.jimmy.voxelv2;

import com.jimmy.tools.Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by jimmy on 21/04/17.
 */
public class VoxelSpace {

    private int size;

    private int size_filter;

    private File input;

    private double[][] init_data;

    private double[][] filtered_data;

    private String[] ijk;

    private double[][][] ijk_prop;

    private List<String> collumn;

    private int ground_distance = 0;

    private double max_vi = 0;
    private double max_vj = 0;
    private double max_vk = 0;

    private double max_I = 0;
    private double max_J = 0;
    private double max_K = 0;

    private int [][][] number_instance;
    private TrialsIntance[][][] trialsIntance;

    private int DF = 0;

    public VoxelSpace(int size, File input) {

        this.size = size;

        this.input = input;

        this.init_data = new double[size][6]; // i j k bvEntering ground_distance transmittance

        createSpace();
        filterData();
        createDF();

    }

    private void createSpace() {

        int nline = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(input))) {

            /* For skip test each line*/
            String l1 = br.readLine();
            String l2 = br.readLine();
            String l3 = br.readLine();
            String l4 = br.readLine();
            String l5 = br.readLine();
            String l6 = br.readLine();

            collumn = Arrays.asList(l6.split(" "));

            String line;

            int position_i = collumn.indexOf("i");
            int position_j = collumn.indexOf("j");
            int position_k = collumn.indexOf("k");
            int position_bvIntercepted = collumn.indexOf("bvIntercepted");
            int position_transmittance = collumn.indexOf("transmittance");
            int position_ground_distance = collumn.indexOf("ground_distance");

            int j;

            while ((line = br.readLine()) != null) {

                /*if(((double) nline % (double)100000) == 0.0)
                    System.out.println(nline);
                */
                j = 0;

                for (String value : Arrays.asList(line.split(" "))) {
                    if(j == position_i) {
                        double v = Double.parseDouble(value);
                        max_vi = Math.max(max_vi, v);
                        init_data[nline][0] = v;
                    }
                    else if(j == position_j) {
                        double v = Double.parseDouble(value);
                        max_vj = Math.max(max_vj, v);
                        init_data[nline][1] = v;
                    }
                    else if(j == position_k) {
                        double v = Double.parseDouble(value);
                        max_vk = Math.max(max_vk, v);
                        init_data[nline][2] = v;
                    }
                    else if(j == position_bvIntercepted) {
                        init_data[nline][3] = Double.parseDouble(value);
                    }
                    else if(j == position_ground_distance) {
                        double gd = Tools.round(Double.parseDouble(value));
                        init_data[nline][4] = gd;
                        if(gd > 0) {
                            ground_distance ++;
                        }
                    }
                    else if(j == position_transmittance) {
                        init_data[nline][5] = Double.parseDouble(value);
                    }

                    j++;
                }

                nline++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void filterData() {

        this.filtered_data = new double[ground_distance][5]; // I J K trials prop
        this.ijk = new String[ground_distance];

        ijk_prop = new double[((int) max_vi)+1][((int) max_vj)+1][((int) max_vk)+1];

        int index = 0;

        for (int i = 0; i < size; i++) {
            if(init_data[i][4] > 0) {

                double vi = init_data[i][0];
                double vj = init_data[i][1];
                double vk = init_data[i][2];

                ijk[index] = (int)vi + "_" + (int)vj + "_" + (int)vk;
                ijk_prop[(int)vi][(int)vj][(int)vk] = init_data[i][4];

                double I = Math.floor(vi / Constant.minivox);
                double J = Math.floor(vj / Constant.minivox);
                double K = Math.floor(vk / Constant.minivox);

                max_I = Math.max(max_I, I);
                max_J = Math.max(max_J, J);
                max_K = Math.max(max_K, K);

                filtered_data[index][0] = I;
                filtered_data[index][1] = J;
                filtered_data[index][2] = K;

                double trials = Tools.round(init_data[i][3] / Constant.EP);
                double success = Tools.round(init_data[i][3] * init_data[i][5] / Constant.EP);
                filtered_data[index][3] = trials;
                filtered_data[index][4] = trials / success;

                index++;
            }
        }

    }

    private void createDF() {

        int sx = (int)max_I+1;
        int sy = (int)max_J+1;
        int sz = (int)max_K+1;

        number_instance = new int[sx][sy][sz];
        trialsIntance = new TrialsIntance[sx][sy][sz];

        for (int i = 0; i < sx; i++) {
            for (int j = 0; j < sy; j++) {
                for (int k = 0; k < sz; k++) {
                    number_instance[i][j][k] = 0;
                    trialsIntance[i][j][k] = new TrialsIntance();
                }
            }
        }

        for (int i = 0; i < ground_distance; i++) {
            int I = (int)filtered_data[i][0];
            int J = (int)filtered_data[i][1];
            int K = (int)filtered_data[i][2];
            int trial = (int)filtered_data[i][3];
            double prop = filtered_data[i][4];

            number_instance[I][J][K]++;
            trialsIntance[I][J][K].addValue(trial, prop, ijk[i]);
        }
/*
        for (int i = 0; i < sx; i++) {
            for (int j = 0; j < sy; j++) {
                for (int k = 0; k < sz; k++) {
                    int nbi = number_instance[i][j][k];
                    if (nbi > 0) {
                        DF++;
                    }
                }
            }
        }
*/
    }

    public void loop() {
        int sx = (int)max_I+1;
        int sy = (int)max_J+1;
        int sz = (int)max_K+1;

        for (int i = 0; i < sx; i++) {
            for (int j = 0; j < sy; j++) {
                for (int k = 0; k < sz; k++) {
                    if (number_instance[i][j][k] > 0) {

                        List<String> lijk = trialsIntance[i][j][k].getIjkP();

                        String[][] data_glme4 = new String[lijk.size()][2];

                        int index = 0;

                        for(String sijk : lijk) {
                            List<String> values = Arrays.asList(sijk.split("_"));

                            int vi = Integer.parseInt(values.get(0));
                            int vj = Integer.parseInt(values.get(1));
                            int vk = Integer.parseInt(values.get(2));

                            data_glme4[index][0] = sijk;
                            data_glme4[index][1] = String.valueOf(ijk_prop[vi][vj][vk]);

                            System.out.println(data_glme4[index][0] + " - " + data_glme4[index][1]);

                            index++;
                        }

                        System.out.println("\n\n");
                    }
                }
            }
        }
    }

}
