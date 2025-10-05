package com.example.server.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProviderInfo {
    private String wsUid;
    private String reqId;
    private String user;
    private Double latitude;
    private Double longitude;
}
