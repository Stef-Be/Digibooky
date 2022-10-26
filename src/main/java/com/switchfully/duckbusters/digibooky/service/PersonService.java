package com.switchfully.duckbusters.digibooky.service;

import com.switchfully.duckbusters.digibooky.api.dto.CreatePersonDTO;
import com.switchfully.duckbusters.digibooky.api.dto.PersonDTO;
import com.switchfully.duckbusters.digibooky.api.mapper.PersonMapper;
import com.switchfully.duckbusters.digibooky.domain.person.Feature;
import com.switchfully.duckbusters.digibooky.domain.person.Person;
import com.switchfully.duckbusters.digibooky.domain.person.Role;
import com.switchfully.duckbusters.digibooky.domain.repository.PersonRepository;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.switchfully.duckbusters.digibooky.domain.person.Feature.VIEW_MEMBERS;

@Component
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final ValidationService validationService;
    private final SecurityService securityService;

    public PersonService(PersonRepository personRepository, PersonMapper personMapper, ValidationService validationService, SecurityService securityService) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.validationService = validationService;
        this.securityService = securityService;
    }

    public List<PersonDTO> getAllPersons(String id) {
        securityService.validateAuthorization(id, VIEW_MEMBERS);
        return personRepository.getAllPersons().stream()
                .map(personMapper::mapToPersonDto)
                .collect(Collectors.toList());
    }


    public void addPersonToRepo(CreatePersonDTO freshPerson) {
        validateFreshPerson(freshPerson);
        personRepository.addPerson(personMapper.createNewPerson(freshPerson));
    }

    public void validateFreshPerson(CreatePersonDTO freshPerson) {
        validationService.validateEmail(freshPerson.geteMail());
        validationService.assertNotNullOrBlank(freshPerson.getLastName(), "Last name");
        validationService.assertNotNullOrBlank(freshPerson.getCity(), "City");
        validationService.assertNotNullOrBlank(freshPerson.getInss(), "Inss");
        validationService.assertNotNullOrBlank(freshPerson.getPassword(), "Password");
        personRepository.getAllPersons().forEach(person -> validationService.validateThatPerson(freshPerson, person));

    }

    public void registerLibrarian(String adminId, CreatePersonDTO newPerson) {
        securityService.validateAuthorization(adminId, Feature.ADD_LIBRARIAN);
        validateFreshPerson(newPerson);
        Person librarian = personMapper.createNewPerson(newPerson);
        librarian.setRole(Role.LIBRARIAN);
        personRepository.addPerson(librarian);
    }

}
