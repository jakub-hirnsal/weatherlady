package com.sda.weatherlady.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentDTO {

    @JsonProperty("Temperature")
    private Temperature temperature;

    @JsonProperty("Wind")
    private Wind wind;

    @JsonProperty("Pressure")
    private Pressure pressure;

}
