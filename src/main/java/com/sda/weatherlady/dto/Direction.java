package com.sda.weatherlady.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Direction {

    @JsonProperty("Degrees")
    private Integer degrees;

    @JsonProperty("Localized")
    private String localized;

}
