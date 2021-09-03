package com.api.person.service;

import com.api.person.dto.request.PersonDTO;
import com.api.person.dto.response.MessageResponseDTO;
import com.api.person.entity.Person;
import com.api.person.exception.PersonNotFoundException;
import com.api.person.dto.mapper.PersonMapper;
import com.api.person.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService
{
    private final PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public MessageResponseDTO create(PersonDTO personDTO)
    {
        Person personToSave = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(personToSave);

        MessageResponseDTO messageResponse = createMessageResponse(savedPerson.getId(), "Person created with ID ");

        return messageResponse;
    }

    public List<PersonDTO> listAll()
    {
        List<Person> allPeople = personRepository.findAll();

        return allPeople.stream().map(personMapper::toDTO).collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException
    {
        Person person = getPersonIfExists(id);

        return personMapper.toDTO(person);
    }

    public MessageResponseDTO update(Long id, PersonDTO personDTO) throws PersonNotFoundException
    {
        getPersonIfExists(id);

        Person personToUpdate = personMapper.toModel(personDTO);
        Person updatedPerson = personRepository.save(personToUpdate);

        MessageResponseDTO messageResponse = createMessageResponse(updatedPerson.getId(), "Person updated with ID ");

        return messageResponse;
    }

    public void delete(Long id) throws PersonNotFoundException
    {
        getPersonIfExists(id);

        personRepository.deleteById(id);
    }

    private MessageResponseDTO createMessageResponse(Long id, String message)
    {
        return MessageResponseDTO.builder().message(message + id).build();
    }

    private Person getPersonIfExists(Long id) throws PersonNotFoundException
    {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }
}