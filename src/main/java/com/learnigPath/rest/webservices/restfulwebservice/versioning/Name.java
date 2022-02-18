package com.learnigPath.rest.webservices.restfulwebservice.versioning;

public class Name {
    private String FirstName;
    private String LastName;

    public Name(String firstName, String lastName) {
        FirstName = firstName;
        LastName = lastName;
    }

    public Name() {
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }
}
