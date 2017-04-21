package com.jimmy.voxelv2;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * Created by jimmy on 21/04/17.
 */
public class MixModTrans {

    private File input;

    private File output;

    private VoxelSpace voxel_space;

    public MixModTrans(String input, String output, boolean overwrite_output) throws Exception {
        this.input = new File(input);
        this.output = new File(output);

        if(!this.input.exists()) {
            throw new Exception("Input file not found : " + input);
        }

        if(this.output.exists() && !overwrite_output) {
            throw new Exception("I don't overwrite the output file : " + output);
        }
    }

    public void loadVoxelSpace() {

        try {
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(input));

            lineNumberReader.skip(Long.MAX_VALUE);

            int lines = lineNumberReader.getLineNumber();

            voxel_space = new VoxelSpace(lines, input);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void runTest() {

        voxel_space.loop();

    }
}
