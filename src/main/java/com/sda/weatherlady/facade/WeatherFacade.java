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
import java.util.Set;
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
    private static final String AVERAGE = "average";

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

        var currentCondition = convertToModel(currentDTO, type);

        currentConditionRepository.save(currentCondition);

        return currentDTO;
    }

    private CurrentCondition convertToModel(CurrentDTO currentDTO, String type) {
        return CurrentCondition.builder()
                .temperature(currentDTO.getTemperature().getMetric().getValue())
                .pressure(currentDTO.getPressure().getMetric().getValue())
                .windDirection(currentDTO.getWind().getDirection().getDegrees())
                .windSpeed(currentDTO.getWind().getSpeed().getMetric().getValue())
                .source(type)
                .city(currentDTO.getCity())
                .countryCode(currentDTO.getCountryCode())
                .build();
    }

    /**
     * FOR the call: http://127.0.0.1:8080/api/weather/average?city=Prague
     *
     * @param city String
     * @return CurrentDTO
     */
    public CurrentDTO getAverageCurrentCondition(String city) {
        CurrentDTO accuweatherDTO = null;
        CurrentDTO openweatherDTO = null;

        CompletableFuture<CurrentDTO> accuweatherCompletableFuture = CompletableFuture.supplyAsync(
                () -> accuweatherService.getCurrentConditionForCity(city)
        );
        CompletableFuture<CurrentDTO> openWeatherCompletableFuture = CompletableFuture.supplyAsync(
                () -> openWeatherService.getCurrentConditionForCity(city)
        );

        try {
            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(accuweatherCompletableFuture, openWeatherCompletableFuture);
            voidCompletableFuture.get();
            accuweatherDTO = accuweatherCompletableFuture.get();
            openweatherDTO = openWeatherCompletableFuture.get();
        } catch (ExecutionException | InterruptedException ex) {
            LOGGER.error("Cannot fetch data from external API", ex);
            throw new InternalServerException("Internal server error");
        }

        CurrentDTO average = AverageCalculator.calculateAverage(accuweatherDTO, openweatherDTO);

        average.setCity(accuweatherDTO.getCity());
        average.setCountryCode(accuweatherDTO.getCountryCode());

        CurrentCondition averageCondition = convertToModel(average, AVERAGE);
        CurrentCondition accuweatherCondition = convertToModel(accuweatherDTO, ACCUWEATHER);
        CurrentCondition openweatherCondition = convertToModel(openweatherDTO, OPENWEATHER);

        averageCondition.setChildren(
                Set.of(accuweatherCondition, openweatherCondition)
        );

        currentConditionRepository.save(averageCondition);

        return average;
    }

}
