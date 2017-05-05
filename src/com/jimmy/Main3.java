package com.jimmy;

import com.jimmy.tools.TextConsole;
import org.rosuda.JRI.Rengine;

import java.util.Arrays;

public class Main3 {

    public static void main(String[] args) {

        float f = (float) 0.01;

        System.out.println(f);

/*
        double[][] mat = new double[10][10];

        double cnt = 0;

        for (int i = 0; i < mat.length; ++i) {
            for (int j = 0; j < mat[i].length; ++j) {
                mat[i][j] = cnt;
                cnt = cnt + 1.0;
            }
        }

        System.out.println("rJava Test 3");

        System.out.println(Arrays.deepToString(mat));

        Rengine r = new Rengine(new String[]{"--no-save"}, false, new TextConsole());

        r.eval("source('mainRJava.R')");

        assignMatDouble(r, mat);

        double[][] rmat = r.eval("foo(res)").asMatrix();
        System.out.println(Arrays.deepToString(rmat));

        r.eval("bar()");

        r.eval("stp()");

        System.out.println("END");

        r.end();*/

    }

    public static void assignMatDouble(Rengine r, double[][] mat) {

        r.assign("res", mat[0]);
        for (int i = 1; i < mat.length; i++) {
            r.assign("tmp", mat[i]);
            r.eval("res<-rbind(res,tmp)");
        }

    }



}
