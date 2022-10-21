package com.switchfully.duckbusters.digibooky.domain;

public class Address {

    private String street;
    private String houseNumber;
    private String postCode;
    private String city;

    public Address(String street, String houseNumber, String postCode, String city) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.postCode = postCode;
        this.city = city;
    }
}
