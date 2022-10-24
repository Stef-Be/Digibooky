package com.switchfully.duckbusters.digibooky.api.dto;

public class CreatePersonDTO {

    private String inss;
    private String eMail;
    private String firstName;
    private String lastName;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;

    public CreatePersonDTO(String inss, String eMail, String firstName, String lastName, String street, String houseNumber, String postalCode, String city) {
        this.inss = inss;
        this.eMail = eMail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.houseNumber = houseNumber;
        this.postalCode = postalCode;
        this.city = city;
    }


    public String getInss() {
        return inss;
    }

    public String geteMail() {
        return eMail;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }
}
