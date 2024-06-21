package dojo.weather.forecast.domain.services;

import static java.util.stream.Collectors.groupingBy;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import dojo.weather.forecast.domain.models.City;
import dojo.weather.forecast.domain.models.Forecast;
import dojo.weather.forecast.domain.models.GeoLocation;


public class DefaultWeatherService implements WeatherService {

    private final Clock clock;
    private final Map<City, List<Forecast>> forecastByCity;

    public DefaultWeatherService(
        Clock clock,
        List<Forecast> forecastList
    ) {
        this.clock = clock;
        this.forecastByCity = forecastList
            .stream()
            .collect(
                groupingBy(Forecast::city, Collectors.toList())
            );
    }

    @Override
    public Optional<Forecast> getForecast(City city) {
        OptionalDouble average = Optional.ofNullable(forecastByCity.get(city))
            .orElseGet(List::of)
            .stream()
            .filter(forecast -> forecast.time().isAfter(now().minusMinutes(10)))
            .mapToDouble(Forecast::temperature)
            .average();
        return average.stream()
            .mapToObj(temperature -> Forecast.of(now(), temperature, city))
            .findFirst();
    }

    @Override
    public void addMeasurement(double temperature, City rome, LocalDateTime time, GeoLocation geoLocation) {
        // TODO implement
    }

    private LocalDateTime now() {
        return LocalDateTime.ofInstant(clock.instant(), clock.getZone());
    }

}
