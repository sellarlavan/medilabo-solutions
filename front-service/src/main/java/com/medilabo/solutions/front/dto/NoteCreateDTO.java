package com.medilabo.solutions.front.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteCreateDTO {
    private Long patId;
    private String patient;
    private String note;
}
