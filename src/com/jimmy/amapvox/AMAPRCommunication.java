package com.jimmy.amapvox;

import com.jimmy.tools.TextConsole;
import org.rosuda.JRI.Rengine;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPInteger;
import org.rosuda.REngine.REXPString;
import org.rosuda.REngine.RList;

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
