package dojo.weather.forecast;

import java.time.LocalDateTime;


public record Forecast(double temperature, String city, GeoLocation geoLocation, LocalDateTime time) {

}

