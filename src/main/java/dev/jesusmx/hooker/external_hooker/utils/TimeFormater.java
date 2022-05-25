package dev.jesusmx.hooker.external_hooker.utils;

import org.apache.commons.lang.time.DurationFormatUtils;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class TimeFormater {

    public static String format(long duration) {
        if (duration < TimeUnit.MINUTES.toMillis(1L)) {
            return String.valueOf(ThreadLocal.withInitial(() -> new DecimalFormat("0.#")).get().format(duration * 0.001)) + 's';
        }
        return DurationFormatUtils.formatDuration(duration, ((duration >= TimeUnit.HOURS.toMillis(1L)) ? "HH:" : "") + "mm:ss");
    }
}
