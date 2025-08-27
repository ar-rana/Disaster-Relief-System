package com.example.server.model;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@Entity
@Data
public class ReliefReq {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;

    private String name;
    private String poc;
    private String criticality;
    private double longitude;
    private double latitude;
    private String description;

    public ReliefReq(String name, String poc, double longitude, double latitude, String description) {
        this.name = name;
        this.poc = poc;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
    }

    @Override
    public String toString() {
        return "ReliefReq {" +
                "name='" + name + '\'' +
                ", poc='" + poc + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", description='" + description + '\'' +
                '}';
    }
}
