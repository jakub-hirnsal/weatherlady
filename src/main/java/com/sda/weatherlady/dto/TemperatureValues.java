package com.sda.weatherlady.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TemperatureValues {

    @JsonProperty("Value")
    private Float value;

    @JsonProperty("Unit")
    private String unit;

    @JsonProperty("UnitType")
    private Integer unitType;
}
