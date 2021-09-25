package com.sda.weatherlady.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrentDTO {

    @JsonProperty("Temperature")
    private Temperature temperature;

    @JsonProperty("Wind")
    private Wind wind;

    @JsonProperty("Pressure")
    private Pressure pressure;

}
