package com.appointmed.appointmed.mapper;

import com.appointmed.appointmed.dto.ContactInfoDto;
import com.appointmed.appointmed.model.ContactInfo;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ContactInfoMapper {

   ContactInfoDto contactInfoToContactInfoDto(ContactInfo contactInfo);
}
