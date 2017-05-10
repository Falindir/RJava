package com.jimmy.amapvox;

import java.io.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jimmy on 02/05/17.
 */
public class AMAPVoxelSpace {

    private String title;


    private AMAPMixModTrans modTrans;

    private float[][] init_data;

    private float[][] filtered_data;

    private String[] ijkInit;

    private float[][] tempVoxel;

    private List<String> collumn;

    private AMAPRCommunication Rcode;

    private int ground_distance = 0;

    private AMAPPoint3D split;

    private int nline = 0;

    private double[][][][] resultat;

    private float max_ground_distance = 0;

    private double[][] meadByGD;

    public AMAPVoxelSpace(AMAPMixModTrans modTrans) {
        this.modTrans = modTrans;

        this.init_data = new float[modTrans.getSizeInput()][8]; // i j k bvEntering ground_distance transmittance ID PadBVTotal

        this.Rcode = new AMAPRCommunication();

        this.createSpace();
    }


    private float getGroundDistance(String value) {
        float gd = Tools.round(Float.parseFloat(value));
        if(gd > 0) {
            ground_distance ++;
            max_ground_distance = Math.max(max_ground_distance, gd);
        }
        return gd;
    }

    private void createSpace() {

        nline = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(modTrans.getInput()))) {

            /* For skip test each line*/
            this.title = br.readLine();
            String l2 = br.readLine();
            String l3 = br.readLine();
            String l4 = br.readLine();
            String l5 = br.readLine();
            String l6 = br.readLine();

            this.split = this.createPoint3D(l4);

            System.out.println(this.split);

            ijkInit = new String[modTrans.getSizeInput()];

            collumn = Arrays.asList(l6.split(" "));

            String line;

            int position_i = collumn.indexOf("i");
            int position_j = collumn.indexOf("j");
            int position_k = collumn.indexOf("k");
            int position_bvIntercepted = collumn.indexOf("bvEntering");
            int position_transmittance = collumn.indexOf("transmittance");
            int position_ground_distance = collumn.indexOf("ground_distance");
            int position_PadBVTotal = collumn.indexOf("PadBVTotal");

            int max = Math.max(Math.max(position_bvIntercepted, position_transmittance), position_ground_distance);

            System.out.println("MAX POS : " + max);

            while ((line = br.readLine()) != null) {

                String[] values = line.split(" ");

                init_data[nline][0] = Float.parseFloat(values[position_i]);
                init_data[nline][1] = Float.parseFloat(values[position_j]);
                init_data[nline][2] = Float.parseFloat(values[position_k]);
                init_data[nline][3] = Float.parseFloat(values[position_bvIntercepted]);
                init_data[nline][4] = getGroundDistance(values[position_ground_distance]);
                init_data[nline][5] = Float.parseFloat(values[position_transmittance]);
                init_data[nline][6] = nline;
                init_data[nline][7] = Float.parseFloat(values[position_PadBVTotal]);

                nline++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createIJK() {
        StringBuilder value = new StringBuilder();
        for (int i = 0; i < nline; i++) {
            ijkInit[i] = value.append((int)(init_data[i][0])).append("_").append((int)(init_data[i][1])).append("_").append((int)(init_data[i][2])).toString();
            value.setLength(0);
        }
    }

    public void preprocess() {
        System.out.println("\n\nFilter Start");
        long startTime = System.currentTimeMillis();

        this.filterData();

        long time = System.currentTimeMillis() - startTime;
        System.out.println("Filter End and took : " + Tools.elapsed(time));

        System.out.println("\n\nCreate DF Start");
        startTime = System.currentTimeMillis();

        this.createDF();

        time = System.currentTimeMillis() - startTime;
        System.out.println("Create DF End and took : " + Tools.elapsed(time));
    }

    private void filterData() {

        filtered_data = new float[this.ground_distance][6]; // i j k trials prop id

        int j = 0;

        for (int i = 0; i < modTrans.getSizeInput(); i++) {
            if(init_data[i][4] > 0) {
                float trials = Tools.round(init_data[i][3] / AMAPConstant.EP);
                float success = Tools.round(init_data[i][3] * init_data[i][5] / AMAPConstant.EP);

                filtered_data[j][0] = (float)(Math.floor(init_data[i][0] / AMAPConstant.minivox));
                filtered_data[j][1] = (float)(Math.floor(init_data[i][1] / AMAPConstant.minivox));
                filtered_data[j][2] = (float)(Math.floor(init_data[i][2] / AMAPConstant.minivox));
                filtered_data[j][3] = trials;
                filtered_data[j][4] = success / trials;
                filtered_data[j][5] = i;

            j++;

            }

        }



    }

    private void createDF() {

        System.out.println("FILTER");

        AMAPPoint3D splitIJK = this.split.floor();

        int VI = splitIJK.getIntX()+1;
        int VJ = splitIJK.getIntY()+1;
        int VK = splitIJK.getIntZ()+1;

        System.out.println(splitIJK.toString());

        int[][][] nbIntanceIJK = new int[VI][VJ][VK];

        for (int i = 0; i < ground_distance; i++) {
            int vvi = (int)filtered_data[i][0];
            int vvj = (int)filtered_data[i][1];
            int vvk = (int)filtered_data[i][2];
            nbIntanceIJK[vvi][vvj][vvk]++;
        }

        int size = 0;
        for (int i = 0; i < VI; i++) {
            for (int j = 0; j < VJ; j++) {
                for (int k = 0; k < VK; k++) {
                    if(nbIntanceIJK[i][j][k] > 0) {
                        size++;
                    }
                }
            }
        }

        tempVoxel = new float[size][3];
        size = 0;
        for (int i = 0; i < VI; i++) {
            for (int j = 0; j < VJ; j++) {
                for (int k = 0; k < VK; k++) {
                    if(nbIntanceIJK[i][j][k] > 0) {
                        tempVoxel[size][0] = i;
                        tempVoxel[size][1] = j;
                        tempVoxel[size][2] = k;
                        size++;
                    }
                }
            }
        }

    }

    public void loop() {

        int[] gd = new int[(int)this.max_ground_distance];

        for (int i = 0; i < this.nline; i++) {

            int ground = (int) init_data[i][4];

            if(ground > 0)
                gd[ground-1]++;

        }

        resultat = new double[this.split.getIntX()][this.split.getIntY()][this.split.getIntZ()][1];

        int hi;
        int hj;
        int hk;

        for (float[] init : this.init_data) {
            hi = (int)init[0];
            hj = (int)init[1];
            hk = (int)init[2];
            resultat[hi][hj][hk][0] = Double.NaN;
        }


        int meanN = 0;
        int fus = 0;
        int val1 = 0;

        for (int i = 0; i < tempVoxel.length; i++) {

            if (i % 100==0) {
                System.out.println(i);
            }

            float TI = tempVoxel[i][0];
            float TJ = tempVoxel[i][1];
            float TK = tempVoxel[i][2];

            int sizeP = 0;
            int sizeZ = 0;

            int size = 0;

            for(float[] v : filtered_data) {
                if((TI == v[0]) && (TJ == v[1]) && (TK == v[2])) {
                    size++;
                    if(v[3] > 0) {
                        sizeP++;
                    } else if(v[3] == 0) {
                        sizeZ++;
                    }
                }
            }

            int sP = 0;
            int sZ = 0;
            int sT = 0;

            String[] ijkT = new String[size];

            String[] ijkP = new String[sizeP];
            double[] propP = new double[sizeP];
            double[] trialsP = new double[sizeP];

            String[] ijkZ = new String[sizeZ];

            for(float[] v : filtered_data) {
                String ijk = ijkInit[(int)v[5]];
                float VT = v[3];
                float VP = v[4];

                if((TI == v[0]) && (TJ == v[1]) && (TK == v[2])) {
                    ijkT[sT] = ijk;
                    sT++;
                    if(VT > 0) {
                        ijkP[sP] = ijk;
                        propP[sP] = VP;
                        trialsP[sP] = VT;
                        sP++;
                    } else if(VT == 0) {
                        ijkZ[sZ] = ijk;
                        sZ++;
                    }
                }
            }

            if(sizeP > AMAPConstant.seuil_echantillonnage) {

                boolean allOne = true;
                for(double p : propP) {
                    if(p != 1.0) {
                        allOne = false;
                        break;
                    }
                }

                if(allOne) {

                    val1++;

                    for(String s : ijkT) {
                        String[] values = s.split("_");
                        int wi = Integer.parseInt(values[0]);
                        int wj = Integer.parseInt(values[1]);
                        int wk = Integer.parseInt(values[2]);
                        resultat[wi][wj][wk][0] = 1.0;
                    }
                }
                else {
                    boolean allZero = true;
                    for(double p : propP) {
                        if(p != 0) {
                            allZero = false;
                            break;
                        }
                    }

                    if(allZero) {
                        //System.out.print("ALLZERO" + tempVoxel.toString());
                    }
                    else {

                        double[] res = Rcode.Jglme(propP, trialsP, ijkP);

                        int index = 0;

                        for(String s : ijkP) {
                            String[] values = s.split("_");
                            int wi = Integer.parseInt(values[0]);
                            int wj = Integer.parseInt(values[1]);
                            int wk = Integer.parseInt(values[2]);

                            resultat[wi][wj][wk][0] = res[index];
                            index++;
                        }

                        if(sizeZ > 0) {
                            meanN++;
                            double mean = 0.0;

                                for (double r : res) {
                                    mean += r;
                                }
                                mean = (mean / (double)res.length);


                                for(String s : ijkZ) {

                                String[] values = s.split("_");
                                int wi = Integer.parseInt(values[0]);
                                int wj = Integer.parseInt(values[1]);
                                int wk = Integer.parseInt(values[2]);

                                resultat[wi][wj][wk][0] = mean;
                                index++;
                                }
                        }
                        else {

                        }
                    }
                }

                fus=0;
            }
            else {

                if(fus < AMAPConstant.seuil_fusion) {
                    fus += 1;
                }
                else {

                }

            }

            Rcode.endConnection();
        }


    }

    public void postprocess() {
        int nbNanMena = 0;
         meadByGD = new double[(int)this.max_ground_distance+1][3];


        int print = 0;
        int nAV = 0;
        for (int i = 0; i < this.nline; i++) {

            int ground = (int) init_data[i][4];

            if(ground > 0) {
                int wi = (int) init_data[i][0];
                int wj = (int) init_data[i][1];
                int wk = (int) init_data[i][2];

                double pred = resultat[wi][wj][wk][0];

                if(!Double.isNaN(pred)) {
                    pred = AMAPVoxelSpace.trunc(pred, 7);
                }

                if(!Double.isNaN(pred)) {
                    if(ground < 10) {
                        if(print < 100) {
                            print++;
                        }
                    }

                    meadByGD[ground-1][0] += pred;
                    meadByGD[ground-1][1]++;
                }
                else {
                    nAV++;
                }
            }
        }


        for (int id = 0; id < this.max_ground_distance; id++) {
            meadByGD[id][2] = meadByGD[id][0] / meadByGD[id][1];
        }

        for (int i = 0; i < this.nline; i++) {
            int ground = (int) init_data[i][4];

            if(ground > 0) {
                int wi = (int) init_data[i][0];
                int wj = (int) init_data[i][1];
                int wk = (int) init_data[i][2];

                double pred = resultat[wi][wj][wk][0];

                if(Double.isNaN(pred)) {
                    pred = meadByGD[ground-1][2];
                }

                init_data[i][5] = (float) pred;

                if(!Double.isNaN(pred)) {
                    init_data[i][7] = (float) (-2 * Math.log(pred));
                }

                if(init_data[i][5] > 0.99) {
                    init_data[i][5] = 1;
                }

            }
            else {
                init_data[i][7] = 0;
            }
        }


    }

    public void writeResult() {

        nline = 0;

        try (BufferedWriter bro = new BufferedWriter(new FileWriter(modTrans.getOutput()))) {

        try (BufferedReader br = new BufferedReader(new FileReader(modTrans.getInput()))) {

            /* For skip test each line*/
            String l1 = br.readLine();
            String l2 = br.readLine();
            String l3 = br.readLine();
            String l4 = br.readLine();
            String l5 = br.readLine();
            String l6 = br.readLine();

            writeLine(bro, l1);
            writeLine(bro, l2);
            writeLine(bro, l3);
            writeLine(bro, l4);
            writeLine(bro, l5);
            writeLine(bro, l6);

            int position_i = collumn.indexOf("i");
            int position_j = collumn.indexOf("j");
            int position_k = collumn.indexOf("k");
            int position_transmittance = collumn.indexOf("transmittance");
            int position_PadBVTotal = collumn.indexOf("PadBVTotal");

            String line;

            while ((line = br.readLine()) != null) {

                String[] values = line.split(" ");

                String newL = "";

                for (int i = 0; i < values.length; i++) {
                    if(i == position_i) {
                        newL += Integer.toString((int)init_data[nline][0]);
                    }
                    else if(i == position_j) {
                        newL += Integer.toString((int)init_data[nline][1]);
                    }
                    else if(i == position_k) {
                        newL += Integer.toString((int)init_data[nline][2]);
                    }
                    else if(i == position_PadBVTotal) {
                        newL += Float.toString(init_data[nline][7]);
                    }
                    else if(i == position_transmittance) {
                        newL += Float.toString(init_data[nline][5]);
                    }
                    else {
                        newL += values[i];
                    }

                    if(i != values.length - 1)
                        newL += " ";

                }

                writeLine(bro, newL);

                nline++;
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

        bro.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private AMAPPoint3D createPoint3D(String s) {
        String[] tab =  s.split(":")[1].split(" ");
        return new AMAPPoint3D(Double.parseDouble(tab[1]), Double.parseDouble(tab[2]), Double.parseDouble(tab[3]));
    }

    public static double trunc(double d, int decimalPlace) {
        if(Double.isNaN(d))
            return d;

        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    private void writeLine(BufferedWriter br, String l) throws IOException {
        br.write(l+"\n");
    }

}
