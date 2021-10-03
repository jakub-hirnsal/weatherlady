package com.sda.weatherlady.controller;

import com.sda.weatherlady.exception.NotFoundException;
import com.sda.weatherlady.model.CurrentCondition;
import com.sda.weatherlady.repository.CurrentConditionRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/condition")
// RESTfull
public class ConditionController {

    private CurrentConditionRepository currentConditionRepository;

    public ConditionController(CurrentConditionRepository currentConditionRepository) {
        this.currentConditionRepository = currentConditionRepository;
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CurrentCondition> getCurrentCondition(
            @PathVariable Long id
    ) {
        Optional<CurrentCondition> conditionOptional = currentConditionRepository.findById(id);

        if (conditionOptional.isEmpty()) {
            throw new NotFoundException(String.format("Condition with id %d not found", id));
        }

        return ResponseEntity.ok(
                conditionOptional.get()
        );
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<CurrentCondition>> getAllConditions() {
        Iterable<CurrentCondition> conditionIterable = currentConditionRepository.findAll();

        List<CurrentCondition> currentConditionList = StreamSupport.stream(conditionIterable.spliterator(), false)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                currentConditionList
        );
    }

    @DeleteMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CurrentCondition> deleteCurrentCondition(
            @PathVariable Long id
    ) {
        Optional<CurrentCondition> byId = currentConditionRepository.findById(id);

        if (byId.isEmpty()) {
            throw new NotFoundException(String.format("Condition with id %d not found", id));
        }

        currentConditionRepository.deleteById(id);

        return ResponseEntity.ok(
                byId.get()
        );
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CurrentCondition> createCurrentCondition(
            @RequestBody CurrentCondition currentCondition
    ) {
        CurrentCondition save = currentConditionRepository.save(currentCondition);

        return ResponseEntity.ok(
                save
        );
    }

    @PutMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CurrentCondition> updateCurrentCondition(
            @RequestBody CurrentCondition currentCondition
    ) {
        CurrentCondition condition = currentConditionRepository.save(currentCondition);

        return ResponseEntity.ok(
                condition
        );
    }

    @GetMapping(
            value = "/city",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<CurrentCondition>> findCurrentConditionByCity(
            @RequestParam String city
    ) {
        return ResponseEntity.ok(
                currentConditionRepository.findCurrentConditionByCity(city)
        );
    }

    @GetMapping(
            value = "/count"
    )
    public ResponseEntity<Long> countAll() {
        return ResponseEntity.ok(
                currentConditionRepository.count()
        );
    }

    @GetMapping(
            value = "/temperature",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<CurrentCondition>> getTemperatureGreaterThan(
            @RequestParam Float temperature
    ) {
        return ResponseEntity.ok(
                currentConditionRepository.findCurrentConditionByTemperatureGreaterThan(temperature)
        );
    }
}
