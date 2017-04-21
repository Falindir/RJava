package com.jimmy.voxel;

import com.jimmy.tools.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VoxelSpace {

    private VoxHeader header;
    private VoxelsArray voxels;
    private TrialsIntance[][][] trialsIntance;

    private List<DF> df;

    //distance de voisinage (en cellules)
    private int minivox = 5;

    // pas de discrétisation des volumes élémentaires
    private double  EP = 0.01;

    public VoxelSpace(List<String> list, int header_size) {


        this.createSpace(list, header_size);

        df = this.createDF();
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

    private void createSpace(List<String> list, int header_size) {

        createHeader(list);

        System.out.println(header);

        int nbElement = list.size()-header_size;

        double groud_distance = 0;

        int index_ground_distance = header.getIndexColumn("ground_distance");

        int size_after = 0;
        for (int i = 0; i < list.size()-header_size; i++) {
            List<String> tab = Arrays.asList(list.get(i+header_size).split(" "));

            groud_distance = Double.parseDouble(tab.get(index_ground_distance));

            if(Tools.round(groud_distance) > 0) {
                size_after++;
            }
        }

        voxels = new VoxelsArray(header, nbElement, header.getNumberColumn(), size_after);

        int filter = 0;

        for (int i = 0; i < list.size()-header_size; i++) {
            List<String> tab = Arrays.asList(list.get(i + header_size).split(" "));

            groud_distance = Double.parseDouble(tab.get(index_ground_distance));

            if (Tools.round(groud_distance) > 0) {

                for (int j = 0; j < tab.size(); j++) {
                    voxels.add_filtered(filter, j, Double.parseDouble(tab.get(j)));
                    voxels.add_gmle(filter, j, Double.parseDouble(tab.get(j)));
                    voxels.add_ijk(filter)
                            .add_I(filter, minivox)
                            .add_J(filter, minivox)
                            .add_K(filter, minivox)
                            .add_trials(filter, EP)
                            .add_success(filter, EP)
                            .add_prop(filter);
                }

                filter++;
            } else {

                for (int j = 0; j < tab.size(); j++) {
                    voxels.add_initial(i, j, Double.parseDouble(tab.get(j)));
                }
            }
        }
    }



    public VoxHeader getHeader() {
        return header;
    }

    public VoxelsArray getVoxels() {
        return voxels;
    }

    private List<DF> createDF() {

        int sx = voxels.getMax_I()+1;
        int sy = voxels.getMax_J()+1;
        int sz = voxels.getMax_K()+1;

        int [][][] number_instance = new int[sx][sy][sz];

        trialsIntance = new TrialsIntance [sx][sy][sz];

        for (int i = 0; i < sx; i++) {
            for (int j = 0; j < sy; j++) {
                for (int k = 0; k < sz; k++) {
                    number_instance[i][j][k] = 0;
                    trialsIntance[i][j][k] = new TrialsIntance();
                }
            }
        }

        for (int i = 0; i < voxels.getSizeVoxelsFiltered(); i++) {

            int I = voxels.getI(i);
            int J = voxels.getJ(i);
            int K = voxels.getK(i);
            int trial = voxels.getTrial(i);
            double prop = voxels.getProp(i);

            TrialsIntance tr = trialsIntance[I][J][K];

            number_instance[I][J][K]++;

            incTrials(tr, trial, prop);

        }

        df = new ArrayList<>();

        for (int i = 0; i < sx; i++) {
            for (int j = 0; j < sy; j++) {
                for (int k = 0; k < sz; k++) {
                    int nbi = number_instance[i][j][k];
                    if(nbi > 0) {
                        DF dfi = new DF(i, j, k, nbi, trialsIntance[i][j][k]);
                        df.add(dfi);
                    }
                }
            }
        }

        System.out.println(df.get(0).getTrialsIntance().getZ());
        System.out.println(df.get(df.size()-1).getTrialsIntance().getZ());

        return df;
    }

    private void incTrials(TrialsIntance tr, int trial, double prop) {
        if(trial == 0) {
                tr.addZero(prop);
        } else if(trial > 0) {
                tr.addPositive(prop);
        }
    }

    public List<DF> getDf() {
        return df;
    }
}