package org.ubp.ent.backend.core.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.ubp.ent.backend.core.exceptions.database.AlreadyDefinedInOnNonPersistedEntity;
import org.ubp.ent.backend.core.exceptions.database.CourseAlreadyAssignedToAnotherWish;
import org.ubp.ent.backend.core.exceptions.database.ModelConstraintViolationException;
import org.ubp.ent.backend.core.exceptions.database.notfound.ResourceNotFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Anthony on 29/11/2015.
 */
@ControllerAdvice
@SuppressWarnings("unused")
public class ErrorHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public void exception(HttpServletRequest req, Throwable e) {
        if (log.isErrorEnabled()) {
            log.error("An unhandled Throwable was catch by the ControllerAdvice : '" + getClass().getName() + "', this needs a fix.", e);
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void httpMessageNotReadableException(HttpServletRequest req, HttpMessageNotReadableException e) {
        if (log.isErrorEnabled()) {
            log.error("Http request has failed.", e);
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public void illegalArgument(HttpServletRequest req, IllegalArgumentException e) {
        if (log.isInfoEnabled()) {
            log.info("An IllegalArgument has been catch by the ControllerAdvice : '" + getClass().getName(), e);
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public void resourceNotFoundException(HttpServletRequest req, ResourceNotFoundException e) {
        if (log.isInfoEnabled()) {
            log.info("Object requested not found.", e);
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AlreadyDefinedInOnNonPersistedEntity.class)
    public void alreadyDefinedInOnNonPersistedClass(HttpServletRequest req, AlreadyDefinedInOnNonPersistedEntity e) {
        if (log.isInfoEnabled()) {
            log.info("Cannot create an object for the first time is id is already defined.", e);
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CourseAlreadyAssignedToAnotherWish.class)
    public void courseAlreadyAssignedToAnotherWish(HttpServletRequest req, CourseAlreadyAssignedToAnotherWish e) {
        if (log.isInfoEnabled()) {
            log.info("An illegal action has been made on wishes.", e);
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ModelConstraintViolationException.class)
    public void modelConstraintViolationException(HttpServletRequest req, ModelConstraintViolationException e) {
        if (log.isInfoEnabled()) {
            log.info("A model constraint failed.", e);
        }
    }

}
