package com.jumkid.garage.controller;

import com.jumkid.garage.exception.GarageProfileDuplicateDisplayNameException;
import com.jumkid.garage.exception.GarageProfileNotFoundException;
import com.jumkid.share.controller.response.CustomErrorResponse;
import com.jumkid.share.security.exception.UserProfileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlingAdvice {

    @ExceptionHandler({UserProfileNotFoundException.class, AccessDeniedException.class})
    @ResponseStatus(FORBIDDEN)
    public CustomErrorResponse handleUserProfileNotFound(RuntimeException ex) {
        return new CustomErrorResponse(getCurrentTime(), ex.getMessage());
    }

    @ExceptionHandler({GarageProfileDuplicateDisplayNameException.class})
    @ResponseStatus(CONFLICT)
    CustomErrorResponse handleNotFoundException(GarageProfileDuplicateDisplayNameException ex) {
        return new CustomErrorResponse(getCurrentTime(), ex.getMessage());
    }

    @ExceptionHandler({GarageProfileNotFoundException.class})
    @ResponseStatus(NO_CONTENT)
    CustomErrorResponse handleNotFoundException(GarageProfileNotFoundException ex) {
        return new CustomErrorResponse(getCurrentTime(), ex.getMessage());
    }

    @ExceptionHandler({NoResourceFoundException.class, HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(NOT_FOUND)
    public CustomErrorResponse handle404(Exception ex) {
        return CustomErrorResponse.builder()
                .timestamp(getCurrentTime())
                .message(ex.getLocalizedMessage())
                .build();
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(BAD_REQUEST)
    public CustomErrorResponse handleBadRequest(Exception ex) {
        return CustomErrorResponse.builder()
                .timestamp(getCurrentTime())
                .message("Request is invalid")
                .build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(BAD_REQUEST)
    public CustomErrorResponse handleBadRequest(MethodArgumentNotValidException ex) {

        List<String> fields = new ArrayList<>();
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            FieldError fieldError = (FieldError)error;
            fields.add(fieldError.getField());
            details.add(fieldError.getDefaultMessage());
        }

        return CustomErrorResponse.builder()
                .timestamp(getCurrentTime())
                .message("Request validation error")
                .property(fields)
                .details(details)
                .build();
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public CustomErrorResponse handle(Exception ex) {
        return CustomErrorResponse.builder()
                .timestamp(getCurrentTime())
                .message("Oops! Backend system failed to process the request. Please contact system admin")
                .build();
    }

    private Date getCurrentTime() {
        return Calendar.getInstance().getTime();
    }
}
