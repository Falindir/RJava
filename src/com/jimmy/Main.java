package com.jimmy;

import com.jimmy.tools.Tools;
import com.jimmy.voxel.MixModTrans;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

public class Main {



    public static void main(String[] args) {

        System.out.println("MixModTrans Start");

        String input = "input/ALS_P15_1m.vox";
        String output = "output/ALS_P15_1m.out.vox";

        long startTime = System.currentTimeMillis();

        try {
            MixModTrans mmt = new MixModTrans(input, output, true);

            mmt.loadVoxelSpace();



        } catch (Exception e) {
            e.printStackTrace();
        }

        long time = System.currentTimeMillis() - startTime;

        System.out.println("That took " + Tools.elapsed(time));

    }



}
