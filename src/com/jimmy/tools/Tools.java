package com.jimmy.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

/**
 * Created by jimmy on 19/04/17.
 */
public class Tools {

    public static double round(double d) {
        if(Double.isNaN(d))
            return Double.NaN;

        return new BigDecimal(d).setScale(0, RoundingMode.HALF_DOWN)
                .doubleValue();
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

}
