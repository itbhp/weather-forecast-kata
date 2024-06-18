package dojo.weather.forecast;

import java.util.Optional;

import org.springframework.stereotype.Service;


@Service
public class WeatherService {

    public Optional<Forecast> getForecast(City city) {
        return Optional.empty();
    }
}
