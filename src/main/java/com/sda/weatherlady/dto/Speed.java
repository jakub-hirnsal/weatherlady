package com.sda.weatherlady.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Speed {

    @JsonProperty("Metric")
    private Values metric;

    @JsonProperty("Imperial")
    private Values imperial;

}
