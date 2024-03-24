package com.appointmed.appointmed.dto;

import com.appointmed.appointmed.constant.Accessibility;
import com.appointmed.appointmed.constant.Language;
import com.appointmed.appointmed.constant.PaymentType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DoctorFiltersDto {
    private Accessibility[] accessibilities = Accessibility.values();
    private PaymentType[] paymentTypes = PaymentType.values();
    private Language[] languages = Language.values();
    private String[] visitTypes;

    public DoctorFiltersDto(String[] visitTypes) {
        this.visitTypes = visitTypes;
    }
}
