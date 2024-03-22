package com.appointmed.appointmed.model;

import com.appointmed.appointmed.constant.Accessibility;
import com.appointmed.appointmed.constant.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@NoArgsConstructor
@Data
@Document("location")
public class Location {

    @Id
    public String id;
    public String address;
    public String name;
    public String openHours;
    public List<PaymentType> paymentTypes;
    public List<Accessibility> accessibility;
    public List<ContactInfo> contactInfo;

    public Location(String address, String name, String openHours, List<PaymentType> paymentTypes, List<Accessibility> accessibility, List<ContactInfo> contactInfo) {
        this.address = address;
        this.name = name;
        this.openHours = openHours;
        this.paymentTypes = paymentTypes;
        this.accessibility = accessibility;
        this.contactInfo = contactInfo;
    }
}
