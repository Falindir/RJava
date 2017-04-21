package com.jimmy.voxelv2;

import com.jimmy.tools.Tools;

/**
 * Created by jimmy on 21/04/17.
 */
public class Main2 {

    public static void main(String[] args) {

        System.out.println("MixModTrans Start");

        String input = "input/ALS_P15_1m.vox";
        //String input = "input/500x500.vox";
        String output = "output/ALS_P15_1m.out.vox";

        long startTime = System.currentTimeMillis();

        try {
            MixModTrans mmt = new MixModTrans(input, output, true);

            mmt.loadVoxelSpace();

            mmt.runTest();

        } catch (Exception e) {
            e.printStackTrace();
        }

        long time = System.currentTimeMillis() - startTime;

        System.out.println("That took " + Tools.elapsed(time));

    }

}
