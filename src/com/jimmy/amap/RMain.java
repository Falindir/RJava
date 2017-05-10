package com.jimmy.amap;

/**
 * Created by jimmy
 */
public class RMain {

    public static void main(String[] args) {

        String inputPath = "input/ALS_P15_1m.vox";
        String outputPath = "output/ALS_P15_1m.out2.vox";

        //String inputPath = "input/Paracou_tile40_2013.vox";
        //String outputPath = "output/Paracou_tile40_2013.vox";

        System.out.println("Input : " + inputPath);
        System.out.println("Output : " + outputPath);

        long startTime = System.currentTimeMillis();

        try {

            new RMixModTrans(inputPath, outputPath, true)
                    .loadVoxelSpace()
                    .preprocess()
                    .loopR()
                    .postprocess()
                    .writeResult();

        } catch (Exception e) {
            e.printStackTrace();
        }

        long time = System.currentTimeMillis() - startTime;

        System.out.println("\n\nThat took " + RTools.elapsed(time));
    }

}
