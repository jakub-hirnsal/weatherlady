package com.sda.weatherlady.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Wind {

    @JsonProperty("Direction")
    private Direction direction;

    @JsonProperty("Speed")
    private Speed speed;

}
