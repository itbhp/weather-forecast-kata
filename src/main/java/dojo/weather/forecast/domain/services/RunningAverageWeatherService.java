package dojo.weather.forecast.domain.services;

import dojo.weather.forecast.domain.models.City;
import dojo.weather.forecast.domain.models.Forecast;
import dojo.weather.forecast.domain.models.GeoLocation;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static dojo.weather.forecast.ClockExtension.isInTheLastTenMinutes;
import static dojo.weather.forecast.ClockExtension.now;


public class RunningAverageWeatherService implements WeatherService {

    private final Map<City, Forecast> forecastMap = new ConcurrentHashMap<>();
    private final Clock clock;

    public RunningAverageWeatherService(Clock clock, List<Forecast> forecastList) {
        this.clock = clock;
        for (Forecast forecast : forecastList) {
            updateCityForeCast(clock, forecast);
        }
    }

    @Override
    public Optional<Forecast> getForecast(City city) {
        return Optional.ofNullable(forecastMap.get(city));
    }

    @Override
    public void addMeasurement(double temperature, City city, LocalDateTime time, GeoLocation geoLocation) {
        updateCityForeCast(clock, Forecast.of(time, temperature, city));
    }

    private void updateCityForeCast(Clock clock, Forecast forecast) {
        if (forecastMap.containsKey(forecast.city())) {
            Forecast existingForecast = forecastMap.get(forecast.city());
            OptionalDouble average = Stream.of(existingForecast, forecast)
                .filter(currentForecast ->
                    isInTheLastTenMinutes(clock, currentForecast.time())
                )
                .mapToDouble(Forecast::temperature).average();
            average.ifPresentOrElse(
                avg -> forecastMap.put(forecast.city(), Forecast.of(now(clock), avg, forecast.city())),
                () -> forecastMap.remove(forecast.city())
            );
        } else {
            if (isInTheLastTenMinutes(clock, forecast.time())) {
                forecastMap.put(forecast.city(), forecast);
            }
        }
    }
}
