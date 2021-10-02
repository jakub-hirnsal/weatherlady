package com.sda.weatherlady.model;

import java.sql.Timestamp;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "current_condition")
@Data
@Builder
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
    @CreationTimestamp
    private Timestamp createAt;

    @ManyToOne(cascade = CascadeType.ALL)
    private CurrentCondition parent;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<CurrentCondition> children;

}
