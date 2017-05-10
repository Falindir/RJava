package com.jimmy.amap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jimmy
 */
public class RVoxelSpace {

    private RPlot3D split;

    private List<String> collumn;

    private RVoxel[] data;

    private int[] gd;

    private RMixModTrans mix;

    private int ground_distance = 0;
    private double max_ground_distance;

    public RVoxelSpace(RMixModTrans mix) {
        this.mix = mix;
    }


    public void createSpace() {
        try (BufferedReader br = new BufferedReader(new FileReader(mix.getInput()))) {

            /* 6 line header file */
            String l1= br.readLine();
            String l2 = br.readLine();
            String l3 = br.readLine();
            split = RTools.createPlot3D(br.readLine());
            String l5 = br.readLine();
            collumn = Arrays.asList(br.readLine().split(" "));

            data = new RVoxel[split.getSize1D()];

            String line;

            int nline = 0;

            while ((line = br.readLine()) != null) {

                String[] values = line.split(" ");

                data[nline] = new RVoxel(
                        Integer.parseInt(values[collumn.indexOf("i")]),
                        Integer.parseInt(values[collumn.indexOf("j")]),
                        Integer.parseInt(values[collumn.indexOf("k")]),
                        Double.parseDouble(values[collumn.indexOf("bvEntering")]),
                        getGroundDistance(values[collumn.indexOf("ground_distance")]),
                        Double.parseDouble(values[collumn.indexOf("transmittance")]),
                        Double.parseDouble(values[collumn.indexOf("PadBVTotal")]));

                nline++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void preprocess() {

        gd = new int[(int)this.max_ground_distance];

        for (int i = 0; i < this.split.getSize1D(); i++) {
            int ground = data[i].getGround_distance();
            if(ground > 0) {
                gd[ground - 1]++;
            }
        }
    }

    public void loop() {

    }

    public void postprocess() {

    }

    public void writeSpace() {

    }

    private int getGroundDistance(String value) {
        int gd = (int)RTools.round(Double.parseDouble(value));
        if(gd > 0) {
            ground_distance++;
            max_ground_distance = Math.max(max_ground_distance, gd);
        }
        return gd;
    }
}
