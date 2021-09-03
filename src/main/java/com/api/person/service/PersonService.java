package com.api.person.service;

import com.api.person.dto.request.PersonDTO;
import com.api.person.dto.response.MessageResponseDTO;
import com.api.person.entity.Person;
import com.api.person.exception.PersonNotFoundException;
import com.api.person.mapper.PersonMapper;
import com.api.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService
{
    private final PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    @Autowired
    public PersonService (PersonRepository personRepository)
    {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO create(PersonDTO personDTO)
    {
        Person personToSave = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(personToSave);

        return new MessageResponseDTO("Created person with ID " + savedPerson.getId());
    }

    public List<PersonDTO> listAll()
    {
        List<Person> allPeople = personRepository.findAll();

        return allPeople.stream().map(personMapper::toDTO).collect(Collectors.toList());
    }

    private Person getPersonIfExists(Long id) throws PersonNotFoundException
    {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException
    {
        Person person = getPersonIfExists(id);

        return personMapper.toDTO(person);
    }

    public void delete(Long id) throws PersonNotFoundException
    {
        getPersonIfExists(id);

        personRepository.deleteById(id);
    }
}