package com.sda.weatherlady.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConditionForm {

    @Min(-50)
    @Max(value = 50, message = "Max allowed values is 50")
    private Float temperature;

    @Range(min = 0, max = 359)
    private Integer windDirection;

    @Min(0)
    private Float windSpeed;

    @NotNull
    private Float pressure;

    @NotNull
    private String source;

    @Length(min = 2)
    private String city;

    @Length(min = 2, max = 3)
    private String countryCode;

}
