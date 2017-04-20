package com.jimmy.voxel;

import com.jimmy.voxel.DF;
import com.jimmy.voxel.VoxelSpace;

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

    // nbre minimum de cellules échantillonnées pour appliquer le modèle glmer
    private int seuil_echantillonnage = 10;

    // nombre max de minivoxels -1 fusionnés pour le calcul du voisinage
    private int seuil_fusion = 0;

    private List<DF> df;

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

        df = voxel_space.createDF();
    }


    //TODO temps name
    public void bigLoop() {

        int i = 0;



        for (DF df : df) {

            double pred = Double.NaN;

            TrialsIntance tr = df.getTrialsIntance();
            int test_nona = tr.getP();
            int test_na = tr.getZ();

            if(test_nona > seuil_echantillonnage) {

                if(tr.allPPropAsEqualValue(1.0)) {
                    pred = 1;
                }
                else {
                    if(tr.allPPropAsEqualValue(0.0)) {
                        pred = Double.NaN;
                    }
                }


            }
            else {

            }



        }

    }


}
