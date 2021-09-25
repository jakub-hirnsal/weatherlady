package com.sda.weatherlady.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Wind {

    @JsonProperty("Direction")
    private Direction direction;

    @JsonProperty("Speed")
    private Speed speed;

}
