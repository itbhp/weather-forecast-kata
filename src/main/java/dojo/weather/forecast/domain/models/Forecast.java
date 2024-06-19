package dojo.weather.forecast.domain.models;

import java.time.LocalDateTime;


public record Forecast(LocalDateTime time, double temperature, City city, GeoLocation geoLocation) {
    public static Forecast of(LocalDateTime time, double temperature, City city) {
        return new Forecast(time, temperature, city, null);
    }
}

