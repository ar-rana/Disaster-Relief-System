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

    private String Address;
    @Column(nullable = false)
    private double longitude;
    @Column(nullable = false)
    private double latitude;

    public HeadQuarters(String address, double longitude, double latitude) {
        Address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "HeadQuarters {" +
                "hqId=" + hqId +
                ", Address='" + Address + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
