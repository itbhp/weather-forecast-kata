package dojo.weather.forecast.domain.services;

import static java.time.Clock.fixed;
import static java.time.Instant.parse;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import dojo.weather.forecast.domain.models.City;
import dojo.weather.forecast.domain.models.Forecast;


class DefaultWeatherServiceTest {

    @Nested
    class GetForecast {

        public static final ZoneId UTC = ZoneId.of("UTC");
        private final Clock fixedClock = fixed(parse("2024-06-18T12:05:00Z"), ZoneId.of("UTC"));

        @Test
        void returns_no_forecast_for_not_recorded_city() {
            // given
            var service = new DefaultWeatherService(
                fixedClock,
                List.of(
                    Forecast.of( LocalDateTime.parse("2024-06-18T12:00:00"), 34.00, City.of("Rome"))
                )
            );

            // when
            var forecast = service.getForecast(City.of("Milan"));

            // then
            assertThat(forecast).isEmpty();
        }

        @Test
        void returns_forecast_for_recorded_city() {
            // given
            var service = new DefaultWeatherService(
                fixedClock,
                List.of(
                    Forecast.of(LocalDateTime.parse("2024-06-18T12:00:00"), 34.00, City.of("Rome"))
                )
            );

            // when
            var forecast = service.getForecast(City.of("Rome"));

            // then
            assertThat(forecast).isEqualTo(Optional.of(
                Forecast.of(LocalDateTime.ofInstant(fixedClock.instant(), UTC), 34.00, City.of("Rome"))
            ));
        }

        @Test
        void returns_mean_forecast_in_the_last_10_minutes_for_recorded_city() {
            // given
            var service = new DefaultWeatherService(
                fixedClock,
                List.of(
                    Forecast.of(LocalDateTime.parse("2024-06-18T12:04:00"), 34.00, City.of("Rome")),
                    Forecast.of(LocalDateTime.parse("2024-06-18T12:03:00"), 36.00, City.of("Rome")),
                    Forecast.of(LocalDateTime.parse("2024-06-18T11:54:00"), 35.00, City.of("Rome"))
                )
            );

            // when
            var forecast = service.getForecast(City.of("Rome"));

            // then
            assertThat(forecast).isEqualTo(Optional.of(
                Forecast.of(LocalDateTime.ofInstant(fixedClock.instant(), UTC), 35.00, City.of("Rome"))
            ));
        }
    }
}
