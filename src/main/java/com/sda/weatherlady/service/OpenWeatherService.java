package com.sda.weatherlady.service;

import com.sda.weatherlady.dto.CurrentDTO;
import com.sda.weatherlady.dto.Direction;
import com.sda.weatherlady.dto.OpenWeatherDTO;
import com.sda.weatherlady.dto.Pressure;
import com.sda.weatherlady.dto.Speed;
import com.sda.weatherlady.dto.Temperature;
import com.sda.weatherlady.dto.Values;
import com.sda.weatherlady.dto.Wind;
import com.sda.weatherlady.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class OpenWeatherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenWeatherService.class);

    @Value("${app.service.openweather.currentUrl}")
    private String currentUrl;

    @Value("${app.service.openweather.apikey}")
    private String apikey;

    private final RestTemplate restTemplate;

    @Autowired // not required
    public OpenWeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CurrentDTO getCurrenConditionForCity(String city) {
        String url = UriComponentsBuilder.fromHttpUrl(currentUrl)
                .queryParam("appid", apikey)
                .queryParam("q", city)
                .queryParam("units", "metric")
                .toUriString();

        ResponseEntity<OpenWeatherDTO> forEntity = restTemplate.getForEntity(
                url,
                OpenWeatherDTO.class
        );

        LOGGER.info("Url to call: {}", url);

        OpenWeatherDTO body = forEntity.getBody();

        if (body == null) {
            throw new NotFoundException("City not found: " + city);
        }

        return convertToCommonFormat(body);
    }

    private CurrentDTO convertToCommonFormat(OpenWeatherDTO openWeatherDTO) {
        return CurrentDTO.builder()
                .pressure(buildPressure(openWeatherDTO))
                .temperature(buildTemperature(openWeatherDTO))
                .wind(buildWind(openWeatherDTO))
                .build();
    }

    private Pressure buildPressure(OpenWeatherDTO openWeatherDTO) {
        return Pressure.builder()
                .metric(new Values(
                        (float) openWeatherDTO.getMain().getPressure(),
                        "mb"
                )).build();
    }

    private Temperature buildTemperature(OpenWeatherDTO openWeatherDTO) {
        return Temperature.builder()
                .metric(new Values(
                        openWeatherDTO.getMain().getTemp(),
                        "C"
                ))
                .build();
    }

    private Wind buildWind(OpenWeatherDTO openWeatherDTO) {
        return Wind.builder()
                .speed(Speed.builder()
                        .metric(new Values(
                                openWeatherDTO.getWind().getSpeed(),
                                "??"
                        ))
                        .build())
                .direction(Direction.builder()
                        .degrees(openWeatherDTO.getWind().getDeg())
                        .build())
                .build();
    }
}
