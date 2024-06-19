package dojo.weather.forecast.domain.models;

import java.time.LocalDateTime;


public record Forecast(LocalDateTime time, double temperature, String city, GeoLocation geoLocation) {
    public static Forecast of(LocalDateTime time, double temperature, String city) {
        return new Forecast(time, temperature, city, null);
    }
}

