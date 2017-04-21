package com.jimmy.voxel;

import com.jimmy.tools.Tools;
import com.jimmy.voxel.MixModTrans;

public class Main {



    public static void main(String[] args) {

        System.out.println("MixModTrans Start");

        String input = "input/ALS_P15_1m.vox";
        //String input = "input/500x500.vox";
        String output = "output/ALS_P15_1m.out.vox";

        long startTime = System.currentTimeMillis();

        long heapsize = Runtime.getRuntime().totalMemory();
        System.out.println("heapsize is :: " + heapsize);



        try {
            MixModTrans mmt = new MixModTrans(input, output, true);

            mmt.loadVoxelSpace();

            //mmt.runTest();

        } catch (Exception e) {
            e.printStackTrace();
        }

        long time = System.currentTimeMillis() - startTime;

        System.out.println("That took " + Tools.elapsed(time));

    }



}
