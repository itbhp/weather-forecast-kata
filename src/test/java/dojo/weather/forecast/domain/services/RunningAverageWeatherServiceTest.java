package dojo.weather.forecast.domain.services;

import java.time.Clock;
import java.util.List;

import dojo.weather.forecast.domain.models.Forecast;


class RunningAverageWeatherServiceTest extends WeatherServiceContractTest{

    @Override
    protected WeatherService weatherServiceWithClockAndPreviousForecast(Clock clock, List<Forecast> previousForecasts) {
        return new RunningAverageWeatherService(clock, previousForecasts);
    }

    @Override
    protected WeatherService weatherServiceWithClock(Clock clock) {
        return weatherServiceWithClockAndPreviousForecast(clock, List.of());
    }
}
