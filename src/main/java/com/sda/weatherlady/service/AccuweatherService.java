package com.sda.weatherlady.service;


import com.sda.weatherlady.dto.AccuweatherCitySearchResponse;
import com.sda.weatherlady.dto.CurrentDTO;
import com.sda.weatherlady.exception.NotFoundException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AccuweatherService implements WeatherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccuweatherService.class);
    private final RestTemplate restTemplate;

    @Value("${app.service.accuweather.forecastUrl}")
    private String forecastUrl;

    @Value("${app.service.accuweather.currentUrl}")
    private String currentUrl;

    @Value("${app.service.accuweather.searchCityUrl}")
    private String searchCityUrl;

    @Value("${app.service.accuweather.apikey}")
    private String apikey;

    public AccuweatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CurrentDTO getForecastForCity(String city) {
        String keyByCity = this.findKeyByCity(city);

        return this.downloadWeather(keyByCity);
    }

    public CurrentDTO downloadWeather(String city) {
        LOGGER.info("About to download Accuweather");

        String url = UriComponentsBuilder.fromHttpUrl(currentUrl)
                .queryParam("apikey", apikey)
                .queryParam("details", true)
                .build(city)
                .toString();

        LOGGER.info("Url to call: {}", url);

        ResponseEntity<List<CurrentDTO>> entity = this.restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        List<CurrentDTO> body = entity.getBody();

        if (body.isEmpty()) {
            throw new NotFoundException("Could not find any Weather condition for city " + city);
        }

        return body.get(0);
    }

    public String findKeyByCity(String city) {

        String uriString = UriComponentsBuilder.fromHttpUrl(this.searchCityUrl)
                .queryParam("q", city)
                .queryParam("apikey", apikey)
                .toUriString();

        ResponseEntity<List<AccuweatherCitySearchResponse>> response = this.restTemplate.exchange(
                uriString,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        var cities = response.getBody();

        if (cities.isEmpty()) {
            throw new NotFoundException("City not found: " + city);
        }

        var accuweatherCitySearchResponse = cities.get(0);
        return accuweatherCitySearchResponse.getKey();
    }

}
