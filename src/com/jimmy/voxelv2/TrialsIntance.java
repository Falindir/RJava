package com.jimmy.voxelv2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jimmy on 21/04/17.
 */
public class TrialsIntance {

    private List<String> ijkP;

    private List<String> ijkZ;

    private List<Double> p_prop;

    private List<Double> z_prop;

    public TrialsIntance(){
        p_prop = new ArrayList<>();
        z_prop = new ArrayList<>();
        ijkP = new ArrayList<>();
        ijkZ = new ArrayList<>();
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

    @Override
    public String toString() {
        return "TrialsIntance{" +
                "p_prop=" + p_prop.size() +
                ", z_prop=" + z_prop.size() +
                '}';
    }

    public List<String> getIjkP() {
        return ijkP;
    }

    public List<String> getIjkZ() {
        return ijkZ;
    }

    public void addValue(int trial, double prop, String ijk) {
        if(trial == 0) {
            addZero(prop);
            ijkP.add(ijk);
        } else if(trial > 0) {
            addPositive(prop);
            ijkZ.add(ijk);
        }
    }
}
