package com.learnigPath.rest.webservices.restfulwebservice.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserVersionController {

    /// uRI based type versioning
    @GetMapping("/v1/person")
    public PersonV1 getPersonV1()
    {
        return  new PersonV1("Ambar Gupta");
    }

    @GetMapping("/v2/person")
    public PersonV2 getPersonV2()
    {
        return  new PersonV2( new Name("Ambar","gupta"));
    }

    /// request param type version ing

    @GetMapping(value = "person/param",params = "version1")
    public PersonV1 getPersonRequestParamV1()
    {
        return  new PersonV1("Ambar Gupta");
    }

    @GetMapping(value = "person/param",params = "version2")
    public PersonV2 getPersonRequestParamV2()
    {
        return  new PersonV2( new Name("Ambar","gupta"));
    }

    // HeaderType versioning
    @GetMapping(value = "/person/header",headers="API-Version=1")
    public PersonV1 getPersonRequestHeaderV1()
    {
        return  new PersonV1("Ambar Gupta");
    }

    @GetMapping(value = "/person/header",headers="API-Version=2")
    public PersonV2 getPersonRequestHeaderV2()
    {
        return  new PersonV2( new Name("Ambar","gupta"));
    }


    // MimType versioning
    @GetMapping(value = "/person/producer",produces="application/vnd.company.app-v1+json")
    public PersonV1 getPersonRequestProducerV1()
    {
        return  new PersonV1("Ambar Gupta");
    }

    @GetMapping(value = "/person/producer",produces="application/vnd.company.app-v2+json")
    public PersonV2 getPersonRequestProducerV2()
    {
        return  new PersonV2( new Name("Ambar","gupta"));
    }

}
