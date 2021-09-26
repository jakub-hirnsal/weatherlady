package com.sda.weatherlady.service;

import com.sda.weatherlady.dto.CurrentDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AverageCalculatorTest {

    @ParameterizedTest
    @MethodSource("calculateAverageInputParams")
    public void testCalculateAverage(CurrentDTO dto1, CurrentDTO dto2, CurrentDTO expected) {
        //when
        CurrentDTO actual = AverageCalculator.calculateAverage(dto1, dto2);

        //then
        Assertions.assertEquals(
                expected.getTemperature().getMetric().getValue(),
                actual.getTemperature().getMetric().getValue()
        );
        Assertions.assertEquals(
                expected.getPressure().getMetric().getValue(),
                actual.getPressure().getMetric().getValue()
        );
        Assertions.assertEquals(
                expected.getWind().getDirection().getDegrees(),
                actual.getWind().getDirection().getDegrees()
        );
        Assertions.assertEquals(
                expected.getWind().getSpeed().getMetric().getValue(),
                actual.getWind().getSpeed().getMetric().getValue()
        );
    }

    public static Stream<Arguments> calculateAverageInputParams() {
        List<List<CurrentDTO>> data = new ArrayList<>();
        data.add(List.of(
                AverageCalculator.buildAverage(
                        1020d,
                        25d,
                        300d,
                        10d,
                        "C",
                        "mb",
                        "km/h"
                ),
                AverageCalculator.buildAverage(
                        1040d,
                        27d,
                        320d,
                        14d,
                        "C",
                        "mb",
                        "km/h"
                ),
                AverageCalculator.buildAverage(
                        1030d,
                        26d,
                        310d,
                        12d,
                        "C",
                        "mb",
                        "km/h"
                )
        ));

        data.add(List.of(
                AverageCalculator.buildAverage(
                        1000d,
                        20d,
                        250d,
                        1d,
                        "C",
                        "mb",
                        "km/h"
                ),
                AverageCalculator.buildAverage(
                        1100d,
                        30d,
                        300d,
                        3d,
                        "C",
                        "mb",
                        "km/h"
                ),
                AverageCalculator.buildAverage(
                        1050d,
                        25d,
                        275d,
                        2d,
                        "C",
                        "mb",
                        "km/h"
                )
        ));

        data.add(List.of(
                AverageCalculator.buildAverage(
                        0d,
                        5d,
                        0d,
                        0d,
                        "C",
                        "mb",
                        "km/h"
                ),
                AverageCalculator.buildAverage(
                        -2d,
                        -5d,
                        0d,
                        14d,
                        "C",
                        "mb",
                        "km/h"
                ),
                AverageCalculator.buildAverage(
                        -1d,
                        0d,
                        0d,
                        7d,
                        "C",
                        "mb",
                        "km/h"
                )
        ));

        return data.stream().map(list -> Arguments.of(list.toArray()));
    }
}
