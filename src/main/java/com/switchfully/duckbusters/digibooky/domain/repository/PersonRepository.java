package com.switchfully.duckbusters.digibooky.domain.repository;

import com.switchfully.duckbusters.digibooky.domain.person.Address;
import com.switchfully.duckbusters.digibooky.domain.person.Person;
import com.switchfully.duckbusters.digibooky.domain.person.Role;
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
        Person admin = new Person("9999999999999","admin","admin","admin@digibooky.com",new Address("adminstreet","18","1842","brussels"), "password");
        admin.setRole(Role.ADMIN);
        System.out.println(admin.getId());
        Person librarian = new Person("8888888888888","librarian","librarian","alibrarian@digibooky.com",new Address("librarianstreet","8","1842","brussels"), "password");
        librarian.setRole(Role.LIBRARIAN);
        Person member = new Person("7777777777777", "member", "member", "amember@digibooky.com", new Address("memberstreet", "7", "1742","brussels"), "password");
        personMap.put(admin.getId(), admin);
        personMap.put(librarian.getId(), librarian);
        personMap.put(member.getId(), member);
    }


    public void addPerson(Person toBeAdded){
        personMap.put(toBeAdded.getId(),toBeAdded);
    }

    public Collection<Person> getAllPersons(){
        return personMap.values();
    }

    public boolean doesPersonExist(String id){
        return personMap.containsKey(id);
    }

   public Person getPersonById(String id){
        return personMap.get(id);
   }

    public Person getPersonbyEmail(String eMail) {
        return personMap.values().stream().filter(person -> person.geteMail().equals(eMail)).findFirst().orElseThrow();
    }
}
