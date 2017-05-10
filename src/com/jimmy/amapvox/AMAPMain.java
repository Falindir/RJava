package com.jimmy.amapvox;

public class AMAPMain {

    public static void main(String[] args) {

        String inputPath = "input/ALS_P15_1m.vox";
        int sizeFile = 236550;
        //String inputPath = "input/Paracou_tile40_2013.vox";
        //int sizeFile = 20000006;
        String outputPath = "output/ALS_P15_1m.out.vox";

        System.out.println("MixModTrans Start");

        long startTime = System.currentTimeMillis();

        try {

            AMAPMixModTrans modTrans = new AMAPMixModTrans(inputPath, outputPath, sizeFile, true);
            modTrans.loadVoxelSpace();
            modTrans.createIJK();
            modTrans.preprocess();
            modTrans.loopR();
            modTrans.postprocess();
            modTrans.writeResult();

        } catch (Exception e) {
            e.printStackTrace();
        }

        long time = System.currentTimeMillis() - startTime;

        System.out.println("\nAMAP took " + Tools.elapsed(time));
    }
}
