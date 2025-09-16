package com.example.server.model;

import java.util.List;

import com.example.server.model.enums.ReliefStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.Queue;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class RequestStatus {

    @Id
    private Integer reliefId;

    @Enumerated(EnumType.STRING)
    private ReliefStatus status;

    private List<byte[]> images;
    @Column(nullable = false)
    private String desc;

}
