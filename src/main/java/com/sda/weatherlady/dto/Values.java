package com.sda.weatherlady.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Values {

    @JsonProperty("Value")
    private Float value;

    @JsonProperty("Unit")
    private String unit;

}
