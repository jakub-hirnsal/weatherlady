package com.sda.weatherlady.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OpenWeatherMain {

    private Float temp;

    @JsonProperty("temp_min")
    private Float tempMin;

    @JsonProperty("temp_max")
    private Float tempMax;

    private Integer pressure;

    private Integer humidity;

}
