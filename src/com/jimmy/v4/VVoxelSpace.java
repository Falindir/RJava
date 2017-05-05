package com.jimmy.v4;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class VVoxelSpace {

    private VMixModTrans mix;

    private String title;
    private VPoint3D min;
    private VPoint3D max;
    private VPlot3D split;
    private String type;
    private String header;

    private VVoxel[] data;


    public VVoxelSpace(VMixModTrans mix) {
        this.mix = mix;
    }

    public void createSpace() {

        try (BufferedReader br = new BufferedReader(new FileReader(mix.getInputFile()))) {

            this.title = br.readLine();
            this.min = VTools.createPoint3D(br.readLine());
            this.max = VTools.createPoint3D(br.readLine());
            this.split = VTools.createPlot3D(br.readLine());
            this.type = br.readLine();
            this.header = br.readLine();

            VTools.xMax = this.split.getSizeX();
            VTools.yMax = this.split.getSizeY();

            System.out.println(this.split.toString());

            String line;

            this.data = new VVoxel[this.split.getSize1D()];

            int index = 0;

            while ((line = br.readLine()) != null) {

                String[] values = line.split(" ");

                data[index] = new VVoxel(
                        Integer.parseInt(values[0]),
                        Integer.parseInt(values[1]),
                        Integer.parseInt(values[2]),
                        Double.parseDouble(values[3]),
                        Double.parseDouble(values[4]),
                        Double.parseDouble(values[5]),
                        Double.parseDouble(values[6]),
                        Double.parseDouble(values[7]),
                        Double.parseDouble(values[8]),
                        Double.parseDouble(values[9]),
                        Integer.parseInt(values[10]),
                        Integer.parseInt(values[11]),
                        Double.parseDouble(values[12]),
                        Double.parseDouble(values[13]),
                        Double.parseDouble(values[14]),
                        Double.parseDouble(values[15]));

                index++;
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void preprocess() {
        int index = 0;

        int nI = 0;
        int nJ = 0;
        int nK = 0;

        int mI = this.split.getSizeMiniX();
        int mJ = this.split.getSizeMiniY();
        int mK = this.split.getSizeMiniZ();



       /*
                    for (VVoxel d : this.data) {
                        if(d.havePositiveRoundGroundDistance()) {
                            if(d.getNewI() == 0) {
                                if(d.getNewJ() == 0) {
                                    if(d.getNewK() == 0) {
                                        index++;
                                        System.out.println(index);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

*/

        System.out.println(index);
    }

    public void loop() {

    }

    public void postprocess() {

    }

    public void writeSpace() {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(mix.getOutput()))) {

            writeLine(br, this.title);
            writeLine(br, this.min.toString());
            writeLine(br, this.max.toString());
            writeLine(br, this.split.toString());
            writeLine(br, this.type);
            writeLine(br, this.header);

            for (VVoxel d : this.data) {

                writeLine(br, d.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeLine(BufferedWriter br, String l) throws IOException {
        br.write(l+"\n");
    }


}
