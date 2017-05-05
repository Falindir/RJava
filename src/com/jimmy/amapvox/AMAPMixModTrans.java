package com.jimmy.amapvox;

import com.jimmy.tools.Tools;

import java.io.File;

/**
 * Created by jimmy on 02/05/17.
 */
public class AMAPMixModTrans {

    private File input;

    private File output;

    private int sizeInput = 0;

    private AMAPVoxelSpace voxelSpace;

    public AMAPMixModTrans(String input, String output, int sizeInput, boolean overwrite_output) throws Exception {
        this.input = new File(input);
        this.output = new File(output);

        if(sizeInput < 0) {
            System.out.println("NEED READ FILE");

        }else {
            this.sizeInput = sizeInput;
        }

        if(!this.input.exists()) {
            throw new Exception("Input file not found : " + input);
        }

        if(this.output.exists() && !overwrite_output) {
            throw new Exception("I don't overwrite the output file : " + output);
        }
    }

    public void loadVoxelSpace() {
        System.out.println("\n\nLoad Voxel Space Start");
        long startTime = System.currentTimeMillis();

        this.voxelSpace = new AMAPVoxelSpace(this);

        long time = System.currentTimeMillis() - startTime;
        System.out.println("Load Voxel Space End and took : " + Tools.elapsed(time));
    }

    public void createIJK() {
        System.out.println("\n\nCreate IJK Start");
        long startTime = System.currentTimeMillis();

        this.voxelSpace.createIJK();

        long time = System.currentTimeMillis() - startTime;
        System.out.println("Create IJK End and took : " + Tools.elapsed(time));
    }

    public void preprocess() {
        System.out.println("\n\nPre process Start");
        long startTime = System.currentTimeMillis();

        this.voxelSpace.preprocess();

        long time = System.currentTimeMillis() - startTime;
        System.out.println("Pre process End and took : " + Tools.elapsed(time));
    }

    public void loopR() {
        System.out.println("\n\nLoop R Start");
        long startTime = System.currentTimeMillis();

        this.voxelSpace.loop();

        long time = System.currentTimeMillis() - startTime;
        System.out.println("Loop R End and took : " + Tools.elapsed(time));
    }

    public void writeResult() {
        System.out.println("\n\nWrite result Start");
        long startTime = System.currentTimeMillis();

        //this.voxelSpace.postprocess();

        long time = System.currentTimeMillis() - startTime;
        System.out.println("Write result End and took : " + Tools.elapsed(time));

    }

    public File getInput() {
        return input;
    }

    public File getOutput() {
        return output;
    }

    public int getSizeInput() {
        return sizeInput;
    }
}
