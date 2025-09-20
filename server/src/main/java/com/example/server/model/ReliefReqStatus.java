package com.example.server.model;

import java.util.List;

import com.example.server.model.enums.ReliefStatus;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ReliefReqStatus {

    @Id
    @Column(nullable = false)
    private Long reliefId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReliefStatus status;

    // map this attribute to a different table
    @ElementCollection
    @CollectionTable(
            name = "relief_images",
            joinColumns = @JoinColumn(name = "relief_id")
    )
    @Column(name = "image")
    private List<byte[]> images;

    @Column(nullable = false)
    private String description;

    public ReliefReqStatus(Long reliefId, ReliefStatus status) {
        this.reliefId = reliefId;
        this.status = status;
        this.description = "";
    }
}
