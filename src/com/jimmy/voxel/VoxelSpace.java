package com.jimmy.voxel;

import com.jimmy.tools.Tools;

import java.util.Arrays;
import java.util.List;

public class VoxelSpace {

    private VoxHeader header;
    private VoxelsArray voxels;

    //distance de voisinage (en cellules)
    private int minivox = 5;

    // pas de discrétisation des volumes élémentaires
    private double  EP = 0.01;

    // nbre minimum de cellules échantillonnées pour appliquer le modèle glmer
    private int seuil_echantillonnage = 10;

    // nombre max de minivoxels -1 fusionnés pour le calcul du voisinage
    private int seuil_fusion = 0;

    public VoxelSpace() {
    }

    private void createHeader(List<String> list) {
        Point3D min = createPoint3D(list.get(1));
        Point3D max = createPoint3D(list.get(2));
        Point3D split = createPoint3D(list.get(3));

        String extra = list.get(4);

        List<String> items = Arrays.asList(list.get(5).split(" "));

        header = new VoxHeader(min, max, split, items, extra);
    }

    private Point3D createPoint3D(String s) {
        String[] tab =  s.split(":")[1].split(" ");
        return new Point3D(Double.parseDouble(tab[1]), Double.parseDouble(tab[2]), Double.parseDouble(tab[3]));
    }

    public void createSpace(List<String> list, int header_size) {

        createHeader(list);

        System.out.println(header);

        int nbElement = list.size()-header_size;

        voxels = new VoxelsArray(header, nbElement, header.getNumberColumn());

        int index_ground_distance = header.getIndexColumn("ground_distance");

        int size_after = 0;
        for (int i = 0; i < list.size()-header_size; i++) {
            List<String> tab = Arrays.asList(list.get(i+header_size).split(" "));

            double groud_distance = Double.parseDouble(tab.get(index_ground_distance));

            if(Tools.round(groud_distance) > 0) {
                size_after++;
            }
        }

        System.out.println(size_after);

        for (int i = 0; i < list.size()-header_size; i++) {
            List<String> tab = Arrays.asList(list.get(i+header_size).split(" "));
            for(int j = 0; j < tab.size(); j++) {
                voxels.add(i, j, Double.parseDouble(tab.get(j)));
            }

            voxels.add_ijk(i)
                    .add_I(i, minivox)
                    .add_J(i, minivox)
                    .add_K(i, minivox)
                    .add_trials(i, EP)
                    .add_success(i, EP)
                    .add_prop(i);
        }
    }



    public VoxHeader getHeader() {
        return header;
    }

}