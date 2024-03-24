package com.appointmed.appointmed.dto;

import com.appointmed.appointmed.constant.Accessibility;
import com.appointmed.appointmed.constant.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LocationDto {

    private String address;
    private String name;
    private String openHours;
    private List<PaymentType> paymentTypes;
    private List<Accessibility> accessibility;
    private List<ContactInfoDto> contactInfo;
    private List<VisitDto> visits;

}
