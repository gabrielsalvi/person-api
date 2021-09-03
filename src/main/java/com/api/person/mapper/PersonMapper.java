package com.api.person.mapper;

import com.api.person.dto.request.PersonDTO;
import com.api.person.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper
{
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    // it'll convert the birthDate to the required format on the database

    @Mapping(source = "birthDate", target = "birthDate", dateFormat = "dd-MM-yyyy")
    Person toModel(PersonDTO personDTO);

    PersonDTO toDTO(Person person);
}