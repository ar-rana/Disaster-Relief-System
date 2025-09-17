package com.example.server.model;

import com.example.server.model.enums.Criticality;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Data
public class ReliefReq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    private String name;
    private String poc;

    @Enumerated(EnumType.STRING)
    private Criticality criticality;
    @Column(nullable = false)
    private double longitude;
    @Column(nullable = false)
    private double latitude;
    private String description;

    public ReliefReq(String name, String poc, double longitude, double latitude, String description) {
        this.name = name;
        this.poc = poc;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
    }
}
