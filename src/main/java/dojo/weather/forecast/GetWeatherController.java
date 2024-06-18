package dojo.weather.forecast;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GetWeatherController {

    private final WeatherService weatherService;

    public GetWeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather/forecast")
    public ResponseEntity<Forecast> getForecast(
        @RequestParam(name = "city") String cityString
    ) {
        return weatherService.getForecast(new City(cityString))
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
