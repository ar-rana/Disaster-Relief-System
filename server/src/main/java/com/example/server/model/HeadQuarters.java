package com.example.server.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
public class HeadQuarters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hqId;

    private String address;
    @Column(nullable = false)
    private double longitude;
    @Column(nullable = false)
    private double latitude;

    private Integer resourceUnits;

    public HeadQuarters(String address, double longitude, double latitude) {
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.resourceUnits = 0;
    }

    public HeadQuarters(String address, double longitude, double latitude, Integer resource) {
        address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.resourceUnits = resource;
    }

    @Override
    public String toString() {
        return "HeadQuarters{" +
                "hqId=" + hqId +
                ", Address='" + address + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", resourceUnits=" + resourceUnits +
                '}';
    }
}
