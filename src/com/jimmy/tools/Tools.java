package com.jimmy.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

/**
 * Created by jimmy on 19/04/17.
 */
public class Tools {

    public static float round(float d) {
        if(Double.isNaN(d))
            return Float.NaN;

        return new BigDecimal(d).setScale(0, RoundingMode.HALF_DOWN)
                .floatValue();
    }

    public static float mean(float d1, float d2) {
        //if(Float.isNaN(d1))
        //    return d2;

        //if(Float.isNaN(d2))
        //    return d1;

        return ( d1 + d2 ) / 2;
    }

    // TODO 3D to 1D and reverse

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

}
