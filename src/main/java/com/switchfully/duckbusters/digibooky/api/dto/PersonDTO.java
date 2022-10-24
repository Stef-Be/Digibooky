package com.switchfully.duckbusters.digibooky.api.dto;

import com.switchfully.duckbusters.digibooky.domain.person.Address;
import com.switchfully.duckbusters.digibooky.domain.person.Role;

public class PersonDTO {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String eMail;
    private final Address address;
    private final Role role;

    public PersonDTO(String id, String firstName, String lastName, String eMail, Address address, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.address = address;
        this.role = role;
    }

    public String geteMail() {
        return eMail;
    }

    public Address getAddress() {
        return address;
    }

    public Role getRole() {
        return role;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
