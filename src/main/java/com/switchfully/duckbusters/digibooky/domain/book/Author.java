package com.switchfully.duckbusters.digibooky.domain.book;

public class Author {
    private  String authorFirstname;
    private  String authorLastname;

    public Author(String firstName, String lastName) {
        this.authorFirstname = firstName;
        this.authorLastname = lastName;
    }

    public String getAuthorFirstname() {
        return authorFirstname;
    }

    public String getAuthorLastname() {
        return authorLastname;
    }

    public void setAuthorFirstname(String authorFirstname) {
        this.authorFirstname = authorFirstname;
    }

    public void setAuthorLastname(String authorLastname) {
        this.authorLastname = authorLastname;
    }

    public String getFullName() {
        return authorFirstname+" "+getAuthorLastname();
    }
}
