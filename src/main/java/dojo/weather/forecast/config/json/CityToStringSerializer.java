package dojo.weather.forecast.config.json;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializerBase;

import dojo.weather.forecast.domain.models.City;

public class CityToStringSerializer extends ToStringSerializerBase {

    public CityToStringSerializer() {
        super(City.class);
    }

    @Override
    public String valueToString(Object o) {
        var city = (City) o;
        return city.name();
    }
}
