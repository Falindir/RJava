package com.jimmy.amapvox;

/**
 * Created by jimmy on 02/05/17.
 */
public class AMAPPoint3D {

    private double x;
    private double y;
    private double z;

    public AMAPPoint3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getIntX() {
        return (int)x;
    }

    public int getIntY() {
        return (int)y;
    }

    public int getIntZ() {
        return (int)z;
    }

    public AMAPPoint3D floor() {
       double I = Math.floor(x / AMAPConstant.minivox);
       double J = Math.floor(y / AMAPConstant.minivox);
       double K = Math.floor(z / AMAPConstant.minivox);

       return new AMAPPoint3D(I, J, K);
    }


    @Override
    public String toString() {
        return "AMAPPoint3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
