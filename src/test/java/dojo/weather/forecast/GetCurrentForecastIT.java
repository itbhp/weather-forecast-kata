package dojo.weather.forecast;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(GetWeatherController.class)
class GetCurrentForecastIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    void returns_not_found_on_not_existent_city_or_city_with_no_data() throws Exception {
        given(weatherService.getForecast(new City("not_existent_city")))
            .willReturn(Optional.empty());

        mockMvc.perform(
            get("/weather/forecast?city=not_existent_city")
            ).andExpect(status().isNotFound());
    }

    @Test
    void returns_ok_when_forecast_found() throws Exception {
        given(weatherService.getForecast(new City("Rome")))
            .willReturn(Optional.of(
                new Forecast(
                    34.00,
                    "Rome",
                    null,
                    LocalDateTime.parse("2024-06-18T12:00:00")
                )
            ));

        mockMvc.perform(
            get("/weather/forecast?city=Rome")
        ).andExpect(status().isOk())
        .andExpect(content().json(
            """
            {
                "temperature": 34.00,
                "city": "Rome",
                "time": "2024-06-18T12:00:00"
            }
            """
        ));
    }
}
