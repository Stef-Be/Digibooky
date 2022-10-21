package com.switchfully.duckbusters.digibooky.domain.repository;

import com.switchfully.duckbusters.digibooky.domain.Address;
import com.switchfully.duckbusters.digibooky.domain.Person;
import com.switchfully.duckbusters.digibooky.domain.Role;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class PersonRepository {

    private final Map<String, Person> personMap;

    public PersonRepository() {
        this.personMap = new HashMap<>();
        addPersonsFromRepository();
    }

    private void addPersonsFromRepository() {
        Person admin = new Person("9999999999999","admin","admin","admin@digibooky.com",new Address("adminstreet","18","1842","brussels"));
        admin.setRole(Role.ADMIN);
        Person librarian = new Person("8888888888888","librarian","librarian","alibrarian@digibooky.com",new Address("librarianstreet","8","1842","brussels"));
        librarian.setRole(Role.LIBRARIAN);
        personMap.put(admin.getId(), admin);
        personMap.put(librarian.getId(), librarian);
    }


    public void addPerson(Person toBeAdded){
        personMap.put(toBeAdded.getId(),toBeAdded);
    }

    public Collection<Person> getAllPersons(){
        return personMap.values();
    }

   public Person getPersonById(String id){
        return personMap.get(id);
   }
}
