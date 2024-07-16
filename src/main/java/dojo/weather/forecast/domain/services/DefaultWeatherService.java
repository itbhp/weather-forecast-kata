package dojo.weather.forecast.domain.services;

import dojo.weather.forecast.domain.models.City;
import dojo.weather.forecast.domain.models.Forecast;
import dojo.weather.forecast.domain.models.GeoLocation;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static dojo.weather.forecast.ClockExtension.isInTheLastTenMinutes;
import static dojo.weather.forecast.ClockExtension.now;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;

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
        groupingBy(
          Forecast::city,
          toCollection(ArrayList::new) // to have a mutable list
        )
      );
  }

  @Override
  public Optional<Forecast> getForecast(City city) {
    var average = Optional.ofNullable(forecastByCity.get(city))
      .orElseGet(List::of)
      .stream()
      .filter(forecast -> isInTheLastTenMinutes(clock, forecast.time()))
      .mapToDouble(Forecast::temperature)
      .average();
    return average.stream()
      .mapToObj(temperature -> Forecast.of(now(clock), temperature, city))
      .findFirst();
  }

  @Override
  public void addMeasurement(double temperature, City city, LocalDateTime time, GeoLocation geoLocation) {
    forecastByCity.computeIfAbsent(city, anyCity -> new ArrayList<>())
      .add(Forecast.of(time, temperature, city));
  }

}
