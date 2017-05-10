package com.jimmy.amapvox;

import org.rosuda.JRI.Rengine;

public class AMAPRCommunication {

    private Rengine r;

    public AMAPRCommunication() {
        r = new Rengine(new String[]{"--no-save"}, false, new TextConsole());

        r.eval("source('gregoire/AMAPglme.R')");
    }

    public double[] Jglme(double[] prop, double[] trials, String[] ijk) {

        r.assign("prop", prop);
        r.assign("trials", trials);
        r.assign("ijk", ijk);



        double[] res = r.eval("Jcode()").asDoubleArray();

        return res;

        /*double mean = 0.0;

        for(double d : res) {
            mean += d;
        }

        return (float)(mean / res.length);*/
    }

    public void endConnection() {
        r.end();
    }

}
