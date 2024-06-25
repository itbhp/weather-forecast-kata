package dojo.weather.forecast.domain.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dojo.weather.forecast.domain.models.City;
import dojo.weather.forecast.domain.models.Forecast;
import dojo.weather.forecast.domain.models.GeoLocation;


@Service
public interface WeatherService {

    Optional<Forecast> getForecast(City city);

    void addMeasurement(double temperature, City city, LocalDateTime time, GeoLocation geoLocation);
}
