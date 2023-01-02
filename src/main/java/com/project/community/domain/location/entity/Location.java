package com.project.community.domain.location.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Location {

    @Id @GeneratedValue
    @Column(name="location_id")
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String localNameOfCity;

    @Column(nullable = true)
    private String province;

    @Builder
    public Location(Long id, String city, String localNameOfCity, String province){
        this.id = id;
        this.city = city;
        this.localNameOfCity = localNameOfCity;
        this.province = province;
    }
}
