package dojo.weather.forecast.domain.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import dojo.weather.forecast.domain.models.City;
import dojo.weather.forecast.domain.models.Forecast;


@Service
public class WeatherService {

    public Optional<Forecast> getForecast(City city) {
        return Optional.empty();
    }
}
