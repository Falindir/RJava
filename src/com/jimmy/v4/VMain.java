package com.jimmy.v4;

/**
 * Created by jimmy on 04/05/17.
 */
public class VMain {

    public static void main(String[] args) {

        String inputPath = "input/ALS_P15_1m.vox";
        //String inputPath = "input/Paracou_tile40_2013.vox";
        String outputPath = "output/ALS_P15_1m.out.vox";
        //String outputPath = "output/Paracou_tile40_2013.vox";

        System.out.println("Input : " + inputPath);
        System.out.println("Output : " + outputPath);

        long startTime = System.currentTimeMillis();

        try {

            new VMixModTrans(inputPath, outputPath, true)
                    .loadVoxelSpace()
                    .preprocess()
                    .loopR()
                    .postprocess()
                    .writeResult();

        } catch (Exception e) {
            e.printStackTrace();
        }

        long time = System.currentTimeMillis() - startTime;

        System.out.println("\nThat took " + VTools.elapsed(time));
    }
}
