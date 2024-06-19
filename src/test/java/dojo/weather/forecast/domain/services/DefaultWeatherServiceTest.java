package dojo.weather.forecast.domain.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import dojo.weather.forecast.domain.models.City;
import dojo.weather.forecast.domain.models.Forecast;


class DefaultWeatherServiceTest {

    @Nested
    class GetForecast {

        @Test
        void returns_no_forecast_for_not_recorded_city() {
            // given
            var service = new DefaultWeatherService(
                List.of(
                    Forecast.of( LocalDateTime.parse("2024-06-18T12:00:00"), 34.00, "Rome")
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
            var romeForecast =
                Forecast.of(LocalDateTime.parse("2024-06-18T12:00:00"), 34.00, "Rome");
            var service = new DefaultWeatherService(
                List.of(
                    romeForecast
                )
            );

            // when
            var forecast = service.getForecast(City.of("Rome"));

            // then
            assertThat(forecast).isEqualTo(Optional.of(
                romeForecast
            ));
        }
    }
}
