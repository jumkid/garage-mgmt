package com.jumkid.garage.controller;

import com.jumkid.garage.exception.GarageProfileDuplicateDisplayNameException;
import com.jumkid.garage.exception.GarageProfileNotFoundException;
import com.jumkid.share.controller.response.CustomErrorResponse;
import com.jumkid.share.security.exception.UserProfileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Calendar;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlingAdvice {

    @ExceptionHandler({UserProfileNotFoundException.class, AccessDeniedException.class})
    @ResponseStatus(FORBIDDEN)
    public CustomErrorResponse handleUserProfileNotFound(RuntimeException ex) {
        return new CustomErrorResponse(Calendar.getInstance().getTime(), ex.getMessage());
    }

    @ExceptionHandler({GarageProfileDuplicateDisplayNameException.class})
    @ResponseStatus(CONFLICT)
    CustomErrorResponse handleNotFoundException(GarageProfileDuplicateDisplayNameException ex) {
        return new CustomErrorResponse(Calendar.getInstance().getTime(), ex.getMessage());
    }

    @ExceptionHandler({GarageProfileNotFoundException.class})
    @ResponseStatus(NO_CONTENT)
    CustomErrorResponse handleNotFoundException(GarageProfileNotFoundException ex) {
        return new CustomErrorResponse(Calendar.getInstance().getTime(), ex.getMessage());
    }

    @ExceptionHandler({NoResourceFoundException.class, HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(NOT_FOUND)
    public CustomErrorResponse handle404(Exception ex) {
        return CustomErrorResponse.builder()
                .timestamp(Calendar.getInstance().getTime())
                .message(ex.getLocalizedMessage())
                .build();
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public CustomErrorResponse handle(Exception ex) {
        return CustomErrorResponse.builder()
                .timestamp(Calendar.getInstance().getTime())
                .message("Oops! Backend system failed to process the request. Please contact system admin")
                .build();
    }
}
