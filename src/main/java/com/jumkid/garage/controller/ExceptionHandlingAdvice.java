package com.jumkid.garage.controller;

import com.jumkid.garage.exception.GarageProfileNotFoundException;
import com.jumkid.share.controller.response.CustomErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Calendar;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlingAdvice {

    @ExceptionHandler({GarageProfileNotFoundException.class})
    @ResponseStatus(NOT_FOUND)
    CustomErrorResponse handleNotFoundException(GarageProfileNotFoundException ex) {
        return new CustomErrorResponse(Calendar.getInstance().getTime(), ex.getMessage());
    }
}
