package com.jimmy.amap;

import java.io.File;

/**
 * Created by jimmy
 */
public class RMixModTrans {

    private File input;

    private File output;

    private RVoxelSpace voxelSpace;

    public RMixModTrans(String input, String output, boolean overwrite_output) throws Exception {
        this.input = new File(input);
        this.output = new File(output);

        this.voxelSpace = new RVoxelSpace(this);

        if(!this.input.exists()) {
            throw new Exception("Input file not found : " + input);
        }

        if(this.output.exists() && !overwrite_output) {
            throw new Exception("I don't overwrite the output file : " + output);
        }
    }

    public File getInput() {
        return input;
    }

    public File getOutput() {
        return output;
    }

    public RMixModTrans loadVoxelSpace() {
        System.out.println("\n\nLoad Voxel Space Start");
        long startTime = System.currentTimeMillis();

        this.voxelSpace.createSpace();

        long time = System.currentTimeMillis() - startTime;
        System.out.println("Load Voxel Space End and took : " + RTools.elapsed(time));

        return this;
    }

    public RMixModTrans preprocess() {
        System.out.println("\n\nPre process Start");
        long startTime = System.currentTimeMillis();

        this.voxelSpace.preprocess();

        long time = System.currentTimeMillis() - startTime;
        System.out.println("Pre process End and took : " + RTools.elapsed(time));

        return this;
    }

    public RMixModTrans loopR() {
        System.out.println("\n\nLoop R Start");
        long startTime = System.currentTimeMillis();

        this.voxelSpace.loop();

        long time = System.currentTimeMillis() - startTime;
        System.out.println("Loop R End and took : " + RTools.elapsed(time));

        return this;
    }

    public RMixModTrans postprocess() {
        System.out.println("\n\nPost process Start");
        long startTime = System.currentTimeMillis();

        this.voxelSpace.postprocess();

        long time = System.currentTimeMillis() - startTime;
        System.out.println("Post process End and took : " + RTools.elapsed(time));

        return this;
    }

    public RMixModTrans writeResult() {
        System.out.println("\n\nWrite result Start");
        long startTime = System.currentTimeMillis();

        this.voxelSpace.writeSpace();

        long time = System.currentTimeMillis() - startTime;
        System.out.println("Write result End and took : " + RTools.elapsed(time));

        return this;
    }
}
