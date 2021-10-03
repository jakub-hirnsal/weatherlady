package com.sda.weatherlady.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccuweatherCitySearchResponse {

    @JsonProperty("Key")
    private String key;

    @JsonProperty("LocalizedName")
    private String localizedName;

    @JsonProperty("Country")
    private Country country;

}
