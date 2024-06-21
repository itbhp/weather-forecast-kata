# Weather Forecast Kata

## Instructions

Create a REST service to collect weather temperature data from different cities.

An example call to the service would be:

```http request
POST /weather/measurement

{
    "location": { // optional
       "latitude": 51.5074,
       "longitude": 0.1278
     },
    "city": "London",
    "temperature": 20.0,
    "timestamp": "2021-01-01T12:00:00Z"
}
```

Then the REST service should provide also an endpoint to get the current temperature of a city:

```http request
GET /weather/forecast?city=London
```

that should return the current temperature of the city with a json body like:

```json
{
    "temperature": 20.0,
    "unit": "C",
    "timestamp": "2021-01-01T12:00:00Z"
}
```

The current temperature should be the running average of the last 10 minutes measurements of the city.
