package com.appointmed.appointmed.dto;

import com.appointmed.appointmed.constant.Specialization;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VisitDto {

    Specialization specialization;
    private String id;
    private String type;
    private float price;
    private int timeSlotMinutes;
}
