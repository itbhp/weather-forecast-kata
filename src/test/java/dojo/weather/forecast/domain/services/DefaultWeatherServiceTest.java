package dojo.weather.forecast.domain.services;

import java.time.Clock;
import java.util.List;

import dojo.weather.forecast.domain.models.Forecast;


class DefaultWeatherServiceTest extends WeatherServiceContractTest {

    @Override
    protected DefaultWeatherService weatherServiceWithClock(Clock clock) {
        return weatherServiceWithClockAndPreviousForecast(clock, List.of());
    }

    @Override
    protected DefaultWeatherService weatherServiceWithClockAndPreviousForecast(Clock clock, List<Forecast> previousForecasts) {
        return new DefaultWeatherService(
            clock,
            previousForecasts
        );
    }
}
