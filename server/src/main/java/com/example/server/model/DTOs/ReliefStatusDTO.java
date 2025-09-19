package com.example.server.model.DTOs;

import java.util.List;

import com.example.server.model.enums.ReliefStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReliefStatusDTO {
    private Long reliefId;
    private List<MultipartFile> images;
    private String desc;
}
