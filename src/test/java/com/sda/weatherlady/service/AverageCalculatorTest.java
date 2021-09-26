package com.sda.weatherlady.service;

import com.sda.weatherlady.dto.CurrentDTO;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AverageCalculatorTest {

    @Test
    public void testCalculateAverage() {
        //given
        List<CurrentDTO> data = new ArrayList<>();
        data.add(AverageCalculator.buildAverage(
                1020d,
                25d,
                300d,
                10d,
                "C",
                "mb",
                "km/h"
        ));
        data.add(AverageCalculator.buildAverage(
                1040d,
                27d,
                320d,
                14d,
                "C",
                "mb",
                "km/h"
        ));

        //when
        CurrentDTO actual = AverageCalculator.calculateAverage(data.toArray(new CurrentDTO[0]));

        //then
        Assertions.assertEquals(
                26f,
                actual.getTemperature().getMetric().getValue()
        );
        Assertions.assertEquals(
                1030f,
                actual.getPressure().getMetric().getValue()
        );
        Assertions.assertEquals(
                310,
                actual.getWind().getDirection().getDegrees()
        );
        Assertions.assertEquals(
                12,
                actual.getWind().getSpeed().getMetric().getValue()
        );
    }
}
