package dojo.weather.forecast.adapters.out;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dojo.weather.forecast.domain.models.City;
import dojo.weather.forecast.domain.models.GeoLocation;
import dojo.weather.forecast.domain.services.WeatherService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@RestController
public class AddWeatherMeasurementController {

    private final WeatherService weatherService;

    public AddWeatherMeasurementController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping("/weather/measurements")
    public ResponseEntity<Void> addMeasurement(
        @RequestBody @Validated @NotNull
        MeasurementRequest measurementRequest
    ) {
        weatherService.addMeasurement(
            measurementRequest.temperature(),
            City.of(measurementRequest.city()),
            measurementRequest.timestamp(),
            measurementRequest.location()
        );
        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    public record MeasurementRequest(
        double temperature,
        @NotEmpty(message = "The city name is required.")
        String city,
        @NotNull(message = "The timestamp of the measurement is required.")
        LocalDateTime timestamp,
        GeoLocation location
    ) {

    }
}
