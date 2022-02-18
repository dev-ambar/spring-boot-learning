package com.learnigPath.rest.webservices.restfulwebservice.filter;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilterController {

    @GetMapping("/getSomeBean")
    public MappingJacksonValue getSomeBean()
    {
        SomeBean someBean = new SomeBean("value1","value2","value3") ;
        return getMappingJacksonValue(someBean,"field1", "field3");
    }

    @GetMapping("/getSomeBean-list")
    public MappingJacksonValue getSomeBeanList()
    {
        List<SomeBean> someBeans = Arrays.asList(new SomeBean("value1", "value2", "value3"),
                new SomeBean("value4", "value5", "value6"));

        return getMappingJacksonValue(someBeans,"field2", "field3");
    }

    private MappingJacksonValue getMappingJacksonValue(Object object,String ... str) {

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(str);
        FilterProvider filters =  new SimpleFilterProvider().addFilter("someBeanFilter", filter);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(object);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }
}
