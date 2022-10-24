package com.switchfully.duckbusters.digibooky.api.mapper;

import com.switchfully.duckbusters.digibooky.api.dto.CreatePersonDTO;
import com.switchfully.duckbusters.digibooky.domain.person.Address;
import com.switchfully.duckbusters.digibooky.domain.person.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {


    public Person createNewPerson(CreatePersonDTO freshPerson) {

        return new Person(freshPerson.getInss(),
                freshPerson.getFirstName(),
                freshPerson.getLastName(),
                freshPerson.geteMail(),
                createAddress(freshPerson));
    }


    private Address createAddress(CreatePersonDTO freshPerson){
        return new Address(freshPerson.getStreet(),
                freshPerson.getHouseNumber(),
                freshPerson.getPostalCode(),
                freshPerson.getCity());
    }


}