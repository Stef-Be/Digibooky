package com.switchfully.duckbusters.digibooky.service;

import com.switchfully.duckbusters.digibooky.api.dto.CreatePersonDTO;
import com.switchfully.duckbusters.digibooky.api.mapper.PersonMapper;
import com.switchfully.duckbusters.digibooky.domain.person.Feature;
import com.switchfully.duckbusters.digibooky.domain.person.Person;
import com.switchfully.duckbusters.digibooky.domain.person.Role;
import com.switchfully.duckbusters.digibooky.domain.repository.PersonRepository;

import org.springframework.stereotype.Component;

@Component
public class PersonService {

    private final PersonRepository personRepo;
    private final PersonMapper personMapper;

    private final ValidationService validationService;
    public PersonService(PersonRepository personRepo, PersonMapper personMapper, ValidationService validationService) {
        this.personRepo = personRepo;
        this.personMapper = personMapper;
        this.validationService = validationService;
    }


    public void addPersonToRepo(CreatePersonDTO freshPerson) {
        validateFreshPerson(freshPerson);
        personRepo.addPerson(personMapper.createNewPerson(freshPerson));
    }

    public void validateFreshPerson(CreatePersonDTO freshPerson) {
        validateEmail(freshPerson.geteMail());
        validateLastName(freshPerson.getLastName());
        validateCity(freshPerson.getCity());
        validateInss(freshPerson.getInss());
        personRepo.getAllPersons().forEach(person -> validateThatPerson(freshPerson, person));

    }

    private void validateThatPerson(CreatePersonDTO freshPerson, Person existing) {
        if (freshPerson.geteMail().equals(existing.geteMail()))
            throw new IllegalArgumentException("E mail is not unique!");
        if (freshPerson.getInss().equals(existing.getInss())) throw new IllegalArgumentException("inss is not unique!");
    }

    private void validateEmail(String eMail) {

        if (eMail == null || !eMail.matches("^[A-z0-9]@[A-z0-9]\\.[A-z0-9]$"))
            throw new IllegalArgumentException("E mail does not conform to format!");

    }

    private void validateInss(String inss) {
        if (inss == null) throw new IllegalArgumentException("inss can not be empty!");
    }

    private void validateLastName(String lastName) {
        if (lastName == null) throw new IllegalArgumentException("last name can not be empty!");
    }

    private void validateCity(String city) {
        if (city == null) throw new IllegalArgumentException("city can not be empty!");
    }


    public void registerLibrarian(String adminId, CreatePersonDTO newPerson) {
        validationService.validateAuthorization(adminId, Feature.ADD_LIBRARIAN);
        validateFreshPerson(newPerson);
        Person librarian = personMapper.createNewPerson(newPerson);
        librarian.setRole(Role.LIBRARIAN);
        personRepo.addPerson(librarian);
    }

}
