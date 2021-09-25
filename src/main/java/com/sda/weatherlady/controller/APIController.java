package com.sda.weatherlady.controller;

import com.sda.weatherlady.dto.CurrentDTO;
import com.sda.weatherlady.service.AccuweatherService;
import com.sda.weatherlady.service.OpenWeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class APIController {

    private static final Logger LOGGER = LoggerFactory.getLogger(APIController.class);
    private final AccuweatherService accuweatherService;
    private final OpenWeatherService openWeatherService;

    public APIController(
            AccuweatherService accuweatherService,
            OpenWeatherService openWeatherService
    ) {
        this.accuweatherService = accuweatherService;
        this.openWeatherService = openWeatherService;
    }

    @GetMapping(
            value = "/ping",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String getPing() {
        return "OK";
    }

    @GetMapping(
            value = "/weather",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CurrentDTO> getWeather(
            @RequestParam String type,
            @RequestParam String location
    ) {
        LOGGER.info("getWeather {}, for city {}", type, location);
        CurrentDTO currentDTO = accuweatherService.getForecastForCity(location);

        return ResponseEntity.ok(currentDTO);
    }

    @GetMapping(
            value = "/openweather",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CurrentDTO> getOpenWeather(
            @RequestParam String city
    ) {
        LOGGER.info("getWeather for city {}", city);
        return ResponseEntity.ok(
                openWeatherService.getCurrenConditionForCity(city)
        );
    }

}
