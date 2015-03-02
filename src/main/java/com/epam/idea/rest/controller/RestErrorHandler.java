package com.epam.idea.rest.controller;

import com.epam.idea.core.service.exception.UserNotFoundException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestErrorHandler {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public VndErrors userNotFoundExceptionHandler(final UserNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public VndErrors validationErrorHandler(final MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<VndErrors.VndError> vndErrorList = result.getFieldErrors()
                .stream()
                .map(error -> new VndErrors.VndError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new VndErrors(vndErrorList);
    }
}
