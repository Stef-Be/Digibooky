package com.switchfully.duckbusters.digibooky.domain.repository;

import com.switchfully.duckbusters.digibooky.domain.Person;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class PersonRepository {

    private final Map<String, Person> personMap = new HashMap<>();


    public void addPerson(Person toBeAdded){
        personMap.put(toBeAdded.getId(),toBeAdded);
    }

    public Collection<Person> getAllPersons(){
        return personMap.values();
    }
}
