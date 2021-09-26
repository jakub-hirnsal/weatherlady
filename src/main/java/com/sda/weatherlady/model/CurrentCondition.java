package com.sda.weatherlady.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "current_condition")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentCondition {

    @Id
    @GeneratedValue
    private Long id;

    private Float temperature;

    @Column(name = "wind_direction")
    private Integer windDirection;

    @Column(name = "wind_speed")
    private Float windSpeed;

    private Float pressure;

    private String source;

    @Column(name = "created_at")
    private Timestamp createAt;

}
