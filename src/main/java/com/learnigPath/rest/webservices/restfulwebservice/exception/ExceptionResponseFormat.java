package com.learnigPath.rest.webservices.restfulwebservice.exception;

import java.util.Date;

public class ExceptionResponseFormat {

    private Date timestamp;
    private String details;
    private String exMessage;

    public ExceptionResponseFormat(Date timestamp, String details, String exMessage) {
        this.timestamp = timestamp;
        this.details = details;
        this.exMessage = exMessage;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getDetails() {
        return details;
    }

    public String getExMessage() {
        return exMessage;
    }
}
