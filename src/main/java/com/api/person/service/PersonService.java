package com.api.person.service;

import com.api.person.dto.response.MessageResponseDTO;
import com.api.person.entity.Person;
import com.api.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class PersonService
{
    private final PersonRepository personRepository;

    @Autowired
    public PersonService (PersonRepository personRepository)
    {
        this.personRepository = personRepository;
    }

    @PostMapping
    public MessageResponseDTO create(Person person)
    {
        Person savedPerson = personRepository.save(person);

        return MessageResponseDTO
                .builder()
                .message("Created person with ID " + savedPerson.getId())
                .build();
    }
}