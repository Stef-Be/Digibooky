package com.switchfully.duckbusters.digibooky.service;

import com.switchfully.duckbusters.digibooky.domain.person.Feature;
import com.switchfully.duckbusters.digibooky.domain.person.Person;
import com.switchfully.duckbusters.digibooky.domain.repository.PersonRepository;
import com.switchfully.duckbusters.digibooky.service.exceptions.UnauthorizatedException;
import com.switchfully.duckbusters.digibooky.service.exceptions.UnknownPersonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {
    private final Logger logger = LoggerFactory.getLogger(ValidationService.class);

    private PersonRepository personRepository;

    public ValidationService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void validateAuthorization(String id, Feature feature) {
        Person person = personRepository.getPersonById(id);
        if (person == null) {
            logger.error("Unknown user with the id " + id);
            throw new UnknownPersonException();
        }

        if (!person.canHaveAccessTo(feature)) {
            logger.error("User " + person.getFullName() + " does not have access to " + feature);
            throw new UnauthorizatedException();
        }

    }
}
