package com.sda.weatherlady.facade;

import com.sda.weatherlady.dto.CurrentDTO;
import com.sda.weatherlady.exception.BadRequestException;
import com.sda.weatherlady.exception.InternalServerException;
import com.sda.weatherlady.exception.NotFoundException;
import com.sda.weatherlady.model.CurrentCondition;
import com.sda.weatherlady.repository.CurrentConditionRepository;
import com.sda.weatherlady.service.AccuweatherService;
import com.sda.weatherlady.service.AverageCalculator;
import com.sda.weatherlady.service.OpenWeatherService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeatherFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherFacade.class);

    private static final String ACCUWEATHER = "accuweather";
    private static final String OPENWEATHER = "openweather";

    private final AccuweatherService accuweatherService;
    private final OpenWeatherService openWeatherService;
    private final CurrentConditionRepository currentConditionRepository;

    @Autowired
    public WeatherFacade(
            AccuweatherService accuweatherService,
            OpenWeatherService openWeatherService,
            CurrentConditionRepository currentConditionRepository
    ) {
        this.accuweatherService = accuweatherService;
        this.openWeatherService = openWeatherService;
        this.currentConditionRepository = currentConditionRepository;
    }


    public CurrentDTO getWeather(String type, String city) {
        CurrentDTO currentDTO = null;

        if (type.equals(ACCUWEATHER)) {
            currentDTO =  accuweatherService.getCurrentConditionForCity(city);
        } else if(type.equals(OPENWEATHER)) {
            currentDTO =  openWeatherService.getCurrentConditionForCity(city);
        } else {
            throw new BadRequestException("This type is not supported");
        }

        if (currentDTO == null) {
            throw new NotFoundException("Weather not found");
        }

        var currentCondition = CurrentCondition.builder()
                .temperature(currentDTO.getTemperature().getMetric().getValue())
                .pressure(currentDTO.getPressure().getMetric().getValue())
                .windDirection(currentDTO.getWind().getDirection().getDegrees())
                .windSpeed(currentDTO.getWind().getSpeed().getMetric().getValue())
                .source(type)
                .build();

        currentConditionRepository.save(currentCondition);

        return currentDTO;
    }

    public CurrentDTO getAverageCurrentCondition(String city) {
        // TODO: Implement future
        CurrentDTO accuweatherCondition = null;
        CurrentDTO openweatherCondition = null;

        CompletableFuture<CurrentDTO> accuweatherCompletableFuture = CompletableFuture.supplyAsync(
                () -> accuweatherService.getCurrentConditionForCity(city)
        );
        CompletableFuture<CurrentDTO> openWeatherCompletableFuture = CompletableFuture.supplyAsync(
                () -> openWeatherService.getCurrentConditionForCity(city)
        );

        try {
            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(accuweatherCompletableFuture, openWeatherCompletableFuture);
            voidCompletableFuture.get();
            accuweatherCondition = accuweatherCompletableFuture.get();
            openweatherCondition = openWeatherCompletableFuture.get();
        } catch (ExecutionException | InterruptedException ex) {
            LOGGER.error("Cannot fetch data from external API", ex);
            throw new InternalServerException("Internal server error");
        }

        return AverageCalculator.calculateAverage(accuweatherCondition, openweatherCondition);
    }

}
