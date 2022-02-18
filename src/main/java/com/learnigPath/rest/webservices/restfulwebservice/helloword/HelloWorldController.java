package com.learnigPath.rest.webservices.restfulwebservice.helloword;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping(path = "/hello-world" )
    public String getHelloWord()
    {
        return "Jai Sita Ram Ji";
    }

    @GetMapping(path = "/hello-world-bean" )
    public HelloWorldBean getHelloWordBean()
    {
        return new HelloWorldBean("Jai Sita Ram Ji");
    }

    @GetMapping(path = "/hello-world-bean/pathvariable/{name}" )
    public HelloWorldBean getHelloWordBean(@PathVariable String name)
    {
        return new HelloWorldBean(String.format("Jai Shri Ram Chandra  ji ki %s" ,name));
    }

    @GetMapping(path = "/hello-world-internalization" )
    public String getHelloWordInterlization(@RequestHeader(name = "Accept-Language",required = false) Locale locale)

    {
        return messageSource.getMessage( "message.hello.world",null, locale);
        //return messageSource.getMessage( "message.hello.world",null, LocaleContextHolder.getLocale());
    }
}
