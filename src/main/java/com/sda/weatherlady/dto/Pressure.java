package com.sda.weatherlady.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pressure {

    @JsonProperty("Metric")
    private Values metric;

    @JsonProperty("Imperial")
    private Values imperial;

}
