package com.sda.weatherlady.controller;

import com.sda.weatherlady.dto.CurrentDTO;
import com.sda.weatherlady.facade.WeatherFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class APIController {

    private static final Logger LOGGER = LoggerFactory.getLogger(APIController.class);
    private final WeatherFacade weatherFacade;

    public APIController(
           WeatherFacade weatherFacade
    ) {
        this.weatherFacade = weatherFacade;
    }

    @Secured("ROLE_SUPER_ADMIN")
//    @RolesAllowed("SUPER_ADMIN") //same as previous line
//    @PreAuthorize("#scope") // for OAUTH2 -
    @GetMapping(
            value = "/ping",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String getPing() {
        return "OK";
    }

    /**
     * Example URL: http://127.0.0.1:8080/api/weather?city=Prague&type=openweather
     * @param type String
     * @param city String
     * @return ResponseEntity<CurrentDTO>
     */
    @GetMapping(
            value = "/weather",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CurrentDTO> getWeather(
            @RequestParam String type,
            @RequestParam String city
    ) {
        LOGGER.info("getWeather {}, for city {}", type, city);
        var currentDTO = weatherFacade.getWeather(type, city);


        return ResponseEntity.ok(currentDTO);
    }

//    @GetMapping(
//            value = "/openweather",
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public ResponseEntity<CurrentDTO> getOpenWeather(
//            @RequestParam String city
//    ) {
//        LOGGER.info("getWeather for city {}", city);
//        return ResponseEntity.ok(
//                openWeatherService.getCurrenConditionForCity(city)
//        );
//    }

    @GetMapping(
            value = "/weather/average",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CurrentDTO> getAverageCondition(
            @RequestParam String city
    ) {
        LOGGER.info("getAverageCondition for city {}", city);

        return ResponseEntity.ok(weatherFacade.getAverageCurrentCondition(city));
    }

}
