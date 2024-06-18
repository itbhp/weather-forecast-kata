package dojo.weather.forecast;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void not_existent_city_or_city_with_no_data() throws Exception {
        given(weatherService.getForecast(new City("not_existent_city")))
            .willReturn(Optional.empty());

        mockMvc.perform(
            get("/weather/forecast?city=not_existent_city")
            ).andExpect(status().isNotFound());
    }
}
