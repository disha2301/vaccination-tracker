package com.gevernova.petvaccinationtracker.exceptionhandler;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
