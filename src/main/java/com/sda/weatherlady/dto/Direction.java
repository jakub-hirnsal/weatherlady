package com.sda.weatherlady.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Direction {

    @JsonProperty("Degrees")
    private Integer degrees;

    @JsonProperty("Localized")
    private String localized;

}
