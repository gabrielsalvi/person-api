package com.api.person.controller;

import com.api.person.dto.response.MessageResponseDTO;
import com.api.person.entity.Person;
import com.api.person.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/people")
public class PersonController
{
    private final PersonService personService;

    @Autowired
    public PersonController (PersonService personService)
    {
        this.personService = personService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO create(@RequestBody Person person)
    {
        return personService.create(person);
    }
}
