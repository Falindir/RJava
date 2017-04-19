package com.jimmy.voxel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class MixModTrans {

    private int header_input = 6; //le nombre de ligne du header de input.vox

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

        List<String> list = new LinkedList<>();

        try (Stream<String> stream = Files.lines(input.toPath())) {

            list = stream.collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        voxel_space = new VoxelSpace();

        voxel_space.createSpace(list, header_input);
    }

}
