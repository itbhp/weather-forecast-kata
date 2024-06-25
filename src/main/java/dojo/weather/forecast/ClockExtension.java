package dojo.weather.forecast;

import java.time.Clock;
import java.time.LocalDateTime;


public class ClockExtension {
    private ClockExtension() {
    }

    public static LocalDateTime now(Clock clock) {
        return LocalDateTime.ofInstant(clock.instant(), clock.getZone());
    }

    public static boolean isInTheLastTenMinutes(Clock clock, LocalDateTime time) {
        return time.isAfter(now(clock).minusMinutes(10));
    }
}
