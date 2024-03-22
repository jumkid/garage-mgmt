package com.jumkid.garage.exception;

public class GarageProfileNotFoundException extends Exception {

    private static final String ERROR = "Can not find garage by Id: ";

    public GarageProfileNotFoundException() { super(ERROR); }

    public GarageProfileNotFoundException(Long id) { super(ERROR + id); }
}
