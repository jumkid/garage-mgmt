package com.jumkid.garage.exception;

public class GarageProfileDuplicateDisplayNameException extends Exception{

    private static final String ERROR = "Display name is already taken";

    public GarageProfileDuplicateDisplayNameException() {
        super(ERROR);
    }

    public GarageProfileDuplicateDisplayNameException(String displayName) {
        super(String.format("Display name \"%s\" is already taken", displayName));
    }
}
