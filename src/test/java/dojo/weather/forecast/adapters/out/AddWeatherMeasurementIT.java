package dojo.weather.forecast.adapters.out;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import dojo.weather.forecast.domain.models.City;
import dojo.weather.forecast.domain.models.GeoLocation;
import dojo.weather.forecast.domain.services.WeatherService;


@WebMvcTest(AddWeatherMeasurementController.class)
class AddWeatherMeasurementIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    void returns_created_on_adding_measurement_when_all_mandatory_fields_provided() throws Exception {
        mockMvc.perform(
            post("/weather/measurements")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                        "temperature": 34.00,
                        "city": "Rome",
                        "timestamp": "2024-06-18T12:00:00",
                        "location": {
                          "latitude": 41.9028,
                          "longitude": 12.4964
                        }
                    }
                    """
                )
        ).andExpect(status().isCreated());

        verify(weatherService).addMeasurement(
            34.00,
            City.of("Rome"),
            LocalDateTime.parse("2024-06-18T12:00:00"),
            new GeoLocation(41.9028,12.4964)
        );
    }

    @Test
    void returns_bad_request_on_adding_measurement_when_some_mandatory_fields_missing() throws Exception {
        mockMvc.perform(
            post("/weather/measurements")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                    }
                    """
                )
        ).andExpect(status().isBadRequest());

        verifyNoInteractions(weatherService);
    }
}
