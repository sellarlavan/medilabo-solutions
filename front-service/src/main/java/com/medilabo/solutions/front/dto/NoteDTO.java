package com.medilabo.solutions.front.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDTO {

    private String id;
    private Long patId;
    private String patient;
    private String note;
}
