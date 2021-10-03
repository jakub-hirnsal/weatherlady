package com.sda.weatherlady.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConditionForm {

    private Float temperature;
    private Integer windDirection;
    private Float windSpeed;
    private Float pressure;
    private String source;
    private String city;
    private String countryCode;

}
