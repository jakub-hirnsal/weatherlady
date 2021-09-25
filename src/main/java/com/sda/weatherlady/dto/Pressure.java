package com.sda.weatherlady.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Pressure {

    @JsonProperty("Metric")
    private Values metric;

    @JsonProperty("Imperial")
    private Values imperial;

}
