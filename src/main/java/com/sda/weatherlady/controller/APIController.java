package com.sda.weatherlady.controller;

import com.sda.weatherlady.dto.WeatherDTO;
import com.sda.weatherlady.service.AccuweatherService;
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
    private AccuweatherService accuweatherService;

    public APIController(AccuweatherService accuweatherService) {
        this.accuweatherService = accuweatherService;
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
    public ResponseEntity<WeatherDTO> getWeather(
            @RequestParam String type
    ) {
        LOGGER.info("getWeather {}", type);
        WeatherDTO weatherDTO = accuweatherService.getForecastForCity();

        return ResponseEntity.ok(weatherDTO);
    }
}
