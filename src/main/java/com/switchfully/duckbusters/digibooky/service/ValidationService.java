package com.switchfully.duckbusters.digibooky.service;

import com.switchfully.duckbusters.digibooky.domain.UsernamePassword;
import com.switchfully.duckbusters.digibooky.domain.person.Feature;
import com.switchfully.duckbusters.digibooky.domain.person.Person;
import com.switchfully.duckbusters.digibooky.domain.repository.PersonRepository;
import com.switchfully.duckbusters.digibooky.service.exceptions.UnauthorizatedException;
import com.switchfully.duckbusters.digibooky.service.exceptions.UnknownPersonException;
import com.switchfully.duckbusters.digibooky.service.exceptions.WrongPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class ValidationService {
    private final Logger logger = LoggerFactory.getLogger(ValidationService.class);

    private final PersonRepository personRepository;

    public ValidationService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void validateAuthorization(String authorization, Feature feature) {
        UsernamePassword usernamePassword = getUsernamePassword(authorization);
        Person person = personRepository.getPersonbyEmail(usernamePassword.getUsername());

        if (person == null) {
            logger.error("Unknown user with the username " + usernamePassword.getUsername());
            throw new UnknownPersonException();
        }
        if(!person.doesPasswordMatch(usernamePassword.getPassword())) {
            logger.error("Password does not match for user " + usernamePassword.getUsername());
            throw new WrongPasswordException();
        }
        if (!person.canHaveAccessTo(feature)) {
            logger.error("User " + person.getFullName() + " does not have access to " + feature);
            throw new UnauthorizatedException();
        }

    }
    private UsernamePassword getUsernamePassword(String authorization) {
        String decodedUsernameAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String username = decodedUsernameAndPassword.substring(0, decodedUsernameAndPassword.indexOf(":"));
        String password = decodedUsernameAndPassword.substring(decodedUsernameAndPassword.indexOf(":") + 1);
        return new UsernamePassword(username, password);
    }
}
