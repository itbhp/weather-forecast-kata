package dojo.weather.forecast.domain.services;

import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import dojo.weather.forecast.domain.models.City;
import dojo.weather.forecast.domain.models.Forecast;


public class DefaultWeatherService implements WeatherService {

    private final Map<String, List<Forecast>> forecastByCity;

    public DefaultWeatherService(List<Forecast> forecastList) {
        this.forecastByCity = forecastList
            .stream()
            .collect(
                groupingBy(Forecast::city, Collectors.toList())
            );
    }

    @Override
    public Optional<Forecast> getForecast(City city) {
        return Optional.ofNullable(forecastByCity.get(city.name()))
            .orElseGet(List::of)
            .stream()
            .findFirst();
    }

}
