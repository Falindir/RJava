package com.jimmy.voxel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jimmy on 20/04/17.
 */

public class TrialsIntance {

    private List<Double> p_prop;

    private List<Double> z_prop;

    public TrialsIntance(){
        p_prop = new ArrayList<>();
        z_prop = new ArrayList<>();
    }

    public int getP() {
        return p_prop.size();
    }

    public int getZ() {
        return z_prop.size();
    }

    public void addPositive(double prop) {
        p_prop.add(prop);
    }

    public void addZero(double prop) {
        z_prop.add(prop);
    }

    public boolean allPPropAsEqualValue(double value) {

        for (double v: p_prop) {
            if(v!=value) {
                return false;
            }
        }

        return true;
    }

}
