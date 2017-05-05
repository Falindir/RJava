package com.jimmy.v4;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

public class VTools {

    public static int xMax = 0;
    public static int yMax = 0;
    public static int zMax = 0;

    public static double round(double d) {
        if(Double.isNaN(d))
            return Double.NaN;

        return new BigDecimal(d).setScale(0, RoundingMode.HALF_DOWN)
                .floatValue();
    }

    public static double mean(double d1, double d2) {
        return ( d1 + d2 ) / 2;
    }

    public static int to1D( int x, int y, int z ) {
        return (z * xMax * yMax) + (y * xMax) + x;
    }

    public static int[] to3D( int idx ) {
        final int z = idx / (xMax * yMax);
        idx -= (z * xMax * yMax);
        final int y = idx / xMax;
        final int x = idx % xMax;
        return new int[]{ x, y, z };
    }

    public static String elapsed(long duration) {
        final TimeUnit scale = TimeUnit.MILLISECONDS;

        long hours = scale.toHours(duration);
        duration -= TimeUnit.HOURS.toMillis(hours);
        long minutes = scale.toMinutes(duration);
        duration -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = scale.toSeconds(duration);
        duration -= TimeUnit.SECONDS.toMillis(seconds);
        long millis = scale.toMillis(duration);

        return String.format(
                "%d hours, %d minutes, %d seconds, %d millis",
                hours, minutes, seconds, millis );
    }

    public static VPoint3D createPoint3D(String s) {
        String[] tab =  s.split(":");
        String[] tab2 = tab[1].split(" ");
        return new VPoint3D(tab[0], tab2[1], tab2[2], tab2[3]);
    }

    public static VPlot3D createPlot3D(String s) {
        String[] tab =  s.split(":");
        String[] tab2 = tab[1].split(" ");
        return new VPlot3D(tab[0], tab2[1], tab2[2], tab2[3]);
    }
}
