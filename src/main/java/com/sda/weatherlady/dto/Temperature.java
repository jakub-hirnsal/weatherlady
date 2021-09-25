package com.sda.weatherlady.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Temperature {

    @JsonProperty("Metric")
    private TemperatureValues metric;

    @JsonProperty("Imperial")
    private TemperatureValues imperial;

}
