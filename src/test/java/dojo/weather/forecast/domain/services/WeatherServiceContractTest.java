package dojo.weather.forecast.domain.services;

import static java.time.Clock.fixed;
import static java.time.Instant.parse;
import static java.time.LocalDateTime.ofInstant;
import static java.time.ZoneId.of;
import static java.time.ZoneOffset.UTC;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import dojo.weather.forecast.domain.models.City;
import dojo.weather.forecast.domain.models.Forecast;


public abstract class WeatherServiceContractTest {

    @Nested
    class AddMeasurement {

        @Test
        void adds_measurement() {
            // given
            var clockInstant = "2024-06-18T12:00:00Z";
            var clock = fixed(parse(clockInstant), of("UTC"));
            var service = weatherServiceWithClock(clock);

            // when
            service.addMeasurement(
                34.00,
                City.of("Rome"),
                LocalDateTime.parse("2024-06-18T12:00:00"),
                null
            );

            // then
            assertThat(service.getForecast(City.of("Rome")))
                .isEqualTo(Optional.of(
                    Forecast.of(LocalDateTime.parse(clockInstant , DateTimeFormatter.ISO_DATE_TIME), 34.00, City.of("Rome")
                    )
                ));
        }
    }
    
    @Nested
    class GetForecast {

        @Test
        void returns_no_forecast_for_not_recorded_city() {
            // given
            var clock = fixed(parse("2024-06-18T12:05:00Z"), of("UTC"));
            var forecastList = List.of(
                Forecast.of(LocalDateTime.parse("2024-06-18T12:00:00"), 34.00, City.of("Rome"))
            );
            var service = weatherServiceWithClockAndPreviousForecast(clock, forecastList);

            // when
            var forecast = service.getForecast(City.of("Milan"));

            // then
            assertThat(forecast).isEmpty();
        }

        @Test
        void returns_forecast_for_recorded_city() {
            // given
            var clock = fixed(parse("2024-06-18T12:00:00Z"), of("UTC"));
            var service = weatherServiceWithClockAndPreviousForecast(clock, List.of(
                Forecast.of(LocalDateTime.parse("2024-06-18T12:00:00"), 34.00, City.of("Rome"))
            ));

            // when
            var forecast = service.getForecast(City.of("Rome"));

            // then
            assertThat(forecast).isEqualTo(Optional.of(
                Forecast.of(ofInstant(clock.instant(), UTC), 34.00, City.of("Rome"))
            ));
        }

        @Test
        void returns_mean_forecast_in_the_last_10_minutes_for_recorded_city() {
            // given
            var clock = fixed(parse("2024-06-18T12:05:00Z"), of("UTC"));
            var service = weatherServiceWithClockAndPreviousForecast(clock, List.of(
                Forecast.of(LocalDateTime.parse("2024-06-18T12:04:00"), 34.00, City.of("Rome")),
                Forecast.of(LocalDateTime.parse("2024-06-18T12:03:00"), 36.00, City.of("Rome")),
                Forecast.of(LocalDateTime.parse("2024-06-18T11:54:00"), 35.00, City.of("Rome"))
            ));

            // when
            var forecast = service.getForecast(City.of("Rome"));

            // then
            assertThat(forecast).isEqualTo(Optional.of(
                Forecast.of(ofInstant(clock.instant(), UTC), 35.00, City.of("Rome"))
            ));
        }
    }

    protected abstract WeatherService weatherServiceWithClockAndPreviousForecast(Clock clock, List<Forecast> previousForecasts);


    protected abstract WeatherService weatherServiceWithClock(Clock clock);

}
