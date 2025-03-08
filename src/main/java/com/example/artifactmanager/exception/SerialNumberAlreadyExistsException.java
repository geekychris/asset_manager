package com.example.artifactmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SerialNumberAlreadyExistsException extends RuntimeException {
    
    public SerialNumberAlreadyExistsException(String serialNumber) {
        super(String.format("Artifact with serial number '%s' already exists", serialNumber));
    }
}

