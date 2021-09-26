package com.sda.weatherlady.repository;

import com.sda.weatherlady.model.CurrentCondition;
import org.springframework.data.repository.CrudRepository;

public interface CurrentConditionRepository extends CrudRepository<CurrentCondition, Long> {
}
