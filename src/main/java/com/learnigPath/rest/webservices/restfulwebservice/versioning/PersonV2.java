package com.learnigPath.rest.webservices.restfulwebservice.versioning;

public class PersonV2 {

    private Name Name;

    public PersonV2() {
    }

    public PersonV2(Name name) {
        Name = name;
    }

    public com.learnigPath.rest.webservices.restfulwebservice.versioning.Name getName() {
        return Name;
    }

    public void setName(com.learnigPath.rest.webservices.restfulwebservice.versioning.Name name) {
        Name = name;
    }
}
