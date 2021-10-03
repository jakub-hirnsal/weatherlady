package com.sda.weatherlady.repository;

import com.sda.weatherlady.model.CurrentCondition;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CurrentConditionRepository extends CrudRepository<CurrentCondition, Long> {

    List<CurrentCondition> findCurrentConditionByCity(String city);

    List<CurrentCondition> findCurrentConditionByTemperatureGreaterThan(Float temperature);

}
