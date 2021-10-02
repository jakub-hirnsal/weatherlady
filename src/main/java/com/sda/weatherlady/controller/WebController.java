package com.sda.weatherlady.controller;

import com.sda.weatherlady.form.ConditionForm;
import com.sda.weatherlady.model.CurrentCondition;
import com.sda.weatherlady.repository.CurrentConditionRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // goes into WebApplicationContext
@RequestMapping(value = "/web")
public class WebController {

    private final CurrentConditionRepository currentConditionRepository;

    public WebController(CurrentConditionRepository currentConditionRepository) {
        this.currentConditionRepository = currentConditionRepository;
    }

    @GetMapping(
            value = "/index"
    )
    public String getIndex(
            final ModelMap modelMap,
            @RequestParam(defaultValue = "unknown") String name
    ) {
        modelMap.addAttribute("name", name);


        return "index";
    }

    @GetMapping(
            value = "/conditions"
    )
    public String showAllConditions(final ModelMap modelMap) {
        Iterable<CurrentCondition> conditionIterable = currentConditionRepository.findAll();

        List<CurrentCondition> currentConditionList = StreamSupport.stream(conditionIterable.spliterator(), false)
                .collect(Collectors.toList());

        modelMap.addAttribute("listOfConditions", currentConditionList);

        return "conditions";
    }


    @GetMapping(
            value = "/form"
    )
    public String showForm(final ModelMap modelMap) {
        return "form";
    }

    @PostMapping(
            value = "/form/handle"
    )
    public String handleForm(
            @ModelAttribute("conditionForm") final ConditionForm conditionForm
    ) {

        CurrentCondition currentCondition = CurrentCondition.builder()
                .temperature(conditionForm.getTemperature())
                .windSpeed(conditionForm.getWindSpeed())
                .windDirection(conditionForm.getWindDirection())
                .pressure(conditionForm.getPressure())
                .source(conditionForm.getSource())
                .build();

        currentConditionRepository.save(currentCondition);

        return "redirect:/web/conditions";
    }


}
