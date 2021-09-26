package com.sda.weatherlady.service;

import com.sda.weatherlady.dto.CurrentDTO;
import com.sda.weatherlady.dto.Direction;
import com.sda.weatherlady.dto.Pressure;
import com.sda.weatherlady.dto.Speed;
import com.sda.weatherlady.dto.Temperature;
import com.sda.weatherlady.dto.Values;
import com.sda.weatherlady.dto.Wind;
import java.util.Arrays;
import java.util.stream.DoubleStream;

public class AverageCalculator {

    public static CurrentDTO calculateAverage(CurrentDTO... conditions) {
        if (conditions == null || conditions.length == 0) {
            return null;
        }

//        for (CurrentDTO condition : conditions) {
//
//        }

        Double averagePressure = calculateAverage(Arrays.stream(conditions)
                .mapToDouble(condition -> condition.getPressure().getMetric().getValue()));

        Double averageTemperature = calculateAverage(Arrays.stream(conditions)
                .mapToDouble(condition -> condition.getTemperature().getMetric().getValue()));

        Double averageWindDirection = calculateAverage(Arrays.stream(conditions)
                .mapToDouble(condition -> condition.getWind().getDirection().getDegrees()));

        Double averageWindSpeed = calculateAverage(Arrays.stream(conditions)
                .mapToDouble(condition -> condition.getWind().getSpeed().getMetric().getValue()));

        CurrentDTO currentDTO = conditions[0];

        return buildAverage(
                averagePressure,
                averageTemperature,
                averageWindDirection,
                averageWindSpeed,
                currentDTO.getTemperature().getMetric().getUnit(),
                currentDTO.getPressure().getMetric().getUnit(),
                currentDTO.getWind().getSpeed().getMetric().getUnit()
        );
    }

    private static Double calculateAverage(DoubleStream stream) {
        return stream.average().orElseGet(null);
    }

    public static CurrentDTO buildAverage(
            Double averagePressure,
            Double averageTemperature,
            Double averageWindDirection,
            Double averageWindSpeed,
            String temperatureUnits,
            String pressureUnits,
            String windUnits
    ) {
        return CurrentDTO.builder()
                .temperature(Temperature.builder()
                        .metric(new Values(averageTemperature.floatValue(), temperatureUnits))
                        .build())
                .pressure(Pressure.builder()
                        .metric(new Values(averagePressure.floatValue(), pressureUnits))
                        .build())
                .wind(Wind.builder()
                        .direction(Direction.builder()
                                .degrees(averageWindDirection.intValue())
                                .build())
                        .speed(Speed.builder()
                                .metric(new Values(averageWindSpeed.floatValue(), windUnits))
                                .build())
                        .build())
                .build();
    }
}
