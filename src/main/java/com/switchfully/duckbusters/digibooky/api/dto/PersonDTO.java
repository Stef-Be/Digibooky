package com.switchfully.duckbusters.digibooky.api.dto;

public class PersonDTO {
    private final String id;
    private final String firstName;
    private final String lastName;

    public PersonDTO(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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
