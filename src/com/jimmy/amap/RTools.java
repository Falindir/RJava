package com.jimmy.amap;

import java.util.concurrent.TimeUnit;

/**
 * Created by jimmy
 */
public class RTools {

    public static double round(double d) {
        if(Double.isNaN(d))
            return Double.NaN;

        int temp = (int)d;

        if(d < 1 && d > 0.5)
            return 1;

        if(d <= 0.5 && d >= 0)
            return 0;

        if(temp % 2 == 0) {
            if(d > 0) {
                double temp2 = temp + 0.5;
                if(d > temp2) {
                    return Math.round(d) ;
                }
                else if(d < temp2) {
                    return Math.round(d);
                }
                else {
                    return Math.round(d) - 1;
                }
            }
            else {
                return Math.round(d);
            }
        }
        else if(temp % 2 != 0) {
            if(d > 0) {
                return Math.round(d);
            }
            else {
                double temp2 = temp - 0.5;

                if(d > temp2) {
                    return Math.round(d);
                }
                else if(d < temp2) {
                    return Math.round(d);
                }
                else {
                    return Math.round(d) - 1 ;
                }
            }
        }
        return 0;
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

    public static RPlot3D createPlot3D(String s) {
        String[] tab =  s.split(":");
        String[] tab2 = tab[1].split(" ");
        return new RPlot3D(tab[0], tab2[1], tab2[2], tab2[3]);
    }

}
