package dojo.weather.forecast.domain.models;

public record City(String name) {
    public static City of(String name) {
        return new City(name);
    }
}
