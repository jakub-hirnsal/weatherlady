package com.sda.weatherlady.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfiguration.class);

    @Bean
    public RestTemplate restTemplate() {
        LOGGER.info("Creating RestTemplate");
        return new RestTemplate();
    }

}
