package com.sda.weatherlady.facade;

import com.sda.weatherlady.dto.CurrentDTO;
import com.sda.weatherlady.exception.BadRequestException;
import com.sda.weatherlady.service.AccuweatherService;
import com.sda.weatherlady.service.OpenWeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeatherFacade {

    private static final String ACCUWEATHER = "accuweather";
    private static final String OPENWEATHER = "openweather";

    private final AccuweatherService accuweatherService;
    private final OpenWeatherService openWeatherService;

    @Autowired
    public WeatherFacade(
            AccuweatherService accuweatherService,
            OpenWeatherService openWeatherService
    ) {
        this.accuweatherService = accuweatherService;
        this.openWeatherService = openWeatherService;
    }


    public CurrentDTO getWeather(String type, String city) {
        if (type.equals(ACCUWEATHER)) {
            return accuweatherService.getCurrentConditionForCity(city);
        } else if(type.equals(OPENWEATHER)) {
            return openWeatherService.getCurrentConditionForCity(city);
        }

        throw new BadRequestException("This type is not supported");
    }

    public CurrentDTO getAverageCurrentCondition(String city) {
        // TODO: Implement future
        CurrentDTO accuweatherCondition = accuweatherService.getCurrentConditionForCity(city);
        CurrentDTO openweatherCondition = openWeatherService.getCurrentConditionForCity(city);



        return null;
    }

}
