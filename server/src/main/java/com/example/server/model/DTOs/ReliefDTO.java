package com.example.server.model.DTOs;

import com.example.server.model.enums.Criticality;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReliefDTO {

    private String name;
    private String poc;
    private double longitude;
    private double latitude;
    private String description;

    @Override
    public String toString() {
        return "ReliefDTO{" +
                "name='" + name + '\'' +
                ", poc='" + poc + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", description='" + description + '\'' +
                '}';
    }
}
