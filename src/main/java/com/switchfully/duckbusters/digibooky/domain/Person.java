package com.switchfully.duckbusters.digibooky.domain;

import java.util.UUID;

public class Person {

    private String inss;
    private final String id;
    private String firstName;
    private String lastName;
    private String eMail;
    private Address adress;

    public Person(String inss, String firstName, String lastName, String eMail, Address adress) {
        this.id = UUID.randomUUID().toString();
        this.inss = inss;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.adress = adress;
    }

    public String getId() {
        return id;
    }

    public String getInss() {
        return inss;
    }

    public String geteMail() {
        return eMail;
    }
}
