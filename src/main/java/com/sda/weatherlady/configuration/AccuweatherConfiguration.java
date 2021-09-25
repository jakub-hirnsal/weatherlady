package com.sda.weatherlady.configuration;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app.service.accuweather")
@EnableConfigurationProperties(AccuweatherConfiguration.class)
@Component
@Data
public class AccuweatherConfiguration {

    private String forecastUrl;
    private String currentUrl;
    private String searchCityUrl;
    private String apikey;
    private List<String> array;

}
